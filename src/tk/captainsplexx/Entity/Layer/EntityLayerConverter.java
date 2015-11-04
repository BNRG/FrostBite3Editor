package tk.captainsplexx.Entity.Layer;

import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.Entity.EntityHandler;
import tk.captainsplexx.Entity.ObjectEntity;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Maths.Matrices;
import tk.captainsplexx.Maths.VectorMath;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.EBX.EBXHandler;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureFile;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXBlueprintTransform;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXObjArray;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXObjArray.ArrayType;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXObjInstanceGUID;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXObjectBlueprint;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXReferencedObjectData;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXSpatialPrefabBlueprint;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXStaticModelEntityData;
import tk.captainsplexx.Resource.TOC.ResourceLink;

public class EntityLayerConverter {
	
	public static final int scaleMultiplier = 4;
	
	public static EntityLayer getEntityLayer(EBXFile ebxFile){
		boolean loadOriginal = false;
		
		EntityHandler entityHandler = Core.getGame().getEntityHandler();
		EBXHandler ebxHandler = Core.getGame().getResourceHandler().getEBXHandler();
		
		EBXStructureFile structFile = ebxHandler.getStructureFileByGUID(ebxFile.getGuid(), true, loadOriginal/*don't load original*/);
		if (structFile!=null){
			String[] strArray = ebxFile.getTruePath().split("/");		
			EntityLayer layer = new EntityLayer(strArray[strArray.length-1]+" "+ebxFile.getGuid());
			
			for (EBXStructureInstance instance : structFile.getInstances()){
				Entity en = getEntity(instance.getEntry(), null, instance.getGuid(), loadOriginal);	
				if (en!=null){
					layer.getEntities().add(en);
				}
			}
			return layer;
		}
		System.err.println("Can not create EntityLayer in EntityLayerConverter from EBXFile!");
		return null;
	}
	
	private static Entity getEntity(EBXStructureEntry entry, Entity parentEntity, String guid, boolean loadOriginal){
		if (entry==null){return null;}
		
		EBXStructureInstance parentInstance = (EBXStructureInstance) entry.getParent();
		EBXStructureFile parentFile = null;
		if (entry.getType()==EntryType.EBXInstance){
			EBXStructureInstance thisInstance = (EBXStructureInstance) entry;
			parentFile = thisInstance.getParentFile();
		}else{
			if (parentInstance!=null){
				parentFile = parentInstance.getParentFile();
			}else{
				System.err.println("Parent not found in EntityLayerConverter!");
			}
		}
	
		Entity en = new ObjectEntity(entry.getType()+" "+guid, parentEntity, null);
		
		switch(entry.getType()){
			case EBXInstance:
				EBXStructureInstance instance = (EBXStructureInstance) entry;
				EBXStructureEntry instanceEntry = instance.getEntry();
				Entity instanceEntity = getEntity(instanceEntry, en, instanceEntry.getType()+" "+instance.getGuid(), loadOriginal);
				if (instanceEntity!=null){
					en.getChildrens().add(instanceEntity);
				}
				break;
			case ReferenceObjectData:
				EBXReferencedObjectData refObjData = (EBXReferencedObjectData) entry;
				EBXBlueprintTransform transform = refObjData.getBlueprintTransform();
				
				/*Vector3f scaling = transform.getScaling();
				if (scaling!=null){
					en.setScaling(scaling);
				}
				
				Vector3f rotation = transform.getRotation();
				if (rotation!=null){
					en.setRotation(rotation);
				}*/
				
				Vector3f position = transform.getTranformation();
				if (position!=null){
					position = VectorMath.multiply(position, new Vector3f(scaleMultiplier, scaleMultiplier, scaleMultiplier), null);
					en.setPosition(position);
				}
				
				handleExternalGUID(refObjData.getBlueprint(), en, loadOriginal);
				break;
			case SpatialPrefabBlueprint:
				EBXSpatialPrefabBlueprint spaPreBlueprint = (EBXSpatialPrefabBlueprint) entry;
				
				EBXObjArray objects = spaPreBlueprint.getObjectArray();
				if (objects.getType()==ArrayType.InstanceGUID){
					for (Object obj : objects.getObjects()){
						handleInternalGUID((EBXObjInstanceGUID) obj, en, parentFile, loadOriginal);
					}
				}else{
					//Other ArrayTypes
				}
				break;
			case ObjectBlueprint:
				EBXObjectBlueprint objBlueprint = (EBXObjectBlueprint) entry;
				handleInternalGUID((EBXObjInstanceGUID) objBlueprint.getObject(), en, parentFile, loadOriginal);
				break;
			case StaticModelEntityData:
				EBXStaticModelEntityData smed = (EBXStaticModelEntityData) entry;
				
				en = getEntity(smed.getMesh(), parentEntity);
				break;
		}
		if (en!=null){
			return en;
		}
		return null;
	}
	
	private static Entity getEntity(EBXExternalGUID externalGUID, Entity parentEntity){
		if (externalGUID==null){return null;};
		ResourceLink ebxLink = Core.getGame().getResourceHandler().getResourceLinkByEBXGUID(externalGUID.getFileGUID());
		if (ebxLink!=null){
			String resLinkname = ebxLink.getName();
			for (ResourceLink resLink : Core.getGame().getCurrentSB().getRes()){
				if (resLink.getName().equalsIgnoreCase(resLinkname)){
					byte[] meshData = Core.getGame().getResourceHandler().readResourceLink(resLink);
					if (meshData!=null){
						return Core.getGame().getEntityHandler().createEntity(meshData, resLinkname, parentEntity, "EntityLayerConverter's getEntity-Method!");
					}
				}
			}
		}
		return null;
	}
	
	private static void handleInternalGUID(EBXObjInstanceGUID instanceGUID, Entity parentEntity, EBXStructureFile parentFile, boolean loadOriginal){
		EBXStructureInstance followedInstance = (EBXStructureInstance) instanceGUID.followInternal(parentFile);
		if (followedInstance!=null){
			Entity iEntity = getEntity(followedInstance, parentEntity, followedInstance.getGuid(), loadOriginal);
			if (iEntity!=null){
				parentEntity.getChildrens().add(iEntity);
			}
		}
	}
	
	private static void handleExternalGUID(EBXExternalGUID externalGUID, Entity parentEntity, boolean loadOriginal){
		EBXStructureInstance target = (EBXStructureInstance) externalGUID.follow(true, loadOriginal);
		if (target!=null){
			Entity iEntity = getEntity(target.getEntry(), parentEntity, target.getGuid(), loadOriginal);
			if (iEntity!=null){
				parentEntity.getChildrens().add(iEntity);
			}
		}
	}
}
