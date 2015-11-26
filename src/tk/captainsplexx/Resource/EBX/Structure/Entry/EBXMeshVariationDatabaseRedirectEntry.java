package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public class EBXMeshVariationDatabaseRedirectEntry extends EBXStructureEntry{
	private EBXExternalGUID mesh = null;
	private long variationAssetNameHash = 0;
	
	public EBXMeshVariationDatabaseRedirectEntry(EBXStructureEntry parent, EBXComplex complex) {
		super(parent, EntryType.MeshVariationDatabaseRedirectEntry);
		
		for (EBXField field : complex.getFields()) {
			switch (field.getFieldDescritor().getName()) {
			case "Mesh": /* -------------- Mesh -------------- */
				this.mesh = new EBXExternalGUID(field);
				break;
			case "VariationAssetNameHash": /* -------------- VariationAssetNameHash -------------- */
				this.variationAssetNameHash = (long) field.getValue();
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
	
}
