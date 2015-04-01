package tk.captainsplexx.CAS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Maths.LZ4;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;

public class CasDataReader { //casPath == folderPath
	public static byte[] readCas(String SHA1, String casFolderPath, ArrayList<CasCatEntry> casCatEntries){
		for (CasCatEntry e : casCatEntries){
			//SKIP IF NOT SHA1
			if (!e.getSHA1().equals(SHA1.toLowerCase())){continue;}
			
			//CONVERT casPath and number TO casFilePath!
			String casFile = "";
			if (e.getCasFile()<10){casFile+="0";}
			int nr = e.getCasFile();
			casFile += nr + "";
			String casFilePath = casFolderPath;
			if (!casFilePath.endsWith("/")){casFilePath+="/";}
			casFilePath += "cas_"+ casFile + ".cas";
			
			System.out.println("READING CAS: "+ casFilePath);
			
			return convertToRAWData(FileHandler.readFile(casFilePath, e.getOffset(), e.getProcSize()), e.getProcSize());
			//DONE
		}
		return null;
	}
	
	public static byte[] convertToRAWData(byte[] encodedEntry, int procSize){ //TAKEs entries with header of type and original size.
		FileSeeker seeker = new FileSeeker();
		byte[] rawData = null;
		int decompressedSize = readInt(encodedEntry, seeker, ByteOrder.LITTLE_ENDIAN);
		int compressionType = readShort(encodedEntry, seeker, ByteOrder.BIG_ENDIAN);
		int compressedSize = readShort(encodedEntry, seeker, ByteOrder.LITTLE_ENDIAN); //TODO maybe LEB128 encoded ??! ------------------------------------------------------
		if (compressionType == 0x0970){//COMPRESSED
			LZ4 lz4Handler = new LZ4();
			rawData = lz4Handler.decompress(readByte(encodedEntry, seeker, procSize-seeker.getOffset()));
			if (rawData.length<decompressedSize){
				System.err.println("Decompressed file size does not match the CatCat given one :/");
			}
			return rawData;
		}else if (compressionType == 0x0070 || compressionType == 0x0071){//UNCOMPRESSED
			return readByte(encodedEntry, seeker, procSize-seeker.getOffset());
		}else{ // 0x0000 - emty payload
			seeker.setOffset(seeker.getOffset()-2); //NULL compressionSize
			System.err.println("CasDataReader needs some help. 0x0000 emty payload"); //TODO
		}
		return rawData;
	}

	static int readInt(byte[] data, FileSeeker seeker, ByteOrder order) {
		return ByteBuffer.wrap(readByte(data, seeker, 4))
				.order(order).getInt();
	}
	
	static short readShort(byte[] data, FileSeeker seeker, ByteOrder order) {
		return ByteBuffer.wrap(readByte(data, seeker, 2))
				.order(order).getShort();
	}
	
	static byte readByte(byte[] input, FileSeeker seeker) {
		byte b = 0x0;
		try {
			b = input[seeker.getOffset()];
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ReadByte out of bounds. " + seeker.getOffset());
		}
		seeker.seek(1);
		return b;
	}
	static byte[] readByte(byte[] input, FileSeeker seeker, int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len; i++) {
			buffer[i] = readByte(input, seeker);
		}
		return buffer;
	}
}
