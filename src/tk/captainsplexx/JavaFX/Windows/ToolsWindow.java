package tk.captainsplexx.JavaFX.Windows;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.TreeViewUtils;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXexplorer1TCF;
import tk.captainsplexx.JavaFX.CellFactories.JavaFXexplorerTCF;
import tk.captainsplexx.JavaFX.Controller.ToolsWindowController;

public class ToolsWindow {
	private Stage stage;
	private FXMLLoader loader;
	private ToolsWindowController controller;
	private Scene scene;
	private Parent parent;
	
	private TreeItem<TreeViewEntry> unfilteredExplorer1Root = null;
	private TreeItem<TreeViewEntry> filteredExplorer1Root = null;
	
	public ToolsWindow() {
		stage = new Stage();
		try {
			loader = new FXMLLoader(getClass().getResource("ToolsWindow.fxml"));
			parent = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		scene = new Scene(parent, 275, 700);
		stage.setX(Display.getDesktopDisplayMode().getWidth()*0.01f);
		stage.setY(Display.getDesktopDisplayMode().getHeight()/2-(scene.getHeight()/2));
		stage.setTitle("Tools / Explorer");
		stage.getIcons().add(JavaFXHandler.applicationIcon16);
		stage.getIcons().add(JavaFXHandler.applicationIcon32);
		stage.setScene(scene);
		stage.hide();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});
        controller.getExplorer().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXexplorerTCF();
            }
        });
        controller.getExplorer().setEditable(false);
        controller.getExplorer().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        controller.getExplorer().setPrefHeight(Display.getDesktopDisplayMode().getHeight()); //Back to top in TCF or what ?

        controller.getExplorer1().setCellFactory(new Callback<TreeView<TreeViewEntry>,TreeCell<TreeViewEntry>>(){
            @Override
            public TreeCell<TreeViewEntry> call(TreeView<TreeViewEntry> p) {
                return new JavaFXexplorer1TCF();
            }
        });
        controller.getExplorer1().setEditable(false);
                
        controller.getExplorer1().setPrefWidth(Display.getDesktopDisplayMode().getWidth());
        controller.getExplorer1().setPrefHeight(Display.getDesktopDisplayMode().getHeight());
        
        controller.getLayer().getItems().addAll("Test","Testsss","Testtssst");
        controller.getLayer().valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.err.println("Old: "+oldValue+" New: "+newValue);
			}
		});
        
        controller.getConsiderPitchBox().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CheckBox considerBox = controller.getConsiderPitchBox();
				if (considerBox.isSelected()){
					Core.getRender().getCamera().setConsiderPitch(true);
				}else{
					Core.getRender().getCamera().setConsiderPitch(false);
				}
			}
		});
        controller.getMouseSensitivity().valueProperty().addListener(new ChangeListener<Number>() {
        	public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        		double mouseSens = (double)new_val/500;
        		Core.getRender().getCamera().setMouseSensitivity((float)mouseSens);
        	}
        });
        controller.getCameraSpeed().valueProperty().addListener(new ChangeListener<Number>() {
        	public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        		double cameraSpeed = (double)new_val*4;
        		Core.getGame().getPlayerHandler().getPlayerEntity().setMovementSpeed((float) cameraSpeed);
        	}
        });
	}
	
	public void setExplorer1(TreeItem<TreeViewEntry> root, String str){
		if (root!=null){
			unfilteredExplorer1Root = root;
		}
		filteredExplorer1Root = TreeViewUtils.filter(unfilteredExplorer1Root, str);
		controller.getExplorer1().setRoot(filteredExplorer1Root);
	}


	public Stage getStage() {
		return stage;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public ToolsWindowController getController() {
		return controller;
	}

	public Scene getScene() {
		return scene;
	}

	public Parent getParent() {
		return parent;
	}
	
	
}
