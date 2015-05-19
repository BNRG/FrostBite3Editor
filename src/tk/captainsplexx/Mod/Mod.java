package tk.captainsplexx.Mod;

public class Mod {
	String name;
	String author;
	String path;
	String tempFolder;
	String desc;
	String game;
	
	public Mod(String name, String author, String game, String path){
		this.name = name;
		this.author = author;
		this.path = path;
		this.game = game;
		this.tempFolder = "";
		this.desc = "";
	}
	
	public Mod(){
		/*USING NULLCONSTRUCTOR*/
	}
	
	
	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTempFolder() {
		return tempFolder;
	}
	public void setTempFolder(String tempFolder) {
		this.tempFolder = tempFolder;
	}	

}
