package tk.captainsplexx.Entity;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Render.ModelHandler;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Resource.MESH.MeshChunkLoader;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;

public class EntityHandler {
	public enum Type {
	    Object,
	}
		
	ArrayList <Entity> entities = new ArrayList <Entity>();
	
	public int MAX_TEXTURES = 1000;
	
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
	
	public int createEntity(byte[] mesh, ConvertedSBpart convSBpart, String textureRoot, String desc){
		try{
			MeshChunkLoader msl = resourceHandler.getMeshChunkLoader();
			msl.loadFile(mesh, convSBpart);/*the chunk guid is located at 0xC8, but why the f*ck are the multiple ones? needs the loader work too?*/
			String[] texturedModel = new String[msl.getSubMeshCount()];
			//ArrayList<String> materials = resourceHandler.getMeshVariationDatabaseHandler().getMaterials(msl.getName(), 0); //VARIATION ID ??!
			for (int submesh=0; submesh<msl.getSubMeshCount();submesh++){
				RawModel model = modelHandler.addRawModel(GL11.GL_TRIANGLES, msl.getName()+submesh, msl.getVertexPositions(submesh), msl.getUVCoords(submesh), msl.getIndices(submesh));
				
				int textureID = modelHandler.getLoader().getNotFoundID();
				/*if (materials!=null){
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
				}*/
				texturedModel[submesh] = (modelHandler.addTexturedModel(model, textureID));
			}
			entities.add(new Entity(Type.Object, msl.getName(), texturedModel));
			return entities.size()-1;
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Could not create entitiy: "+desc);
			return -1;
		}
	}
	
}
