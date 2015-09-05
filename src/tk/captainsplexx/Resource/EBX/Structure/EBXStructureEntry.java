package tk.captainsplexx.Resource.EBX.Structure;

public abstract class EBXStructureEntry {	
	private EntryType type;
	private String guid;
	
	public static enum EntryType {
		ReferenceObjectData, WorldPartReferenceObjectData, SubWorldInclusionSetting,
		InterfaceDescriptorData, SubWorldData, LightProbeVolumeData, PointLightEntityData,
		CameraEntityData, EffectReferenceObjectData, GroundHeightEntityData, TerrainPhysicsComponentData,
		TerrainEntityData, LocatorEntityData, MapMarkerEntityData, VehicleSpawnReferenceObjectData,
		OBBData, VolumeVectorShapeData, BoolEntityData, AndEntityData, LogicReferenceObjectData, DelayEntityData,
		RandomMultiEventEntityData, LogicVisualEnvironmentEntityData, PlatformSplitterEntityData,
		OrEntityData, TransformEntityData, SyncedBoolEntityData, FadeEntityData,
		UINPXTooltipEntityData, SequenceEntityData, CompareBoolEntityData, TransformPartPropertyTrackData,
		UINPXTooltipLine, UINPXTextLine, UINPXPaddingLine, WorldPartData
	}

	public EBXStructureEntry(EntryType type, String guid) {
		this.type = type;
		this.guid = guid;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public static EntryType getTypeFromInstance(String name) {
		try{
			return EntryType.valueOf(name);
		}catch (Exception e){
			return null;
		}
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
	
	
}
