package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Game.EntityHandler.Type;

public class Entity {
	
	public Type type;
	
	public String name;
		
	public Vector3f position;
	public Vector3f rotation;
	public Vector3f scaling;
	public Boolean highlighted;
	public Boolean isVisible;
	
	public String[] texturedModelNames;
		
	
	public Entity(Type type, String name, String[] texturedModelNames) {
		this.type = type;
		this.name = name;
		this.position = new Vector3f(0.0f,0.0f,0.0f);
		this.rotation = new Vector3f(0.0f,0.0f,0.0f);
		this.scaling = new Vector3f(1.0f,1.0f,1.0f);
		this.texturedModelNames = texturedModelNames;
		this.highlighted = false;
		this.isVisible = true;
	}

	public Type getType() {
		return type;
	}
	
	public void update() {
		
	}
	
	public void changePosition(float dx, float dy, float dz){
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	
	public void changeRotation(float dx, float dy, float dz){
		rotation.x += dx;
		rotation.y += dy;
		rotation.z += dz;
	}
	
	public void changeScaling(float dx, float dy, float dz){
		scaling.x += dx;
		scaling.y += dy;
		scaling.z += dz;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public String[] getTexturedModelNames() {
		return texturedModelNames;
	}

	public Vector3f getScaling() {
		return scaling;
	}

	public void setScaling(Vector3f scaling) {
		this.scaling = scaling;
	}

	public Boolean getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(Boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	public void toggleHighlighted(){
		if (this.highlighted){
			this.highlighted = false;
		}else{
			this.highlighted = true;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void toggleVisibility(){
		if (this.isVisible){
			this.isVisible = false;
		}else{
			this.isVisible = true;
		}
	}
	
	
	
		
	
}
