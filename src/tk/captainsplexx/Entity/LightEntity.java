package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;

public class LightEntity extends Entity{

	public LightEntity(String name, Entity parent, String[] texturedModelNames,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, parent, texturedModelNames, minCoords, maxCoords);
	}
	public LightEntity(String name, Entity parent, String[] texturedModelNames) {
		super(name, parent, texturedModelNames);
	}
	
	@Override
	public void update() {
		
	}

}
