package tk.captainsplexx.Resource.EBX.Structure.Entry;

import java.util.ArrayList;

import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXField;

public class EBXObjBlueprintTransform {
	
	private BlueprintTransformType type;
	private ArrayList<EBXTransformationVec3> tranformations;
	
	public static enum BlueprintTransformType{
		LinearTransform, 
	}
	
	//Consturctor
	public EBXObjBlueprintTransform(BlueprintTransformType type, ArrayList<EBXTransformationVec3> tranformations){
		this.type = type;
		this.tranformations = tranformations;
	}
	
	public EBXObjBlueprintTransform(EBXComplex ebxComplex){
		this.tranformations = new ArrayList<>();
		if (ebxComplex.getComplexDescriptor().getName().equals("LinearTransform")){
			for (EBXField field : ebxComplex.getFields()){
				EBXTransformationVec3 transVec3 = EBXTransformationVec3.getTransformVec3(field);
				if (transVec3!=null){
					tranformations.add(transVec3);
					//System.out.println(transVec3.getName() +" "+ transVec3.getX()+" "+ transVec3.getY()+" "+ transVec3.getZ());
				}else{
					System.err.println("EBXObjBlueprintTransform constructor has a problem with reading a Transformation (null)!");
				}
			}
		}else{
			System.err.println("Can not read in EBXObjBlueprintTransform because type is unknown!");
		}
	}
	
	//Getter and Setter
	public BlueprintTransformType getType() {
		return type;
	}

	public void setType(BlueprintTransformType type) {
		this.type = type;
	}


	public ArrayList<EBXTransformationVec3> getTranformations() {
		return tranformations;
	};	
}
