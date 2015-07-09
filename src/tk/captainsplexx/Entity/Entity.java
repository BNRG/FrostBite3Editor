package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Entity.EntityHandler.Type;

public class Entity {
	
	public Type type;
	
	public String name;
		
	public Vector3f position;
	public Vector3f rotation;
	public Vector3f scaling;
	
	public Boolean isVisible;
	
	public Boolean highlighted;
	public Vector3f heighlightedColor;
	
	public Vector3f minCoords;
	public Vector3f maxCoords;
	public boolean showBoundingBox;
	
	public String[] texturedModelNames;
		
	
	public Entity(Type type, String name, String[] texturedModelNames, Vector3f minCoords, Vector3f maxCoords) {
		this.type = type;
		this.name = name;
		this.position = new Vector3f(0.0f,0.0f,0.0f);
		this.rotation = new Vector3f(0.0f,0.0f,0.0f);
		this.scaling = new Vector3f(1.0f,1.0f,1.0f);
		this.texturedModelNames = texturedModelNames;
		this.highlighted = false;
		this.isVisible = true;
		this.heighlightedColor = new Vector3f(0.5f, 0.0f, 0.0f);
		this.minCoords = minCoords;
		this.maxCoords = maxCoords;
		this.showBoundingBox = false;
	}
	public Entity(Type type, String name, String[] texturedModelNames){
		this.type = type;
		this.name = name;
		this.position = new Vector3f(0.0f,0.0f,0.0f);
		this.rotation = new Vector3f(0.0f,0.0f,0.0f);
		this.scaling = new Vector3f(1.0f,1.0f,1.0f);
		this.texturedModelNames = texturedModelNames;
		this.highlighted = false;
		this.isVisible = true;
		this.heighlightedColor = new Vector3f(0.5f, 0.0f, 0.0f);
		this.minCoords = new Vector3f(0.0f, 0.0f, 0.0f);
		this.maxCoords = new Vector3f(0.0f, 0.0f, 0.0f);
		this.showBoundingBox = false;
	}
	public Vector3f getMaxCoords() {
		return maxCoords;
	}


	public void setMaxCoords(Vector3f maxCoords) {
		this.maxCoords = maxCoords;
	}


	public void setMinCoords(Vector3f minCoords) {
		this.minCoords = minCoords;
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
	
	public void changePosition(Vector3f relPos){
		position.x += relPos.x;
		position.y += relPos.y;
		position.z += relPos.z;
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

	public Vector3f getHeighlightedColor() {
		return heighlightedColor;
	}

	public void setHeighlightedColor(Vector3f heighlightedColor) {
		this.heighlightedColor = heighlightedColor;
	}
	public boolean isShowBoundingBox() {
		return showBoundingBox;
	}
	public void setShowBoundingBox(boolean showBoundingBox) {
		this.showBoundingBox = showBoundingBox;
	}
	public Vector3f getMinCoords() {
		return minCoords;
	}


	public Vector2f moveForward(float distance)
	{
		Vector2f vec = new Vector2f(distance * (float)Math.sin(Math.toRadians(rotation.y)), distance * (float)Math.cos(Math.toRadians(rotation.y)));
		position.x += vec.x;
		position.z -= vec.y;
		return vec;
	}
	public Vector2f moveBackwards(float distance)
	{
		Vector2f vec = new Vector2f(distance * (float)Math.sin(Math.toRadians(rotation.y)), distance * (float)Math.cos(Math.toRadians(rotation.y)));
		position.x -= vec.x;
		position.z += vec.y;
		return vec;
	}
	public Vector2f moveLeft(float distance)
	{
		Vector2f vec = new Vector2f(distance * (float)Math.sin(Math.toRadians(rotation.y-90)), distance * (float)Math.cos(Math.toRadians(rotation.y-90)));
		position.x += vec.x;
		position.z -= vec.y;
		return vec;
	}
	public Vector2f moveRight(float distance)
	{
		Vector2f vec = new Vector2f(distance * (float)Math.sin(Math.toRadians(rotation.y+90)), distance * (float)Math.cos(Math.toRadians(rotation.y+90)));
		position.x += vec.x;
		position.z -= vec.y;
		return vec;
	}
	
}
