package tk.captainsplexx.Resource.EBX;

import java.util.ArrayList;

public class EBXFile {
	private String truePath;
	private String guid;
	private ArrayList<EBXInstance> instances;
	
	public EBXFile(String truePath, ArrayList<EBXInstance> instances, String guid) {
		this.truePath = truePath;
		this.instances = instances;
		this.guid = guid;
	}

	public String getTruePath() {
		return truePath;
	}

	public ArrayList<EBXInstance> getInstances() {
		return instances;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setTruePath(String truePath) {
		this.truePath = truePath;
	}
	
}
