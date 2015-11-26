package tk.captainsplexx.Resource.EBX;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Resource.EBX.EBXHandler.FieldValueType;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;

public class EBXExternalGUID {
	private String fileGUID = null;
	private String instanceGUID = null;
	
	public EBXExternalGUID(String fileGUID, String instanceGUID) {
		this.fileGUID = fileGUID;
		this.instanceGUID = instanceGUID;
	}
	
	public EBXExternalGUID(EBXField ebxField){
		if (ebxField.getValue()!=null){
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
	
	public EBXStructureEntry follow(boolean tryLoad, boolean loadOriginal){
		return Core.getGame().getResourceHandler().getEBXHandler().getStructureInstance(this, tryLoad, loadOriginal);
	}
}
