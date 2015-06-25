package tk.captainsplexx.JavaFX.CellFactories;

import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Game.Game;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.ITEXTURE.ItextureHandler;
import tk.captainsplexx.Resource.TOC.ResourceLink;
import tk.captainsplexx.Resource.TOC.TocConverter.ResourceBundleType;

public class JavaFXexplorer1TCF extends TreeCell<TreeViewEntry> {
	
	public JavaFXexplorer1TCF() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TreeItem<TreeViewEntry> i = getTreeItem();
				if (i != null){
					if (i.getParent()!=null){
						if (i.getValue().getValue() != null){
							Game game = Main.getGame();
							ResourceHandler rs = game.getResourceHandler();
							ResourceLink link = (ResourceLink) i.getValue().getValue();
							if (link.getBundleType() == ResourceBundleType.EBX){
								byte[] data = CasDataReader.readCas(link.getBaseSha1(), link.getDeltaSha1(), link.getSha1(), link.getCasPatchType());
					FileHandler.writeFile("output/ebx_data", data);//TODO DEBUG
								if (data != null){
									TreeItem<TreeViewEntry> ebx = TreeViewConverter.getTreeView(game.getResourceHandler().getEBXHandler().loadFile(data));
									Main.getJavaFXHandler().setTreeViewStructureRight(ebx);
									Main.getJavaFXHandler().getMainWindow().updateRightRoot();
								}else{
									System.err.println("Could not build EBX Explorer because of missing data.");
								}
							}else if (link.getBundleType() == ResourceBundleType.RES){
								byte[] data = CasDataReader.readCas(link.getBaseSha1(), link.getDeltaSha1(), link.getSha1(), link.getCasPatchType());
								if (data != null){
									if (link.getType() == ResourceType.ITEXTURE){
										byte[] itexture = CasDataReader.readCas(link.getSha1(), Main.gamePath+"/Data", rs.getCasCatManager().getEntries(), false);
										//System.out.println("Itexture: "+FileHandler.bytesToHex(itexture));
										FileHandler.writeFile("output/"+link.getName().replace('/', '_')+".dds", ItextureHandler.getDSS(itexture, Main.gamePath+"/Data", rs.getCasCatManager().getEntries()));
										//DDSConverter.convertToTGA(new File("output/"+link.getName().replace('/', '_')+".dds"));
									}else{
										System.err.println("Type not supported yet.");
										FileHandler.writeFile("output/"+link.getName().replace('/', '_')+"."+link.getType(), data);
									}
									Main.getJavaFXHandler().getMainWindow().toggleResToolsVisibility();
								}else{
									System.err.println("Could not find data.");
								}
							}else if (i.getParent().getValue().getType() == EntryType.LIST){
								System.out.println(((ResourceLink)i.getValue().getValue()).getBundleType()+" is currently not supported.");
							}
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
		    String tooltip = item.getTooltip();
		    if (tooltip != null){
		    	 setTooltip(new Tooltip(tooltip));
		    }
	    }
	}
}