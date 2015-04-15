package tk.captainsplexx.Toc;

public class TocSBLink {
	public String name;
	public long offset;
	public int size;
	public TocSBLink(String name, long offset, int size) {
		this.name = name;
		this.offset = offset;
		this.size = size;
	}
	
	public TocSBLink(/*USING NULLCONSTUCTOR*/){
		this.name = "";
		this.offset = 0;
		this.size = 0;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
