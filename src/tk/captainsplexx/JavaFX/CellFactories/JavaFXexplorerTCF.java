package tk.captainsplexx.JavaFX.CellFactories;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.Windows.MainWindow.EntryType;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;
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
				if (getTreeItem() != null){
					if (getTreeItem().getChildren().isEmpty() && getTreeItem().getValue().getValue() instanceof TocSBLink){
						TocSBLink link = ((TocSBLink)getTreeItem().getValue().getValue());
						//TOC MODE
						if (link.getType() == LinkBundleType.BUNDLES){
							TocFile part = link.getLinkedSBPart();
							ConvertedSBpart sbpart = TocConverter.convertSBpart(part);
							
							/* THIS CREATOR DOES CHANGE CURRENT TOC
							 * OFFSETS TO NEW ONE. THIS WILL AND CAN NOT BE HERE!
							 * ONLY FOR DEBUG! - IF ENABLED, NO COMPARE TO ORIGINAL DATA POSSIBLE!
							 */
							//TocCreator.createModifiedSBFile(Core.getGame().getCurrentToc(), sbpart/*REPLACE WITH MODIF. 1*/, false, "output/"+getTreeItem().getParent().getValue().getName()+"_splexx.sb", true);	
							//FileHandler.writeFile("output/"+getTreeItem().getParent().getValue().getName()+"_splexx.toc", TocCreator.createTocFile(Core.getGame().getCurrentToc()));
							/*END*/
							
							Core.getGame().setCurrentSB(sbpart);
							TreeItem<TreeViewEntry> tree = TreeViewConverter.getTreeView(sbpart);
							//Core.getJavaFXHandler().setTreeViewStructureLeft1(tree);
							Core.getJavaFXHandler().getMainWindow().setPackageExplorer1(tree, null);
						}else{
							System.err.println(((TocSBLink)getTreeItem().getValue().getValue()).getType()+" are not supported yet.");
						}
					}else if (getTreeItem().getChildren().isEmpty() && getTreeItem().getValue().getValue() instanceof File){
						//EXPLORER MODE
						Core.getGame().setCurrentFile(FileHandler.normalizePath(((File)getTreeItem().getValue().getValue()).getAbsolutePath().replace(".sb", "")));
						TocFile toc = TocManager.readToc(Core.getGame().getCurrentFile());
						ConvertedTocFile convToc = TocConverter.convertTocFile(toc);
						if (convToc.getName()==null){
							//is null if patched! (update folder files are noname's)
							convToc.setName(Core.getGame().getCurrentFile().replace(FileHandler.normalizePath(Core.gamePath), ""));
						}
						
						if (convToc.isCas()){
							Core.getGame().setCurrentToc(convToc);
							TreeItem<TreeViewEntry> masterTree = new TreeItem<TreeViewEntry>(new TreeViewEntry("BACK (Click)", new ImageView(JavaFXHandler.leftArrowIcon), "GO BACK", EntryType.LIST));
							TreeItem<TreeViewEntry> convTocTree = TreeViewConverter.getTreeView(convToc);
							convTocTree.setExpanded(true);
							masterTree.getChildren().add(convTocTree);
							masterTree.setExpanded(true);
							//Core.getJavaFXHandler().setTreeViewStructureLeft(masterTree);
							Core.getJavaFXHandler().getMainWindow().setPackageExplorer(masterTree);
						}else{
							System.err.println("NON CAS DETECTED!! NO SUPPORT YET.");
							getTreeItem().getParent().getChildren().remove(getTreeItem());
						}
						
						/*DEBUG FOR TOC FILE RECREATION
						System.out.println("DEBUG: TocCreator || sysout in JavaFXExplorer");
						System.out.println(FileHandler.bytesToHex(TocCreator.createTocFile(convToc)));
						
						END OF DEBUG*/
						
						
					}else if (getTreeItem().getValue().getValue() instanceof String){
						if (((String)getTreeItem().getValue().getValue()).equals("GO BACK")){
							//BACK
							Core.getGame().buildExplorerTree();
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
	    	if (item.getTooltip()!=null){
	    		setTooltip(new Tooltip(item.getTooltip()));
	    	}
		    setGraphic(item.getGraphic());
	    }
	}
}