package tk.captainsplexx.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class FileHandler {
	
	//READ - FileInputStream
	public static byte[] readFile(String filepath){
		try{
			File file = new File(filepath.replaceAll("//", "/"));
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
	
	public static byte[] readFile(String filepath, long offset, int length){
		try{
			File file = new File(filepath.replaceAll("//", "/"));
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
			is = new FileInputStream(path.replaceAll("//", "/"));
		} catch (FileNotFoundException e) {
			System.err.println("Could not read ImputStream from: "+path);
			e.printStackTrace();
		}
		return is;
	}
	
	//WRITE - FileOutputStream
	public static boolean writeFile(String filepath, byte[] arr, boolean append){
		FileOutputStream fos;
		prepareDir(filepath);
		try {
			fos = new FileOutputStream(filepath, append);
			fos.write(arr);
			System.out.println("Write: "+filepath+"!");
			fos.close();
			return true;
		} catch (NullPointerException e) {
			System.err.println("could not write data to file: "+filepath+" because of nullpointer.");
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("could not write data to file: "+filepath);
			return false;
		}	
	}
	
	public static boolean prepareDir(String filepath){
		String[] split = filepath.split("/");
		String currPath = "";
		for (int index=0; index<split.length-1;index++){
			currPath += split[index]+"/";
			File folder = new File(currPath);
			if (folder.isDirectory()){
				//System.out.println(folder.getName());
				continue;
			}else{
				//System.err.println(folder.getName());
				folder.mkdir();
			}
		}
		return false;
	}
	
	public static boolean writeFile(String filepath, byte[] arr){
		return writeFile(filepath, arr, false);
	}
	
	public static boolean writeFileFromFile(String sourceFile, long sourceOffset, long sourceSize, String targetFile, FileSeeker targetSeeker){
		try{
			File file = new File(sourceFile.replaceAll("//", "/"));
			FileInputStream fin = new FileInputStream(file);
			fin.skip(sourceOffset);
			
			FileOutputStream fos = new FileOutputStream(targetFile, true);
			
			byte[] data = new byte[1];
			for (int i=0; i<sourceSize; i++){
				fin.read(data);
				fos.write(data, 0x0, 1);
				targetSeeker.seek(1);
			}
			
			fin.close();
			fos.close();
			return true;
		}catch (Exception e){
			e.printStackTrace();
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
			String out = "Exception while read byte from inputStream at " + seeker.getOffset();
			if (seeker.getDescription()!=null){
				out+=" ("+seeker.getDescription()+")";
			}
			System.err.println(out);
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
	
	public static byte[] readByte(byte[] input, int offset, int len) {
		byte[] buffer = new byte[len];
		FileSeeker seeker = new FileSeeker();
		seeker.seek(offset);
		for (int i = 0; i < len; i++) {
			buffer[i] = readByte(input, seeker);
		}
		return buffer;
	}
	
	public static ArrayList<Byte> toArrayList(byte[] data){
		ArrayList<Byte> list = new ArrayList<>();
		for (Byte b : data){
			list.add(b);
		}
		return list;
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
		try{
			final StringBuilder builder = new StringBuilder();
			for (byte b : in) {
				builder.append(String.format("%02x", b));
			}
			return builder.toString();
		}catch (NullPointerException e){
			return "";
		}
	}
	public static int readInt(byte[] fileArray, FileSeeker seeker) {
		return ByteBuffer.wrap(readByte(fileArray, seeker, 4))
				.order(ByteOrder.LITTLE_ENDIAN).getInt();
	
	}
	
	public static String readSHA1(byte[] fileArray, FileSeeker seeker) {
		return bytesToHex(readByte(fileArray, seeker, 20));
	}
	
	public static byte[] toBytes(int value, ByteOrder order)
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
	
	public static byte[] toBytes(long l, ByteOrder order) {
        byte[] bytesp = new byte[8];
        if (order == ByteOrder.LITTLE_ENDIAN){
	        for (int i=0; i<bytesp.length; i++){
	        	bytesp[i]=((byte) (l % (0xff + 1)));
	            l = l >> 8;
	        }
        }else{
        	for (int i=7; i>=0; i--){
            	bytesp[i]=((byte) (l % (0xff + 1)));
                l = l >> 8;
            }
        }
        return bytesp;
    }
	
	public static int readHeigh(byte b){
		return b >> 4 & 0xF;
	}
	
	public static int readLow(byte b){
		return b & 0x0F;
	}
	
	public static byte[] toBytes(short value, ByteOrder order){ //TODO not tested
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
			
	public static byte[] toByteArray(ArrayList<Byte> in) {
	    final int n = in.size();
	    byte ret[] = new byte[n];
	    for (int i = 0; i < n; i++) {
	        ret[i] = in.get(i);
	    }
	    return ret;
	}
	
	public static int readLEB128(byte[] fileArray, FileSeeker seeker){
		//Read the next few bytes as LEB128/7bit encoding and return an integer.
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
	
	public static ArrayList<Byte> toLEB128List(int uinteger) {
        ArrayList<Byte> out = new ArrayList<Byte>();
		int remaining = uinteger >>> 7;

        while (remaining != 0) {
        	out.add(((byte) ((uinteger & 0x7f) | 0x80)));
            uinteger = remaining;
            remaining >>>= 7;
        }
        out.add((byte) (uinteger & 0x7f));
        return out;
    }
	
	public static byte[] toLEB128Bytes(int uinteger){
		return FileHandler.toByteArray(FileHandler.toLEB128List(uinteger));
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
	
	public static String toHexInteger(int i, ByteOrder order) {
		return bytesToHex(toBytes(i, order)).toUpperCase();
	}
	public static String toHexInteger(int i) {
		return bytesToHex(toBytes(i, ByteOrder.LITTLE_ENDIAN)).toUpperCase();
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
	
	/*FILEFINDER*/
	public static ArrayList<File> listf(String directoryName, String endsWith) {
	    File directory = new File(directoryName);
	    if (!directory.isDirectory()){
	    	return new ArrayList<File>();
	    }
	    ArrayList<File> files = new ArrayList<File>();
	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	        	if (file.getName().endsWith(endsWith)){
	        		files.add(file);
	        	}
	        } else if (file.isDirectory()) {
	        	listfdir(file.getAbsolutePath(), files, endsWith);
	        }
	    }
	    return files;
	}
	static void listfdir(String directoryName, ArrayList<File> files , String endsWith) {
	    File directory = new File(directoryName);
	    if (directory.isDirectory()){
	    	// get all the files from a directory
		    File[] fList = directory.listFiles();
		    for (File file : fList) {
		        if (file.isFile()) {
		        	if (file.getName().endsWith(endsWith)){
		        		files.add(file);
		        	}
		        } else if (file.isDirectory()) {
		        	listfdir(file.getAbsolutePath(), files, endsWith);
		        }
		    }
	    }
	}
	/*END OF FINDER*/
	
}
