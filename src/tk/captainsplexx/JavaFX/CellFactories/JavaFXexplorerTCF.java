package tk.captainsplexx.JavaFX.CellFactories;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Toc.TocConverter;
import tk.captainsplexx.Toc.TocSBLink;

public class JavaFXexplorerTCF extends TreeCell<TreeViewEntry> {
	public static enum ExplorerMode {
		TOC, FILEEXPLORER
	};
	
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
				if (mode == ExplorerMode.TOC){
					if (getTreeItem().getChildren().isEmpty() && getTreeItem().getValue().getValue() instanceof TocSBLink){
						//if (getTreeItem().getParent().getValue().getName().startsWith("bundles")){
						if (((TocSBLink)getTreeItem().getValue().getValue()).getType() == LinkBundleType.BUNDLES){
							
							Main.getJavaFXHandler().setTreeViewStructureLeft1(
									TreeViewConverter.getTreeView(
											TocConverter.convertSBpart( //REMOVE THIS LINE TO DEBUG.
													((TocSBLink)getTreeItem().getValue().getValue()).getLinkedSBPart(Main.getGame().getCurrentTocPath()+".sb")))
							);
							Main.getJavaFXHandler().getMainWindow().updateLeftRoot1();
						}else{
							System.err.println(((TocSBLink)getTreeItem().getValue().getValue()).getType()+" are not supported yet.");
						}
					}
				}else{
					//TODO
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