package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public class EBXMeshVariationDatabaseEntry extends EBXStructureEntry{
	private EBXExternalGUID mesh = null;
	private long variationAssetNameHash = 0;
	private EBXObjArray materials = null;
	
	public EBXMeshVariationDatabaseEntry(EBXStructureEntry parent, EBXComplex complex) {
		super(parent, EntryType.MeshVariationDatabaseEntry);
		
		for (EBXField field : complex.getFields()) {
			switch (field.getFieldDescritor().getName()) {
			case "Mesh": /* -------------- Mesh -------------- */
				this.mesh = new EBXExternalGUID(field);
				break;
			case "VariationAssetNameHash": /* -------------- VariationAssetNameHash -------------- */
				this.variationAssetNameHash = (long) field.getValue();
				break;
			case "Materials": /* -------------- Materials -------------- */
				if (field.getValue() instanceof String){
					this.materials = null;
				}else{
					this.materials = new EBXObjArray(field.getValueAsComplex().getFields(), parent);
				}
				break;
			}
		}
	}

	public EBXExternalGUID getMesh() {
		return mesh;
	}

	public long getVariationAssetNameHash() {
		return variationAssetNameHash;
	}

	public EBXObjArray getMaterials() {
		return materials;
	}
	


	
	

	
	
}
