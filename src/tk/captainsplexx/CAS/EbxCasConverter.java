package tk.captainsplexx.CAS;

import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
		addBytes(toBytes(decompressedSize, ByteOrder.BIG_ENDIAN));
		addBytes(compressionType); // 0x0070 -- uncompressed || 0x0970 lz4 compressed || 0x0071 -- uncompressed no payload ??!
		addBytes(toBytes((short) decompressedSize, ByteOrder.BIG_ENDIAN));
		addBytes(decompressedEBX);
		return toByteArray(newCAS);
	}
	
	public static byte[] toBytes(int i, ByteOrder order)
	{
	  byte[] result = new byte[4];

	  result[0] = (byte) (i >> 24);
	  result[1] = (byte) (i >> 16);
	  result[2] = (byte) (i >> 8);
	  result[3] = (byte) (i /*>> 0*/);
	  return ByteBuffer.wrap(result).order(order).array();
	}
	
	public static byte[] toBytes(short s, ByteOrder order){
		byte[] bArray = new byte[2];
		byte[] sArray = toBytes((int) s, order);
		if (order == ByteOrder.BIG_ENDIAN){
			bArray[0] = sArray[2];
			bArray[1] = sArray[3];
		}else{
			bArray[0] = sArray[0];
			bArray[1] = sArray[1];
		}
		return bArray;
	}
	
	public void addBytes(byte[] arr){
		for (Byte b : arr){
			newCAS.add(b);
		}
	}
	
	public static byte[] toByteArray(ArrayList<Byte> in) {
	    final int n = in.size();
	    byte ret[] = new byte[n];
	    for (int i = 0; i < n; i++) {
	        ret[i] = in.get(i);
	    }
	    return ret;
	}
}
