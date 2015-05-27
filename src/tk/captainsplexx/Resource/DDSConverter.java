package tk.captainsplexx.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class DDSConverter {
	public static int MIP_MAP_LEVEL = 0;

	public static File convertToTGA(File ddsFile){
		if (!ddsFile.exists()){
			System.err.println("DDS File does not exist! "+ddsFile.getAbsolutePath());
		}		
		String[] commands = {"lib/bin/nvidia/readdxt.exe", ddsFile.getAbsolutePath()};
		try {
			Process p = Runtime.getRuntime().exec(commands);
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("DDSConverter runtime command execution failture.");
			return null;
		}
		for (int level = MIP_MAP_LEVEL; level >= 0; level--){
			File tga = new File(ddsFile.getAbsolutePath().replace(".dds", "0"+level+".tga"));
			if (tga.exists()){
				return tga;
			}
		}
		System.err.println("Converted tga could not be found for: "+ddsFile.getAbsolutePath());
		return null;
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
