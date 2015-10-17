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
	
	public void createNew(){
		Core.getJavaFXHandler().getDialogBuilder().showInfo("Info",
				"No Interface for that!\n\n"
				+ "1. Create a new Folder inside the 'mods' folder.\n"
				+ "2. Copy 'sample_info.txt' and 'sample_logo.png' inside it,\n"
				+ "              as 'info.txt' and 'logo.png'.\n"
				+ "3. Change values/image to whatever you need.\n\n"
				+ "Restart the Tool, Select your new mod -> Click Editor!\n\n\n\n"
				+ "Share your mod:\n\n"
				+ "1. Make sure the clients are on the same Version.\n"
				+ "2. ZIP your Modfolder using 7zip, Winrar or any other archiver!\n"
				+ "3. Extract the ZIP inside the 'mods' folder on the target system\n\n"
				+ "Restart the Tool, if it's already running!");
			
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
