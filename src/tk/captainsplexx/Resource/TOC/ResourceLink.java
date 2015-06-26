package tk.captainsplexx.Resource.TOC;

import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.TOC.TocConverter.ResourceBundleType;

public class ResourceLink {
	// Global
	public String name;
	public long size;
	public long originalSize;
	public ResourceType type;
	public String sha1;
	public int casPatchType; //2 if patched
	public String baseSha1;
	public String deltaSha1;

	// ++res
	public int resType;
	public byte[] resMeta; // 0x13 RAW2
	public long resRid;
	public byte[] idata; // 0x13 RAW2

	// ++chunks
	public String id;
	public int logicalOffset;
	public int logicalSize;
	public int rangeStart;
	public int rangeEnd;

	// ++chunksMeta
	public int h32;
	public byte[] meta; // 0x02 RAW1
	public int firstMip;

	// **EBX EXTRA
	public String ebxFileGUID;

	// additional
	public ResourceBundleType bundleType;
	public boolean hasModFile;

	public ResourceLink(/* USING NULLCONSTRUCTOR */) {
		this.name = "";
		this.size = 0;
		this.originalSize = 0;
		this.type = ResourceType.UNDEFINED;
		this.sha1 = "";
		this.bundleType = null;
		this.ebxFileGUID = "";
		this.casPatchType = 0;
		this.baseSha1 = null;
		this.deltaSha1 = null;
		this.rangeStart = -1; //This can be 0, so we choose a negative value
		this.rangeEnd = -1;
		this.firstMip = -1;
		this.hasModFile = false;
	}

	public String getEbxFileGUID() {
		return ebxFileGUID;
	}
	

	public String getBaseSha1() {
		return baseSha1;
	}

	public void setBaseSha1(String baseSha1) {
		this.baseSha1 = baseSha1;
	}


	public String getDeltaSha1() {
		return deltaSha1;
	}

	public void setDeltaSha1(String deltaSha1) {
		this.deltaSha1 = deltaSha1;
	}

	public int getCasPatchType() {
		return casPatchType;
	}

	public void setCasPatchType(int casPatchType) {
		this.casPatchType = casPatchType;
	}

	public int getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(int rangeStart) {
		this.rangeStart = rangeStart;
	}

	public int getFirstMip() {
		return firstMip;
	}

	public void setFirstMip(int firstMip) {
		this.firstMip = firstMip;
	}

	public int getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(int rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	public void setEbxFileGUID(String ebxFileGUID) {
		this.ebxFileGUID = ebxFileGUID;
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

	public boolean isHasModFile() {
		return hasModFile;
	}

	public void setHasModFile(boolean hasModFile) {
		this.hasModFile = hasModFile;
	}
	

}
