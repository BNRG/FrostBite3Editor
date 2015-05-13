package tk.captainsplexx.Modif;

import java.io.File;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;

public class Client {
	public static boolean createModifClient(String sourceFolder, String newFolderName){
		sourceFolder = FileHandler.normalizePath(sourceFolder);
		ArrayList<File> sourceFiles = null;
		File folder = new File(sourceFolder);
		if (!folder.isDirectory()){
			System.err.println("Not a vaild folder given.");
			return false;
		}
		String[] split = sourceFolder.split("/");
		int length = split.length;
		if (sourceFolder.endsWith("/")){
			length--;
		}
		String destFolderPath = "";
		for (int i=0; i<length;i++){
			destFolderPath +=split[i]+"/";
		}
		destFolderPath += newFolderName;
		File destFolder = new File(destFolderPath);
		if (destFolder.isDirectory()){
			System.err.println("New folder does already exist.");
			return false;
		}
		sourceFiles = FileHandler.listf(sourceFolder, "");
		for (File f : sourceFiles){
			String linkPath = FileHandler.normalizePath(f.getAbsolutePath()).replace(sourceFolder, destFolderPath+"/");
			FileHandler.prepareDir(linkPath);
			FileHandler.createLink(linkPath, FileHandler.normalizePath(f.getAbsolutePath()));
		}
		return true;
	}
}
