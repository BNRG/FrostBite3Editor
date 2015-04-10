package tk.captainsplexx.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class FileHandler {
	
	//READ - FileInputStream
	public static byte[] readFile(String filepath){
		try{
			File file = new File(filepath);
			FileInputStream fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int)file.length()];	
			fin.read(fileContent);
			fin.close();
			return fileContent;
		}catch (Exception e){
			System.err.println("could not read file: "+filepath);
			return null;
		}
	}
	
	public static byte[] readFile(String filepath, int offset, int length){
		try{
			File file = new File(filepath);
			FileInputStream fin = new FileInputStream(file);
			byte fileContent[] = new byte[length];
			fin.skip(offset);
			fin.read(fileContent, 0x0, length);
			fin.close();
			return fileContent;
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("could not read file part from: "+filepath);
			return null;
		}
	}
	
	public static InputStream getStream(String path){
		InputStream is = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.err.println("Could not read ImputStream from: "+path);
			e.printStackTrace();
		}
		return is;
	}
	
	//WRITE - FileOutputStream
	public static boolean writeFile(String filepath, byte[] arr){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filepath);
			fos.write(arr);
			fos.close();
			return true;
		} catch (Exception e) {
			System.err.println("could not write data to file: "+filepath);
			return false;
		}	
	}
	
	
	public static int readInt(byte[] data, FileSeeker seeker, ByteOrder order) {
		return ByteBuffer.wrap(readByte(data, seeker, 4))
				.order(order).getInt();
	}
	
	public static short readShort(byte[] data, FileSeeker seeker, ByteOrder order) {
		return ByteBuffer.wrap(readByte(data, seeker, 2))
				.order(order).getShort();
	}
	
	public static byte readByte(byte[] input, FileSeeker seeker) {
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
	public static byte[] readByte(byte[] input, FileSeeker seeker, int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len; i++) {
			buffer[i] = readByte(input, seeker);
		}
		return buffer;
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	public static String bytesToHex(byte[] in) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : in) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
	public static int readInt(byte[] fileArray, FileSeeker seeker) {
		return ByteBuffer.wrap(readByte(fileArray, seeker, 4))
				.order(ByteOrder.LITTLE_ENDIAN).getInt();
	
	}
	
	public static String readSHA1(byte[] fileArray, FileSeeker seeker) {
		return bytesToHex(readByte(fileArray, seeker, 20));
	}
	
	public static byte[] convertToBytes(int value, ByteOrder order)
    {
        byte[] byteArray = new byte[4];
        int shift = 0;
        for (int i = 0; i < byteArray.length;
             i++) {
 
            if (order == ByteOrder.BIG_ENDIAN)
                shift = (byteArray.length - 1 - i) * 8;
            else
                shift = i * 8;
 
            byteArray[i] = (byte) (value >>> shift);
        }
        return byteArray;
 
    }
	
	public static byte[] convertToBytes(short value, ByteOrder order){ //TODO not tested
		byte[] bytes = new byte[2];
		
		if (order == ByteOrder.BIG_ENDIAN){
			bytes[0] = (byte)(value & 0xff);
			bytes[1] = (byte)((value >> 8) & 0xff);
		}else{
			bytes[1] = (byte)(value & 0xff);
			bytes[0] = (byte)((value >> 8) & 0xff);	
		}
		return bytes;
	}
	
	public static byte[] convertFromList(ArrayList<Byte> list){
		byte[] output = new byte[list.size()];
		for (int i=0; i<output.length; i++){
			output[i] = list.get(i);
		}
		return output;
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
	
	public static byte[] toByteArray(ArrayList<Byte> in) {
	    final int n = in.size();
	    byte ret[] = new byte[n];
	    for (int i = 0; i < n; i++) {
	        ret[i] = in.get(i);
	    }
	    return ret;
	}
	
	public static int readLEB128(byte[] fileArray, FileSeeker seeker){ //Read the next few bytes as LEB128/7bit encoding and return an integer.
		int result = 0;
		int shift = 0;
		while(true){
			byte b = readByte(fileArray, seeker);
			result |= (b & 0x7f) << shift;
			if ((b & 0x80) == 0){
			   return result;
			}
			shift += 7;
		}
	}
	
	public static float convertHalfToFloat(short half) {
        switch ((int) half) {
            case 0x0000:
                return 0f;
            case 0x8000:
                return -0f;
            case 0x7c00:
                return Float.POSITIVE_INFINITY;
            case 0xfc00:
                return Float.NEGATIVE_INFINITY;
            default:
                return Float.intBitsToFloat(((half & 0x8000) << 16)
                        | (((half & 0x7c00) + 0x1C000) << 13)
                        | ((half & 0x03FF) << 13));
        }
    }
		
	public static float readFloat(byte[] fileArray, FileSeeker seeker){
		return ByteBuffer.wrap(readByte(fileArray, seeker, 4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
	
	public static long readLong(byte[] fileArray, FileSeeker seeker) {
		return ByteBuffer.wrap(readByte(fileArray, seeker, 8)).order(ByteOrder.LITTLE_ENDIAN).getLong();
	}
	
	public static long readLong(byte[] fileArray, FileSeeker seeker, ByteOrder order) {
		return ByteBuffer.wrap(readByte(fileArray, seeker, 8)).order(order).getLong();
	}
	
	public static String readString(byte[] fileArray, FileSeeker seeker) {
		String tmp = "";
		while(true){
			byte[] b = readByte(fileArray, seeker, 1);
			if (b[0] != 0x0) {
				try {
					tmp += new String(b, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else{
				break;
			}
		}
		return tmp;
	}
	
	
	
}
