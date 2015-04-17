package tk.captainsplexx.CAS;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Maths.LZ4;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;

public class CasDataReader { //casPath == folderPath
	public static byte[] readCas(String SHA1, String casFolderPath, ArrayList<CasCatEntry> casCatEntries){
		SHA1 = SHA1.replaceAll("\\s","");
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
			
			System.out.println("Reading CAS: "+ casFilePath+" for SHA1: "+SHA1);
			
			return convertToRAWData(FileHandler.readFile(casFilePath, e.getOffset(), e.getProcSize()), e.getProcSize());
			//DONE
		}
		return null;
	}
	
	public static byte[] convertToRAWData(byte[] encodedEntry, int procSize){ //TAKEs entries with header of type and original size.
		FileSeeker seeker = new FileSeeker();
		byte[] rawData = null;
		int decompressedSize = FileHandler.readInt(encodedEntry, seeker, ByteOrder.BIG_ENDIAN);
		int compressionType = FileHandler.readShort(encodedEntry, seeker, ByteOrder.BIG_ENDIAN);
		int compressedSize = FileHandler.readShort(encodedEntry, seeker, ByteOrder.LITTLE_ENDIAN); //TODO maybe LEB128 encoded ??! ------------------------------------------------------
		if (compressionType == 0x0970){//COMPRESSED
			LZ4 lz4Handler = new LZ4();
			rawData = lz4Handler.decompress(FileHandler.readByte(encodedEntry, seeker, procSize-seeker.getOffset()));
			if (rawData.length<decompressedSize){
				System.err.println("Decompressed file size does not match the cas.cat given one. "+rawData.length+" of "+decompressedSize+" Bytes loaded.");
			}
			return rawData;
		}else if (compressionType == 0x0070 || compressionType == 0x0071){//UNCOMPRESSED
			return FileHandler.readByte(encodedEntry, seeker, procSize-seeker.getOffset());
		}else{ // 0x0000 - emty payload
			seeker.setOffset(seeker.getOffset()-2); //NULL compressionSize
			System.err.println("CasDataReader needs some help. 0x0000 emty payload"); //TODO
		}
		return rawData;
	}
}
