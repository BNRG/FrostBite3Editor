package tk.captainsplexx.Resource.EBX;

import java.util.ArrayList;

public class EBXFile {
	public String truePath;
	public ArrayList<EBXInstance> instances;
	
	public EBXFile(String truePath, ArrayList<EBXInstance> instances) {
		this.truePath = truePath;
		this.instances = instances;
	}

	public String getTruePath() {
		return truePath;
	}

	public ArrayList<EBXInstance> getInstances() {
		return instances;
	}	
}
