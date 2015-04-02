package tk.captainsplexx.JavaFX;

import tk.captainsplexx.Game.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class LeftController {
	@FXML
	public TreeView<String> explorer;
	
	/*OnAction*/
	public void exit(){ 
		System.out.println("EXIT -> FXML LEFT -> MENU -> QUIT");
	}
	
	public void changegamepath(){
		System.out.println("Current gamepath: "+Main.getGame().getGamePath());
		//Main.getGame().setGamePath(""); //TODO
	}

	public TreeView<String> getExplorer() {
		return explorer;
	}

}
