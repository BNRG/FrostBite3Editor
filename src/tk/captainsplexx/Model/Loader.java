package tk.captainsplexx.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import tk.captainsplexx.Resource.DDSConverter;

public class Loader {
	public ArrayList<Integer> vaos = new ArrayList<Integer>();
	public ArrayList<Integer> vbos = new ArrayList<Integer>();
	public HashMap<String, Integer> textures = new HashMap<String, Integer>();
	public int notFoundID;
	
	public RawModel loadVAO(String name, int drawMethod, float[] positions, float[] textures, int[] indices){
		int vaoID = createVAO();
		bindIndiciesBuffer(indices);
		storeDataAsAttr(0, 3, positions);
		storeDataAsAttr(1, 2, textures);
		unbindVAO();
		return new RawModel(name, vaoID, indices.length, drawMethod);
	}
	
	public int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public void cleanUp(){
		for(int vao: vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo: vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture: textures.values()){
			GL11.glDeleteTextures(texture);
		}
	}
	
	public Loader(){
		notFoundID = 0;
	}
	public void init(){
		notFoundID = loadTexture("res/notFound/notFound.dds");
	}
	
	public int getNotFoundID() {
		return notFoundID;
	}

	public int loadTexture(String path){
		Texture texture;
		if (textures.containsKey(path)){
			return textures.get(path);
		}else{
			try {
				if (path.endsWith(".dds")){
					File tga = DDSConverter.convertToTGA(new File(path));
					texture = TextureLoader.getTexture("TGA", new FileInputStream(tga.getAbsolutePath()));
				}else{
					texture = TextureLoader.getTexture("PNG", new FileInputStream(path));
				}
				textures.put(path, texture.getTextureID());
				return texture.getTextureID();
			} catch (IOException e) {
				System.err.println("FILE NOT FOUND! "+path);
				return notFoundID;
			}	
		}
	}
	
	
	
	public void storeDataAsAttr(int index, int dimensions, float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = getFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, dimensions, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
	}
	public void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	public void bindIndiciesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = getIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	
	public IntBuffer getIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public FloatBuffer getFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
