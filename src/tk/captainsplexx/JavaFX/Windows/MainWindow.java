package tk.captainsplexx.JavaFX.Windows;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import tk.captainsplexx.Entity.Layer.EntityLayer;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Mod.Mod;
import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.TOC.ResourceLink;


public class MainWindow extends Application{
	
	public static enum EntryType{
		STRING, INTEGER, LONG, BOOL, FLOAT, DOUBLE, ARRAY, LIST, BYTE, NULL, SHORT,
		SHA1, GUID, ENUM, HEX8, UINTEGER, RAW, CHUNKGUID, RAW2
	};
	
	public static enum WorkDropType { DROP_INTO, REORDER };

	private ArrayList<EBXWindow> ebxWindows;
	private ArrayList<ImagePreviewWindow> imagePreviewWindows;
	private ModLoaderWindow modLoaderWindow = null;
	private ToolsWindow toolsWindow;


	/*---------START--------------*/
	public void runApplication(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				launchApplication();
			}
		}).start();
	}
		
	void launchApplication(){
		launch();
		System.err.println("JavaFX Application closed.");
	}

	@Override
	public void start(Stage stageLeft) {
		ebxWindows = new ArrayList<>();
		imagePreviewWindows = new ArrayList<>();
		modLoaderWindow = new ModLoaderWindow();
		toolsWindow = new ToolsWindow();
		Core.getJavaFXHandler().setMainWindow(this);
	}
	
	public boolean createEBXWindow(EBXFile ebxFile){
		try{
			Platform.runLater(new Runnable() {
				public void run() {
					EBXWindow window = new EBXWindow(ebxFile);
					ebxWindows.add(window);
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("EBXWindow creation failed");
			return false;
		}
		return true;
	}
	
	public boolean destroyEBXWindow(Stage stage){
		try{Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				for (EBXWindow window : ebxWindows){
					if (window.getStage()==stage){
						stage.close();
						ebxWindows.remove(window);
						break;
					}
				}
			}
		});
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("EBXWindow could not get destroyed!");
			return false;
		}
		return true;
	}
	
	public boolean createImagePreviewWindow(File file, File ddsFile, ResourceLink resourceLink, String title){
		try{
			Platform.runLater(new Runnable() {
				public void run() {
					ImagePreviewWindow ipw = new ImagePreviewWindow(file, ddsFile, resourceLink, title);
					ipw.getController().setParentStage(ipw.getStage());
					imagePreviewWindows.add(ipw);
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("EBX Window could not get created!");
			return false;
		}
		return true;
	}
	
	public boolean destroyImagePreviewWindow(Stage stage){
		try{Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (ImagePreviewWindow window : imagePreviewWindows){
					if (window.getStage()==stage){
						stage.close();
						imagePreviewWindows.remove(window);
						break;
					}
				}
				System.err.println("ImagePreviewWindow's stage not found!");
			}
		});
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("ImagePreviewWindow could not get destroyed!");
			return false;
		}
		return true;
	}
	
	

	/*UPDATE METHODS*/	
	public void setPackageExplorer(TreeItem<TreeViewEntry> treeview){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				toolsWindow.getController().getExplorer().setRoot(treeview);
				toolsWindow.getController().getExplorer().scrollTo(0);
				if (toolsWindow.getController().getExplorer().getRoot() != null){
					toolsWindow.getController().getExplorer().getRoot().setExpanded(true);
				}
			}
		});	
	}
	
	public void updateModsList(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<Mod> mods = modLoaderWindow.getController().getList().getItems();
				mods.clear();
				mods.addAll(Core.getModTools().getMods());
			}
		});	
	}
	
	public void setPackageExplorer1(TreeItem<TreeViewEntry> treeview, String filterStr){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				toolsWindow.setExplorer1(treeview, filterStr);
				/*
				toolsWindow.getController().getExplorer1().setRoot(treeview);
				if (toolsWindow.getController().getExplorer1().getRoot() != null){
					toolsWindow.getController().getExplorer1().getRoot().setExpanded(true);
				}*/
			}
		});	
	}
		
	public void toggleLeftVisibility(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Stage tools = toolsWindow.getStage();
				if (tools.isShowing()){
					tools.hide();
				}else{
					tools.show();
				}
			}
		});	
	}
		
	public void toggleModLoaderVisibility(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Stage modLoaderStage = modLoaderWindow.getStage();
				if (modLoaderStage.isShowing()){
					modLoaderStage.hide();
				}else{
					modLoaderStage.show();
				}
			}
		});	
	}
	/*END OF UPDATE METHODS*/
	
	public void selectGamePath(){
		try{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					final DirectoryChooser directoryChooser = new DirectoryChooser();
					directoryChooser.setTitle("Select a game root directory!");
					final File selectedDirectory = directoryChooser.showDialog(new Stage());
					if (selectedDirectory != null) {
						String path = selectedDirectory.getAbsolutePath().replace('\\', '/');
						System.out.println("Selected '"+path+"' as gamepath.");
						Core.gamePath = path;
					}else{
						Core.gamePath = "emty";
						Core.keepAlive = false;
					}
				}
			});
		}catch(IllegalStateException e){
			System.out.println("Waiting for Toolkit...");
			//JavaFX is threaded and may take a while to work.
			//java.lang.IllegalStateException: Toolkit not initialized
			selectGamePath();
		}
	}
	
	public void updateLayers(ArrayList<EntityLayer> layers){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<String> list = toolsWindow.getController().getLayer().getItems();
				list.clear();
				for (EntityLayer layer : layers){
					list.add(layer.getName());
				}
				toolsWindow.getController().getDestroyLayerButton().setDisable(layers.isEmpty());
			}
		});		
	}


	public ModLoaderWindow getModLoaderWindow() {
		return modLoaderWindow;
	}



	public ArrayList<ImagePreviewWindow> getImagePreviewWindows() {
		return imagePreviewWindows;
	}
	

	public ArrayList<EBXWindow> getEBXWindows() {
		return ebxWindows;
	}
	
}
