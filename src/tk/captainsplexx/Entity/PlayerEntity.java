package tk.captainsplexx.Entity;


public class PlayerEntity {
	public float height = 100;
	public float width = 100;
	
	public float posX;
	public float posY;
	public float posZ;
	
	public float rotX;
	public float rotY;
	public float rotZ;
	
	public float velX = 0;
	public float velY = 0;
	public float velZ = 0;
	
	public float movementSpeed = 10.0f;
	public float jumpStrength = 0.5f;
	public float gravity = 0.0f;
	
	public boolean onGround = false;
	
	public PlayerEntity(float posX, float posY, float posZ){
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getPosZ() {
		return posZ;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public float getVelZ() {
		return velZ;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public float getJumpStrength() {
		return jumpStrength;
	}

	public float getGravity() {
		return gravity;
	}

	public boolean isOnGround() {
		return onGround;
	}	
}

