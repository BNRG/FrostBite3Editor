package tk.captainsplexx.Toc;

import tk.captainsplexx.Resource.ResourceHandler.ResourceType;

public class ResourceLink {
	public String name;
	public long size;
	public long originalSize;
	public ResourceType type;
	public String sha1;
	public ResourceLink(String name, long size, long originalSize,
			ResourceType type, String sha1) {
		this.name = name;
		this.size = size;
		this.originalSize = originalSize;
		this.type = type;
		this.sha1 = sha1;
	}
	public ResourceLink(/*USING NULLCONSTRUCTOR*/) {
		this.name = "";
		this.size = 0;
		this.originalSize = 0;
		this.type = ResourceType.UNDEFINEDSPLEXX;
		this.sha1 = "";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getOriginalSize() {
		return originalSize;
	}
	public void setOriginalSize(long originalSize) {
		this.originalSize = originalSize;
	}
	public ResourceType getType() {
		return type;
	}
	public void setType(ResourceType type) {
		this.type = type;
	}
	public String getSha1() {
		return sha1;
	}
	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	
}
