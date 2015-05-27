package tk.captainsplexx.Render;

import java.util.HashMap;

import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Model.TexturedModel;

public class ModelHandler {
	public HashMap<String, RawModel> rawModels = new HashMap<String, RawModel>();
	public HashMap<String, TexturedModel> texturedModels = new HashMap<String, TexturedModel>();
	
	public Loader loader = new Loader();
	
	public RawModel addRawModel(int drawMethod, String name, float[] positions, float[] textures, int[] indices){
		RawModel model = loader.loadVAO(name, drawMethod, positions, textures, indices);
		rawModels.put(name, model);
		return model;
	}
	
	public String addTexturedModel(RawModel rawModel, int textureID){
		String name = rawModel.getName()+"_TX_"+textureID;
		texturedModels.put(name, new TexturedModel(rawModel, textureID));
		return name;
	}
	
	public HashMap<String, RawModel> getRawModels(){
		return rawModels;
	}
	
	public HashMap<String, TexturedModel> getTexturedModels(){
		return texturedModels;
	}
	
	public Loader getLoader(){
		return loader;
	}
}
