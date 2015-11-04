package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public class EBXObjectBlueprint extends EBXStructureEntry{
	
	private EBXObjInstanceGUID object = null;

	public EBXObjectBlueprint(EBXStructureEntry parent, EBXComplex complex) {
		super(parent, EntryType.ObjectBlueprint);
		for (EBXField field : complex.getFields()) {
			switch (field.getFieldDescritor().getName()) {
			case "Object": /* -------------- Object -------------- */
				this.object = new EBXObjInstanceGUID((String) field.getValue());
				break;
			default:
				System.err.println(field.getFieldDescritor().getName()+" is not handled in EBXObjectBlueprint's Constructor for readIn!");
				break;
			}
		}
	}

	public EBXObjInstanceGUID getObject() {
		return object;
	}
}
