package tk.captainsplexx.Resource.EBX;

import java.util.HashMap;


public class EBXHandler {
	//public String guidTablePath;
	public EBXLoader loader;
	public EBXCreator creator;
	//public EBXGUIDHandler guidHandler;
	public HashMap<EBXExternalFileReference, EBXFile> files /*FileName, File*/;
	
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
		this.files = new HashMap<EBXExternalFileReference, EBXFile>();
	}

	public HashMap<EBXExternalFileReference, EBXFile> getFiles() {
		return files;
	}
		
	public EBXFile loadFile(byte[] data) {
		loader.loadEBX(data);
		EBXFile newFile = new EBXFile(loader.getTrueFilename(), loader.getInstances(), loader.getFileGUID());
		EBXExternalFileReference efr = new EBXExternalFileReference(loader.getFileGUID(), loader.getFileGUID());
		files.put(efr, newFile);
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

	public EBXFile getEBXFileByGUID(String fileGUID){
		for (EBXExternalFileReference efr : files.keySet()){
			if (efr.getGuid().equalsIgnoreCase(fileGUID)){
				return files.get(efr);
			}
		}
		System.err.println("FileGUID "+fileGUID+" has no reference in EBXHandler for an EBXFile!");
		return null;
		
	}
	public EBXFile getEBXFileByName(String trueFileName){
		for (EBXExternalFileReference efr : files.keySet()){
			if (efr.getTrueFileName().equalsIgnoreCase(trueFileName)){
				return files.get(efr);
			}
		}
		System.err.println("TrueFileName "+trueFileName+" has no reference in EBXHandler for an EBXFile!");
		return null;
		
	}
}
