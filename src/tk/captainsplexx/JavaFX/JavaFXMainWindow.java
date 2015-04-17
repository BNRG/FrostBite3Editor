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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
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
	public FXMLLoader rightLoader;
	public LeftController leftController;
	public RightController rightController;
		
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
		launch(); //Runs until window closes.
		System.exit(0); //TODO
	}

	@Override
	public void start(Stage stageLeft) {
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
        Stage stageRight = new Stage();
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
	}

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
}
