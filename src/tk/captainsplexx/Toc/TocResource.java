package tk.captainsplexx.Toc;

public class TocResource {
	public String path;
	public String type;
	public String sha1;
	
	public TocResource(String path, String type, String sha1) {
		this.path = path;
		this.type = type;
		this.sha1 = sha1;
	}

	public String getPath() {
		return path;
	}

	public String getType() {
		return type;
	}

	public String getSHA1() {
		return sha1;
	}	
}
