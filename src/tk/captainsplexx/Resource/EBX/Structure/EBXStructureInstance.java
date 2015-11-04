package tk.captainsplexx.Resource.EBX.Structure;

import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public abstract class EBXStructureInstance extends EBXStructureEntry{
	private String guid;
	private EBXStructureFile parentFile;
	private EBXStructureEntry entry;
	
	public EBXStructureInstance(EBXStructureFile parentFile, String instanceGUID, EBXStructureEntry entry) {
		super(null, EntryType.EBXInstance);
		this.parentFile = parentFile;
		this.guid = instanceGUID;
		this.entry = entry;
	}

	public String getGuid() {
		return guid;
	}

	public EBXStructureFile getParentFile() {
		return parentFile;
	}

	public EBXStructureEntry getEntry() {
		return entry;
	}

	public void setEntry(EBXStructureEntry entry) {
		this.entry = entry;
	}
}
