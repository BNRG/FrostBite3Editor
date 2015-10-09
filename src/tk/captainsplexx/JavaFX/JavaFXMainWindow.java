package tk.captainsplexx.JavaFX;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.Display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXebxTCF;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXexplorer1TCF;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXexplorerTCF;
import tk.captainsplexx.JavaFX.CellFactories.ModLoaderListFactory;
import tk.captainsplexx.JavaFX.Controller.LeftController;
import tk.captainsplexx.JavaFX.Controller.ModLoaderController;
import tk.captainsplexx.JavaFX.Controller.ResToolsController;
import tk.captainsplexx.JavaFX.Controller.RightController;
import tk.captainsplexx.Mod.Mod;


public class JavaFXMainWindow extends Application{
	
	public static enum EntryType{
		STRING, INTEGER, LONG, BOOL, FLOAT, DOUBLE, ARRAY, LIST, BYTE, NULL, SHORT,
		SHA1, GUID, ENUM, HEX8, UINTEGER, RAW, CHUNKGUID, RAW2
	};
	
	public static enum WorkDropType { DROP_INTO, REORDER };

	public FXMLLoader leftLoader;
	public FXMLLoader resToolsLoader;
	public FXMLLoader rightLoader;
	public FXMLLoader modLoaderLoader;
	public LeftController leftController;
	public RightController rightController;
	public ResToolsController resToolsController;
	public ModLoaderController modLoaderController;
	public Stage stageLeft;
	public Stage stageRight;
	public Stage stageResTools;
	public Stage stageModLoader;

	public FXMLLoader getLeftLoader() {
		return leftLoader;
	}

	public FXMLLoader getRightLoader() {
		return rightLoader;
	}
	
	public LeftController getLeftController() {
		return leftController;
	}

	public RightController getRightController() {
		return rightController;
	}	

	public FXMLLoader getResToolsLoader() {
		return resToolsLoader;
	}

	public ResToolsController getResToolsController() {
		return resToolsController;
	}

	public ModLoaderController getModLoaderController() {
		return modLoaderController;
	}

