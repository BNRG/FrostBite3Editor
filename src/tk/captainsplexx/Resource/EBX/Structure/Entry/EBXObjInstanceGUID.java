package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureFile;

public class EBXObjInstanceGUID {
	private String guid;
	
	
	public EBXObjInstanceGUID(String internalGuid){
		this.guid = internalGuid;
	}
	
	public EBXStructureEntry followInternalGUID(EBXStructureFile ebxStructureFile){
		return ebxStructureFile.getEntryFromInstanceGUID(guid);		
	}
	
	public String getGUID(){
		return guid;
	}
}
