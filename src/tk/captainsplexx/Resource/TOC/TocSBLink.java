package tk.captainsplexx.Resource.TOC;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;

public class TocSBLink {
	public String id;
	public String sha1;
	public long offset;
	public int size;
	public long sizeLong;
	public LinkBundleType type;
	public String sbPath;	
	public TocSBLink(/*USING NULLCONSTUCTOR*/){
		this.id = "";
		this.offset = 0;
		this.size = 0;
		this.type = null;
		this.sbPath = "";
		this.sha1 = "";
		this.sizeLong = 0;
	}
	
	
	public long getSizeLong() {
		return sizeLong;
	}


	public void setSizeLong(long sizeLong) {
		this.sizeLong = sizeLong;
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
	
	public String getSbPath() {
		return sbPath;
	}

	public void setSbPath(String sbPath) {
		this.sbPath = sbPath;
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
	
	
	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	public TocFile getLinkedSBPart(){
		try{
			return TocManager.readSbPart(FileHandler.readFile(sbPath, (int) this.offset, this.size));
		}catch (Exception e){
			//e.printStackTrace();
			System.err.println("Could not read Sb part from "+sbPath+" at "+this.offset);
			return null;
		}
	}
}
