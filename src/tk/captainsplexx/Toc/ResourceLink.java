package tk.captainsplexx.Toc;

import tk.captainsplexx.Resource.ResourceHandler.ResourceType;

public class ResourceLink {
	public String path;
	public ResourceType type;
	public String sha1;
	
	public ResourceLink(String path, ResourceType type, String sha1) {
		this.path = path;
		this.type = type;
		this.sha1 = sha1;
	}

	public String getPath() {
		return path;
	}

	public ResourceType getType() {
		return type;
	}

	public String getSHA1() {
		return sha1;
	}	
}
