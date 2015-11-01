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
		
		FileSeeker baseSeeker = new FileSeeker("BASE in PATCHER");
		FileSeeker deltaSeeker = new FileSeeker("DELTA in PATCHER");
		
		deltaSeeker.seek(2); // SKIP FIRST 2 BYTES (IDK WHAT THIS IS :X)
		procSize = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);
		patchedSize = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN); // (CONTAINS PATCHED SIZE)
		ArrayList<Byte> patchedData = new ArrayList<>();
		
		int offset = 0;
		int removeBytes = 0;
		int addBytes = 0;
		
		int procOffset = deltaSeeker.getOffset();
		
		byte basedata = 0;
		byte deltadata = 0;
		
		//fill spaces - patch data
		while(deltaSeeker.getOffset()<procOffset+procSize){
			
			//*MAY USING LEB128 (DOES EVEN BIG_END. ENCODING EXIST ?)*//
			offset = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN)&0xFFFF;
			removeBytes = FileHandler.readByte(delta, deltaSeeker)&0xFF;
			addBytes = FileHandler.readByte(delta, deltaSeeker)&0xFF;
			
			
			//System.out.println("Offset: "+offset+" Rem: "+removeBytes+" Add: "+addBytes);
			
			//filldata up to offset
			while(baseSeeker.getOffset()<offset){
				basedata = FileHandler.readByte(decompressedBase, baseSeeker);
				if (!baseSeeker.hasError()){
					patchedData.add(basedata);
				}else{
					System.err.println("Error while patching, can't fill up bytes. (maybe out of bounds)");
					dump(decompressedBase, delta);
					return null;
				}
			}
			//remove
			baseSeeker.seek(removeBytes);
			
			//add
			for (int patchIndex=0; patchIndex<addBytes; patchIndex++){
				deltadata = FileHandler.readByte(delta, deltaSeeker);
				if (!deltaSeeker.hasError()){
					patchedData.add(deltadata);
				}else{
					dump(decompressedBase, delta);
					System.err.println("Error while patching, can't get delta bytes. (maybe out of bounds)");
					return null;
				}
			}	
		}
		
		//fill up left over data
		if (patchedData.size()<patchedSize){
			if (baseSeeker.getOffset() < decompressedBase.length){
				while (baseSeeker.getOffset() < decompressedBase.length){
					basedata = FileHandler.readByte(decompressedBase, baseSeeker);
					if (!baseSeeker.hasError()){
						patchedData.add(basedata);
					}else{
						System.err.println("Error while patching, can't fill up the ---leftover--- bytes. (maybe out of bounds)");
						dump(decompressedBase, delta);
						return null;
					}
				}
			}else{
				dump(decompressedBase, delta);
				System.err.println("Patched size is smaller as given one :( ["+patchedData.size()+"/"+patchedSize+"]");
				return null;
			}
		}
		if (baseSeeker.hasError() || deltaSeeker.hasError()){
			dump(decompressedBase, delta);
			return null;
		}
		
		return FileHandler.convertFromList(patchedData);
	}
	
	static private void dump(byte[] decompressedBase, byte[] delta){
		FileHandler.writeFile("output/debug/base_in_patcher", decompressedBase);
		FileHandler.writeFile("output/debug/delta_in_patcher", delta);
	}
}
