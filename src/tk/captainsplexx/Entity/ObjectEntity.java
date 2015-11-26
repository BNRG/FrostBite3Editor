package tk.captainsplexx.Entity;

import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;


public class ObjectEntity extends Entity{
	
	private EntityTextureData textureData;

	public ObjectEntity(String name, EBXStructureEntry structEntry, Entity parent, RawModel[] rawModels, EntityTextureData textureData) {
		super(name, Type.Object, structEntry, parent, rawModels);
		this.textureData = textureData;
	}
	public ObjectEntity(String name, EBXStructureEntry structEntry, Entity parent, RawModel[] rawModels, EntityTextureData textureData,
			Vector3f minCoords, Vector3f maxCoords) {
		super(name, Type.Object, structEntry, parent, rawModels, minCoords, maxCoords);
		this.textureData = textureData;
	}
	@Override
	public void update() {
	}
	public EntityTextureData getTextureData() {
		return textureData;
	}
}
