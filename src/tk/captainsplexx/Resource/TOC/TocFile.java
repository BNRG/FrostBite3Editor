package tk.captainsplexx.Resource.TOC;

import java.util.ArrayList;

import tk.captainsplexx.Resource.TOC.TocManager.TocFileType;

public class TocFile {
	public ArrayList<TocEntry> entries;
	//entries could be only one root entry, but may needed in other frostbite games :)
	public TocFileType type;
	public String sbpath;
	
	public TocFile(TocFileType type, String sbpath){
		this.entries = new ArrayList<TocEntry>();
		this.type = type;
		this.sbpath = sbpath;
	}
	public TocFile(TocFileType type){
		this.entries = new ArrayList<TocEntry>();
		this.type = type;
		this.sbpath = "";
	}
	
	public ArrayList<TocEntry> getEntries() {
		return entries;
	}

	public TocFileType getType() {
		return type;
	}

	public void setType(TocFileType type) {
		this.type = type;
	}
	public String getSBPath() {
		return sbpath;
	}
	public void setSBPath(String sbpath) {
		this.sbpath = sbpath;
	}		
	
	
}
