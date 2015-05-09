package tk.captainsplexx.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DDSConverter {
	public static File convertToTGA(File ddsFile){
		if (!ddsFile.exists()){
			System.err.println("DDS File does not exist! "+ddsFile.getAbsolutePath());
		}		
		String[] commands = {"lib/bin/nvidia/readdxt.exe", ddsFile.getAbsolutePath()};
		try {
			Runtime.getRuntime().exec(commands);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("DDSConverter runtime command execution failture.");
			return null;
		}
		
		File tga = new File(ddsFile.getAbsolutePath().replace(".dds", "00.dds"));
		if (tga.exists()){
			return tga;
		}else{
			System.err.println("Converted dds could not be found for: "+ddsFile.getAbsolutePath());
			return null;
		}
	}
	
	public static File storeDDSasTGA(byte[] ddsFileData, String description){
		Random random = new Random();
		String path = "temp/images/"+description+"/"+random.nextInt(1234567891);
		FileHandler.writeFile(path, ddsFileData);
		File dds = new File(path);
		if (dds.exists()){
			File tga = convertToTGA(dds);
			return tga;
		}else{
			System.err.println("Could not store DDS.");
			return null;
		}
	}
}
