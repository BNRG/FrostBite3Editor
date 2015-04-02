package tk.captainsplexx.JavaFX;

import tk.captainsplexx.Resource.FileHandler;
import javafx.scene.image.Image;


public class JavaFXHandler {
	
	JavaFXMainWindow main;
	
	public static final Image textIcon = new Image(FileHandler.getStream("res/images/edit-small-caps.png"));
	public static final Image byteIcon = new Image(FileHandler.getStream("res/images/document-attribute-b.png"));
	public static final Image boolIcon = new Image(FileHandler.getStream("res/images/document-attribute-bool.png"));
	public static final Image doubleIcon = new Image(FileHandler.getStream("res/images/document-attribute-d.png"));
	public static final Image floatIcon = new Image(FileHandler.getStream("res/images/document-attribute-f.png"));
	public static final Image integerIcon = new Image(FileHandler.getStream("res/images/document-attribute-i.png"));
	public static final Image longIcon = new Image(FileHandler.getStream("res/images/document-attribute-l.png"));
	public static final Image shortIcon = new Image(FileHandler.getStream("res/images/document-attribute-s.png"));
	public static final Image arrayIcon = new Image(FileHandler.getStream("res/images/edit-code.png"));
	public static final Image listIcon = new Image(FileHandler.getStream("res/images/box.png"));
	 
	public static final Image pencilIcon = new Image(FileHandler.getStream("res/images/pencil.png"));
	public static final Image removeIcon = new Image(FileHandler.getStream("res/images/cross.png"));
	
	public JavaFXHandler(){
		main = new JavaFXMainWindow();
		main.runApplication();
	}
	
	public JavaFXMainWindow getMainWindow() {
		return main;
	}
}
