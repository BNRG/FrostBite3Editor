package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;

public class LightEntity extends Entity{

	public LightEntity(String name, String[] texturedModelNames,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, texturedModelNames, minCoords, maxCoords);
	}
	public LightEntity(String name, String[] texturedModelNames) {
		super(name, texturedModelNames);
	}
	
	@Override
	public void update() {
		
	}

}
