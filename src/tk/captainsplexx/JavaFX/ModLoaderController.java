package tk.captainsplexx.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Mod.Mod;
import tk.captainsplexx.Resource.FileHandler;

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
	Label gameName;
	@FXML
	ImageView logo;
	@FXML
	TextArea desc;
	@FXML
	Button runEditor;
	
	
	public void runEditor(){
		Main.runEditor = true;
	}
	
	public void openModFolder(){
		FileHandler.openFolder("mods/");
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

	public Button getRunEditor() {
		return runEditor;
	}

	public Label getGameName() {
		return gameName;
	}
	
	
}
