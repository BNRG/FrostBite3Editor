package tk.captainsplexx.Entity;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity {
	
	public static enum Type{
			Object, Light,
	};

	public String name;
	public Type type;

	public Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
	public Vector3f rotation = new Vector3f(0.0f, 0.0f, 0.0f);
	public Vector3f scaling = new Vector3f(1.0f, 1.0f, 1.0f);
	public Vector3f velocity = new Vector3f(0.0f, 0.0f, 0.0f);

	public Boolean isVisible = true;

	public Boolean highlighted = false;
	public Vector3f heighlightedColor = new Vector3f(0.5f, 0.0f, 0.0f);

	public Vector3f minCoords = new Vector3f(0.0f, 0.0f, 0.0f);
	public Vector3f maxCoords = new Vector3f(0.0f, 0.0f, 0.0f);
	public boolean showBoundingBox = false;

	public String[] texturedModelNames;
	
	public ArrayList<Entity> childrens = new ArrayList<>();
	public Entity parent = null;

	public Entity(String name, Type type, Entity parent, String[] texturedModelNames) {
		this.name = name;
		this.type = type;
		this.parent = parent;
		this.texturedModelNames = texturedModelNames;
	}

	public Entity(String name, Type type, Entity parent, String[] texturedModelNames,
			Vector3f minCoords, Vector3f maxCoords) {		
		this.name = name;
		this.type = type;
		this.parent = parent;
		this.texturedModelNames = texturedModelNames;
		this.minCoords = minCoords;
		this.maxCoords = maxCoords;
	}

	public void changePosition(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}

	public void changePosition(Vector3f relPos) {
		position.x += relPos.x;
		position.y += relPos.y;
		position.z += relPos.z;
	}

	public void changeRotation(float dx, float dy, float dz) {
		rotation.x += dx;
		rotation.y += dy;
		rotation.z += dz;
	}

	public void changeScaling(float dx, float dy, float dz) {
		scaling.x += dx;
		scaling.y += dy;
		scaling.z += dz;
	}

	public void changeVelocity(float relX, float relY, float relZ) {
		this.velocity.x += relX;
		this.velocity.y += relY;
		this.velocity.z += relZ;
	}

	public void changeVelocity(Vector3f relVel) {
		this.velocity.x += relVel.x;
		this.velocity.y += relVel.y;
		this.velocity.z += relVel.z;
	}

	public ArrayList<Entity> getChildrens() {
		return childrens;
	}

	public Vector3f getHeighlightedColor() {
		return heighlightedColor;
	}

	public Boolean getHighlighted() {
		return highlighted;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public Vector3f getMaxCoords() {
		return maxCoords;
	}

	public Vector3f getMinCoords() {
		return minCoords;
	}

	public String getName() {
		return name;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScaling() {
		return scaling;
	}


	public String[] getTexturedModelNames() {
		return texturedModelNames;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public boolean isShowBoundingBox() {
		return showBoundingBox;
	}

	public Vector2f moveBackwards(float distance) {
		Vector2f vec = new Vector2f(distance
				* (float) Math.sin(Math.toRadians(rotation.y)), distance
				* (float) Math.cos(Math.toRadians(rotation.y)));
		position.x -= vec.x;
		position.z += vec.y;
		return vec;
	}

	public Vector2f moveForward(float distance) {
		Vector2f vec = new Vector2f(distance
				* (float) Math.sin(Math.toRadians(rotation.y)), distance
				* (float) Math.cos(Math.toRadians(rotation.y)));
		position.x += vec.x;
		position.z -= vec.y;
		return vec;
	}

	public Vector2f moveLeft(float distance) {
		Vector2f vec = new Vector2f(distance
				* (float) Math.sin(Math.toRadians(rotation.y - 90)), distance
				* (float) Math.cos(Math.toRadians(rotation.y - 90)));
		position.x += vec.x;
		position.z -= vec.y;
		return vec;
	}

	public Vector2f moveRight(float distance) {
		Vector2f vec = new Vector2f(distance
				* (float) Math.sin(Math.toRadians(rotation.y + 90)), distance
				* (float) Math.cos(Math.toRadians(rotation.y + 90)));
		position.x += vec.x;
		position.z -= vec.y;
		return vec;
	}

	public void setHeighlightedColor(Vector3f heighlightedColor) {
		this.heighlightedColor = heighlightedColor;
	}

	public void setHighlighted(Boolean highlighted) {
		this.highlighted = highlighted;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void setMaxCoords(Vector3f maxCoords) {
		this.maxCoords = maxCoords;
	}

	public void setMinCoords(Vector3f minCoords) {
		this.minCoords = minCoords;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setScaling(Vector3f scaling) {
		this.scaling = scaling;
	}
	
	public void setShowBoundingBox(boolean showBoundingBox) {
		this.showBoundingBox = showBoundingBox;
	}
	
	public void setVelocity(float velX, float velY, float velZ) {
		this.velocity = new Vector3f(velX, velY, velZ);
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public void toggleHighlighted() {
		if (this.highlighted) {
			this.highlighted = false;
		} else {
			this.highlighted = true;
		}
	}
	
	
	public void toggleVisibility() {
		if (this.isVisible) {
			this.isVisible = false;
		} else {
			this.isVisible = true;
		}
	}
	
	
	

	public void setTexturedModelNames(String[] texturedModelNames) {
		this.texturedModelNames = texturedModelNames;
	}

	public Entity getParent() {
		return parent;
	}

	public void setParent(Entity parent) {
		this.parent = parent;
	}
	
	

	public Type getType() {
		return type;
	}

	public abstract void update();

}
