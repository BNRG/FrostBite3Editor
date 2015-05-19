package tk.captainsplexx.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Mod.Mod;

public class ModLoaderController {
	@FXML
	Label gamepath;
	@FXML
	ListView<Mod> list;
	@FXML
	Label modName;
	@FXML
	Label authorName;
	@FXML
	ImageView logo;
	@FXML
	TextArea desc;
	
	
	public void runEditor(){
		Main.runEditor = true;
	}
	
	public void openModFolder(){
		System.out.println("TODO");
	}	
	
	//set bottom label
	public void setGamepath(String path) {
		if (this.gamepath != null){
			this.gamepath.setText(path);
		}else{
			setGamepath(path);
		}
	}
	
	public ListView<Mod> getList() {
		return list;
	}

	public Label getModName() {
		return modName;
	}

	public Label getAuthorName() {
		return authorName;
	}

	public ImageView getLogo() {
		return logo;
	}

	public TextArea getDesc() {
		return desc;
	}
	
}
