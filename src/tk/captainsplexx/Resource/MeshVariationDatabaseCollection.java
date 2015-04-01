package tk.captainsplexx.Resource;

import java.util.ArrayList;

public class MeshVariationDatabaseCollection {
	public ArrayList<MeshVariationDatabaseEntry> variations = new ArrayList<MeshVariationDatabaseEntry>();
	public String meshName;
	
	
	public String getMeshName() {
		return meshName;
	}
	public void setMeshName(String meshName) {
		this.meshName = meshName;
	}
	public ArrayList<MeshVariationDatabaseEntry> getVariations() {
		return variations;
	}
	
	
}
