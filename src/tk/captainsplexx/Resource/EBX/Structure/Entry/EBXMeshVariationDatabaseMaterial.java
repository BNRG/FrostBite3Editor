package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public class EBXMeshVariationDatabaseMaterial extends EBXStructureEntry{
	private EBXExternalGUID material = null;
	private EBXExternalGUID materialVariation = null;
	private EBXObjArray textureParameters = null;
	
	public EBXMeshVariationDatabaseMaterial(EBXStructureEntry parent, EBXComplex complex) {
		super(parent, EntryType.MeshVariationDatabaseMaterial);
		
		for (EBXField field : complex.getFields()) {
			switch (field.getFieldDescritor().getName()) {
			case "Material": /* -------------- Material -------------- */
				this.material = new EBXExternalGUID(field);
				break;
			case "MaterialVariation": /* -------------- MaterialVariation -------------- */
				this.materialVariation = new EBXExternalGUID(field);
				break;
			case "TextureParameters": /* -------------- TextureParameters -------------- */
				if (field.getValue() instanceof String){
					this.textureParameters = null;
				}else{
					this.textureParameters = new EBXObjArray(field.getValueAsComplex().getFields(), parent);
				}
				break;
			}
		}
	}

	public EBXExternalGUID getMaterial() {
		return material;
	}

	public EBXExternalGUID getMaterialVariation() {
		return materialVariation;
	}

	public EBXObjArray getTextureParameters() {
		return textureParameters;
	}
	
	

	
	
}
