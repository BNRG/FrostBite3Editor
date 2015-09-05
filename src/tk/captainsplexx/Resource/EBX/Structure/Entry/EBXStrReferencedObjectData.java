package tk.captainsplexx.Resource.EBX.Structure.Entry;

import java.util.HashMap;

import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.EBXFieldDescriptor;
import tk.captainsplexx.Resource.EBX.EBXHandler.FieldValueType;
import tk.captainsplexx.Resource.EBX.EBXInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;

public class EBXStrReferencedObjectData extends EBXStructureEntry {

	private EBXExternalGUID blueprint = null;
	private EBXObjBlueprintTransform blueprintTransform = null;
	private EBXExternalGUID objectVariation = null;
	private EBXObjEnum streamRealm = null;
	private EBXObjEnum radiosityTypeOverride = null;
	private boolean castSunShadowEnable = false;
	private boolean castReflectionEnable = false;
	private boolean excluded = false;

	public EBXStrReferencedObjectData(EBXInstance ebxInstance) {
		super(EntryType.ReferenceObjectData, ebxInstance.getGuid());
		for (EBXField instanceEntry : ebxInstance.getComplex().getFields()) {
			switch (instanceEntry.getFieldDescritor().getName()) {
			case "Blueprint": /* -------------- Blueprint -------------- */
				if (instanceEntry.getType() == FieldValueType.ExternalGuid) {
					String[] splitString = ((String) instanceEntry.getValue()).split(" ");
					if (splitString.length == 2) {
						EBXExternalGUID exGUID = new EBXExternalGUID(
								splitString[0], splitString[1]);
						this.blueprint = exGUID;
					} else {
						System.err.println("Invalid External GUID length in EBXStrReferencedObjectData's Constructor!");
					}
				}
				break;
			case "BlueprintTransform": /* -------------- BlueprintTransform -------------- */
				this.blueprintTransform = new EBXObjBlueprintTransform(instanceEntry.getValueAsComplex());
				break;
			case "ObjectVariation": /* -------------- BlueprintTransform -------------- */
				if (instanceEntry.getType() == FieldValueType.ExternalGuid) {
					String[] splitString = ((String) instanceEntry.getValue()).split(" ");
					if (splitString.length == 2) {
						EBXExternalGUID exGUID = new EBXExternalGUID(
								splitString[0], splitString[1]);
						this.objectVariation = exGUID;
					} else {
						System.err.println("Invalid External GUID length in EBXStrReferencedObjectData's Constructor!");
					}
				}
				break;
			case "StreamRealm": /* -------------- StreamRealm -------------- */
				@SuppressWarnings("unchecked")
				HashMap<EBXFieldDescriptor, Boolean> hashMap = (HashMap<EBXFieldDescriptor, Boolean>) instanceEntry.getValue();
				HashMap<String, Boolean> streamRealmEnums = new HashMap<>();
				for (EBXFieldDescriptor fieldDescriptor : hashMap.keySet()){
					Boolean bool = hashMap.get(fieldDescriptor);
					streamRealmEnums.put(fieldDescriptor.getName(), bool);
				}		
				this.streamRealm = new EBXObjEnum(streamRealmEnums);
				break;
			case "RadiosityTypeOverride": /* -------------- RadiosityTypeOverride -------------- */
				@SuppressWarnings("unchecked")
				HashMap<EBXFieldDescriptor, Boolean> hashMap1 = (HashMap<EBXFieldDescriptor, Boolean>) instanceEntry.getValue();
				HashMap<String, Boolean> radiosityTypeOverrideEnums = new HashMap<>();
				for (EBXFieldDescriptor fieldDescriptor : hashMap1.keySet()){
					Boolean bool = hashMap1.get(fieldDescriptor);
					radiosityTypeOverrideEnums.put(fieldDescriptor.getName(), bool);
				}		
				this.radiosityTypeOverride = new EBXObjEnum(radiosityTypeOverrideEnums);
				break;
			case "CastSunShadowEnable": /* -------------- CastSunShadowEnable -------------- */
				this.castSunShadowEnable = (boolean) instanceEntry.getValue();
				break;
			case "CastReflectionEnable": /* -------------- CastReflectionEnable -------------- */
				this.castReflectionEnable = (boolean) instanceEntry.getValue();
				break;
			case "Excluded": /* -------------- Excluded -------------- */
				this.excluded = (boolean) instanceEntry.getValue();
				break;
			}
		}

		// Read in

		// TODO LOGIC!

	}

	public EBXExternalGUID getBlueprint() {
		return blueprint;
	}

	public void setBlueprint(EBXExternalGUID blueprint) {
		this.blueprint = blueprint;
	}

	public EBXExternalGUID getObjectVariation() {
		return objectVariation;
	}

	public void setObjectVariation(EBXExternalGUID objectVariation) {
		this.objectVariation = objectVariation;
	}

	public EBXObjBlueprintTransform getBlueprintTransform() {
		return blueprintTransform;
	}

	public void setBlueprintTransform(
			EBXObjBlueprintTransform blueprintTransform) {
		this.blueprintTransform = blueprintTransform;
	}

	public EBXObjEnum getStreamRealm() {
		return streamRealm;
	}

	public void setStreamRealm(EBXObjEnum streamRealm) {
		this.streamRealm = streamRealm;
	}

	public EBXObjEnum getRadiosityTypeOverride() {
		return radiosityTypeOverride;
	}

	public void setRadiosityTypeOverride(EBXObjEnum radiosityTypeOverride) {
		this.radiosityTypeOverride = radiosityTypeOverride;
	}

	public boolean isCastSunShadowEnable() {
		return castSunShadowEnable;
	}

	public void setCastSunShadowEnable(boolean castSunShadowEnable) {
		this.castSunShadowEnable = castSunShadowEnable;
	}

	public boolean isCastReflectionEnable() {
		return castReflectionEnable;
	}

	public void setCastReflectionEnable(boolean castReflectionEnable) {
		this.castReflectionEnable = castReflectionEnable;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

}
