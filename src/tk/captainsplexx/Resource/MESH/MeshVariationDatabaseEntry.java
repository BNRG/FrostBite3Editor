package tk.captainsplexx.Resource.MESH;

import java.util.ArrayList;
import java.util.Collections;

public class MeshVariationDatabaseEntry {
	public String name;
	public long VariationAssetNameHash;
	public ArrayList<String> diffuseMaterials;
	public int materials;
	
	public long getVariationAssetNameHash() {
		return VariationAssetNameHash;
	}
	public void setVariationAssetNameHash(long variationAssetNameHash) {
		VariationAssetNameHash = variationAssetNameHash;
	}
	public ArrayList<String> getDiffuseMaterials() {
		return diffuseMaterials;
	}
	public MeshVariationDatabaseEntry() {
		diffuseMaterials = new ArrayList<String>();
		materials = 0;
		//NULL CONSTRUCTOR FOR LATER ON FILLING!!
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	
	public void reverseMaterials(){
		Collections.reverse(diffuseMaterials);
	}
	
	public void addMaterial(){
		materials++;
	}
	
	public int getMaterials(){
		return materials;
	}
	
	
}
