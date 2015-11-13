package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;

public class LightEntity extends Entity{

	public LightEntity(String name, EBXStructureEntry structEntry, Entity parent, String[] texturedModelNames,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, Type.Light, structEntry, parent, texturedModelNames, minCoords, maxCoords);
	}
	public LightEntity(String name, EBXStructureEntry structEntry, Entity parent, String[] texturedModelNames) {
		super(name, Type.Light, structEntry, parent, texturedModelNames);
	}
	
	@Override
	public void update() {
		
	}

}
