package tk.captainsplexx.JavaFX;

import tk.captainsplexx.Resource.DDSConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;

public class LeftController {
	@FXML
	public TreeView<TreeViewEntry> explorer;
	@FXML
	public TreeView<TreeViewEntry> explorer1;
	
	@FXML
	public Label lodLabel;
	
	/*OnAction*/
	public void exit(){ 
		System.out.println("EXIT -> FXML LEFT -> MENU -> QUIT");	
	}
	
	public void about(){ 
		//TODO
	}
	
	public void incLOD(){ 
		int current = Integer.valueOf(lodLabel.getText());
		current+=1;
		DDSConverter.MIP_MAP_LEVEL = current;
		lodLabel.setText(current+"");
		System.out.println("New LOD for textures: "+DDSConverter.MIP_MAP_LEVEL);
	}
	
	public void decLOD(){ 
		int current = Integer.valueOf(lodLabel.getText());
		if (current>0){
			current-=1;
			DDSConverter.MIP_MAP_LEVEL = current;
			lodLabel.setText(current+"");
			System.out.println("New LOD for textures: "+DDSConverter.MIP_MAP_LEVEL);
		}
	}
	
	
	
	
	
	
	public TreeView<TreeViewEntry> getExplorer() {
		return explorer;
	}

	public TreeView<TreeViewEntry> getExplorer1() {
		return explorer1;
	}
}
