package tk.captainsplexx.Resource.TOC;

import java.util.ArrayList;

import tk.captainsplexx.Resource.TOC.TocManager.TocEntryType;

public class TocEntry {
	public TocEntryType type;
	public ArrayList<TocField> fields;
	
	public TocEntry(TocEntryType type) {
		this.type = type;
		this.fields = new ArrayList<TocField>();
	}

	public TocEntryType getType() {
		return type;
	}

	public void setType(TocEntryType type) {
		this.type = type;
	}

	public ArrayList<TocField> getFields() {
		return fields;
	}
}
