package tk.captainsplexx.Resource.ITEXTURE;

import java.nio.ByteOrder;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;

public class ITextureConverter {
	public static int DXT1HEX = 0x44585431;
	public static int DXT5HEX = 0x44585435;
	public static int ATI2HEX = 0x41544932;
		
	public static byte[] getITextureHeader(byte[] ddsFileBytes, String chunkID){
		//TODO mipmaps/color channel ??
		if (ddsFileBytes!=null){
			FileSeeker seeker = new FileSeeker();
			
			//Type
			seeker.seek(0x54);
			int type = FileHandler.readInt(ddsFileBytes, seeker, ByteOrder.BIG_ENDIAN);			
			byte[] header = FileHandler.readFile("res/itexture/itexture_header.hex");
			if (type==DXT1HEX){
				header[0x0C] = 0x00;
				header[0x0D] = 0x00;
				header[0x0E] = 0x00;
				header[0x0F] = 0x00;
			}else if (type==DXT5HEX){
				header[0x0C] = 0x03;
				header[0x0D] = 0x03;
				header[0x0E] = 0x03;
				header[0x0F] = 0x03;
			}else if (type==ATI2HEX){
				header[0x0C] = 0x13;
				header[0x0D] = 0x13;
				header[0x0E] = 0x13;
				header[0x0F] = 0x13;
			}else{
				System.err.println("Unknown type found! (ITextureConverter.getITextureHeader)");
				return null;
			}
			
			//ChunkGuid
			byte[] chunkIDBytes = FileHandler.hexStringToByteArray(chunkID);
			for (int i=0; i<chunkIDBytes.length;i++){
				header[i+0x1C] = chunkIDBytes[i];
			}
			
			//Width & Height
			seeker.setOffset(0x0C);
			int width = FileHandler.readInt(ddsFileBytes, seeker);
			int height = FileHandler.readInt(ddsFileBytes, seeker);
			byte[] heightBytes = FileHandler.toBytes((short) height, ByteOrder.LITTLE_ENDIAN);
			for (int i=0; i<heightBytes.length;i++){
				header[i+0x12] = heightBytes[i];
			}
			byte[] widthBytes = FileHandler.toBytes((short) width, ByteOrder.LITTLE_ENDIAN);
			for (int i=0; i<widthBytes.length;i++){
				header[i+0x14] = widthBytes[i];
			}
			//BlockDataSize
			int blockDataSize = ddsFileBytes.length-0x80;
			byte[] blockDataSizeBytes = FileHandler.toBytes(blockDataSize, ByteOrder.LITTLE_ENDIAN);
			for (int i=0; i<blockDataSizeBytes.length;i++){
				header[i+0x68] = blockDataSizeBytes[i];
			}
			//FileHandler.writeFile("output/debug_itexture_header.hex", header);
			return header;
		}else{
			System.err.println("DDS File can not be null. (ITextureConverter.getITextureHeader)!");
			return null;
		}		
	}
	
	
	
	public static byte[] getBlockData(byte[] ddsFileBytes){
		if (ddsFileBytes!=null){
			//DDS Header has a size of 0x80!
			byte[] blockData = new byte[ddsFileBytes.length-0x80];
			for (int i=0; i<blockData.length; i++){
				blockData[i] = ddsFileBytes[i+0x80];
			}			
			return blockData;
		}else{
			System.err.println("DDS File can not be null. (ITextureConverter.getBlockData)!");
			return null;
		}
	}

}
