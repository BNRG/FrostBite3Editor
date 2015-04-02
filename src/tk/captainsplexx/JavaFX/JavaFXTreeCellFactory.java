package tk.captainsplexx.JavaFX;

import tk.captainsplexx.JavaFX.JavaFXMainWindow.WorkDropType;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class JavaFXTreeCellFactory extends TreeCell<TreeViewEntry> {
        private TextField textField;
        private WorkDropType dropType;
        private ContextMenu addMenu = new ContextMenu();
        TreeItem<TreeViewEntry> draggedTreeItem;
 
        public JavaFXTreeCellFactory() {
            MenuItem addMenuItem = new MenuItem("Add Entry");
            addMenu.getItems().add(addMenuItem);
            /*addMenuItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    TreeItem newEmployee = 
                        new TreeItem<TreeViewEntry>(new TreeViewEntry().setName("TEST"));
                            getTreeItem().getChildren().add(newEmployee);
                }
            });*/

            setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                	draggedTreeItem = getTreeItem();
                	if (draggedTreeItem!=null){
	                    ClipboardContent content;
	                    
	                    content = new ClipboardContent();
	                    content.putString("");
	                    
	                    Dragboard dragboard;
	                    
	                    dragboard = getTreeView().startDragAndDrop(TransferMode.MOVE);
	                    dragboard.setContent(content);
	                    
	                    
	                    System.out.println("dragging: "+draggedTreeItem.getValue().getName());
	                    event.consume();
                	}
                }});
            
            setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {                    
                        if (getTreeItem() != draggedTreeItem){
                        	System.out.println("on to: "+ getTreeItem().getValue().getName()+" unsing "+dropType);
                        	event.setDropCompleted(true);   
                        }
     
                    
                    draggedTreeItem = null;
                    event.consume();
                }});
            setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                	if (getTreeItem()!=null){
	                	double height = JavaFXTreeCellFactory.this.getHeight();
	                	Point2D sceneCoordinates = JavaFXTreeCellFactory.this.localToScene(0d, 0d);
	                    
	                    double y = event.getSceneY() - (sceneCoordinates.getY());
	                    
	                    if (y > (height * .75d)) {
							InnerShadow shadow;
	                        
	                        shadow = new InnerShadow();
	                        shadow.setOffsetY(-1.0);
	                        shadow.setWidth(1.0f);
	                        setEffect(shadow);
	                        dropType = WorkDropType.REORDER;
	                    } 
	                    else {                        
	                        InnerShadow shadow;
	                        
	                        shadow = new InnerShadow();
	                        shadow.setOffsetX(1.0);
	                        shadow.setColor(Color.web("#666666"));
	                        shadow.setOffsetY(1.0);
	                        setEffect(shadow);
	                        dropType = WorkDropType.DROP_INTO;
	                    }
	                    event.acceptTransferModes(TransferMode.MOVE);
                	}           	
               }
            });
            
            setOnDragExited(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    setEffect(null);
                }});
        }
       
        @Override
        public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField(getTreeItem());
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem().getName());
            setGraphic(getTreeItem().getValue().getGraphic());
        }
 
        @Override
        public void updateItem(TreeViewEntry item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(item.getName()); //TODO
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(item.getName());
                    setGraphic(getTreeItem().getValue().getGraphic());
                    if (!getTreeItem().isLeaf()&&getTreeItem().getParent()!= null){
                        setContextMenu(addMenu);
                    }
                }
            }
        }
        
        private void createTextField(TreeItem<TreeViewEntry> treeItem) {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                    	treeItem.getValue().setName(textField.getText());
                        commitEdit(treeItem.getValue());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });  
            
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
}
