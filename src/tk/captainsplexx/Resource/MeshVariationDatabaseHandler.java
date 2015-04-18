package tk.captainsplexx.Resource;

import java.util.ArrayList;
import java.util.HashMap;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.EBXHandler;
import tk.captainsplexx.Resource.EBX.EBXInstance;

public class MeshVariationDatabaseHandler {
	public HashMap<String, MeshVariationDatabaseCollection> entries;
	private EBXHandler ebxHandler;
	
	public MeshVariationDatabaseHandler(EBXHandler ebxHandler) {
		this.entries = new HashMap<String, MeshVariationDatabaseCollection>();
		this.ebxHandler = ebxHandler;
	}

	public ArrayList<String> getMaterials(String meshName, int variation) {
		try{
			for(MeshVariationDatabaseCollection coll : entries.values()){
				if (coll.getMeshName().endsWith(meshName)){
					return coll.getVariations().get(variation).getDiffuseMaterials();
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("Could not get Material list for "+meshName+" and variation id: "+variation);
			return null;
		}
		return null;
	}
	
	public void addDatabase(EBXInstance instance){
		if (instance.getComplex().getComplexDescriptor().getName().equals("MeshVariationDatabase")){
			 for (EBXField field1 : instance.getComplex().getField(1).getValueAsComplex().getFields()){ // for each member in Entries
				 MeshVariationDatabaseEntry entry = new MeshVariationDatabaseEntry();
				 EBXComplex member = field1.getValueAsComplex(); //member(n)::MeshVariationDatabaseEntry
				 entry.setName(ebxHandler.getGUIDHandler().getFileName(member.getField(0).getValue().toString()));
				 entry.setVariationAssetNameHash((long) member.getField(1).getValue());
				 EBXComplex material = member.getField(2).getValueAsComplex();
				 for (EBXField materialMemberField : material.getFields()){ //Materials::array
					 EBXComplex materialMemberComplex = materialMemberField.getValueAsComplex(); //member(n)::MeshVariationDatabaseMaterial
					 boolean done = false;
					 for (EBXField textureParameters : materialMemberComplex.getFieldAsComplex(2).getFields()){ //TextureParameters::array
						 EBXComplex textureShaderParameter = textureParameters.getValueAsComplex(); //member(n)::TextureShaderParameter
						 if (((String) textureShaderParameter.getField(0).getValue()).equals("Diffuse")){
							 String name = ebxHandler.getGUIDHandler().getFileName((String) textureShaderParameter.getField(1).getValue());
							 if (name!=null && name.contains("/")){
								 String[] splitname = name.split("/");
								 String small_name = "";
								 for (int i=0; i<splitname.length-1; i++){
									 small_name += splitname[i]+"/";
								 }
								 small_name += "small_"+splitname[splitname.length-1];
								 entry.getDiffuseMaterials().add(small_name);
								 entry.addMaterial();
								 done = true;
							 }
						 }
						 
					 }
					 if (!done){entry.getDiffuseMaterials().add("");}
				 }
				 MeshVariationDatabaseCollection collection = entries.get(entry.getName());
				 if (collection != null){
					 collection.getVariations().add(entry);
				 }else{
					 MeshVariationDatabaseCollection newCollection = new MeshVariationDatabaseCollection();
					 newCollection.setMeshName(entry.getName());
					 newCollection.getVariations().add(entry);
					 entries.put(entry.name, newCollection);
				 }
				 
			 }
		 }
	}
}
