package tk.captainsplexx.Resource;

public class FileSeeker {
	public int offset;
	public FileSeeker(){
		offset = 0;
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
}
