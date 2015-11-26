package tk.captainsplexx.JavaFX.CellFactories;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureEntry;
import tk.captainsplexx.Resource.EBX.Structure.EBXStructureReader.EntryType;

public class JavaFXlayerTCF extends TreeCell<Entity> {
	
	EBXStructureEntry tmpEntry = null;
	
	private MenuItem show, hide, remove;
	private ContextMenu contextMenu = new ContextMenu();
	
	
	public JavaFXlayerTCF() {
		
		show = new MenuItem("Show");
		show.setGraphic(new ImageView(JavaFXHandler.binocular));
		show.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	            getTreeItem().getValue().setIsVisible(true);
	            updateItem(getTreeItem().getValue(), false);
	        }
	    }
		);
		
		hide = new MenuItem("Hide");
		hide.setGraphic(new ImageView(JavaFXHandler.binocular2));
		hide.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	            getTreeItem().getValue().setIsVisible(false);
	            updateItem(getTreeItem().getValue(), false);
	        }
	    }
		);
		
		remove = new MenuItem("Remove");
		remove.setDisable(true);
		remove.setGraphic(new ImageView(JavaFXHandler.removeIcon));
		remove.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	            System.err.println("Not implemented yet.");
	        }
	    }
		);
	}
	
	@Override
	public void updateItem(Entity item, boolean empty) {
		super.updateItem(item, empty);
		setStyle(null);
		setEffect(null);
		if (empty) {
			setText(null);
			setGraphic(null);
		}else if (item==null){
			setText("Layers");
		} 
		else {
			tmpEntry = item.getStructEntry();
			contextMenu.getItems().clear();
			if (tmpEntry!=null){
				String[] instanaceGUID = item.getName().split("/");
				if (instanaceGUID.length==2){
					setText(item.getStructEntry().getType().toString()+" "+instanaceGUID[1]);
				}else{
					setText(item.getStructEntry().getType().toString()+" "+item.getName());
				}
				if (!item.getIsVisible()){
					contextMenu.getItems().add(show);
					setStyle("-fx-background-color: red");
				}else if(item.getIsVisible()){
					contextMenu.getItems().add(hide);
				}/*else if (item.getTexturedModelNames()!=null){
					setStyle("-fx-background-color: green");
				}*/
			}else{
				setText(item.getName());
				setStyle("-fx-background-color: lightgrey");
			}
			setGraphic(getTreeItem().getGraphic());
			contextMenu.getItems().add(remove);
			setContextMenu(contextMenu);
		}

	}
}