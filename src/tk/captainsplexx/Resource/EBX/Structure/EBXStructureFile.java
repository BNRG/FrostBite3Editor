package tk.captainsplexx.Resource.EBX.Structure;

import java.util.ArrayList;

public class EBXStructureFile {
	private ArrayList<EBXStructureEntry> entries;
	private String structureName;
	
	public EBXStructureFile(String structureName){
		this.entries = new ArrayList<>();
		this.structureName = structureName;
	}
	public EBXStructureFile(String structureName, ArrayList<EBXStructureEntry> entries){
		this.structureName = structureName;
		this.entries = entries;
	}
	public String getStructureName() {
		return structureName;
	}
	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}
	public ArrayList<EBXStructureEntry> getEntries() {
		return entries;
	}
	
	
}
