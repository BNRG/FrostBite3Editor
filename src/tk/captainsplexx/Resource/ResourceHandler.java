package tk.captainsplexx.Resource;

import tk.captainsplexx.Render.TextureHandler;
import tk.captainsplexx.Resource.CAS.CasCatManager;
import tk.captainsplexx.Resource.EBX.EBXHandler;

public class ResourceHandler {
	//public String chunkFolderPath;
	
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

	public ResourceHandler(/*String chunkFolderPath, String guidTablePath*/) {
		//this.chunkFolderPath = chunkFolderPath;
		this.mcL = new MeshChunkLoader();
		this.ebxHandler = new EBXHandler(/*guidTablePath*/);
		this.mvdH = new MeshVariationDatabaseHandler(this.ebxHandler);
		this.textureHandler = new TextureHandler();
		this.cCatManager = new CasCatManager();
		this.patchedCasCatManager = new CasCatManager();
	}
	
	
	public CasCatManager getPatchedCasCatManager() {
		return patchedCasCatManager;
	}


	public MeshChunkLoader getMeshChunkLoader() {
		return mcL;
	}
	
	/*public String getChunkFolderPath() {
		return chunkFolderPath;
	}*/
	

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
