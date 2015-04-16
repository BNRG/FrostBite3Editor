package tk.captainsplexx.JavaFX.CellFactories;

import javafx.scene.control.TreeCell;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;

public class JavaFXexplorer1TCF extends TreeCell<TreeViewEntry> {
	@Override
	public void updateItem(TreeViewEntry item, boolean empty) {
	    super.updateItem(item, empty);
	    if (empty) {
		    setText(null);
		    setGraphic(null);
	    }else if (item.getType() == EntryType.LIST){
    		setText(item.getName());
    		setGraphic(item.getGraphic());
    	}else {
	    	setText(item.getName()+"TEST1");
		    setGraphic(item.getGraphic());
	    }
	}
}