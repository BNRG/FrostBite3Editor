package tk.captainsplexx.Resource.ITEXTURE;

import java.io.File;

public class ImageConverter {
	public static enum ImageType{
		TGA, PNG, DDS, JPG
	};
	
	public static File convert(File sourceFile, ImageType targetType, boolean forceOverride){
		if (!sourceFile.exists()){
			System.err.println("Can't convert file. SourceFile not found: "+sourceFile.getAbsolutePath());
			return null;
		}
		File targetFile = new File(sourceFile.getAbsolutePath().split("\\.")[0]+"."+targetType.toString().toLowerCase());
		if (targetFile.exists()){
			if (forceOverride){
				targetFile.delete();
			}else{
				System.err.println("Target file does already exist!");
				return targetFile;
			}
		}
		String[] commands = {"lib/bin/ImageMagick/convert.exe", sourceFile.getAbsolutePath(), targetFile.getAbsolutePath()};
		try {
			Process p = Runtime.getRuntime().exec(commands);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ImageConverter runtime command execution failture.");
			return null;
		}
		if (targetFile.exists()){
			return targetFile;
		}
		System.err.println("ImageConverter was not able to convert the target file!");
		return null;
	}
}
