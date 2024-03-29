package tk.captainsplexx.JavaFX.Controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Resource.FileHandler;

public class ToolsWindowController {
	@FXML
	public TreeView<TreeViewEntry> explorer;
	@FXML
	public TreeView<TreeViewEntry> explorer1;
	@FXML
	public Label lodLabel;
	@FXML
	public ComboBox<String> layer;
	@FXML
	public ComboBox<String> variationDatabase;
	@FXML
	public ComboBox<String> lightning;
	@FXML
	public CheckBox considerPitchBox;
	@FXML
	public Slider mouseSensitivity;
	@FXML
	public Button search;
	@FXML
	public TextField filter;
	@FXML
	public Button destroyLayerButton;
	@FXML
	public TreeView<Entity> layerTreeView;
	
	public void destroyLayer(){
		Core.getGame().getEntityHandler().destroyEntityLayer(layer.getValue());
	}
	
	public void search(){
		if (filter.getText().equals("")){
			Core.getJavaFXHandler().getMainWindow().setPackageExplorer1(null, null);
		}else{
			System.out.println("SEARCH for "+filter.getText());
			Core.getJavaFXHandler().getMainWindow().setPackageExplorer1(null, filter.getText());
		}
	}
	
	
	/*OnAction*/
	public void exit(){ 
		Core.keepAlive = false;
	}
	
	public void about(){ 
		FileHandler.openURL("https://github.com/CaptainSpleXx/FrostBite3Editor#stay-in-contact");
	}
	
	public void incLOD(){ 
		/*
		int current = Integer.valueOf(lodLabel.getText());
		current+=1;
		DDSConverter.MIP_MAP_LEVEL = current;
		lodLabel.setText(current+"");
		System.out.println("New LOD for textures: "+DDSConverter.MIP_MAP_LEVEL);
		*/
	}
	
	public void decLOD(){ 
		/*int current = Integer.valueOf(lodLabel.getText());
		if (current>0){
			current-=1;
			DDSConverter.MIP_MAP_LEVEL = current;
			lodLabel.setText(current+"");
			System.out.println("New LOD for textures: "+DDSConverter.MIP_MAP_LEVEL);
		}*/
	}
	
	
	public TreeView<TreeViewEntry> getExplorer() {
		return explorer;
	}

	public TreeView<TreeViewEntry> getExplorer1() {
		return explorer1;
	}


	public ComboBox<String> getLayer() {
		return layer;
	}

	public ComboBox<String> getLightning() {
		return lightning;
	}

	public CheckBox getConsiderPitchBox() {
		return considerPitchBox;
	}

	public Slider getMouseSensitivity() {
		return mouseSensitivity;
	}

	public Button getSearch() {
		return search;
	}

	public TextField getFilter() {
		return filter;
	}

	public Button getDestroyLayerButton() {
		return destroyLayerButton;
	}

	public TreeView<Entity> getLayerTreeView() {
		return layerTreeView;
	}

	public ComboBox<String> getVariationDatabase() {
		return variationDatabase;
	}
		
	
}
