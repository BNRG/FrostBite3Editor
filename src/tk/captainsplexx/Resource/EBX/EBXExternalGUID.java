package tk.captainsplexx.Resource.EBX;

import tk.captainsplexx.Resource.EBX.EBXHandler.FieldValueType;

public class EBXExternalGUID {
	private String fileGUID;
	private String instanceGUID;
	
	public EBXExternalGUID(String fileGUID, String instanceGUID) {
		this.fileGUID = fileGUID;
		this.instanceGUID = instanceGUID;
	}
	
	public EBXExternalGUID(EBXField ebxField){
		if (ebxField.getType() == FieldValueType.ExternalGuid || ebxField.getType() == FieldValueType.Guid ) {
			String[] splitString = ((String) ebxField.getValue()).split(" ");
			if (splitString.length == 2) {
				this.fileGUID = splitString[0];
				this.instanceGUID = splitString[1];
			}else if(ebxField.getType() == FieldValueType.Guid && ebxField.getValue().toString().contains("null")){
				this.fileGUID = null;
				this.instanceGUID = null;
			}else{
				System.err.println("Invalid External GUID length in readExternalGUID");
			}
		}else{
			System.err.println(ebxField.getType()+" is not an ExternalGUID!" + ebxField.getValue());
		}
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
	
	public String getBothGUIDs(){
		return fileGUID+"/"+instanceGUID;
	}
}
