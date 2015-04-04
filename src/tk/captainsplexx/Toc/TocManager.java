package tk.captainsplexx.Toc;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileSeeker;

public class TocManager {
	public static enum TocFieldType {
		STRING, BOOL, INTEGER, LONG, GUID, SHA1, LIST
	};
	
	public static enum TocEntryType {
		ORDINARY, UNKNOWN
	};

	public static enum TocFileType {
		XORSig, Sig, None, SbPart
	};

	public ArrayList<TocFile> files;
	public ArrayList<TocEntry> entries;
	public FileSeeker seeker;
	public byte[] data;

	public TocManager() {
		files = new ArrayList<TocFile>();
	}

	public TocFile readToc(byte[] fileArray) {
		int header = readInt(fileArray, 0x0, ByteOrder.BIG_ENDIAN);
		TocFileType fileType = null;
		if (header == 0x00D1CE00 || header == 0x00D1CE01) { //#the file is XOR encrypted and has a signature
			fileType = TocFileType.XORSig;
			data = null;
			System.out.println("TODO: XOR Decryption TOC Files!"); //TODO
			/*
			 *  seek(296) #skip the signature
		     *  key=[ord(f.read(1))^123 for i in xrange(260)] #bytes 257 258 259 are not used; XOR the key with 123 right away
		     *  encryptedData=f.read()
		     *  data="".join([chr(key[i%257]^ord(encryptedData[i])) for i in xrange(len(encryptedData))]) #go through the data applying one key byte on one data 
			 */
		} else if (header == 0x00D1CE03) {
			fileType = TocFileType.Sig;
			data = new byte[fileArray.length-556];
			for (int i = 0; i < data.length; i++){
				data[i] = fileArray[i+556]; //seek(556) #skip signature + skip empty key + skip header
			}
		} else { //#the file is not encrypted; no key + no signature
			fileType = TocFileType.None;
			data = fileArray;
		}
		seeker = new FileSeeker();
		entries = new ArrayList<TocEntry>();
		while (seeker.getOffset() < data.length){ //READ ENTRIES
			entries.add(readEntry(data, seeker));
		}//EOF
		TocFile file = new TocFile(fileType);
		file.getEntries().addAll(entries);
		return file;
	}
	
	public TocFile readSbPart(byte[] part) { // instead of reading the complete file, only read parts
		seeker = new FileSeeker();
		entries = new ArrayList<TocEntry>();
		while (seeker.getOffset() < part.length){ //READ ENTRIES
			entries.add(readEntry(part, seeker));
		}//EOF
		TocFile file = new TocFile(TocFileType.SbPart);
		file.getEntries().addAll(entries);
		return file;
	}
	
	public TocEntry readEntry(byte[] data, FileSeeker seeker){
		TocEntry entry;
		int entryType = (readByte(data, seeker) & 0xFF); //byte needs to be casted to unsigned.
		if (entryType == 0x82){
			entry = new TocEntry(TocEntryType.ORDINARY);
			int entrySize = readLEB128(data, seeker); 
			int entryOffset = seeker.getOffset();
			while (seeker.getOffset() < entryOffset+entrySize){ //READ FIELDS
				TocField field = readField(data, seeker);
				if (field != null){
					entry.getFields().add(field);
				}
			}
		}else{
			entry = new TocEntry(TocEntryType.UNKNOWN);
			//TODO
		}
		return entry;
	}
	
