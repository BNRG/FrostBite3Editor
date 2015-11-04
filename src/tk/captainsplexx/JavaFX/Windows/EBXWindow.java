package tk.captainsplexx.JavaFX.Windows;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXebxTCF;
import tk.captainsplexx.JavaFX.Controller.EBXWindowController;
import tk.captainsplexx.Resource.EBX.EBXFile;

public class EBXWindow {
	private FXMLLoader ebxWindowLoader = new FXMLLoader(EBXWindow.class.getResource("EBXWindow.fxml"));
	private EBXWindowController controller;
	private Parent parent;
	private Stage stage;
	private Scene scene;
	private EBXFile ebxFile;

	public EBXWindow(EBXFile ebxFile){
		this.ebxFile = ebxFile;
		try {
			controller = new EBXWindowController();
			ebxWindowLoader.setController(controller);
			parent = ebxWindowLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	    scene = new Scene(parent, 475, 700);
	    stage = new Stage();
	    stage.setScene(scene);
	    if (ebxFile==null){
	    	stage.setTitle("EBX Tools - NO FILE");
	    }else{
	    	stage.setTitle(ebxFile.getTruePath());
	    }
	    /*stage.setX(Display.getDesktopDisplayMode().getWidth()*0.985f-scene.getWidth());
	    stage.setY(Display.getDesktopDisplayMode().getHeight()/2-(scene.getHeight()/2));*/
	    controller.setStage(stage);
	    
	    stage.getIcons().add(JavaFXHandler.applicationIcon16);
	    stage.getIcons().add(JavaFXHandler.applicationIcon32);
	    stage.show();
	    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Core.getJavaFXHandler().getMainWindow().destroyEBXWindow(stage);
			}
		});
	    controller.setWindow(this);
	    
	    controller.getEBXExplorer().setEditable(true);
	    controller.getEBXExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
	    controller.getEBXExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
	    
	    controller.getEBXExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
	        @Override
	        public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
	            return new JavaFXebxTCF();
	        }
	    });
	    
	    TreeItem<TreeViewEntry> ebxTreeView = null;
	    if (ebxFile!=null){
	    	ebxTreeView = TreeViewConverter.getTreeView(ebxFile);
	    }
	    controller.getEBXExplorer().setRoot(ebxTreeView);
	}

	public FXMLLoader getEbxWindowLoader() {
		return ebxWindowLoader;
	}

	public EBXWindowController getController() {
		return controller;
	}

	public Parent getParent() {
		return parent;
	}

	public Stage getStage() {
		return stage;
	}

	public Scene getScene() {
		return scene;
	}

	public EBXFile getEBXFile() {
		return ebxFile;
	}
	
	
	
}
