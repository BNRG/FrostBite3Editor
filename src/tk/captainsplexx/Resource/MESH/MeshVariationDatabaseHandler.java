package tk.captainsplexx.Resource.MESH;

import java.util.ArrayList;
import java.util.HashMap;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.EBXHandler;
import tk.captainsplexx.Resource.EBX.EBXInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureFile;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureInstance;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXMeshVariationDatabase;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXMeshVariationDatabaseEntry;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXMeshVariationDatabaseMaterial;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXMeshVariationDatabaseRedirectEntry;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXObjArray;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXTextureShaderParameter;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXTextureShaderParameter.ParameterName;

public class MeshVariationDatabaseHandler {
	public ArrayList<EBXStructureFile> databases;
	
	public void reset(){
		databases = new ArrayList<>();
	}
	
	public void addDatabase(EBXStructureFile structFile){
		databases.add(structFile);
	}
	
	public EBXMeshVariationDatabaseEntry getVariationDatabaseEntry(EBXStructureFile databaseFile, long variationAssetNameHash, EBXExternalGUID meshExternalGUID, boolean excludeRedirect){
		if (variationAssetNameHash<1&&meshExternalGUID==null){
			System.err.println("MeshVariationDataBaseHander can't fetch the Entry, no GUID or AssetNameHash given!");
			return null;
		}
		EBXStructureInstance instance = databaseFile.getFirstInstance(EntryType.MeshVariationDatabase);
		if (instance!=null){
			EBXMeshVariationDatabase database = (EBXMeshVariationDatabase) instance.getEntry();
			Object[] entries = database.getEntries().getObjects();
			if (entries!=null){
				if (meshExternalGUID!=null){
					//Try to fetch using mesh guid
					for (Object obj : entries){
						EBXMeshVariationDatabaseEntry entry = (EBXMeshVariationDatabaseEntry) obj;
						if (entry.getMesh().getInstanceGUID().equalsIgnoreCase(meshExternalGUID.getInstanceGUID())){
							return entry;
						}else{
							continue;
						}
					}
				}
				else if(variationAssetNameHash>1){
					//Try to fetch using AssetNameHash
					for (Object obj : entries){
						EBXMeshVariationDatabaseEntry entry = (EBXMeshVariationDatabaseEntry) obj;
						if (entry.getVariationAssetNameHash()==variationAssetNameHash){
							return entry;
						}else{
							continue;
						}
					}
				}
				if (!excludeRedirect){
					//Try to find the redirect
					EBXMeshVariationDatabaseRedirectEntry[] redirectEntries = (EBXMeshVariationDatabaseRedirectEntry[]) database.getRedirectEntries().getObjects();
					if (redirectEntries!=null){
						for (EBXMeshVariationDatabaseRedirectEntry redi : redirectEntries){
							if (redi.getMesh().getInstanceGUID().equalsIgnoreCase(meshExternalGUID.getInstanceGUID())){
								return getVariationDatabaseEntry(databaseFile, redi.getVariationAssetNameHash(), null, true);
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public EBXMeshVariationDatabaseMaterial getVariationDatabaseMaterial(EBXMeshVariationDatabaseEntry dbEntry, EBXExternalGUID materialExternalGUID){
		EBXObjArray materialsArr = dbEntry.getMaterials();
		if (materialsArr!=null){
			Object[] materials = materialsArr.getObjects();
			if (materials!=null){
				for (Object obj : materials){
					EBXMeshVariationDatabaseMaterial dbMaterial = (EBXMeshVariationDatabaseMaterial) obj;
					if (dbMaterial.getMaterial().getInstanceGUID().equalsIgnoreCase(materialExternalGUID.getInstanceGUID())){
						return dbMaterial;
					}else{
						continue;
					}
				}
			}			
		}
		return null;
	}
	
	public EBXExternalGUID getTexture(EBXMeshVariationDatabaseMaterial material, ParameterName parameterName){
		EBXObjArray parameters = material.getTextureParameters();
		if (parameters!=null){
			Object[] tspArr = parameters.getObjects();
			if (tspArr!=null){
				for (Object obj : tspArr){
					EBXTextureShaderParameter parameter = (EBXTextureShaderParameter) obj;
					if (parameter.getParameterName()!=null){
						if (parameter.getParameterName().equalsIgnoreCase(parameterName.toString())){
							return parameter.getValue();
						}else{
							continue;
						}
					}
				}
			}
		}
		return null;
	}
}
