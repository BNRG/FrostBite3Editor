package tk.captainsplexx.Toc;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;

public class TocManager {
	public static enum TocFieldType {
		STRING, BOOL, INTEGER, LONG, GUID, SHA1, LIST, RAW
	};
	
	public static enum TocEntryType {
		ORDINARY, UNKNOWN
	};

	public static enum TocFileType {
		XORSig, Sig, None, SbPart
	};
	
	/*ONLY NEEDED IF RAW FILE SHOULD SHOW UP IN EXPLORER. (DEBUG)
	public static ConvertedTocFile getConvertedToc(byte[] toc){
		return TocConverter.convertTocFile(readToc(toc));
	}
	*/

	public static TocFile readToc(byte[] fileArray) {
		FileSeeker seeker = new FileSeeker();
		ArrayList<TocEntry> entries = new ArrayList<TocEntry>();
		byte[] data;
		int header = FileHandler.readInt(fileArray, seeker, ByteOrder.BIG_ENDIAN);
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
		while (seeker.getOffset() < data.length){ //READ ENTRIES
			TocEntry entry = readEntry(data, seeker);
			if (entry != null){
				entries.add(entry);
			}
		}//EOF
		TocFile file = new TocFile(fileType);
		file.getEntries().addAll(entries);
		return file;
	}
	
	public static TocFile readSbPart(byte[] part) { // instead of reading the complete file, only read parts
		FileSeeker seeker = new FileSeeker();
		ArrayList<TocEntry> entries = new ArrayList<TocEntry>();
		while (seeker.getOffset() < part.length){ //READ ENTRIES
			TocEntry entry = readEntry(part, seeker);
			if (entry != null){
				entries.add(entry);
			}
		}//EOF
		TocFile file = new TocFile(TocFileType.SbPart);
		file.getEntries().addAll(entries);
		return file;
	}
	
	static TocEntry readEntry(byte[] data, FileSeeker seeker){
		//System.out.println("Reading ENTRY at "+seeker.getOffset());
		TocEntry entry;
		int entryType = (FileHandler.readByte(data, seeker) & 0xFF); //byte needs to be casted to unsigned.
		if (entryType == 0x82){
			entry = new TocEntry(TocEntryType.ORDINARY);
			int entrySize = FileHandler.readLEB128(data, seeker); 
			int entryOffset = seeker.getOffset();
			
			while (seeker.getOffset() < entryOffset+entrySize){ //READ FIELDS
				TocField field = readField(data, seeker);
				if (field != null){
					entry.getFields().add(field);
				}
			}
		}else{
			entry = new TocEntry(TocEntryType.UNKNOWN);
			System.err.println("Unknown Type in TocManger detected: "+entryType+" at "+seeker.getOffset());
			//TODO
		}
		return entry;
	}
	
	static TocField readField(byte[] data, FileSeeker seeker){
		TocField field = null;
		int fieldType = (FileHandler.readByte(data, seeker) & 0xFF); //byte needs to be casted to unsigned.
		String name = "";
		if (fieldType != 0x00){
			name = FileHandler.readString(data, seeker);
		}
		if (fieldType == 0x01){ //#list type, containing ENTRIES (MULTIPLE ONE) 
			ArrayList<TocEntry> list = new ArrayList<TocEntry>();
			int listSize = FileHandler.readLEB128(data, seeker); 
			int listOffset = seeker.getOffset();
			while (seeker.getOffset() < listOffset+listSize){ 
				list.add(readEntry(data, seeker));
			}
			if (list.isEmpty()){list = null;}
			field = new TocField(list, TocFieldType.LIST, name);
		}else if (fieldType == 0x0F){ //ID 16 stored as HEXSTRING
			field = new TocField(FileHandler.bytesToHex(FileHandler.readByte(data, seeker, 16)), TocFieldType.GUID, name);
		}else if (fieldType == 0x09){ //LONG
			field = new TocField(FileHandler.readLong(data, seeker), TocFieldType.LONG, name);

		}else if (fieldType == 0x08){ //INTEGER
			field = new TocField(FileHandler.readInt(data, seeker), TocFieldType.INTEGER, name);
		}else if (fieldType == 0x06){ //BOOL
			boolean bool = false;
			if (FileHandler.readByte(data, seeker) == 0x01){
				bool = true;
			}
			field = new TocField(bool, TocFieldType.BOOL, name);
		}else if ((fieldType == 0x02) || (fieldType == 0x13)){ //sbTocFile -> RES -> ENTRY: idata 0x13 //TODO What is the diffrence ?
			int length = FileHandler.readLEB128(data, seeker);
			field = new TocField(FileHandler.readByte(data, seeker, length), TocFieldType.RAW, name);
		}else if (fieldType == 0x10){ //SHA1 stored as HEXSTRING
			field = new TocField(FileHandler.bytesToHex(FileHandler.readByte(data, seeker, 20)), TocFieldType.SHA1, name);
		}else if (fieldType == 0x07){ // #string, length (including trailing null) prefixed as 7bit int
			FileHandler.readLEB128(data, seeker); //SKIP LENGTH
			field = new TocField(FileHandler.readString(data, seeker), TocFieldType.STRING, name);
		}else if (fieldType == 0x00){
			return null;
		}else{
			System.err.println("Unknown FieldType: "+fieldType+" in TocManager detected at "+seeker.getOffset());
		}
		return field;
	}
}
