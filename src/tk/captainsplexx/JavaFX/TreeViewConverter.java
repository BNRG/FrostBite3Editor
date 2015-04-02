package tk.captainsplexx.JavaFX;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;

public class TreeViewConverter {
	
	public static InputStream getStream(String path){
		InputStream is = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.err.println("Could not read ImputStream from: "+path+" in TreeViewConverter");
			e.printStackTrace();
		}
		return is;
	}
	/*ICON-IMAGES*/
	public static final Image boxIcon = new Image(getStream("res/images/box.png"));
	public static final Image textIcon = new Image(getStream("res/images/edit-small-caps.png"));
	public static final Image byteIcon = new Image(getStream("res/images/document-attribute-b.png"));
	public static final Image doubleIcon = new Image(getStream("res/images/document-attribute-d.png"));
	public static final Image floatIcon = new Image(getStream("res/images/document-attribute-f.png"));
	public static final Image integerIcon = new Image(getStream("res/images/document-attribute-i.png"));
	public static final Image longIcon = new Image(getStream("res/images/document-attribute-l.png"));
	public static final Image shortIcon = new Image(getStream("res/images/document-attribute-s.png"));
	public static final Image arrayIcon = new Image(getStream("res/images/edit-code.png"));
	public static final Image compoundIcon = new Image(getStream("res/images/edit-list.png"));
	 
	public static final Image pencilIcon = new Image(getStream("res/images/pencil.png"));
	public static final Image removeIcon = new Image(getStream("res/images/cross.png"));
	 
}
