package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureInstance;

public class EBXObjArray {
	private Object[] objects = null;
	private ArrayType type = null;
	private EBXStructureInstance parent;
	
	public static enum ArrayType{
		InstanceGUID, ExternalGUID //Complex, Objects, blah blah
	};
	
	public EBXObjArray(Object[] objects, EBXStructureInstance parent){
		this.objects = objects;
		this.parent = parent;		
	}
	
	public EBXObjArray(EBXField[] fields, EBXStructureInstance parent){
		this.parent = parent;
		if (fields.length>0){
			switch (fields[0].getType()){
				case ExternalGuid:
					this.type = ArrayType.ExternalGUID;
					this.objects = new Object[fields.length];
					for (int i=0; i<fields.length; i++){
						objects[i] = new EBXExternalGUID(fields[i]);
					}
					break;
				case Guid:
					this.type = ArrayType.InstanceGUID;
					this.objects = new Object[fields.length];
					for (int i=0; i<fields.length; i++){
						objects[i] = new EBXObjInstanceGUID((String) fields[i].getValue());
					}
					break;
				/*case Hex8:
					this.type = ArrayType.InstanceGUID;
					this.objects = new Object[fields.length];
					for (int i=0; i<fields.length; i++){
						objects[i] = new EBXObjInstanceGUID((String) fields[i].getValue());
					}
					break;*/
				default:
					System.err.println("EBXObjArray has unhandled types in his constructor! "+fields[0].getType().toString());
					break;
			}
		}
	}

	public Object[] getObjects() {
		return objects;
	}

	public ArrayType getType() {
		return type;
	}

	public EBXStructureInstance getParent() {
		return parent;
	}
	
	
	
	
	
}
