package tk.captainsplexx.JavaFX;

import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Toc.TocSBLink;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class JavaFXexplorerTCF extends TreeCell<TreeViewEntry> {
	public static enum ExplorerMode {
		TOC, SB, FILEEXPLORER
	};
	
	public static String initString = " (INITIALIZED)";
	
	public ExplorerMode mode;
	

	public ExplorerMode getMode() {
		return mode;
	}

	public void setMode(ExplorerMode mode) {
		this.mode = mode;
	}

	public JavaFXexplorerTCF() {
		this.mode = ExplorerMode.TOC;
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (getTreeItem().getChildren().isEmpty() && getTreeItem().getValue().getValue() instanceof TocSBLink){
					getTreeItem().getValue().setName(getTreeItem().getValue().getName()+initString);
					getTreeItem().getValue().setGraphic(new ImageView(JavaFXHandler.rawIcon));
					
					/*REPLACE WITH CONTENT*/
					getTreeItem().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("test", null, null, EntryType.STRING)));
					
					getTreeItem().setExpanded(true);
				}
			}
		});
	}
	@Override
	public void updateItem(TreeViewEntry item, boolean empty) {
	    super.updateItem(item, empty);
	    if (empty) {
		    setText(null);
		    setGraphic(null);
	    } else {
	    	if (item.getType() == EntryType.STRING){
	    		if (item.getValue() != null && !(item.getValue() instanceof TocSBLink)){
	    			setText(item.getName()+":"+(String)item.getValue());
	    		}else{
	    			setText(item.getName());
	    		}
	    	}else if (item.getType() == EntryType.LIST){
	    		setText(item.getName());
	    	}else{
	    		setText(item.getName()+"TEST");
	    	}
		    setGraphic(item.getGraphic());
	    }
	}
}