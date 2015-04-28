package tk.captainsplexx.Resource;

public class FileSeeker {
	public int offset;
	public String description;
	public FileSeeker(){
		offset = 0;
		description = null;
	}
	
	public FileSeeker(String description){
		offset = 0;
		this.description = description;
	}
	
	public void seek(int bytes){
		offset += bytes;
	}
	
	public int getOffset(){
		return offset;
	}
	public void setOffset(int offset){
		this.offset = offset;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
