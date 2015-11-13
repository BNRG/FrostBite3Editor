package tk.captainsplexx.Resource.EBX.Structure;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.EBX.EBXInstance;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXBreakableModelEntityData;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXDynamicModelEntityData;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXObjectBlueprint;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXReferencedObjectData;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXSpatialPrefabBlueprint;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXStaticModelEntityData;

public class EBXStructureReader {
	
	public static enum EntryType {
		EBXInstance,
		
		ReferenceObjectData, WorldPartReferenceObjectData, SubWorldInclusionSetting,
		InterfaceDescriptorData, SubWorldData, LightProbeVolumeData, PointLightEntityData,
		CameraEntityData, EffectReferenceObjectData, GroundHeightEntityData, TerrainPhysicsComponentData,
		TerrainEntityData, LocatorEntityData, MapMarkerEntityData, VehicleSpawnReferenceObjectData,
		OBBData, VolumeVectorShapeData, BoolEntityData, AndEntityData, LogicReferenceObjectData, DelayEntityData,
		RandomMultiEventEntityData, LogicVisualEnvironmentEntityData, PlatformSplitterEntityData,
		OrEntityData, TransformEntityData, SyncedBoolEntityData, FadeEntityData,
		UINPXTooltipEntityData, SequenceEntityData, CompareBoolEntityData, TransformPartPropertyTrackData,
		UINPXTooltipLine, UINPXTextLine, UINPXPaddingLine, WorldPartData, SpatialPrefabBlueprint, ObjectBlueprint,
		VegetationTreeEntityData, StaticModelEntityData, DynamicModelEntityData, BreakableModelEntityData
	}
	
	public static EntryType getEntryTypeByName(String name) {
		try{
			return EntryType.valueOf(name);
		}catch (Exception e){
			return null;
		}
	}
		
	public static EBXStructureFile readStructure(EBXFile ebxFile){
		EBXStructureFile structFile = new EBXStructureFile(ebxFile.getTruePath(), ebxFile.getGuid());
		for (EBXInstance instance : ebxFile.getInstances()){
			EBXStructureInstance strInstance = readInstance(structFile, instance);
			if (strInstance!=null){
				structFile.getInstances().add(strInstance);
			}else{
				//return null;
			}
		}		
		return structFile;
	}
	
	private static EBXStructureInstance readInstance(EBXStructureFile parent, EBXInstance ebxInstance){
		EBXStructureInstance instance = new EBXStructureInstance(parent, ebxInstance.getGuid(), null) {};
		EBXStructureEntry entry = readEntry(instance, ebxInstance.getComplex());
		if (entry!=null){
			instance.setEntry(entry);
			return instance;
		}else{
			return null;
		}
	}
	
	private static EBXStructureEntry readEntry(EBXStructureInstance parent, EBXComplex ebxComplex){
		try{
			EBXStructureEntry entry = null;
			String name = ebxComplex.getComplexDescriptor().getName();
			EntryType type = getEntryTypeByName(name);
			if (type!=null){
				switch (type) {
					case ReferenceObjectData:
						entry = new EBXReferencedObjectData(parent, ebxComplex);
						break;
					case SpatialPrefabBlueprint:
						entry = new EBXSpatialPrefabBlueprint(parent, ebxComplex);
						break;
					case ObjectBlueprint:
						entry = new EBXObjectBlueprint(parent, ebxComplex);
						break;
					case StaticModelEntityData:
						entry = new EBXStaticModelEntityData(parent, ebxComplex);
						break;
					case DynamicModelEntityData:
						entry = new EBXDynamicModelEntityData(parent, ebxComplex);
						break;
					case BreakableModelEntityData:
						entry = new EBXBreakableModelEntityData(parent, ebxComplex);
						break;
				}
			}else{
				System.err.println("EBXStructureReader is INCLOMPLETE: "+name);
				return null;
			}
			if (entry==null){
				System.err.println("Unhandled Type in EBXStructureEntry-Reader: "+name);
				return null;
			}
			return entry;
		}catch (Exception e){
			System.err.println("EBXStructureEntry could not get read in!");
			e.printStackTrace();
			return null;
		}
	}	
}