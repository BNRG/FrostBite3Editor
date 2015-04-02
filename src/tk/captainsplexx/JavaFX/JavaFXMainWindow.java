package tk.captainsplexx.JavaFX;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeSortMode;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class JavaFXMainWindow extends Application{
	
	public static enum EntryType{
		STRING, INTEGER, LONG, BOOL, FLOAT, DOUBLE, ARRAY, COMPOUND, BYTE, NULL
	};
	
	public static enum WorkDropType { DROP_INTO, REORDER };
	
	/*LOADER and CONTROLLER*/
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
	/*END OF LOADER*/

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
		/*LEFT*/
		Parent leftroot = null;
		try {
			leftLoader = new FXMLLoader(getClass().getResource("LeftWindow.fxml")); //not static to access controller class
			leftroot = leftLoader.load();
			leftController = leftLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene sceneLeft = new Scene(leftroot, 275, 700);
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
                
        /*RIGHT*/
        Parent rightroot = null;
		try { 
			rightLoader = new FXMLLoader(getClass().getResource("RightWindow.fxml")); //not static to access controller class
			rightroot = rightLoader.load();
			rightController = (RightController) rightLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Stage stageRight = new Stage();
        Scene sceneRight = new Scene(rightroot, 275, 700);
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
        	
        
        
        rightController.getEBXExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXTreeCellFactory();
            }
        });
        
        rightController.getEBXExplorer().setRoot(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST", new ImageView(TreeViewConverter.boxIcon), null, EntryType.NULL)));
        rightController.getEBXExplorer().getRoot().getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("TEST2", new ImageView(TreeViewConverter.textIcon), null, EntryType.NULL)));
	}
	
	
}
