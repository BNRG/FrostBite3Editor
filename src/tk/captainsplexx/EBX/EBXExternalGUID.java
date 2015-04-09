package tk.captainsplexx.EBX;

public class EBXExternalGUID {
	public String fileGUID;
	public String ObjectGUID;
	public EBXExternalGUID(String fileGUID, String objectGUID) {
		this.fileGUID = fileGUID;
		this.ObjectGUID = objectGUID;
	}
	public String getFileGUID() {
		return fileGUID;
	}
	public void setFileGUID(String fileGUID) {
		this.fileGUID = fileGUID;
	}
	public String getObjectGUID() {
		return ObjectGUID;
	}
	public void setObjectGUID(String objectGUID) {
		this.ObjectGUID = objectGUID;
	}
	
}
