package tk.captainsplexx.Maths;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;
import tk.captainsplexx.Resource.CAS.CasDataReader;

public class Patcher {

	public static byte[] getPatchedData(byte[] decompressedBase, byte[] delta){
		if (decompressedBase.length == 0 || delta.length == 0){
			System.err.println("Could not patch data, because of 0 length.");
			return null;
		}
		
		/*FileHandler.writeFile("output/debug/patchType2_base", decompressedBase);
		FileHandler.writeFile("output/debug/patchType2_delta", delta);
		*/
		
		int type = 0;
		int procSize = 0;
		int patchedSize = 0;
		
		int offset = 0;
		int removeBytes = 0;
		int addBytes = 0;
		
		FileSeeker baseSeeker = new FileSeeker("BASE in PATCHER");
		FileSeeker deltaSeeker = new FileSeeker("DELTA in PATCHER");
		
		ArrayList<Byte> patchedData = new ArrayList<>();
		
		
		type = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);// Type 0x2000 if its not compressed. 0x1000 if its compressed.
		if (type==0x1000){
			int numEntries = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);
			
			byte[] compressedDelta = null;
			
			for (int i=0; i<numEntries; i++){
				/*get entry information*/
				offset = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);
				removeBytes = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);
				if (deltaSeeker.hasError()){return null;}
				
				/*fill up to offset*/
				while(baseSeeker.getOffset()<offset){
					patchedData.add(FileHandler.readByte(decompressedBase, baseSeeker));
					if (baseSeeker.hasError()){return null;}
				}
								
				/*take a look into block logic to obtain the compressed size*/
				deltaSeeker.seek(6);//4 bytes decompressed size, 2 bytes type
				compressedDelta = new byte[FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN)+8/*compressed size is without header, so add decompsize, type and compressedsize to it!*/];
				if (deltaSeeker.hasError()){return null;}
				
				/*go back the beginning of the block logic and fill up the byte array*/
				deltaSeeker.seek(-8);
				int seekerOffset = deltaSeeker.getOffset();
				for (int ix=0; ix<compressedDelta.length; ix++){
					int index = ix+seekerOffset;
					compressedDelta[ix] = delta[index];
					deltaSeeker.seek(1);
				}
				
				/*decompress the extracted block logic and add it to return data.*/
				byte[] rawBlock = CasDataReader.convertToRAWData(compressedDelta);
				if (rawBlock==null||!FileHandler.addBytes(rawBlock, patchedData)){return null;}
				
				/*skip bytes from base, defined by entry information*/
				baseSeeker.seek(removeBytes);						
			}
			return FileHandler.convertFromList(patchedData);
		}else if (type==0x2000){
			procSize = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN);
			patchedSize = FileHandler.readShort(delta, deltaSeeker, ByteOrder.BIG_ENDIAN); // (CONTAINS PATCHED SIZE)
			
			
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
		System.out.println("Type "+type+" is not known inside Patcher!");
		return null;
	}
	
	static private void dump(byte[] decompressedBase, byte[] delta){
		FileHandler.writeFile("output/debug/base_in_patcher", decompressedBase);
		FileHandler.writeFile("output/debug/delta_in_patcher", delta);
	}
}
