package tk.captainsplexx.Resource.EBX;

public class EBXInstanceHelper {
	private int offset;
	private String guid;
	private int instanceComplexIndex;
	private boolean isPrimaryInstance;
	
	public EBXInstanceHelper(int offset, String guid, int instanceComplexIndex, boolean isPrimaryInstance) {
		this.offset = offset;
		this.guid = guid;
		this.instanceComplexIndex = instanceComplexIndex;
		this.isPrimaryInstance = isPrimaryInstance;
	}
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getInstanceComplexIndex() {
		return instanceComplexIndex;
	}
	public void setInstanceComplexIndex(int instanceComplexIndex) {
		this.instanceComplexIndex = instanceComplexIndex;
	}

	public boolean isPrimaryInstance() {
		return isPrimaryInstance;
	}

	public void setPrimaryInstance(boolean isPrimaryInstance) {
		this.isPrimaryInstance = isPrimaryInstance;
	}
	
	
}