	public TocField readField(byte[] data, FileSeeker seeker){
		TocField field = null;
		int fieldType = (readByte(data, seeker) & 0xFF); //byte needs to be casted to unsigned.
		String name = "";
		if (fieldType != 0x00){
			name = readString(data, seeker);
		}
		if (fieldType == 0x01){ //#list type, containing ENTRIES (MULTIPLE ONE) 
			ArrayList<TocEntry> list = new ArrayList<TocEntry>();
			int listSize = readLEB128(data, seeker); 
			int listOffset = seeker.getOffset();
			while (seeker.getOffset() < listOffset+listSize){ 
				list.add(readEntry(data, seeker));
			}
			if (list.isEmpty()){list = null;}
			field = new TocField(list, TocFieldType.LIST, name);
		}else if (fieldType == 0x0F){ //ID 16 stored as HEXSTRING
			field = new TocField(bytesToHex(readByte(data, 16, seeker)), TocFieldType.GUID, name);
		}else if (fieldType == 0x09){ //LONG
			field = new TocField(readLong(data, seeker), TocFieldType.LONG, name);
		}else if (fieldType == 0x08){ //INTEGER
			field = new TocField(readInt(data, seeker), TocFieldType.INTEGER, name);
		}else if (fieldType == 0x06){ //BOOL
			boolean bool = false;
			if (readByte(data, seeker) == 0x01){
				bool = true;
			}
			field = new TocField(bool, TocFieldType.BOOL, name);
		//}else if ((fieldType == 0x02) || (fieldType == 0x13)){
			//read128(f)
			//System.err.println("todo-tocmanager");
		}else if (fieldType == 0x10){ //SHA1 stored as HEXSTRING
			field = new TocField(bytesToHex(readByte(data, 20, seeker)), TocFieldType.SHA1, name);
		}else if (fieldType == 0x07){ // #string, length (including trailing null) prefixed as 7bit int
			readLEB128(data, seeker); //SKIP LENGTH
			field = new TocField(readString(data, seeker), TocFieldType.STRING, name);
		}else if (fieldType == 0x00){
			//RETURN
		}else{
			System.err.println("Unknown FieldType: "+fieldType+" -- TocManager");
		}
		return field;
	}

	// Inputstream Operations
	int readInt(byte[] fileArray, int offset) {
		return ByteBuffer.wrap(readByte(fileArray, offset, 4))
				.order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	int readInt(byte[] fileArray, int offset, ByteOrder order) {
		return ByteBuffer.wrap(readByte(fileArray, offset, 4))
				.order(order).getInt();
	}
	
	long readLong(byte[] fileArray, int offset) {
		return ByteBuffer.wrap(readByte(fileArray, offset, 8))
				.order(ByteOrder.LITTLE_ENDIAN).getLong();
	}

	String readString(byte[] fileArray, FileSeeker seeker) {
		String tmp = "";
		while(true){
			byte[] b = readByte(fileArray, 1, seeker);
			if (b[0] != 0x0) {
				try {
					tmp += new String(b, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					System.err.println("Error - UnsupportedEncodingException in TocManager");
					e.printStackTrace();
				}
			}else{
				break;
			}
		}
		return tmp;
	}

	int readInt(byte[] fileArray, FileSeeker seeker) {
		return ByteBuffer.wrap(readByte(fileArray, 4, seeker))
				.order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	int readInt(byte[] fileArray, FileSeeker seeker, ByteOrder order){
		return ByteBuffer.wrap(readByte(fileArray, 4, seeker))
				.order(order).getInt();
	}
	
	long readLong(byte[] fileArray, FileSeeker seeker){
		return ByteBuffer.wrap(readByte(fileArray, 8, seeker)).order(ByteOrder.LITTLE_ENDIAN).getLong();
	}

	byte[] readMagic(byte[] fileArray) {
		return readByte(fileArray, 0, 4);
	}

	String bytesToHex(byte[] in) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : in) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
	
	int readLEB128(byte[] fileArray, FileSeeker seeker){ //Read the next few bytes as LEB128/7bit encoding and return an integer.
		int result = 0;
		int shift = 0;
		while(true){
			byte b = readByte(fileArray, seeker);
			result |= (b & 0x7f) << shift;
			if ((b & 0x80) == 0){
			   return result;
			}
			shift += 7;
		}
	}

	byte[] readByte(byte[] fileArray, int offset, int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len; i++) {
			try {
				buffer[i] = fileArray[offset + i];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("END OF FILE REACHED!!");
				buffer[i] = 0;
			}
		}
		return buffer;
	}
	
	byte readByte(byte[] fileArray, FileSeeker seeker){
		return readByte(fileArray, 1, seeker)[0];
	}

	byte[] readByte(byte[] fileArray, int len, FileSeeker seeker) {
		byte[] buffer = readByte(fileArray, seeker.offset, len);
		seeker.seek(buffer.length);
		return buffer;
	}
}
