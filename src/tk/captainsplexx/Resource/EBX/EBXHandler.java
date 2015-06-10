package tk.captainsplexx.Resource.EBX;

import java.util.HashMap;


public class EBXHandler {
	//public String guidTablePath;
	public EBXLoader loader;
	public EBXCreator creator;
	//public EBXGUIDHandler guidHandler;
	public HashMap<String, EBXFile> files;
	
	public enum FieldValueType{
		Complex, ArrayComplex, String, Enum, ExternalGuid, Hex8, Unknown,/*Field,*/ Float, Integer, Bool, Short, Byte, UInteger, ChunkGuid, Guid
	}
	
	public static int hasher(byte[] bytes) {
		int hash = 5381;
		for (Byte b : bytes) {
			hash = hash * 33 ^ b;
		}
		return hash;
	}
	
	public EBXHandler(/*String guidTablePath*/){
		//this.guidTablePath = guidTablePath;
		this.loader = new EBXLoader();
		this.creator = new EBXCreator();
		//this.guidHandler = new EBXGUIDHandler(guidTablePath);
		this.files = new HashMap<String, EBXFile>();
	}

	public HashMap<String, EBXFile> getFiles() {
		return files;
	}
		
	public EBXFile loadFile(byte[] data) {
		loader.loadEBX(data);
		EBXFile newFile = new EBXFile(loader.getTrueFilename(), loader.getInstances(), loader.getFileGUID());
		files.put(loader.getTrueFilename(), newFile);
		return newFile;
	}
	
	public byte[] createEBX(EBXFile ebxFile){
		//ADD TODO
		return creator.createEBX(ebxFile);
	}
	
	/*
	public EBXGUIDHandler getGUIDHandler(){
		return guidHandler;
	}*/

	public EBXLoader getLoader() {
		return loader;
	}

	public EBXCreator getCreator() {
		return creator;
	}
	
	
}
