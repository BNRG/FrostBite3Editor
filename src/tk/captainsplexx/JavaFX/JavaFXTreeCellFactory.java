package tk.captainsplexx.JavaFX;

import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.WorkDropType;
import javafx.event.Event;
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
            addMenuItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    TreeItem newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW ENTRY", null, null, EntryType.NULL));
                    getTreeItem().getChildren().add(newItem);
                }
            });

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
            TreeViewEntry entry = getTreeItem().getValue();
	        if (entry.getType()==EntryType.BOOL){
	        	entry.setValue(!((Boolean) entry.getValue()));
	        	commitEdit(getTreeItem().getValue());
	        }else{
	        	//if (textField == null) { //USELESS ?
		        createTextField(getTreeItem());
		        //}
		        setText(null);
		        setGraphic(textField);
		        textField.selectAll();
	        }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            updateItem(getTreeItem().getValue(), getTreeItem().getValue()==null);
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
                        textField.setText(convertToString(item)); //TODO
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                	if (item.getType() == EntryType.ARRAY || item.getType() == EntryType.LIST){
                		setText(item.getName()+":"+item.getType().toString());
                	}else{
                		setText(item.getName()+":"+convertToString(item));
                	}
                    setGraphic(getTreeItem().getValue().getGraphic());
                    if (!getTreeItem().isLeaf()&&getTreeItem().getParent()!= null){
                        setContextMenu(addMenu);
                    }
                }
            }
        }
        
        private void createTextField(TreeItem<TreeViewEntry> treeItem) {
            textField = new TextField(convertToString(treeItem.getValue()));
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                    	Object obj = convertToObject(textField.getText(), treeItem.getValue());
                    	if (obj != null && (treeItem.getValue().getType() == EntryType.ARRAY) || treeItem.getValue().getType() == EntryType.LIST){
                    		treeItem.getValue().setName((String) obj);
                            commitEdit(treeItem.getValue());
                    	}else if (obj != null){
                    		treeItem.getValue().setValue(obj);
                            commitEdit(treeItem.getValue());
                    	}else{
                    		cancelEdit();
                    	}
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });  
            
        }
        
        public String convertToString(TreeViewEntry item){
        	switch(item.type){
	    		case STRING:
	    			return (String)item.getValue();
	    		case FLOAT:
	    			return ((Float)item.getValue()).toString();
	    		case DOUBLE:
	    			return ((Double)item.getValue()).toString();
	    		case SHORT:
	    			return ((Short)item.getValue()).toString();
	    		case INTEGER:
	    			return ((Integer)item.getValue()).toString();
	    		case LONG:
	    			return ((Long)item.getValue()).toString();
	    		case ARRAY:
	    			return item.getName();
	    		case LIST:
	    			return item.getName();
	    		case BOOL:
	    			if (((Boolean)item.getValue())==true){
	    				return "TRUE";
	    			}else{
	    				return "FALSE";
	    			}
	    		case BYTE:
	    			return byteToHex(((Byte)item.getValue()));
	    		case NULL:
	    			return ("NULL"); //DEFINED NULL ("NULL")
				default:
					return null; //UNDEFINED NULL ("null")
        	}
        }
        
        public Object convertToObject(String value, TreeViewEntry item){
        	try{
	        	switch(item.type){
		    		case STRING:
		    			return(value);
		    		case LIST:
		    			return(value);
		    		case ARRAY:
		    			return(value);
		    		case FLOAT:
		    			return(Float.valueOf(value));
		    		case DOUBLE:
		    			return(Double.valueOf(value));
		    		case SHORT:
		    			return(Short.valueOf(value));
		    		case INTEGER:
		    			return(Integer.valueOf(value));
		    		case LONG:
		    			return(Long.valueOf(value));
		    		case BYTE:
		    			return(hexToByte(value));
		    		case BOOL:
		    			if (value.equals("TRUE")){
		    				return true;
		    			}else{
		    				return false;
		    			}
		    		case NULL:
		    			return("NULL"); //DEFINED NULL ("NULL")
					default:
						return null; //UNDEFINED NULL ("null")
	        	}
        	}catch(Exception e){
        		System.err.println("Couldn't not parse entry with name "+item.getName()+" in JavaFXTreeCellFactory!");
        		return null;
        	}
        }
        
        byte hexToByte(String s) {
    	    return (byte)((Character.digit(s.charAt(0), 16) << 4) + Character.digit(s.charAt(1), 16));
    	}
        
    	String byteToHex(byte in) {
    		return String.format("%02x", in).toUpperCase();
    	}
}
