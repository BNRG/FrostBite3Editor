package tk.captainsplexx.Toc;

import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Toc.TocConverter.ResourceBundleType;

public class ResourceLink {
	//Global
	public String name;
	public long size;
	public long originalSize;
	public ResourceType type;
	public String sha1;
	
	//++res
	public int resType;
	public byte[] resMeta;
	public long resRid;
	public byte[] idata;
	
	//++chunks
	public String id;
	public int logicalOffset;
	public int logicalSize;
	
	//++chunksMeta
	public int h32;
	public byte[] meta;
	
	//additional
	public ResourceBundleType bundleType;
	
	public ResourceLink(String name, long size, long originalSize,
			ResourceType type, String sha1, int resType, byte[] resMeta,
			long resRid, byte[] idata, String id, int logicalOffset,
			int logicalSize, int h32, byte[] meta) {
		this.name = name;
		this.size = size;
		this.originalSize = originalSize;
		this.type = type;
		this.sha1 = sha1;
		this.resType = resType;
		this.resMeta = resMeta;
		this.resRid = resRid;
		this.idata = idata;
		this.id = id;
		this.logicalOffset = logicalOffset;
		this.logicalSize = logicalSize;
		this.h32 = h32;
		this.meta = meta;
	}

	public ResourceLink(/*USING NULLCONSTRUCTOR*/) {
		this.name = "";
		this.size = 0;
		this.originalSize = 0;
		this.type = ResourceType.UNDEFINED;
		this.sha1 = "";
		this.bundleType = null;
	}
	
	
	public ResourceBundleType getBundleType() {
		return bundleType;
	}

	public void setBundleType(ResourceBundleType bundleType) {
		this.bundleType = bundleType;
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

	public int getResType() {
		return resType;
	}

	public void setResType(int resType) {
		this.resType = resType;
	}

	public byte[] getResMeta() {
		return resMeta;
	}

	public void setResMeta(byte[] resMeta) {
		this.resMeta = resMeta;
	}

	public long getResRid() {
		return resRid;
	}

	public void setResRid(long resRid) {
		this.resRid = resRid;
	}

	public byte[] getIdata() {
		return idata;
	}

	public void setIdata(byte[] idata) {
		this.idata = idata;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLogicalOffset() {
		return logicalOffset;
	}

	public void setLogicalOffset(int logicalOffset) {
		this.logicalOffset = logicalOffset;
	}

	public int getLogicalSize() {
		return logicalSize;
	}

	public void setLogicalSize(int logicalSize) {
		this.logicalSize = logicalSize;
	}

	public int getH32() {
		return h32;
	}

	public void setH32(int h32) {
		this.h32 = h32;
	}

	public byte[] getMeta() {
		return meta;
	}

	public void setMeta(byte[] meta) {
		this.meta = meta;
	}

	
	
}
