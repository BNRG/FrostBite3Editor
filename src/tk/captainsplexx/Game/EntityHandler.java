package tk.captainsplexx.Game;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Render.ModelHandler;
import tk.captainsplexx.Resource.MeshChunkLoader;
import tk.captainsplexx.Resource.ResourceHandler;

public class EntityHandler {
	public enum Type {
	    Object,
	}
		
	ArrayList <Entity> entities = new ArrayList <Entity>();
	
	public int MAX_TEXTURES = 10000;
	
	public ModelHandler modelHandler;
	public ResourceHandler resourceHandler;
	
	public EntityHandler(ModelHandler modelHandler, ResourceHandler resourceHandler) {
		this.modelHandler = modelHandler;
		this.resourceHandler = resourceHandler;
	}


	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	
	public Entity getEntity(Type type) {
		for (Entity e : entities){
			if (e.type == type){
				return e;
			}
		}
		return null;
	}
	
	public int createEntityFromMesh(String subpathMesh, String pathRoot, String textureRoot){
		try{
			MeshChunkLoader msl = resourceHandler.getMeshChunkLoader();
			//msl.loadFile(pathRoot+subpathMesh, resourceHandler.getChunkFinder().findChunkFile(pathRoot+subpathMesh,resourceHandler)); //input for chunk file.
			String[] texturedModel = new String[msl.getSubMeshCount()];
			ArrayList<String> materials = resourceHandler.getMeshVariationDatabaseHandler().getMaterials(msl.getName(), 0); //VARIATION ID ??!
			for (int submesh=0; submesh<msl.getSubMeshCount();submesh++){
				RawModel model = modelHandler.addRawModel(GL11.GL_TRIANGLES, msl.getName()+submesh, msl.getVertexPositions(submesh), msl.getUVCoords(submesh), msl.getIndices(submesh));
				
				int textureID = modelHandler.getLoader().getNotFoundID();
				if (materials!=null){
					if (!materials.isEmpty()){
						try{
							if (resourceHandler.getTextureHandler().isExisting(materials.get(submesh))){
								textureID = resourceHandler.getTextureHandler().getTextureID(materials.get(submesh));
							}else{
								if (entities.size()<MAX_TEXTURES && !materials.get(submesh).equals("")){
									textureID = modelHandler.getLoader().loadTexture(textureRoot+"res/"+materials.get(submesh)+".jpg"); 
									resourceHandler.getTextureHandler().addTextureID(textureID, materials.get(submesh));
								}
							}
						}catch(Exception e){
							System.err.println("Problem while loding Submesh Texture "+subpathMesh+" Submesh: "+submesh);
						}
					}
				}
				texturedModel[submesh] = (modelHandler.addTexturedModel(model, textureID));
			}
			return entities.indexOf(entities.add(new Entity(Type.Object, msl.getName(), texturedModel)));
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Could not create entitiy: "+subpathMesh);
			return -1;
		}
	}
	
}
