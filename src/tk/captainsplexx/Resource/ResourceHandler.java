package tk.captainsplexx.Resource;

import java.io.File;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Mod.ModTools;
import tk.captainsplexx.Mod.Package;
import tk.captainsplexx.Mod.PackageEntry;
import tk.captainsplexx.Render.TextureHandler;
import tk.captainsplexx.Resource.CAS.CasCatManager;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.EBX.EBXHandler;
import tk.captainsplexx.Resource.MESH.MeshChunkLoader;
import tk.captainsplexx.Resource.MESH.MeshVariationDatabaseHandler;
import tk.captainsplexx.Resource.TOC.ResourceLink;

public class ResourceHandler {	
	public static enum ResourceType{ EBX, CHUNK, ITEXTURE, MESH, HKDESTRUCTION, HKNONDESTRUCTION, ANT,
				ANIMTRACKDATA, RAGDOLL, OCCLUSIONMESH,
			LIGHTINGSYSTEM, GFX, STREAIMINGSTUB, ENLIGHTEN, PROBESET, STATICENLIGHTEN,
		SHADERDATERBASE, SHADERDB, SHADERPROGRAMDB, LUAC, UNDEFINED
	};
		
	public static enum LinkBundleType{BUNDLES, CHUNKS};
	
	MeshChunkLoader mcL;
	MeshVariationDatabaseHandler mvdH;
	EBXHandler ebxHandler;
	TextureHandler textureHandler;
	CasCatManager cCatManager; 
	CasCatManager patchedCasCatManager;

	public ResourceHandler() {
		this.mcL = new MeshChunkLoader();
		this.ebxHandler = new EBXHandler();
		this.mvdH = new MeshVariationDatabaseHandler();
		this.textureHandler = new TextureHandler();
		this.cCatManager = new CasCatManager();
		this.patchedCasCatManager = new CasCatManager();
	}
	
	public void resetEBXRelated(){
		ebxHandler.reset();
		mvdH.reset();
		Core.getJavaFXHandler().getMainWindow().destroyEBXWindows();
	}
	
	public ResourceLink getResourceLinkByEBXGUID(String ebxGUID){
		for (ResourceLink link : Core.getGame().getCurrentSB().getEbx()){
			if (link.getEbxFileGUID()!=null){
				if (link.getEbxFileGUID().equalsIgnoreCase(ebxGUID)){
					return link;
				}
			}
		}
		//System.err.println("ResourceLink not found for EBXGUID "+ebxGUID+"!");
		return null;
	}
	
	public ResourceLink getResourceLink(String resourceName, ResourceType resourceType){
		if (resourceType==ResourceType.EBX){
			for (ResourceLink resLinkEBX : Core.getGame().getCurrentSB().getEbx()){
				if (resLinkEBX.getName().equalsIgnoreCase(resourceName)){
					return resLinkEBX;
				}
			}
		}else{
			for (ResourceLink resLinkRES : Core.getGame().getCurrentSB().getRes()){
				if (resLinkRES.getName().equalsIgnoreCase(resourceName)){
					return resLinkRES;
				}
			}
		}
		return null;
	}
		
	public byte[] readResourceLink(ResourceLink link, boolean useOriginal){
		System.out.println("Reading Link: "+link.getName()+" - Original only:("+useOriginal+")");
		return readResource(link.getBaseSha1(), link.getDeltaSha1(), link.getSha1(), link.getCasPatchType(),
				link.getName(), link.getType(), useOriginal);
	}	
	public byte[] readResourceLink(ResourceLink link){
		return readResourceLink(link, false);
	}
	public byte[] readResource(String baseSHA1, String deltaSHA1, String SHA1, int casPatchType,
			String name, ResourceType resourceType, boolean useOriginal)
		{
		byte[] data = null;
		if (!useOriginal && name!=null){//should get patched files used ? (default: true)
			File modFilePack = new File(FileHandler.normalizePath(
					Core.getGame().getCurrentMod().getPath()+ModTools.PACKAGEFOLDER+
					Core.getGame().getCurrentFile().replace(Core.gamePath, "")+ModTools.PACKTYPE)
			);
			Package modPackage = null;
			if (modFilePack.exists()){
				modPackage = Core.getModTools().readPackageInfo(modFilePack);
			}
			
			if (modPackage!=null){
				//package was found. letz find the entry!
				for (PackageEntry entry : modPackage.getEntries()){
					if (entry.getSubPackage().equalsIgnoreCase(Core.getGame().getCurrentSB().getPath())&&//mp_playground/content
						entry.getResourcePath().equalsIgnoreCase(name+"."+resourceType))//levels/mp_playground/content/layer2_buildings.ebx .itexture .mesh
					{
						//we are in the right package and an entry was found ;)
						System.err.println("Mod file was found, this is our resource!");
						data = FileHandler.readFile(Core.getGame().getCurrentMod().getPath()+ModTools.RESOURCEFOLDER+entry.getResourcePath());
						if (data!=null){
							return data;
						}
					}
				}
			}
		}
		System.err.println("No Mod file does exist for this resource, use ORIGINAL data from Game.");
		data = CasDataReader.readCas(baseSHA1, deltaSHA1, SHA1, casPatchType);
		//hope here, that it was found!
		return data;
	}
	
	
	public CasCatManager getPatchedCasCatManager() {
		return patchedCasCatManager;
	}


	public MeshChunkLoader getMeshChunkLoader() {
		return mcL;
	}
	

	public MeshVariationDatabaseHandler getMeshVariationDatabaseHandler() {
		return mvdH;
	}

	public EBXHandler getEBXHandler() {
		return ebxHandler;
	}

	public TextureHandler getTextureHandler() {
		return textureHandler;
	}


	public CasCatManager getCasCatManager() {
		return cCatManager;
	}
	
}
