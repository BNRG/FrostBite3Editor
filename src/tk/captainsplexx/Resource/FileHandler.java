package tk.captainsplexx.Resource;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class FileHandler {
	
	//READ - FileInputStream
	public static byte[] readFile(String filepath){
		try{
			File file = new File(normalizePath(filepath));
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
	
	public static int longToInt(long l) {
	    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
	        System.err.println("Long is to small/big. No convertion to Integer possible!");
	    	return 0;
	    }
	    return (int) l;
	}
	
	public static byte[] readFile(String filepath, long offset, int length){
		try{
			File file = new File(normalizePath(filepath));
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
	public static boolean openFolder(String path){
		Desktop desktop = Desktop.getDesktop();
	    File dirToOpen = null;
	    try {
	        dirToOpen = new File(normalizePath(path));
	        desktop.open(dirToOpen);
	        return true;
	    } catch (Exception iae) {
	        System.out.println("Folder not found.");
	        return false;
	    }
	}
	
	public static boolean openURL(String URL){
		Desktop desktop = Desktop.getDesktop();
	    try {
	    	URI oURL = new URI(URL);
	        desktop.browse(oURL);
	        return true;
	    } catch (Exception iae) {
	        return false;
	    }
	}
	
	
	
	public static InputStream getStream(String path){
		InputStream is = null;
		try {
			is = new FileInputStream(normalizePath(path));
		} catch (FileNotFoundException e) {
			System.err.println("Could not read ImputStream from: "+path);
			e.printStackTrace();
		}
		return is;
	}
	
	public static boolean writeLine(ArrayList<String> lines, File file){
		try{
			prepareDir(file.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(file);
			 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (String str : lines){
				bw.write(str+"\n");
			}
			bw.close();
			fos.close();
			return true;
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("Could not write line. "+file.getAbsolutePath());
			return false;
		}
	}
	
	public static boolean createLink(String newLink, String source){
		File sourceFile = new File(normalizePath(source));
		File newLinkFile = new File(normalizePath(newLink));
		if (sourceFile.exists() && !newLinkFile.exists()){
			String[] commands = {"cmd.exe","/r","mklink", "/H", newLink, source};
			//System.out.println("");
			//for (String s : commands){
			//	System.out.print(s);
			//}
			try {
				Process p = Runtime.getRuntime().exec(commands);
				p.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Something went wrong while creating a new hardlink.");
				return false;
			}
			return true;
		}
		System.out.println("Link Creator: SOURCE may not exist or LINK does already exist");
		return false;
	}
	
	public static boolean cleanFolder(String folderPath){
		for (File f : FileHandler.listf(folderPath, "")){
			f.delete();
		}
		return true;
	}
	
	public static byte[] toBytes(ArrayList<ArrayList<Byte>> arraylist){
		int totalSize = 0;
		for (ArrayList<Byte> array : arraylist){
			if (array!=null){
				totalSize += array.size();
			}
		}
		byte[] arr = new byte[totalSize];
		int index = 0;
		for (ArrayList<Byte> array : arraylist){
			if (array!=null){
				for (Byte b : array){
					arr[index] = b; 
					index++;
				}
			}
		}
		return arr;
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
	
	public static String normalizePath(String filepath){
		filepath = filepath.replace("\\", "/").replace("//", "/");
		return filepath;
	}
	
	public static boolean prepareDir(String filepath){
		filepath = normalizePath(filepath);
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
	
	public static boolean extendFileFromFile(String sourceFile, long sourceOffset, long sourceSize, String targetFile, FileSeeker targetSeeker){
		try{
			File file = new File(normalizePath(sourceFile));
			//System.out.println(file.length());
			FileInputStream fin = new FileInputStream(file);
			fin.skip(sourceOffset);
			
			FileOutputStream fos = new FileOutputStream(normalizePath(targetFile), true);
			//byte[] test = FileHandler.readFile(normalizePath(targetFile));
			byte[] data = new byte[1];
			for (int i=0; i<sourceSize; i++){
				int bob = fin.read(data);//TODO ??
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
			seeker.setError(true);
		}
		seeker.seek(1);
		return b;
	}
	public static byte[] readByte(byte[] input, FileSeeker seeker, int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len; i++) {
			buffer[i] = readByte(input, seeker);
			if (seeker.hasError()){
				return null;
			}
		}
		return buffer;
	}
	
	public static byte[] readByte(byte[] input, int offset, int len) {
		byte[] buffer = new byte[len];
		FileSeeker seeker = new FileSeeker();
		seeker.seek(offset);
		for (int i = 0; i < len; i++) {
			buffer[i] = readByte(input, seeker);
			if (seeker.hasError()){
				return null;
			}
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
	
	public static byte[] toBytes(int value, ByteOrder order)//tested working
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
	
	public static byte[] toBytes(float flooooat, ByteOrder order) {
	    byte[] outData=new byte[4];
	    int data=Float.floatToIntBits(flooooat);
	    if (order == ByteOrder.LITTLE_ENDIAN){
	    	outData[3]=(byte)(data>>>24);
		    outData[2]=(byte)(data>>>16);
		    outData[1]=(byte)(data>>>8);
		    outData[0]=(byte)(data>>>0);
	    }else{
		    outData[0]=(byte)(data>>>24);
		    outData[1]=(byte)(data>>>16);
		    outData[2]=(byte)(data>>>8);
		    outData[3]=(byte)(data>>>0);
	    }
	    return outData;
	}
	
	public static int readHeigh(byte b){
		return b >> 4 & 0xF;
	}
	
	public static int readLow(byte b){
		return b & 0x0F;
	}
	
	public static byte[] toBytes(short value, ByteOrder order){//tested!
		byte[] bytes = new byte[2];
		
		if (order == ByteOrder.LITTLE_ENDIAN){
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
					seeker.setError(true);
				}
			}else{
				break;
			}
		}
		return tmp;
	}
	
	public static boolean addBytes(byte[] arr, ArrayList<Byte> targetList){
		try{
			for (Byte b : arr){
				targetList.add(b);
			}
			return true;
		}catch (Exception e){
			System.err.println("Something wrent wrong while adding byte's from array to list.");
			return false;
		}
	}
	
	public static boolean addBytes(byte[] arr, ArrayList<Byte> targetList, int startIdx, int length){
		try{
			for (int i=startIdx; i<(startIdx+length);i++){
				targetList.add(arr[i]);
			}
			return true;
		}catch (Exception e){
			System.err.println("Something wrent wrong while adding byte's from array to list.");
			return false;
		}
	}
	public static boolean addBytes(byte[] sourceArr, byte[] targetArr, FileSeeker seeker){
		return addBytes(sourceArr, 0, sourceArr.length, targetArr, seeker);
	}
	
	public static boolean overrideBytes(byte[] sourceArr, byte[] targetArr, int targetOffset){
		if (targetOffset>targetArr.length+sourceArr.length){
			System.err.println("Can't override, target offset + source length would be out of bounds!");
			return false;
		}
		for (int i=0; i<sourceArr.length; i++){
			targetArr[targetOffset+i] = sourceArr[i];
		}
		return true;
	}
	
	public static boolean addBytes(byte[] sourceArr, int startSource, int lengthSource, byte[] targetArr, FileSeeker seeker){
		try{
			if (sourceArr.length> seeker.getOffset()+targetArr.length){
				System.err.println("Can't copy sourceArr to targetArr because out of bounds!");
				seeker.setError(true);
				return false;
			}
			int sourceIndex = 0;
			for (int i=seeker.getOffset(); i<(seeker.getOffset()+sourceArr.length); i++){
				targetArr[i] = sourceArr[startSource+sourceIndex];
				sourceIndex++;
			}
			seeker.seek(lengthSource);
			return true;
		}catch (Exception e){
			System.err.println("Something wrent wrong while adding byte's from array to array.");
			seeker.setError(true);
			return false;
		}
	}
	
	/*FILEFINDER*/
	public static ArrayList<File> listf(String directoryName, String contains) {
	    File directory = new File(directoryName);
	    if (!directory.isDirectory()){
	    	return new ArrayList<File>();
	    }
	    ArrayList<File> files = new ArrayList<File>();
	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	        	if (file.getName().contains(contains)){
	        		files.add(file);
	        	}
	        } else if (file.isDirectory()) {
	        	listfdir(file.getAbsolutePath(), files, contains);
	        }
	    }
	    return files;
	}
	static void listfdir(String directoryName, ArrayList<File> files , String contains) {
	    File directory = new File(directoryName);
	    if (directory.isDirectory()){
	    	// get all the files from a directory
		    File[] fList = directory.listFiles();
		    for (File file : fList) {
		        if (file.isFile()) {
		        	if (file.getName().contains(contains)){
		        		files.add(file);
		        	}
		        } else if (file.isDirectory()) {
		        	listfdir(file.getAbsolutePath(), files, contains);
		        }
		    }
	    }
	}
	/*END OF FINDER*/
	
}
