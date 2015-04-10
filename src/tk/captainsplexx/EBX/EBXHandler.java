package tk.captainsplexx.EBX;

import java.util.HashMap;


public class EBXHandler {
	public String guidTablePath;
	public EBXLoader loader;
	public EBXGUIDHandler guidHandler;
	public HashMap<String, EBXFile> files;
	
	public enum FieldValueType{
		Complex, ArrayComplex, String, Enum, ExternalGuid, Hex8, Unknown,/*Field,*/ Float, Integer, Bool, Short, Byte, UInteger, ChunkGuid, Guid
	}
	
	public EBXHandler(String guidTablePath){
		this.guidTablePath = guidTablePath;
		this.loader = new EBXLoader();
		this.guidHandler = new EBXGUIDHandler(guidTablePath);
		this.files = new HashMap<String, EBXFile>();
	}

	public HashMap<String, EBXFile> getFiles() {
		return files;
	}
	
	/*public EBXFile loadFile(String filePath){
		loader.loadEBX(filePath);
		EBXFile newFile = new EBXFile(loader.getTrueFilename(), loader.getInstances());
		files.put(loader.getTrueFilename(), newFile);
		return newFile;
	}*/
	
	public EBXFile loadFile(byte[] data) {
		loader.loadEBX(data);
		EBXFile newFile = new EBXFile(loader.getTrueFilename(), loader.getInstances());
		files.put(loader.getTrueFilename(), newFile);
		return newFile;
	}
	
	public EBXGUIDHandler getGUIDHandler(){
		return guidHandler;
	}

	public EBXLoader getLoader() {
		return loader;
	}
	
}
