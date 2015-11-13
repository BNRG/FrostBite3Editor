package tk.captainsplexx.JavaFX;

import java.util.concurrent.CountDownLatch;

import antonsmirnov.javafx.dialog.Dialog.Builder;
import javafx.application.Platform;
import javafx.scene.image.Image;
import tk.captainsplexx.JavaFX.Windows.MainWindow;
import tk.captainsplexx.Resource.FileHandler;


public class JavaFXHandler {
	
	MainWindow main;
		
	public static final Image applicationIcon16 = new Image(FileHandler.getStream("res/icon/16.png"));
	public static final Image applicationIcon32 = new Image(FileHandler.getStream("res/icon/32.png"));
	public static final String imageFolder = "res/images/";
	
	public static final Image textIcon = new Image(FileHandler.getStream(imageFolder+"edit-small-caps.png"));
	public static final Image byteIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-b.png"));
	public static final Image boolIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-bool.png"));
	public static final Image doubleIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-d.png"));
	public static final Image floatIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-f.png"));
	public static final Image integerIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-i.png"));
	public static final Image uintegerIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-i_unsigned.png"));
	public static final Image longIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-l.png"));
	public static final Image shortIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-s.png"));
	public static final Image ushortIcon = new Image(FileHandler.getStream(imageFolder+"document-attribute-s_unsigned.png"));
	public static final Image arrayIcon = new Image(FileHandler.getStream(imageFolder+"edit-code.png"));
	public static final Image instanceIcon = new Image(FileHandler.getStream(imageFolder+"box.png"));
	public static final Image listIcon = new Image(FileHandler.getStream(imageFolder+"wooden-box.png"));
	public static final Image hashIcon = new Image(FileHandler.getStream(imageFolder+"hash.png"));
	public static final Image rawIcon = new Image(FileHandler.getStream(imageFolder+"block.png"));
	public static final Image structureIcon = new Image(FileHandler.getStream(imageFolder+"structure.png"));
	public static final Image internalIcon = new Image(FileHandler.getStream(imageFolder+"internal.png"));
	public static final Image resourceIcon = new Image(FileHandler.getStream(imageFolder+"resource.png"));
	public static final Image imageIcon = new Image(FileHandler.getStream(imageFolder+"image.png"));
	public static final Image geometryIcon = new Image(FileHandler.getStream(imageFolder+"xyz.png"));
	public static final Image geometry2Icon = new Image(FileHandler.getStream(imageFolder+"xyz2.png"));
	public static final Image luaIcon = new Image(FileHandler.getStream(imageFolder+"lua.png"));
	public static final Image enumIcon = new Image(FileHandler.getStream(imageFolder+"enum.png"));
	 
	public static final Image pencilIcon = new Image(FileHandler.getStream(imageFolder+"pencil.png"));
	public static final Image removeIcon = new Image(FileHandler.getStream(imageFolder+"cross.png"));
	public static final Image documentIcon = new Image(FileHandler.getStream(imageFolder+"folder-open-document.png"));
	public static final Image leftArrowIcon = new Image(FileHandler.getStream(imageFolder+"arrow-000.png"));
	public static final Image rightArrowIcon = new Image(FileHandler.getStream(imageFolder+"arrow-180.png"));
	public static final Image clipboardPasteIcon = new Image(FileHandler.getStream(imageFolder+"clipboard-paste.png"));
	public static final Image asteriskYellow = new Image(FileHandler.getStream(imageFolder+"asterisk-yellow.png"));
	public static final Image binocular = new Image(FileHandler.getStream(imageFolder+"binocular.png"));
	public static final Image binocular2 = new Image(FileHandler.getStream(imageFolder+"binocular--arrow.png"));
	
	public Builder dialogBuilder;
	
	public JavaFXHandler(){
		main = new MainWindow();
		main.runApplication();
		dialogBuilder = new Builder();
	}
	

	public boolean runAndWait(Runnable action) {
	    if (action == null){
	        System.err.println("No action given to run on JavaFX Thread.");
	        return false;
	    }

	    if (Platform.isFxApplicationThread()) {
	        action.run();
	        return true;
	    }
	
	    final CountDownLatch doneLatch = new CountDownLatch(1);
	    Platform.runLater(() -> {
	        try {
	            action.run();
	        } finally {
	            doneLatch.countDown();
	        }
	    });
	    try {
	        doneLatch.await();
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	    	return false;
	    }
	    return true;
	}
	
	public MainWindow getMainWindow() {
		return main;
	}

	public void setMainWindow(MainWindow main){
		this.main = main;
	}

	public Builder getDialogBuilder() {
		return dialogBuilder;
	}

}