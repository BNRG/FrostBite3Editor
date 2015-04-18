package tk.captainsplexx.Resource.TOC;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;

public class TocSBLink {
	public String id;
	public long offset;
	public int size;
	public LinkBundleType type;
	public TocSBLink(String id, long offset, int size, LinkBundleType type) {
		this.id = id;
		this.offset = offset;
		this.size = size;
		this.type = type;
	}
	
	public TocSBLink(/*USING NULLCONSTUCTOR*/){
		this.id = "";
		this.offset = 0;
		this.size = 0;
		this.type = null;
	}
	
	public LinkBundleType getType() {
		return type;
	}

	public void setType(LinkBundleType type) {
		this.type = type;
	}

	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public TocFile getLinkedSBPart(String sbPath){
		try{
			return TocManager.readSbPart(FileHandler.readFile(sbPath, (int) this.offset, this.size));
		}catch (Exception e){
			//e.printStackTrace();
			System.err.println("Could not read Sb part from "+sbPath+" at "+this.offset);
			return null;
		}
	}
}
