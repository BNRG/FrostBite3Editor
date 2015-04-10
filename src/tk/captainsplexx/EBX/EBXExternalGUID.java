package tk.captainsplexx.EBX;

public class EBXExternalGUID {
	public String fileGUID;
	public String instanceGUID;
	public EBXExternalGUID(String fileGUID, String instanceGUID) {
		this.fileGUID = fileGUID;
		this.instanceGUID = instanceGUID;
	}
	public String getFileGUID() {
		return fileGUID;
	}
	public void setFileGUID(String fileGUID) {
		this.fileGUID = fileGUID;
	}
	public String getInstanceGUID() {
		return instanceGUID;
	}
	public void setInstanceGUID(String instanceGUID) {
		this.instanceGUID = instanceGUID;
	}
	
}
