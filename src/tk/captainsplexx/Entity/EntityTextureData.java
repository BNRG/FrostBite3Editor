package tk.captainsplexx.Entity;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.EBX.EBXExternalGUID;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureFile;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureInstance;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXMeshVariationDatabaseEntry;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXMeshVariationDatabaseMaterial;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXObjArray;
import tk.captainsplexx.Resource.EBX.Structure.Entry.EBXTextureShaderParameter.ParameterName;
import tk.captainsplexx.Resource.MESH.MeshVariationDatabaseHandler;
import tk.captainsplexx.Resource.TOC.ResourceLink;

public class EntityTextureData {
	private int[] diffuseIDs;
	
	private EBXExternalGUID meshGUID = null;
	//private EBXExternalGUID materialGUID = null;
	
	public EntityTextureData(EBXExternalGUID meshGUID, /*EBXExternalGUID materialGUID,*/ EBXStructureFile meshVariationDatabase) {
		this.meshGUID = meshGUID;
		//this.materialGUID = materialGUID;
		updateTextures(meshVariationDatabase);
	}
	
	public boolean updateTextures(EBXStructureFile meshVariationDatabase){
		if (meshGUID!=null&&meshVariationDatabase!=null){
			MeshVariationDatabaseHandler dbH = Core.getGame().getResourceHandler().getMeshVariationDatabaseHandler();
			EBXMeshVariationDatabaseEntry entry = dbH.getVariationDatabaseEntry(meshVariationDatabase, -1, this.meshGUID, false);
			if (entry!=null){
				EBXObjArray materialsArr = entry.getMaterials();
				if (materialsArr!=null){
					Object[] materials = materialsArr.getObjects();
					diffuseIDs = new int[materials.length];
					int counter = 0;
					for (Object obj : materials){
						EBXMeshVariationDatabaseMaterial material = (EBXMeshVariationDatabaseMaterial) obj;
						boolean diffuseSuccess = false;
						if (material!=null){
							//Diffuse
							EBXExternalGUID diffuseTextureGUID = dbH.getTexture(material, ParameterName.Diffuse);
							if (diffuseTextureGUID!=null){
							ResourceLink diffuseLinkEBX = Core.getGame().getResourceHandler().getResourceLinkByEBXGUID(diffuseTextureGUID.getFileGUID());
								if (diffuseLinkEBX!=null){
									ResourceLink diffuseLinkITEXTURE = Core.getGame().getResourceHandler().getResourceLink(diffuseLinkEBX.getName(), ResourceType.ITEXTURE);
									if (diffuseLinkITEXTURE!=null){
										diffuseIDs[counter] = Core.getGame().getResourceHandler().getTextureHandler().loadITexture(diffuseLinkITEXTURE);
										diffuseSuccess = true;
									}
								}
							}
							
							//Specular
								
								
							//Normal
						}
						if (!diffuseSuccess){
							diffuseIDs[counter] = Core.getGame().getModelHandler().getLoader().getNotFoundID();
						}
						counter++;
					}
				}
			}
			return true;
		}
		this.diffuseIDs = null;
		return false;
	}

	public int[] getDiffuseIDs() {
		return diffuseIDs;
	}

	public EBXExternalGUID getMeshGUID() {
		return meshGUID;
	}
	
	
	
}
