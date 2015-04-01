package tk.captainsplexx.Resource;

import tk.captainsplexx.EBX.EBXHandler;
import tk.captainsplexx.Render.TextureHandler;

public class ResourceHandler {
	//public String chunkFolderPath;
	
	public MeshChunkLoader mcL;
	public ChunkFinder ckF;
	public MeshVariationDatabaseHandler mvdH;
	public EBXHandler ebxHandler;
	public TextureHandler textureHandler;

	public ResourceHandler(/*String chunkFolderPath,*/ String guidTablePath) {
		//this.chunkFolderPath = chunkFolderPath;
		this.mcL = new MeshChunkLoader();
		this.ckF = new ChunkFinder();
		this.ebxHandler = new EBXHandler(guidTablePath);
		this.mvdH = new MeshVariationDatabaseHandler(this.ebxHandler);
		this.textureHandler = new TextureHandler();
	}
	

	public MeshChunkLoader getMeshChunkLoader() {
		return mcL;
	}
	
	public ChunkFinder getChunkFinder(){
		return ckF;
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
	
	
	
	
	
}
