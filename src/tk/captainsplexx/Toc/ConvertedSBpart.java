package tk.captainsplexx.Toc;

import java.util.ArrayList;

public class ConvertedSBpart {
	public String path;
	public Integer magicSalt;
	public ArrayList<ResourceLink> ebx;
	public ArrayList<ResourceLink> dbx;
	public ArrayList<ResourceLink> res;
	public boolean alignMembers;
	public boolean ridSupport;
	public boolean storeCompressedSizes;
	public long totalSize;
	public long dbxTotalSize;
	

	public ConvertedSBpart(String path, Integer magicSalt,
			ArrayList<ResourceLink> ebx, ArrayList<ResourceLink> dbx,
			ArrayList<ResourceLink> res, boolean alignMembers,
			boolean ridSupport, boolean storeCompressedSizes, long totalSize,
			long dbxTotalSize) {
		this.path = path;
		this.magicSalt = magicSalt;
		this.ebx = ebx;
		this.dbx = dbx;
		this.res = res;
		this.alignMembers = alignMembers;
		this.ridSupport = ridSupport;
		this.storeCompressedSizes = storeCompressedSizes;
		this.totalSize = totalSize;
		this.dbxTotalSize = dbxTotalSize;
	}
	
	public ConvertedSBpart(/*USING NULLCONSTRUCTOR*/){
		this.path = "";
		this.magicSalt = 0;
		this.ebx = new ArrayList<ResourceLink>();
		this.dbx = new ArrayList<ResourceLink>();
		this.res = new ArrayList<ResourceLink>();
		this.alignMembers = false;
		this.ridSupport = false;
		this.storeCompressedSizes = false;
		this.totalSize = 0;
		this.dbxTotalSize = 0;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getMagicSalt() {
		return magicSalt;
	}
	public void setMagicSalt(Integer magicSalt) {
		this.magicSalt = magicSalt;
	}
	public ArrayList<ResourceLink> getEbx() {
		return ebx;
	}
	public void setEbx(ArrayList<ResourceLink> ebx) {
		this.ebx = ebx;
	}
	public ArrayList<ResourceLink> getDbx() {
		return dbx;
	}
	public void setDbx(ArrayList<ResourceLink> dbx) {
		this.dbx = dbx;
	}
	public ArrayList<ResourceLink> getRes() {
		return res;
	}
	public void setRes(ArrayList<ResourceLink> res) {
		this.res = res;
	}
	public boolean isAlignMembers() {
		return alignMembers;
	}
	public void setAlignMembers(boolean alignMembers) {
		this.alignMembers = alignMembers;
	}
	public boolean isRidSupport() {
		return ridSupport;
	}
	public void setRidSupport(boolean ridSupport) {
		this.ridSupport = ridSupport;
	}
	public boolean isStoreCompressedSizes() {
		return storeCompressedSizes;
	}
	public void setStoreCompressedSizes(boolean storeCompressedSizes) {
		this.storeCompressedSizes = storeCompressedSizes;
	}
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public long getDbxTotalSize() {
		return dbxTotalSize;
	}
	public void setDbxTotalSize(long dbxTotalSize) {
		this.dbxTotalSize = dbxTotalSize;
	}
	
	
	
}
