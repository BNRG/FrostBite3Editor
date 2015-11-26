package tk.captainsplexx.Resource.EBX;

import java.util.ArrayList;
import java.util.HashMap;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Resource.EBX.Modify.EBXModifyHandler;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureFile;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader;
import tk.captainsplexx.Resource.TOC.ResourceLink;


public class EBXHandler {
	//public String guidTablePath;
	public EBXLoader loader;
	public EBXCreator creator;
	public HashMap<EBXExternalFileReference, EBXFile> ebxFiles /*FileName, File*/;
	public ArrayList<EBXStructureFile> ebxStructureFiles;
	public EBXModifyHandler modifyHandler;
	
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
	
	public EBXHandler(){
		reset();
	}
	
	public void reset(){
		this.loader = new EBXLoader();
		this.creator = new EBXCreator();
		this.ebxFiles = new HashMap<EBXExternalFileReference, EBXFile>();
		this.ebxStructureFiles = new ArrayList<>();
		this.modifyHandler = new EBXModifyHandler();
	}


		
	public EBXFile loadFile(byte[] data) {
		try{
			if (loader.loadEBX(data)){
				EBXFile newFile = new EBXFile(loader.getTrueFilename(), loader.getInstances(), loader.getFileGUID(), loader.getByteOrder());
				EBXExternalFileReference efr = new EBXExternalFileReference(loader.getFileGUID(), loader.getTrueFilename());
				ebxFiles.put(efr, newFile);
				return newFile;
			}else{
				return null;
			}
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("EBXFile could not be loaded.");
			return null;
		}
	}
	
	public byte[] createEBX(EBXFile ebxFile){
		//ADD TODO
		return creator.createEBX(ebxFile);
	}
	
	public EBXStructureFile getStructureFileByGUID(String fileGUID, boolean tryLoad, boolean loadOriginal){
		for (EBXStructureFile strFile : ebxStructureFiles){
			if (strFile.getEBXGUID().equalsIgnoreCase(fileGUID)){
				return strFile;
			}
		}
		
		EBXFile ebxFile = getEBXFileByGUID(fileGUID, tryLoad, loadOriginal);
		if (ebxFile!=null){
			EBXStructureFile strFile = readEBXStructureFile(ebxFile);
			if (strFile!=null){
				return strFile;
			}else{
				System.err.println("EBXFile for 'StructureFile from GUID' could not get converted.");
				return null;
			}
		}
		System.err.println("EBXStructureFile with GUID "+fileGUID+" was not found or could not get created!");
		return null;
	}
	
	
	public EBXStructureEntry getStructureInstance(EBXExternalGUID externalGUID, boolean tryLoad, boolean loadOriginal){
		if (externalGUID==null){return null;}
		EBXStructureFile targetFile = getStructureFileByGUID(externalGUID.getFileGUID(), tryLoad, loadOriginal);
		if (targetFile!=null){
			for (EBXStructureInstance instance : targetFile.getInstances()){
				if (instance.getGuid().equalsIgnoreCase(externalGUID.getInstanceGUID())){
					return instance;
				}	
			}
			System.err.println("The instance "+externalGUID.getInstanceGUID()+" does not exist inside the StrucutureFile. "+targetFile.getStructureName());
			return null;
		}
		System.err.println("Unable to get StructureInstance from a Structure, that not exist!");
		return null;
	}
	
	public EBXStructureFile readEBXStructureFile(EBXFile ebxFile){
		EBXStructureFile existingFile = getEBXStructureFileByGUID(ebxFile.getGuid(), false, false, true/*Look Only*/);
		if (existingFile!=null){
			return existingFile;
		}
		EBXStructureFile file = EBXStructureReader.readStructure(ebxFile);
		if (file!=null){
			if (ebxStructureFiles!=null){
				ebxStructureFiles.add(file);
			}
			return file;
		}
		return null;
	}
	public EBXStructureFile getEBXStructureFileByGUID(String fileGUID, boolean tryLoad, boolean loadOriginal, boolean lookOnly){
		for (EBXStructureFile file : ebxStructureFiles){
			if (file.getEBXGUID().equalsIgnoreCase(fileGUID)){
				return file;
			}
		}
		if (!lookOnly){
			EBXFile ebxFile = getEBXFileByGUID(fileGUID, tryLoad, loadOriginal);
			if (ebxFile!=null){
				return readEBXStructureFile(ebxFile);
			}
		}
		return null;
	}
	
	public EBXFile getEBXFileByGUID(String fileGUID, boolean tryLoad, boolean loadOriginal){
		if (fileGUID==null){return null;}
		for (EBXExternalFileReference efr : ebxFiles.keySet()){
			if (efr.getGuid().equalsIgnoreCase(fileGUID)){
				return ebxFiles.get(efr);
			}
		}
		if (tryLoad){	
			byte[] data = getEBXFileBytesByGUID(fileGUID, loadOriginal);
			if (data==null){
				return null;
			}
			EBXFile ebxFile = loadFile(data);
			if (ebxFile!=null){
				return ebxFile;
			}else{
				System.err.println("EBXFile's data was found, but could not be converted.");
			}
		}
		//System.err.println("EBXFile could not be found.");
		return null;
		
	}
	
	public byte[] getEBXFileBytesByGUID(String fileGUID, boolean loadOriginal){
		ResourceLink targetLink = null;
		for (ResourceLink ebxLink : Core.getGame().getCurrentSB().getEbx()){
			if (ebxLink.getEbxFileGUID()!=null){
				if (ebxLink.getEbxFileGUID().equalsIgnoreCase(fileGUID)){
					targetLink = ebxLink;
					break;
				}
			}else{
				System.err.println(ebxLink.getName()+" could not be read!");
			}
		}
		if (targetLink==null){
			System.err.println("EBXFile not found. No ResourceLink with FileGUID "+fileGUID+" does exist.");
			return null;
		}
		byte[] data = Core.getGame().getResourceHandler().readResourceLink(targetLink, loadOriginal);
		return data;
	}
	
	public EBXFile getEBXFileByTrueFileName(String trueFileName){
		for (EBXExternalFileReference efr : ebxFiles.keySet()){
			if (efr.getTrueFileName().equalsIgnoreCase(trueFileName)){
				return ebxFiles.get(efr);
			}
		}
		System.err.println("No EBXFile with trueFileName "+trueFileName+" was found.");
		return null;
	}
	
	public EBXFile getEBXFileByResourceName(String resourceName, boolean tryLoad, boolean loadOrignal){
		for (ResourceLink link : Core.getGame().getCurrentSB().getEbx()){
			if (link.getName().equalsIgnoreCase(resourceName)){
				return getEBXFileByGUID(link.getEbxFileGUID(), tryLoad, loadOrignal);
			}
		}
		System.err.println("No ResourceLink was found for "+resourceName);
		return null;
	}
	
	public String getEBXGUIDByResourceName(String resLinksName){
		for (ResourceLink resLink : Core.getGame().getCurrentSB().getEbx()){
			if (resLink.getName().equalsIgnoreCase(resLinksName)){
				return resLink.getEbxFileGUID();
			}
		}
		return null;
	}
	
	/*GETTER AND SETTER*/

	public ArrayList<EBXStructureFile> getEBXStructureFiles() {
		return ebxStructureFiles;
	}	

	public EBXLoader getLoader() {
		return loader;
	}

	public EBXCreator getCreator() {
		return creator;
	}
	
	public HashMap<EBXExternalFileReference, EBXFile> getEBXFiles() {
		return ebxFiles;
	}

	public EBXModifyHandler getModifyHandler() {
		return modifyHandler;
	}
	
	
}
