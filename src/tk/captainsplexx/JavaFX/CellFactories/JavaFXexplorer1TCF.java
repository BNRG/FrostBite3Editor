package tk.captainsplexx.JavaFX.CellFactories;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Game.Game;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.TOC.ResourceLink;
import tk.captainsplexx.Resource.TOC.TocConverter.ResourceBundleType;

public class JavaFXexplorer1TCF extends TreeCell<TreeViewEntry> {
	
	public JavaFXexplorer1TCF() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TreeItem<TreeViewEntry> i = getTreeItem();
				if (i.getParent()!=null){
					if (i.getValue().getValue() != null){
						if (((ResourceLink)i.getValue().getValue()).getBundleType() == ResourceBundleType.EBX && i.getParent().getValue().getType() == EntryType.LIST){
							Game game = Main.getGame();
							ResourceLink link = (ResourceLink) i.getValue().getValue();
							byte[] data = CasDataReader.readCas(link.getSha1(), game.getGamePath()+"/Data", game.getResourceHandler().getCasCatManager().getEntries());
							TreeItem<TreeViewEntry> ebx = TreeViewConverter.getTreeView(game.getResourceHandler().getEBXHandler().loadFile(data));
							Main.getJavaFXHandler().setTreeViewStructureRight(ebx);
							Main.getJavaFXHandler().getMainWindow().updateRightRoot();
						}else if (i.getParent().getValue().getType() == EntryType.LIST){
							System.out.println(((ResourceLink)i.getValue().getValue()).getBundleType()+" is currently not supported.");
						}
					}
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
	    }else if (item.getType() == EntryType.LIST){
    		setText(item.getName());
    		setGraphic(item.getGraphic());
    	}else {
	    	setText(item.getName());
		    setGraphic(item.getGraphic());
	    }
	}
}