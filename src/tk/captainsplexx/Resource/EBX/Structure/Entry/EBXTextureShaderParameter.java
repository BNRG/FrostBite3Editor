package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public class EBXTextureShaderParameter extends EBXStructureEntry{
	public static enum ParameterName {Diffuse, Normal, SpecSmooth, DetailNormalTexture,
		Specular, DetailNormal, Break_Diffuse, Break_Normal, SpecMask,
		BarkNormal, DiffuseBark, DiffuseLeaves, LeavesNormal};
	
	private String parameterName = null;
	private EBXExternalGUID value = null;
	
	public EBXTextureShaderParameter(EBXStructureEntry parent, EBXComplex complex) {
		super(parent, EntryType.TextureShaderParameter);
		
		for (EBXField field : complex.getFields()) {
			switch (field.getFieldDescritor().getName()) {
			case "ParameterName": /* -------------- ParameterName -------------- */
				this.parameterName = (String) field.getValue();
				break;
			case "Value": /* -------------- Value -------------- */
				this.value = new EBXExternalGUID(field);
				break;
			}
		}
	}

	public String getParameterName() {
		return parameterName;
	}

	public EBXExternalGUID getValue() {
		return value;
	}
	
	
}
