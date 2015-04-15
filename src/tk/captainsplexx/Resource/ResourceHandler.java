package tk.captainsplexx.Resource;

import tk.captainsplexx.CAS.CasCatManager;
import tk.captainsplexx.EBX.EBXHandler;
import tk.captainsplexx.Render.TextureHandler;

public class ResourceHandler {
	//public String chunkFolderPath;
	
	public static enum ResourceType{DDS, EBX, CHUNK, SOUND, ITEXTURE, MESH, HKDESTRUCTION, HKNONDESTRUCTION, ANT,
				ANIMTRACKDATA, RAGDOLL, OCCLUDERMESH,
			LIGHTINGSYSTEM, GFX, STREAIMINGSTUB, ENLIGHTEN, PROBESET, STATICENLIGHTEN,
		SHADERDATERBASE, SHADERDB, SHADERPROGRAMDB, LUAC, UNDEFINEDSPLEXX
	};
	
	public MeshChunkLoader mcL;
	public MeshVariationDatabaseHandler mvdH;
	public EBXHandler ebxHandler;
	public TextureHandler textureHandler;
	public CasCatManager cCatManager; 

	public ResourceHandler(/*String chunkFolderPath,*/ String guidTablePath) {
		//this.chunkFolderPath = chunkFolderPath;
		this.mcL = new MeshChunkLoader();
		this.ebxHandler = new EBXHandler(guidTablePath);
		this.mvdH = new MeshVariationDatabaseHandler(this.ebxHandler);
		this.textureHandler = new TextureHandler();
		this.cCatManager = new CasCatManager();
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
