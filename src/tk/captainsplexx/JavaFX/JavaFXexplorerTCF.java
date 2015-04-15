package tk.captainsplexx.JavaFX;

import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import javafx.scene.control.TreeCell;

public class JavaFXexplorerTCF extends TreeCell<TreeViewEntry> {
	@Override
	public void updateItem(TreeViewEntry item, boolean empty) {
	    super.updateItem(item, empty);
	    if (empty) {
		    setText(null);
		    setGraphic(null);
	    } else {
	    	if (item.getType() == EntryType.STRING){
	    		setText(item.getName()+":"+(String)item.getValue());
	    	}else{
	    		setText("TEST");
	    	}
		    setGraphic(item.getGraphic());
	    }
	}
}