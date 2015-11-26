package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;

public class LightEntity extends Entity{

	public LightEntity(String name, EBXStructureEntry structEntry, Entity parent, RawModel[] rawModels,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, Type.Light, structEntry, parent, rawModels, minCoords, maxCoords);
	}
	public LightEntity(String name, EBXStructureEntry structEntry, Entity parent, RawModel[] rawModels) {
		super(name, Type.Light, structEntry, parent, rawModels);
	}
	
	@Override
	public void update() {
		
	}

}
