package tk.captainsplexx.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class LeftController {
	@FXML
	public TreeView<TreeViewEntry> explorer;
	@FXML
	public TreeView<TreeViewEntry> explorer1;
	
	/*OnAction*/
	public void exit(){ 
		System.out.println("EXIT -> FXML LEFT -> MENU -> QUIT");	
	}
	
	public TreeView<TreeViewEntry> getExplorer() {
		return explorer;
	}

	public TreeView<TreeViewEntry> getExplorer1() {
		return explorer1;
	}
	
	public void about(){
		
	}
}