	public void setModLoaderController(ModLoaderController modLoaderController) {
		this.modLoaderController = modLoaderController;
	}

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
		this.stageLeft = stageLeft;
		Core.getJavaFXHandler().setMainWindow(this); //Stupid thread bypass.
		Parent leftroot = null;
		/*
		 * 
		 * LEFT
		 * 
		 * */
		try {
			leftLoader = new FXMLLoader(getClass().getResource("LeftWindow.fxml")); //not static to access controller class
			leftroot = leftLoader.load();
			leftController = leftLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene sceneLeft = new Scene(leftroot, 275, 700);
		stageLeft.setX(Display.getDesktopDisplayMode().getWidth()*0.01f);
		stageLeft.setY(Display.getDesktopDisplayMode().getHeight()/2-(sceneLeft.getHeight()/2));
		stageLeft.setTitle("Tools / Explorer");
		stageLeft.getIcons().add(JavaFXHandler.applicationIcon16);
		stageLeft.getIcons().add(JavaFXHandler.applicationIcon32);
		stageLeft.setScene(sceneLeft);
		stageLeft.hide();
		stageLeft.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
        leftController.getExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXexplorerTCF();
            }
        });
        leftController.getExplorer().setEditable(false);
        leftController.getExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        leftController.getExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight()); //Back to top in TCF or what ?

        leftController.getExplorer1().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXexplorer1TCF();
            }
        });
        leftController.getExplorer1().setEditable(false);
        leftController.getExplorer1().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        leftController.getExplorer1().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        
        leftController.getLayer().getItems().addAll("Test","Testsss","Testtssst");
        leftController.getLayer().valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.err.println("Old: "+oldValue+" New: "+newValue);
				// TODO Auto-generated method stub	
			}
		});
        
        leftController.getConsiderPitchBox().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CheckBox considerBox = leftController.getConsiderPitchBox();
				if (considerBox.isSelected()){
					Core.getRender().getCamera().setConsiderPitch(true);
				}else{
					Core.getRender().getCamera().setConsiderPitch(false);
				}
			}
		});
        leftController.getMouseSensitivity().valueProperty().addListener(new ChangeListener<Number>() {
        	public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        		double mouseSens = (double)new_val/500;
        		Core.getRender().getCamera().setMouseSensitivity((float)mouseSens);
        	}
        });
        leftController.getCameraSpeed().valueProperty().addListener(new ChangeListener<Number>() {
        	public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        		double cameraSpeed = (double)new_val*4;
        		Core.getGame().getPlayerHandler().getPlayerEntity().setMovementSpeed((float) cameraSpeed);
        	}
        });
        
        /*
         * 
         * RIGHT
         * 
         * */
        Parent rightroot = null;
		try { 
			rightLoader = new FXMLLoader(getClass().getResource("RightWindow.fxml")); //not static to access controller class
			rightroot = rightLoader.load();
			rightController = rightLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
        stageRight = new Stage();
        Scene sceneRight = new Scene(rightroot, 275, 700);
        stageRight.setTitle("EBX Tools");
        stageRight.setX(Display.getDesktopDisplayMode().getWidth()*0.985f-sceneLeft.getWidth());
        stageRight.setY(Display.getDesktopDisplayMode().getHeight()/2-(sceneLeft.getHeight()/2));
        stageRight.setScene(sceneRight);
        stageRight.getIcons().add(JavaFXHandler.applicationIcon16);
        stageRight.getIcons().add(JavaFXHandler.applicationIcon32);
        stageRight.hide();
        stageRight.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				if (Core.isDEBUG){
					System.exit(1);
				}else{
					e.consume();
				}
			}
		});
        
        rightController.getEBXExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXebxTCF();
            }
        });
        rightController.getEBXExplorer().setEditable(true);
        rightController.getEBXExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        rightController.getEBXExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        
        /*
         * 
         * RES TOOLS
         * 
         * */
        Parent resToolsRoot = null;
		try { 
			resToolsLoader = new FXMLLoader(getClass().getResource("ResToolsWindow.fxml")); //not static to access controller class
			resToolsRoot = resToolsLoader.load();
			resToolsController = resToolsLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
        stageResTools = new Stage();
        Scene sceneResTools = new Scene(resToolsRoot, 275, 275);
        stageResTools.setTitle("Resource Tools");
        stageResTools.setScene(sceneResTools);
        stageResTools.getIcons().add(JavaFXHandler.applicationIcon16);
        stageResTools.getIcons().add(JavaFXHandler.applicationIcon32);
        stageResTools.hide();
        stageResTools.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
        
        /*
         * 
         * MOD LOADER
         * 
         * */
        Parent modLoaderRoot = null;
		try { 
			modLoaderLoader = new FXMLLoader(getClass().getResource("ModLoaderWindow.fxml")); //not static to access controller class
			modLoaderRoot = modLoaderLoader.load();
			modLoaderController = modLoaderLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
        stageModLoader = new Stage();
        Scene sceneModLoader = new Scene(modLoaderRoot, 800, 600);
        stageModLoader.setTitle("FrostBite 3 Tools "+Core.buildVersion);
        stageModLoader.setScene(sceneModLoader);
        stageModLoader.getIcons().add(JavaFXHandler.applicationIcon16);
        stageModLoader.getIcons().add(JavaFXHandler.applicationIcon32);
        stageModLoader.hide();
        stageModLoader.setResizable(false);
        stageModLoader.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Core.keepAlive(false);
			}
		});
        
        modLoaderController.getList().setCellFactory(new Callback<ListView<Mod>, ListCell<Mod>>() {
	        @Override 
	        public ListCell<Mod> call(ListView<Mod> list) {
	            return new ModLoaderListFactory();
	        }
        });
        modLoaderController.getRunEditor().setDisable(true);
	}

	/*UPDATE METHODS*/
	public void updateRightRoot(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				rightController.getEBXExplorer().setRoot(Core.getJavaFXHandler().getTreeViewStructureRight());
				if (rightController.getEBXExplorer().getRoot() != null){
					rightController.getEBXExplorer().getRoot().setExpanded(true);
				}
			}
		});	
	}
	
	public void updateLeftRoot(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				leftController.getExplorer().setRoot(Core.getJavaFXHandler().getTreeViewStructureLeft());
				leftController.getExplorer().scrollTo(0);
				if (leftController.getExplorer().getRoot() != null){
					leftController.getExplorer().getRoot().setExpanded(true);
				}
			}
		});	
	}
	
	public void updateModsList(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<Mod> mods = modLoaderController.getList().getItems();
				mods.clear();
				mods.addAll(Core.getModTools().getMods());
			}
		});	
	}
	
	public void updateLeftRoot1(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				leftController.getExplorer1().setRoot(Core.getJavaFXHandler().getTreeViewStructureLeft1());
				if (leftController.getExplorer1().getRoot() != null){
					leftController.getExplorer1().getRoot().setExpanded(true);
				}
			}
		});	
	}
	
	public void toggleRightVisibility(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (stageRight.isShowing()){
					stageRight.hide();
				}else{
					stageRight.show();
				}
			}
		});	
	}
	
	public void toggleLeftVisibility(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (stageLeft.isShowing()){
					stageLeft.hide();
				}else{
					stageLeft.show();
				}
			}
		});	
	}
	
	public void toggleResToolsVisibility(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (stageResTools.isShowing()){
					stageResTools.hide();
				}else{
					stageResTools.show();
				}
			}
		});	
	}
	
	public void toggleModLoaderVisibility(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (stageModLoader.isShowing()){
					stageModLoader.hide();
				}else{
					stageModLoader.show();
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
}
