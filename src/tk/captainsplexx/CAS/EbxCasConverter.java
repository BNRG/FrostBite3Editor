package tk.captainsplexx.CAS;

import java.util.ArrayList;
import java.nio.ByteOrder;

import tk.captainsplexx.Resource.FileHandler;

public class EbxCasConverter {
	ArrayList<Byte> newCAS;
	public byte[] header = new byte[] { (byte) 0xFA, (byte) 0xCE, (byte) 0x00, //  << FA CE __ CAPTAINSPLEXX>>
			(byte) 0x43, (byte) 0x61, (byte) 0x70, (byte) 0x74, (byte) 0x61,
			(byte) 0x69, (byte) 0x6E, (byte) 0x53, (byte) 0x70, (byte) 0x6C,
			(byte) 0x65, (byte) 0x58, (byte) 0x78, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
	
	public byte[] compressionType = new byte[] {(byte) 0x00, (byte) 0x70};
	
	public byte[] createCAS(byte[] decompressedEBX){
		if (decompressedEBX.length >= 0xFFFF){
			System.err.println("Can't create CAS from decompressed ebx file. Size must be under 64KB in this version."); //TODO || CAS BLOCK LOGIC's
			return null;
		}
		newCAS = new ArrayList<Byte>();		
		addBytes(header);
		int decompressedSize = decompressedEBX.length;
		addBytes(FileHandler.toBytes(decompressedSize, ByteOrder.BIG_ENDIAN));
		addBytes(compressionType); // 0x0070 -- uncompressed || 0x0970 lz4 compressed || 0x0071 -- uncompressed no payload ??!
		addBytes(FileHandler.toBytes((short) decompressedSize, ByteOrder.BIG_ENDIAN));
		addBytes(decompressedEBX);
		return FileHandler.toByteArray(newCAS);
	}
	
	
	public void addBytes(byte[] arr){
		for (Byte b : arr){
			newCAS.add(b);
		}
	}
	
	
}
