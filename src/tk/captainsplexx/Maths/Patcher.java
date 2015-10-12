package tk.captainsplexx.Maths;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;

public class Patcher {

	public static byte[] getPatchedData(byte[] decompressedBase, byte[] delta){
		if (decompressedBase.length == 0 || delta.length == 0){
			System.err.println("Could not patch data, because of 0 length.");
			return null;
		}
		int procSize = 0;
		int patchedSize = 0;
		
		FileSeeker baseSeeker = new FileSeeker("BASE SEEKER | PATCHER");
		FileSeeker deltaSeeker = new FileSeeker("DELTA SEEKER | PATCHER");
		
		deltaSeeker.seek(2); // SKIP FIRST 2 BYTES (IDK WHAT THIS IS :X)
		procSize = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);
		patchedSize = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN); // (CONTAINS PATCHED SIZE)
		ArrayList<Byte> patchedData = new ArrayList<>();
		
		int offset = 0;
		int removeBytes = 0;
		int addBytes = 0;
		
		int procOffset = deltaSeeker.getOffset();
		
		//fill spaces - patch data
		while(deltaSeeker.getOffset()<procOffset+procSize){
			
			//*MAY USING LEB128 (DOES EVEN BIG_END. ENCODING EXIST ?)*//
			offset = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);
			removeBytes = FileHandler.readByte(delta, deltaSeeker);
			addBytes = FileHandler.readByte(delta, deltaSeeker);
			
			
			//System.out.println("Offset: "+offset+" Rem: "+removeBytes+" Add: "+addBytes);
			
			//filldata up to offset
			while(baseSeeker.getOffset()<offset){
				patchedData.add(FileHandler.readByte(decompressedBase, baseSeeker));
			}
			//remove
			baseSeeker.seek(removeBytes);
			
			//add
			for (int patchIndex=0; patchIndex<addBytes; patchIndex++){
				patchedData.add(FileHandler.readByte(delta, deltaSeeker));
			}	
		}
		
		//fill up left over data
		if (patchedData.size()<patchedSize){
			if (baseSeeker.getOffset() < decompressedBase.length){
				while (baseSeeker.getOffset() < decompressedBase.length){
					patchedData.add(FileHandler.readByte(decompressedBase, baseSeeker));
				}
			}else{
				System.err.println("Patched size is smaller as given one :( ["+patchedData.size()+"/"+patchedSize+"]");
			}
		}
		if (baseSeeker.hasError() || deltaSeeker.hasError()){
			return null;
		}
		
		return FileHandler.convertFromList(patchedData);
	}
}
