package tk.captainsplexx.Toc;

import java.util.ArrayList;

public class ConvertedTocFile {
	public String name;
	public boolean cas;
	public boolean alwaysEmitSuperBundle;
	public ArrayList<TocSBLink> bundles;
	public ArrayList<TocSBLink> chunks;
	
	public String getName() {
		return name;
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
	
	public ConvertedTocFile(String name, boolean cas,
			boolean alwaysEmitSuperBundle, ArrayList<TocSBLink> bundles,
			ArrayList<TocSBLink> chunks) {
		this.name = name;
		this.cas = cas;
		this.alwaysEmitSuperBundle = alwaysEmitSuperBundle;
		this.bundles = bundles;
		this.chunks = chunks;
	}
	
	public ConvertedTocFile(/*NULLCONSTRUCTOR*/) {
		this.name = "";
		this.cas = false;
		this.alwaysEmitSuperBundle = false;
		this.bundles = new ArrayList<TocSBLink>();
		this.chunks = new ArrayList<TocSBLink>();
	}

	
}
