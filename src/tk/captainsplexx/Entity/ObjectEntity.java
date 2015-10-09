package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;


public class ObjectEntity extends Entity{

	public ObjectEntity(String name, String[] texturedModelNames) {
		super(name, texturedModelNames);
	}
	public ObjectEntity(String name, String[] texturedModelNames,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, texturedModelNames, minCoords, maxCoords);
	}
	@Override
	public void update() {
		//PS: I would love to have physic's support too ;)
	}
}
