package tk.captainsplexx.Resource.TOC;

import tk.captainsplexx.Resource.TOC.TocManager.TocFieldType;


public class TocField {
	public Object obj;
	public TocFieldType type;
	public String name;
	
	public TocField(Object obj, TocFieldType type, String name) {
		this.obj = obj;
		this.type = type;
		this.name = name;
	}
	
	public TocField(){
		//NULL CONSTRUCTOR
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public TocFieldType getType() {
		return type;
	}

	public void setType(TocFieldType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
