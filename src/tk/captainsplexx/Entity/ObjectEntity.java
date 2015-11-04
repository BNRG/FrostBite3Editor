package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;


public class ObjectEntity extends Entity{

	public ObjectEntity(String name, Entity parent, String[] texturedModelNames) {
		super(name, parent, texturedModelNames);
	}
	public ObjectEntity(String name, Entity parent, String[] texturedModelNames,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, parent, texturedModelNames, minCoords, maxCoords);
	}
	@Override
	public void update() {
		//PS: I would love to have physic's support too ;)
	}
}
