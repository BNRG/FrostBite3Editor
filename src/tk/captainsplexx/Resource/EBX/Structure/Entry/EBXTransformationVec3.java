package tk.captainsplexx.Resource.EBX.Structure.Entry;

import tk.captainsplexx.Resource.EBX.EBXField;

public class EBXTransformationVec3 {
	private float x = 0f, y = 0f, z = 0f;
	private String name;
	
	
	//Constructors
	
	public static EBXTransformationVec3 getTransformVec3(EBXField complexField){
		EBXTransformationVec3 transVec3 = new EBXTransformationVec3(complexField.getFieldDescritor().getName());
		
		for (EBXField axis : complexField.getValueAsComplex().getFields()){
			switch (axis.getFieldDescritor().getName()){
				case "x":
					transVec3.setX((float) axis.getValue());
					break;
				case "y":
					transVec3.setY((float) axis.getValue());
					break;
				case "z":
					transVec3.setZ((float) axis.getValue());
					break;
			}
		}
		return transVec3;
	}
	
	
	public EBXTransformationVec3(String name) {
		this.name = name;
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
	}


	public EBXTransformationVec3(String name, float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
	}
	
	
	//Getter and Setter
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
