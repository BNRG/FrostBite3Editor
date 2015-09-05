package tk.captainsplexx.Resource.EBX.Structure;

import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.EBX.EBXInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry.EntryType;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXStrReferencedObjectData;

public class EBXStructureReader {
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
				}
			}else{
				System.err.println("EBXStructureEntity-Enum is INCLOMPLETE: "+name);
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
