package tk.captainsplexx.Resource.TOC;

import java.util.ArrayList;

public class ConvertedTocFile {
	public String name;
	public String tag;
	public boolean cas;
	public boolean alwaysEmitSuperBundle;
	public ArrayList<TocSBLink> bundles;
	public ArrayList<TocSBLink> chunks;
	public long totalSize;
	

	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public String getName() {
		return name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean isCas() {
		return cas;
	}
	public boolean isAlwaysEmitSuperBundle() {
		return alwaysEmitSuperBundle;
	}
	public ArrayList<TocSBLink> getBundles() {
		return bundles;
	}
	public ArrayList<TocSBLink> getChunks() {
		return chunks;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCas(boolean cas) {
		this.cas = cas;
	}
	public void setAlwaysEmitSuperBundle(boolean alwaysEmitSuperBundle) {
		this.alwaysEmitSuperBundle = alwaysEmitSuperBundle;
	}
	
	public ConvertedTocFile(/*NULLCONSTRUCTOR*/) {
		this.cas = false;
		this.alwaysEmitSuperBundle = false;
		this.bundles = new ArrayList<TocSBLink>();
		this.chunks = new ArrayList<TocSBLink>();
		this.tag = "";
		this.totalSize = -1;
	}	
}
