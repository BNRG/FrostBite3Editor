package tk.captainsplexx.JavaFX.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tk.captainsplexx.Game.Core;

public class ImagePreviewWindowController {
	@FXML
	ImageView imageView;
	
	
	Stage parentStage;
	
	public void importImage(){
		System.err.println("TODO");
	}
	
	public void exportImage(){
		System.err.println("TODO");
	}
	
	public void close(){
		Core.getJavaFXHandler().getMainWindow().destroyImagePreviewWindow(parentStage);
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Stage getParentStage() {
		return parentStage;
	}

	public void setParentStage(Stage parentStage) {
		this.parentStage = parentStage;
	}
	
	

}
