package tk.captainsplexx.Render;

import java.util.HashMap;

public class TextureHandler {
	public HashMap<String, Integer> textures;
	
	public TextureHandler(){
		this.textures = new HashMap<String, Integer>();
	}
	
	public Integer getTextureID(String meshName){
		try{
			return textures.get(meshName);
		}catch (Exception e){
			return null;
		}
	}
	
	public boolean isExisting(String meshName){
		for (String s : textures.keySet()){
			if (s.equals(meshName)){
				return true;
			}
		}
		return false;
	}
	
	public void addTextureID(Integer id, String meshName){
		textures.put(meshName, id);
	}

	
	
}
