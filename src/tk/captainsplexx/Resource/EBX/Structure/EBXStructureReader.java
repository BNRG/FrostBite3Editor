package tk.captainsplexx.Resource.EBX.Structure;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.EBX.EBXInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry.EntryType;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXStrReferencedObjectData;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXStrSpatialPrefabBlueprint;
import tk.captainsplexx.Resource.TOC.ResourceLink;

public class EBXStructureReader {
	
	public static EBXStructureFile readExternalGUIDTarget(EBXExternalGUID externalGUID, boolean useOriginalOnly){
		ResourceLink targetLink = null;
		for (ResourceLink ebxLink:Core.getGame().getCurrentSB().getEbx()){
			if (ebxLink.getEbxFileGUID().equals(externalGUID.getFileGUID())){
				targetLink = ebxLink;
				break;
			}
		}
		if (targetLink==null){
			System.err.println("Target "+externalGUID.getBothGUIDs()+" could not get resolved. No link does exist.");
		}
		byte[] data = Core.getGame().getResourceHandler().readResource(
				targetLink.getBaseSha1(), targetLink.getDeltaSha1(), targetLink.getSha1(),
				targetLink.getCasPatchType(), targetLink.getName(),
				targetLink.getType(), useOriginalOnly);
		if (data==null){
			return null;
		}
		EBXFile ebxFile = Core.getGame().getResourceHandler().getEBXHandler().loadFile(data);	
		return EBXStructureReader.readStructure(ebxFile);
	}
	
	
	public static EBXStructureFile readStructure(EBXFile ebxFile){
		EBXStructureFile structFile = new EBXStructureFile(ebxFile.getTruePath());
		for (EBXInstance instance : ebxFile.getInstances()){
			EBXStructureEntry entry = readEntry(instance);
			if (entry!=null){
				structFile.getEntries().add(entry);
			}else{
				//return null;
			}
		}
		return structFile;
	}
	
	private static EBXStructureEntry readEntry(EBXInstance ebxEntry){
		try{
			EBXStructureEntry entry = null;
/*INFO	System.out.println("Reading: "+ebxEntry.getComplex().getComplexDescriptor().getName()+" - "+ebxEntry.getGuid());*/
			String name = ebxEntry.getComplex().getComplexDescriptor().getName();
			EntryType type = EBXStructureEntry.getTypeFromInstance(name);
			if (type!=null){
				switch (type) {
					case ReferenceObjectData:
						entry = new EBXStrReferencedObjectData(ebxEntry);
						break;
					case SpatialPrefabBlueprint:
						entry = new EBXStrSpatialPrefabBlueprint(ebxEntry);
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
			System.err.println("EBXStructureEntry could net get read in!");
			e.printStackTrace();
			return null;
		}
	}	
}
