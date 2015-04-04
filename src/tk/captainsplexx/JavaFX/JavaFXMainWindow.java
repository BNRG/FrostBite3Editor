package tk.captainsplexx.JavaFX;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import tk.captainsplexx.Game.Main;
import javafx.application.Application;
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
		SHA1, GUID
	};
	
	public static enum WorkDropType { DROP_INTO, REORDER };

	public FXMLLoader leftLoader;
	public FXMLLoader rightLoader;
	public LeftController leftController;
	public RightController rightController;
	
	public Parent leftroot = null;
	public Parent rightroot = null;
	
	public FXMLLoader getLeftLoader() {
		return leftLoader;
	}

	public FXMLLoader getRightLoader() {
		return rightLoader;
	}
	
	
	public LeftController getLeftController() {
		return leftController;
	}

	public void setLeftController(LeftController leftController) {
		this.leftController = leftController;
	}

	public RightController getRightController() {
		return rightController;
	}

	public void setRightController(RightController rightController) {
		this.rightController = rightController;
	}

	public void setLeftLoader(FXMLLoader leftLoader) {
		this.leftLoader = leftLoader;
	}

	public void setRightLoader(FXMLLoader rightLoader) {
		this.rightLoader = rightLoader;
	}
	
	public Parent getLeftroot() {
		return leftroot;
	}
	
	public void setLeftroot(Parent leftroot) {
		this.leftroot = leftroot;
	}
	
	public Parent getRightroot() {
		return rightroot;
	}

	public void setRightroot(Parent rightroot) {
		this.rightroot = rightroot;
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
	public void start(Stage stage) {
		JavaFXMainWindow mainWindow = Main.getJavaFXHandler().getMainWindow(); //Stupid thread bypass.
		/*LEFT*/
		try {
			mainWindow.setLeftLoader(new FXMLLoader(getClass().getResource("LeftWindow.fxml"))); //not static to access controller class
			mainWindow.setLeftroot(mainWindow.getLeftLoader().load());
			mainWindow.setLeftController(mainWindow.getLeftLoader().getController());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Scene sceneLeft = new Scene(leftroot, 275, 700);
		Scene sceneLeft = new Scene(mainWindow.getLeftroot(), 275, 700);
        stage.setX(Display.getDesktopDisplayMode().getWidth()*0.01f);
        stage.setY(Display.getDesktopDisplayMode().getHeight()/2-(sceneLeft.getHeight()/2));
        stage.setTitle("Tools / Explorer");
        stage.setScene(sceneLeft);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
                
        mainWindow.getLeftController().getExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXTreeCellFactory();
            }
        });
        mainWindow.getLeftController().getExplorer().setEditable(true);
        mainWindow.getLeftController().getExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        mainWindow.getLeftController().getExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        
        
        /*RIGHT*/
		try { 
			mainWindow.setRightLoader(new FXMLLoader(getClass().getResource("RightWindow.fxml"))); //not static to access controller class
			mainWindow.setRightroot(mainWindow.getRightLoader().load());
			mainWindow.setRightController(mainWindow.getRightLoader().getController());
		} catch (IOException e) {
			e.printStackTrace();
		}
        Stage stageRight = new Stage();
        Scene sceneRight = new Scene(mainWindow.getRightroot(), 275, 700);
        stageRight.setTitle("EBX Tools");
        stageRight.setX(Display.getDesktopDisplayMode().getWidth()*0.985f-sceneLeft.getWidth());
        stageRight.setY(Display.getDesktopDisplayMode().getHeight()/2-(sceneLeft.getHeight()/2));
        stageRight.setScene(sceneRight);
        stageRight.show();
        stageRight.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
        
        mainWindow.getRightController().getEBXExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXTreeCellFactory();
            }
        });
        mainWindow.getRightController().getEBXExplorer().setEditable(true);
        mainWindow.getRightController().getEBXExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        mainWindow.getRightController().getEBXExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        
        
        mainWindow.getRightController().getEBXExplorer().setRoot(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST)));
        mainWindow.getRightController().getEBXExplorer().getRoot().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST2", new ImageView(JavaFXHandler.textIcon), "TEXT2", EntryType.STRING)));
        mainWindow.getRightController().getEBXExplorer().getRoot().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST3", new ImageView(JavaFXHandler.integerIcon), 1, EntryType.INTEGER)));
        mainWindow.getRightController().getEBXExplorer().getRoot().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST4", new ImageView(JavaFXHandler.doubleIcon), 1.0d, EntryType.DOUBLE)));
        mainWindow.getRightController().getEBXExplorer().getRoot().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST5", new ImageView(JavaFXHandler.floatIcon), 100.1002f, EntryType.FLOAT)));
        mainWindow.getRightController().getEBXExplorer().getRoot().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST6", new ImageView(JavaFXHandler.boolIcon), true, EntryType.BOOL)));
        mainWindow.getRightController().getEBXExplorer().getRoot().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST7", new ImageView(JavaFXHandler.byteIcon), (byte) (0xFA>>0), EntryType.BYTE)));
        mainWindow.getRightController().getEBXExplorer().getRoot().setExpanded(true);
        
	}

	public void setRoot(TreeItem<TreeViewEntry> treeViewStructure){
		Main.getJavaFXHandler().getMainWindow().getLeftController().getExplorer().setRoot(treeViewStructure); //NOT ALLOWED ?
	}

}
