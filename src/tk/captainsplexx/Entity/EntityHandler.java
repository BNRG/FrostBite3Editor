package tk.captainsplexx.Entity;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Maths.RayCasting;
import tk.captainsplexx.Maths.VectorMath;
import tk.captainsplexx.Model.ModelHandler;
import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Resource.MESH.MeshChunkLoader;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;

public class EntityHandler {
		
	ArrayList <Entity> entities = new ArrayList <Entity>();
	Vector3f ray = null;
	Entity focussedEntity = null;
	
	public int MAX_TEXTURES = 1000;
	public int MAX_RAY_CHECKS = 1000;
	public float RAY_CHECK_DISTANCE = 10f;
	
	public ModelHandler modelHandler;
	public ResourceHandler resourceHandler;
	
	public EntityHandler(ModelHandler modelHandler, ResourceHandler resourceHandler) {
		this.modelHandler = modelHandler;
		this.resourceHandler = resourceHandler;
	}


	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	
	public Entity getFocussedEntity(Vector3f position, Vector3f direction){
		this.ray = new Vector3f(position.x, position.y, position.z);
		Vector3f origin = new Vector3f(position.x, position.y, position.z);
		Vector3f absMinCoords = null;
		Vector3f absMaxCoords = null;
		for (int check=1; check<=MAX_RAY_CHECKS; check++){
			this.ray = RayCasting.getRayPosition(ray, direction, RAY_CHECK_DISTANCE, null);
			for (Entity e : entities){
				e.setShowBoundingBox(false);
				absMinCoords = Vector3f.add(e.getPosition(), VectorMath.multiply(e.getMinCoords(), e.getScaling(), null), null);//pos+(minCoords*Scaling)
				absMaxCoords = Vector3f.add(e.getPosition(), VectorMath.multiply(e.getMaxCoords(), e.getScaling(), null), null);
				if (ray.x >= absMinCoords.x && ray.x <= absMaxCoords.x && ray.y >= absMinCoords.y
						&& ray.y <= absMaxCoords.y&& ray.z >= absMinCoords.z && ray.z <= absMaxCoords.z){ //Entity covers area of RayPoint
					
					
					if (origin.x >= absMinCoords.x && origin.x <= absMaxCoords.x &&
							origin.y >= absMinCoords.y && origin.y <= absMaxCoords.y&& origin.z >= absMinCoords.z && origin.z <= absMaxCoords.z){
						//Origin point isn't allowed to be inside of entity!
						continue;
					}
					
					e.setShowBoundingBox(true);
					focussedEntity = e;
					return e;//entity found!
				}
			}
		}
		focussedEntity = null;
		return null;//nothing found!
	}
	
	
	public Entity createEntity(byte[] mesh, ConvertedSBpart convSBpart, String textureRoot, ArrayList<String> materials, String desc){
		try{
			MeshChunkLoader msl = resourceHandler.getMeshChunkLoader();
			msl.loadFile(mesh, convSBpart);/*the chunk guid is located at 0xC8, but why the f*ck are the multiple ones? needs the loader work too?*/
			String[] texturedModel = new String[msl.getSubMeshCount()];
			//ArrayList<String> materials = resourceHandler.getMeshVariationDatabaseHandler().getMaterials(msl.getName(), 0); //VARIATION ID ??!
			for (int submesh=0; submesh<msl.getSubMeshCount();submesh++){
				RawModel model = modelHandler.addRawModel(GL11.GL_TRIANGLES, msl.getName()+submesh, msl.getVertexPositions(submesh), msl.getUVCoords(submesh), msl.getIndices(submesh));
				
				int textureID = modelHandler.getLoader().getNotFoundID();
				if (materials!=null){
					if (!materials.isEmpty()){
						try{
							if (resourceHandler.getTextureHandler().isExisting(materials.get(submesh))){
								textureID = resourceHandler.getTextureHandler().getTextureID(materials.get(submesh));
							}else{
								if (resourceHandler.getTextureHandler().getTextures().size()<MAX_TEXTURES && !materials.get(submesh).equals("")){
									textureID = modelHandler.getLoader().loadTexture(textureRoot+"res/"+materials.get(submesh)+".jpg"); 
									resourceHandler.getTextureHandler().addTextureID(textureID, materials.get(submesh));
								}
							}
						}catch(Exception e){
							System.err.println("Problem while loding Texture of Submesh: "+submesh);
						}
					}
				}
				texturedModel[submesh] = (modelHandler.addTexturedModel(model, textureID));
			}
			Entity en = new ObjectEntity(msl.getName(), texturedModel);
			//axis aligned bounding box
			float[] maxCoords = msl.getMaxCoords();
			float[] minCoords = msl.getMinCoords();
			en.setMaxCoords(new Vector3f(maxCoords[0], maxCoords[1], maxCoords[2]));
			en.setMinCoords(new Vector3f(minCoords[0], minCoords[1], minCoords[2]));
			
			return en;
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Could not create entitiy: "+desc);
			return null;
		}
	}


	public Vector3f getRay() {
		return ray;
	}


	public Entity getFocussedEntity() {
		return focussedEntity;
	}
	
	
	
}