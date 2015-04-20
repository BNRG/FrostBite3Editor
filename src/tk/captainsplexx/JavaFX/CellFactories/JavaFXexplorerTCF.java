package tk.captainsplexx.JavaFX.CellFactories;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;
import tk.captainsplexx.Resource.TOC.TocConverter;
import tk.captainsplexx.Resource.TOC.TocFile;
import tk.captainsplexx.Resource.TOC.TocManager;
import tk.captainsplexx.Resource.TOC.TocSBLink;

public class JavaFXexplorerTCF extends TreeCell<TreeViewEntry> {
	public JavaFXexplorerTCF() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (getTreeItem().getChildren().isEmpty() && getTreeItem().getValue().getValue() instanceof TocSBLink){
					//TOC MODE
					if (((TocSBLink)getTreeItem().getValue().getValue()).getType() == LinkBundleType.BUNDLES){
						
						Main.getJavaFXHandler().setTreeViewStructureLeft1(
								TreeViewConverter.getTreeView(
										TocConverter.convertSBpart( //REMOVE THIS LINE TO DEBUG.
												((TocSBLink)getTreeItem().getValue().getValue()).getLinkedSBPart())));
						Main.getJavaFXHandler().getMainWindow().updateLeftRoot1();
					}else{
						System.err.println(((TocSBLink)getTreeItem().getValue().getValue()).getType()+" are not supported yet.");
					}
				}else if (getTreeItem().getChildren().isEmpty() && getTreeItem().getValue().getValue() instanceof File){
					//EXPLORER MODE
					TocFile toc = TocManager.readToc(((File)getTreeItem().getValue().getValue()).getAbsolutePath().replace(".sb", ""));
					ConvertedTocFile convToc = TocConverter.convertTocFile(toc);
					TreeItem<TreeViewEntry> masterTree = new TreeItem<TreeViewEntry>(new TreeViewEntry("BACK (Click)", new ImageView(JavaFXHandler.leftArrowIcon), "GO BACK", EntryType.LIST));
					TreeItem<TreeViewEntry> convTocTree = TreeViewConverter.getTreeView(convToc);
					masterTree.getChildren().add(convTocTree);
					masterTree.setExpanded(true);
					Main.getJavaFXHandler().setTreeViewStructureLeft(masterTree);
					Main.getJavaFXHandler().getMainWindow().updateLeftRoot();
				}else if (getTreeItem().getValue().getValue() instanceof String){
					if (((String)getTreeItem().getValue().getValue()).equals("GO BACK")){
						//BACK
						Main.getGame().buildExplorerTree();
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
	    		setText(item.getName()+" with undefined type for explorerTCF: "+item.getType());
	    	}
		    setGraphic(item.getGraphic());
	    }
	}
}