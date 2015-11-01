package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.EBXInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureFile;

public class EBXSpatialPrefabBlueprint extends EBXStructureEntry {
	//Skip Events
	//Skip Name
	private EBXObjArray objects = null;//Instance Reference!
	
	public EBXSpatialPrefabBlueprint(EBXStructureFile parent, EBXInstance ebxInstance) {
		super(parent, EntryType.SpatialPrefabBlueprint, ebxInstance.getGuid());
		
		for (EBXField field : ebxInstance.getComplex().getFieldAsComplex(0).getFields()) {//Everything is inside a PrefabBlueprint
			switch (field.getFieldDescritor().getName()) {
				case "Objects": /* -------------- Objects Array -------------- */
					this.objects = new EBXObjArray(field.getValueAsComplex().getFields());
					break;
				case "$":
					System.err.println("EBXSpatialPrefabBlueprint's construcotor found an unhandled type while read in '$'");
					break;
				default:
					System.err.println(field.getFieldDescritor().getName()+" is not handled in EBXSpatialPrefabBlueprint's Constructor for readIn!");
					break;
			}
		}
	}

	public EBXObjArray getObjectArray() {
		return objects;
	}
}


