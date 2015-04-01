package tk.captainsplexx.JavaFX;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import tk.captainsplexx.Game.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXMainWindow extends Application{ //is Controller
		
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
		System.err.println("FXML Window CLOSED!");
		System.exit(0); //TODO
	}

	@Override
	public void start(Stage stage) {
		Parent root = null;
		try { 
			root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scene scene = new Scene(root, 275, 700);
                
        stage.setX(Display.getWidth()*0.01);
        stage.setY(Display.getHeight()*0.25);
        
        stage.setTitle("Tools / Explorer");
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
	}
	
	/*OnAction*/
	public void exit(){ 
		System.out.println("EXIT -> FXML LEFT -> MENU -> QUIT");
	}
	
	public void changegamepath(){
		System.out.println("Current gamepath: "+Main.getGame().getGamePath());
		//Main.getGame().setGamePath(""); //TODO
	}

}
