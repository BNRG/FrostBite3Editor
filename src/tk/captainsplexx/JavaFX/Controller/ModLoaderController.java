package tk.captainsplexx.JavaFX.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import tk.captainsplexx.Game.Core;
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
	@FXML
	Button playButton;
	@FXML
	CheckBox checkBox;
	
	
	public void runEditor(){
		Core.runEditor = true;
	}
	
	public void playMod(){
		//Core.getJavaFXHandler().getDialogBuilder().showInfo("INFO", "This may take a while!");
		Core.getModTools().playMod((checkBox.isVisible()&&checkBox.isSelected())||!checkBox.isVisible());
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

	public Button getPlayButton() {
		return playButton;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}
	
	
	
	
}
