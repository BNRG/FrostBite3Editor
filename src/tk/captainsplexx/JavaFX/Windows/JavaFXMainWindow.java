package tk.captainsplexx.JavaFX.Windows;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import tk.captainsplexx.JavaFX.Controller.JavaFXMainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXMainWindow extends Application{
	
	public JavaFXMainWindowController controller;
	
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
		System.err.println("Main Window CLOSED!");
	}

	@Override
	public void start(Stage stage) {
		Parent root = null;
		FXMLLoader fxmlLoader = new FXMLLoader();
		try { 
			root = fxmlLoader.load(getClass().getResource("MainWindow.fxml"));
			controller = fxmlLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scene scene = new Scene(root, 275, 700);
                
        stage.setX(Display.getWidth()*0.01);
        stage.setY(Display.getHeight()*0.25);
        
        stage.setTitle("FXML TEST");
        stage.setScene(scene);
        stage.show();
	}	

}
