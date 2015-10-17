package tk.captainsplexx.JavaFX.Windows;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.Controller.ImagePreviewWindowController;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.TOC.ResourceLink;

public class ImagePreviewWindow {
	private Stage stage;
	private FXMLLoader loader;
	private Parent parent;
	private Scene scene;
	private ImagePreviewWindowController controller;
	
	public ImagePreviewWindow(File file, File ddsFile, ResourceLink resourceLink, String title) {
		try { 
			loader = new FXMLLoader(getClass().getResource("ImagePreviewWindow.fxml"));
			controller = new ImagePreviewWindowController();
			loader.setController(controller);
			parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		controller.setResourceLink(resourceLink);
		controller.setDdsFile(ddsFile);
		
		stage = new Stage();
        scene = new Scene(parent, 800, 800);
        stage.setTitle("Preview: "+title);
        stage.setScene(scene);
        stage.getIcons().add(JavaFXHandler.applicationIcon16);
        stage.getIcons().add(JavaFXHandler.applicationIcon32);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Core.getJavaFXHandler().getMainWindow().destroyImagePreviewWindow(stage);
			}
		});
        if (file!=null){
	        controller.getImageView().setImage(new Image(FileHandler.getStream(file.getAbsolutePath())));
	        controller.getImageView().setFitHeight(stage.getHeight());
	        controller.getImageView().setFitWidth(stage.getWidth());
        }
        
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
            	controller.getImageView().setFitWidth((double) newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            	controller.getImageView().setFitHeight((double) newSceneHeight);
            }
        });
	}	

	public Stage getStage() {
		return stage;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public Parent getParent() {
		return parent;
	}

	public Scene getScene() {
		return scene;
	}

	public ImagePreviewWindowController getController() {
		return controller;
	}

	
	
}
