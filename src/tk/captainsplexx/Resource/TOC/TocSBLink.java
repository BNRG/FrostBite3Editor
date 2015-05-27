package tk.captainsplexx.Resource.TOC;

import java.io.File;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;

public class TocSBLink {
	public String id;
	public String guid;
	public String sha1;
	public long offset;
	public int size;
	public long sizeLong;
	public LinkBundleType type;
	public String sbPath;
	public boolean delta;
	public boolean base;
	
	public TocSBLink(/*USING NULLCONSTUCTOR*/){
		this.id = "";
		this.offset = -1;
		this.size = -1;
		this.type = null;
		this.sbPath = "";
		this.sha1 = "";
		this.sizeLong = -1;
		this.guid = "";
		this.delta = false;
		this.base = false;
	}
	
	public TocFile getLinkedSBPart(){
		try{
			byte[] data = null;
			if (!delta && base){
				System.out.println("Delta: "+delta+" Base: "+base);
				//Link to unpached
				File unpatched = new File(sbPath.replace("Update/Patch/", ""));
				String normPath = FileHandler.normalizePath(unpatched.getAbsolutePath());
				if (!unpatched.exists()){
					System.err.println("Could not found unpatched file. ("+normPath+")");
					return null;
				}
				data = FileHandler.readFile(normPath, (int) this.offset, this.size);
			}else{
				//In current sb file exists.
				System.out.println("Delta: "+delta+" Base: "+base);
				data = FileHandler.readFile(sbPath, (int) this.offset, this.size);
			}
			
			if (data==null){
				return null;
			}
			return TocManager.readSbPart(data);
		}catch (Exception e){
			//e.printStackTrace();
			System.err.println("Could not read Sb part from "+sbPath+" at "+this.offset);
			return null;
		}
	}
	
	
	/*GETTER AND SETTER*/
	
	public boolean isBase() {
		return base;
	}

	public void setBase(boolean base) {
		this.base = base;
	}


	public boolean isDelta() {
		return delta;
	}


	public void setDelta(boolean delta) {
		this.delta = delta;
	}


	public String getGuid() {
		return guid;
	}


	public void setGuid(String guid) {
		this.guid = guid;
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
}
