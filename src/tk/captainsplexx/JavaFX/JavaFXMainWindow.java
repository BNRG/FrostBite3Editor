package tk.captainsplexx.JavaFX;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXMainWindow extends Application{
	
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
		System.err.println("FXML Windows CLOSED!");
		System.exit(0); //TODO
	}

	@Override
	public void start(Stage stage) {
		/*LEFT*/
		Parent leftroot = null;
		try {
			leftLoader = new FXMLLoader(); //not static to access controller class
			leftroot = leftLoader.load(getClass().getResource("LeftWindow.fxml"));
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
                
        /*RIGHT*/
        Parent rightroot = null;
		try { 
			rightLoader = new FXMLLoader(); //not static to access controller class
			rightroot = rightLoader.load(getClass().getResource("RightWindow.fxml"));
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
        stageRight.show();
        
        
	}
	
}
