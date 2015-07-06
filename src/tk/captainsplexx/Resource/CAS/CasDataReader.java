package tk.captainsplexx.Resource.CAS;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Game.Game;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Maths.LZ4;
import tk.captainsplexx.Maths.Patcher;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;
import tk.captainsplexx.Resource.ResourceHandler;

public class CasDataReader { //casPath == folderPath
	public static byte[] readCas(String baseSHA1, String deltaSHA1, String SHA1, Integer patchType){
		return readCas(baseSHA1, deltaSHA1, SHA1, patchType, null);
	}
	
	public static byte[] readCas(String baseSHA1, String deltaSHA1, String SHA1, Integer patchType, ResourceHandler resHandler){
		ResourceHandler rs = null;
		if (resHandler==null){
			Game game = Main.getGame();
			rs = game.getResourceHandler();
		}else{
			rs = resHandler;
		}
		if (patchType == 2){
			//Patched using delta
			byte[] base = CasDataReader.readCas(baseSHA1, Main.gamePath+"/Data", rs.getCasCatManager().getEntries(), false);
			byte[] delta = CasDataReader.readCas(deltaSHA1, Main.gamePath+"/Update/Patch/Data", rs.getPatchedCasCatManager().getEntries(), true);
			
			byte[] data = Patcher.getPatchedData(base, delta);
			if (data != null){
				return data;
			}else{
				System.err.println("null data... in CasDataReader (patched data useing delta)");
				return null;
			}
		}else if(patchType == 1){
			//Patched using data from update cas
			byte[] data = CasDataReader.readCas(SHA1, Main.gamePath+"/Update/Patch/Data", rs.getPatchedCasCatManager().getEntries(), false);
			if (data != null){
				return data;
			}else{
				System.err.println("null data... in CasDataReader (patched data replacement)");
				return null;
			}
		}else{
			//Unpatched
			byte[] data = CasDataReader.readCas(SHA1, Main.gamePath+"/Data", rs.getCasCatManager().getEntries(), false);
			if (data != null){
				return data;
			}else{
				System.err.println("null data... in CasDataReader (unpatched data)");
				return null;
			}
		}
	}
	
	
	public static byte[] readCas(String SHA1, String casFolderPath, ArrayList<CasCatEntry> casCatEntries, boolean hasNoBlockLogic){
		try{
			SHA1 = SHA1.replaceAll("\\s","");
			for (CasCatEntry e : casCatEntries){
				if (!e.getSHA1().equals(SHA1.toLowerCase())){continue;}
				
				String casFile = "";
				if (e.getCasFile()<10){casFile+="0";}
				casFile += e.getCasFile() + "";
				String casFilePath = casFolderPath;
				if (!casFilePath.endsWith("/")){casFilePath+="/";}
				casFilePath += "cas_"+ casFile + ".cas";
				
				System.out.println("Reading CAS: "+ casFilePath+" for SHA1: "+SHA1);
				if (!hasNoBlockLogic){//hasBlockLogic :)
					if (e.getProcSize()>=0x010000){
						System.out.println("WARNING: Decompress each block and glue the decompressed parts together to obtain the file.");
					}
					return convertToRAWData(FileHandler.readFile(casFilePath, e.getOffset(), e.getProcSize()));
				}else{
					return FileHandler.readFile(casFilePath, e.getOffset(), e.getProcSize());
				}
			}
			System.err.println("Invalid SHA given :(");
			return null;
		}catch (NullPointerException e){
			return null;
		}
	}
	
	static byte[] convertToRAWData(byte[] data){
		FileSeeker seeker = new FileSeeker("CasDataReader");
		ArrayList<Byte> output = new ArrayList<Byte>();
		while(seeker.getOffset()<data.length){
			byte[] decompressedBlock = readBlock(data, seeker);
			if (decompressedBlock != null){
				for (Byte b : decompressedBlock){
					output.add(b);
				}
			}else{
				System.err.println("Could not read from given block!");
				return null;
			}
		}//End of InputStream
		return FileHandler.convertFromList(output);
	}
	
	public static byte[] readBlock(byte[] encodedEntry, FileSeeker seeker){
		//FileHandler.writeFile("output/readBlock", encodedEntry);
		int decompressedSize = FileHandler.readInt(encodedEntry, seeker, ByteOrder.BIG_ENDIAN);
		int compressionType = FileHandler.readShort(encodedEntry, seeker, ByteOrder.BIG_ENDIAN);
		int compressedSize = FileHandler.readShort(encodedEntry, seeker, ByteOrder.BIG_ENDIAN) & 0xFFFF;
		if (compressionType == 0x0970){//COMPRESSED
			//rawData = LZ4.decompress(FileHandler.readByte(encodedEntry, seeker, procSize-seeker.getOffset()));
			byte[] rawData = LZ4.decompress(FileHandler.readByte(encodedEntry, seeker, compressedSize));
			if (rawData.length<decompressedSize){
				System.err.println("Decompressed file size does not match the cas.cat given one. "+rawData.length+" of "+decompressedSize+" Bytes loaded.");
			}
			return rawData;
		}else if (compressionType == 0x0070 || compressionType == 0x0071){//UNCOMPRESSED
			//return FileHandler.readByte(encodedEntry, seeker, procSize-seeker.getOffset());
			return FileHandler.readByte(encodedEntry, seeker, compressedSize);
		}else if (compressionType == 0x0){ // 0x0000 - emty payload
			//seeker.setOffset(seeker.getOffset()-2); //NULL compressionSize
			System.err.println("CasDataReader needs some help. 0x0000 emty payload"); //TODO
			//return FileHandler.readByte(encodedEntry, seeker, compressedSize);
			return null;
		}else{
			System.err.println(FileHandler.bytesToHex(FileHandler.toBytes(compressionType, ByteOrder.LITTLE_ENDIAN))+" type was not defined in CasDataReader.");
			return null;
		}
	}
}
