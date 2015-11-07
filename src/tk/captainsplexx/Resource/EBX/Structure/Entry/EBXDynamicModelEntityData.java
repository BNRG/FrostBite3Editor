package tk.captainsplexx.Resource.EBX.Structure.Entry;

import java.util.HashMap;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public class EBXDynamicModelEntityData extends EBXStructureEntry{

	//$
	private EBXExternalGUID mesh = null;
	//DestructiblePartCount
	private boolean noCollision = false;
	
	
	public EBXDynamicModelEntityData(EBXStructureEntry parent, EBXComplex complex) {
		super(parent, EntryType.DynamicModelEntityData);
		
		for (EBXField field : complex.getFields()) {
			switch (field.getFieldDescritor().getName()) {
			case "Mesh": /* -------------- Mesh -------------- */
				this.mesh = new EBXExternalGUID(field);
				break;
			case "NoCollision": /* -------------- NoCollision -------------- */
				this.noCollision = (boolean) field.getValue();
				break;
			}
		}
	}


	public EBXExternalGUID getMesh() {
		return mesh;
	}




	public boolean hasNoCollision() {
		return noCollision;
	}


	public void setNoCollision(boolean noCollision) {
		this.noCollision = noCollision;
	}
	
	
	
	
	

}
