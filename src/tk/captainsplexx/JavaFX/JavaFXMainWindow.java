package tk.captainsplexx.JavaFX;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import tk.captainsplexx.Game.Main;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXebxTCF;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXexplorer1TCF;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXexplorerTCF;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


public class JavaFXMainWindow extends Application{
	
	public static enum EntryType{
		STRING, INTEGER, LONG, BOOL, FLOAT, DOUBLE, ARRAY, LIST, BYTE, NULL, SHORT,
		SHA1, GUID, ENUM, HEX8, UINTEGER, RAW, CHUNKGUID
	};
	
	public static enum WorkDropType { DROP_INTO, REORDER };

	public FXMLLoader leftLoader;
	public FXMLLoader resToolsLoader;
	public FXMLLoader rightLoader;
	public LeftController leftController;
	public RightController rightController;
	public ResToolsController resToolsController;
	public Stage stageLeft;
	public Stage stageRight;
	public Stage stageResTools;

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
		Main.getJavaFXHandler().setMainWindow(this); //Stupid thread bypass.
		Parent leftroot = null;
		/*LEFT*/
		try {
			leftLoader = new FXMLLoader(getClass().getResource("LeftWindow.fxml")); //not static to access controller class
			leftroot = leftLoader.load();
			leftController =  leftLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Scene sceneLeft = new Scene(leftroot, 275, 700);
		Scene sceneLeft = new Scene(leftroot, 275, 700);
		stageLeft.setX(Display.getDesktopDisplayMode().getWidth()*0.01f);
		stageLeft.setY(Display.getDesktopDisplayMode().getHeight()/2-(sceneLeft.getHeight()/2));
		stageLeft.setTitle("Tools / Explorer");
		stageLeft.getIcons().add(JavaFXHandler.applicationIcon16);
		stageLeft.getIcons().add(JavaFXHandler.applicationIcon32);
		stageLeft.setScene(sceneLeft);
		stageLeft.show();
		stageLeft.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
        /*EXPLORER 0*/
        leftController.getExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXexplorerTCF();
            }
        });
        leftController.getExplorer().setEditable(false);
        leftController.getExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        leftController.getExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        /*END OF EXPLORER 0*/
        
        /*EXPLORER 1*/
        leftController.getExplorer1().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXexplorer1TCF();
            }
        });
        leftController.getExplorer1().setEditable(false);
        leftController.getExplorer1().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        leftController.getExplorer1().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        /*END OF EXPLORER 1*/
        
        /*RIGHT*/
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
        stageRight.show();
        stageRight.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
        
        /*EBX-EXPLORER*/
        rightController.getEBXExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXebxTCF();
            }
        });
        rightController.getEBXExplorer().setEditable(true);
        rightController.getEBXExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        rightController.getEBXExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        /*END OF EBX-EXPLORER*/
        
        /*RES TOOLS*/
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
        /*END OF RES TOOLS*/
	}

	/*UPDATE METHODS*/
	public void updateRightRoot(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				rightController.getEBXExplorer().setRoot(Main.getJavaFXHandler().getTreeViewStructureRight());
				rightController.getEBXExplorer().getRoot().setExpanded(true);
			}
		});	
	}
	
	public void updateLeftRoot(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				leftController.getExplorer().setRoot(Main.getJavaFXHandler().getTreeViewStructureLeft());
				leftController.getExplorer().scrollTo(0);
				leftController.getExplorer().getRoot().setExpanded(true);
			}
		});	
	}
	
	public void updateLeftRoot1(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				leftController.getExplorer1().setRoot(Main.getJavaFXHandler().getTreeViewStructureLeft1());
				leftController.getExplorer1().getRoot().setExpanded(true);
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
	/*END OF UPDATE METHODS*/
}
