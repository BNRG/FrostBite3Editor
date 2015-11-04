package tk.captainsplexx.JavaFX.CellFactories;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Game.Game;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.Windows.MainWindow.EntryType;
import tk.captainsplexx.Mod.ModTools;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.ITEXTURE.ITexture;
import tk.captainsplexx.Resource.ITEXTURE.ITextureHandler;
import tk.captainsplexx.Resource.ITEXTURE.ImageConverter;
import tk.captainsplexx.Resource.ITEXTURE.ImageConverter.ImageType;
import tk.captainsplexx.Resource.MESH.MeshConverter;
import tk.captainsplexx.Resource.TOC.ResourceLink;
import tk.captainsplexx.Resource.TOC.TocConverter.ResourceBundleType;

public class JavaFXexplorer1TCF extends TreeCell<TreeViewEntry> {
	private ContextMenu contextMenu = new ContextMenu();
    private MenuItem restore, remove, rename;
    
	public JavaFXexplorer1TCF() {
		
		restore = new MenuItem("Restore Orignal File");
		restore.setGraphic(new ImageView(JavaFXHandler.clipboardPasteIcon));
		restore.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	byte[] data = null;
            	ResourceLink link = (ResourceLink) getTreeItem().getValue().getValue();
            	if (link!=null){
            		data = CasDataReader.readCas(link.getBaseSha1(), link.getDeltaSha1(), link.getSha1(), link.getCasPatchType());
            		if (data!=null){
            			File target = new File(Core.getGame().getCurrentMod().getPath()+ModTools.RESOURCEFOLDER+link.getName()+"."+link.getBundleType().toString().toLowerCase());
            			if (target.exists()){
            				File backup = new File(target.getAbsoluteFile()+".bak");
            				if (backup.exists()){
            					backup.delete();
            				}
            				target.renameTo(backup);
            			}
            			if (!FileHandler.writeFile(target.getAbsolutePath(), data)){
            				System.err.println("Could not write data to file. Check permissions!");
            			}
            		}else{
            			System.err.println("Could not fetch original data.");
            		}
            	}
            }
        });
		
		remove = new MenuItem("Remove");
		remove.setGraphic(new ImageView(JavaFXHandler.removeIcon));
		remove.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	System.err.println("TODO REMOVE");
            }
        });
		remove.setDisable(true);
		
		rename = new MenuItem("Rename");
		rename.setGraphic(new ImageView(JavaFXHandler.textIcon));
		rename.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	System.err.println("TODO RENAME");
            }
        });
		rename.setDisable(true);
		
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TreeItem<TreeViewEntry> i = getTreeItem();
				if (i != null){
					if (i.getParent()!=null){
						if (i.getValue().getValue() != null){
							if (event.getButton() == MouseButton.PRIMARY){
								
								Game game = Core.getGame();
								ResourceHandler rs = game.getResourceHandler();
								ResourceLink link = (ResourceLink) i.getValue().getValue();
								byte[] data = rs.readResourceLink(link, false /*useOriginalDataONLY*/);
								if (Core.isDEBUG&&data!=null){
									FileHandler.writeFile("output/debug/targetData_TCF1", data);
								}
								if (link.getBundleType() == ResourceBundleType.EBX){
									if (data != null){
										EBXFile ebxFile = game.getResourceHandler().getEBXHandler().loadFile(data);
										if (ebxFile!=null){
											TreeItem<TreeViewEntry> ebx = TreeViewConverter.getTreeView(ebxFile);
											
											Core.getJavaFXHandler().getMainWindow().createEBXWindow(ebxFile);
										}
									}else{
										System.err.println("Could not build EBX Explorer because of missing data.");
									}
								}else if (link.getBundleType() == ResourceBundleType.RES){
									if (data != null){
										if (link.getType() == ResourceType.ITEXTURE){
											byte[] ddsBytes = ITextureHandler.getDSS(data);
											if (ddsBytes!=null){
												File ddsFile = new File("temp/images/"+link.getName().replace('/', '_')+".dds");
												FileHandler.writeFile(ddsFile.getAbsolutePath(), ddsBytes);
												
												File pngFile = null;
												ITexture itexture = new ITexture(data, null);
												if (itexture.getPixelFormat()==ITexture.TF_NormalDXN){
													//Convert using Nvidia
													File tga = ImageConverter.convertToTGA(ddsFile);
													pngFile = ImageConverter.convert(tga, ImageType.PNG, true);
												}else{
													//Convert using ImageMagick
													pngFile = ImageConverter.convert(ddsFile, ImageType.PNG, true);
												}
												if (pngFile!=null){
													Core.getJavaFXHandler().getMainWindow().createImagePreviewWindow(pngFile, ddsFile, link, link.getName());
												}
											}
										}else if (link.getType() == ResourceType.MESH){
											byte[] obj = MeshConverter.getAsOBJ(data, game.getCurrentSB());
											FileHandler.writeFile("output/"+link.getName().replace('/', '_')+".obj", obj);
										}else{
											System.err.println("Type not supported yet.");
											FileHandler.writeFile("output/"+link.getName().replace('/', '_')+"."+link.getType(), data);
										}
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
			}
		});
	}
	
	@Override
	public void updateItem(TreeViewEntry item, boolean empty) {
	    super.updateItem(item, empty);
	    contextMenu.getItems().clear();
	    if (empty) {
		    setText(null);
		    setGraphic(null);
		    setUnderline(false);
	    }else if (item.getType() == EntryType.LIST){
    		setText(item.getName());
    		setGraphic(item.getGraphic());
    		setUnderline(false);
    		setContextMenu(contextMenu);
    	}else {
    		setUnderline(false);
	    	setText(item.getName());
		    setGraphic(item.getGraphic());
		    String tooltip = item.getTooltip();
		    if (tooltip != null){
		    	 setTooltip(new Tooltip(tooltip));
		    }
		    setContextMenu(contextMenu);
		    if (item.getValue()!=null&&item.getValue() instanceof ResourceLink){
		    	ResourceLink link = (ResourceLink) item.getValue();
		    	if (link.isHasModFile()){
		    		setUnderline(true);
		    		contextMenu.getItems().addAll(restore);
		    	}else{
		    		contextMenu.getItems().addAll(rename, remove);
		    	}
		    }
	    }
	}
}