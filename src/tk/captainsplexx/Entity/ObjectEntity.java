package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;


public class ObjectEntity extends Entity{

	public ObjectEntity(String name, EBXStructureEntry structEntry, Entity parent, String[] texturedModelNames) {
		super(name, Type.Object, structEntry, parent, texturedModelNames);
	}
	public ObjectEntity(String name, EBXStructureEntry structEntry, Entity parent, String[] texturedModelNames,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, Type.Object, structEntry, parent, texturedModelNames, minCoords, maxCoords);
	}
	@Override
	public void update() {
		//PS: I would love to have physic's support too ;)
	}
}
