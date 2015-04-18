package tk.captainsplexx.Resource.TOC;

import java.util.ArrayList;

import tk.captainsplexx.Resource.TOC.TocManager.TocFileType;

public class TocFile {
	public ArrayList<TocEntry> entries;
	public TocFileType type;
	
	public TocFile(TocFileType type){
		this.entries = new ArrayList<TocEntry>();
		this.type = type;
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
}
