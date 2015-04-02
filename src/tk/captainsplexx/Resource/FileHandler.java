package tk.captainsplexx.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

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
}
