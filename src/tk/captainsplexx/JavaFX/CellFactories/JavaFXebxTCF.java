package tk.captainsplexx.JavaFX.CellFactories;

import java.nio.ByteOrder;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Game.Game;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.WorkDropType;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.EBX.EBXFieldDescriptor;
import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.EBX.EBXHandler;
import tk.captainsplexx.Resource.TOC.ResourceLink;

public class JavaFXebxTCF extends TreeCell<TreeViewEntry> {
		private enum Operation {Name, Value};
		private Operation modifyOp;
        private TextField textField;
        private WorkDropType dropType;
        private ContextMenu contextMenu = new ContextMenu();
        public TreeItem<TreeViewEntry> draggedTreeItem;
        private MenuItem addText, addFloat, addDouble, addArray, addInteger, addBool, addList, addLong, addByte, addShort, remove, rename, follow;
        public JavaFXebxTCF() {
        	
            addText = new MenuItem("Add Text");
            addText.setGraphic(new ImageView(JavaFXHandler.textIcon));
            addText.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW STRING ENTRY", new ImageView(JavaFXHandler.textIcon), "", EntryType.STRING));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addFloat = new MenuItem("Add Float");
            addFloat.setGraphic(new ImageView(JavaFXHandler.floatIcon));
            addFloat.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW FLOAT ENTRY", new ImageView(JavaFXHandler.floatIcon), 0.0f, EntryType.FLOAT));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addDouble = new MenuItem("Add Double");
            addDouble.setGraphic(new ImageView(JavaFXHandler.doubleIcon));
            addDouble.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW DOUBLE ENTRY", new ImageView(JavaFXHandler.doubleIcon), 0.0d, EntryType.DOUBLE));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addArray = new MenuItem("Add Array");
            addArray.setGraphic(new ImageView(JavaFXHandler.arrayIcon));
            addArray.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW ARRAY ENTRY", new ImageView(JavaFXHandler.arrayIcon), null, EntryType.ARRAY));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addInteger = new MenuItem("Add Integer");
            addInteger.setGraphic(new ImageView(JavaFXHandler.integerIcon));
            addInteger.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW INTEGER ENTRY", new ImageView(JavaFXHandler.integerIcon), 1337, EntryType.INTEGER));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addBool = new MenuItem("Add Bool");
            addBool.setGraphic(new ImageView(JavaFXHandler.boolIcon));
            addBool.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW BOOL ENTRY", new ImageView(JavaFXHandler.boolIcon), false, EntryType.BOOL));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addList = new MenuItem("Add List");
            addList.setGraphic(new ImageView(JavaFXHandler.listIcon));
            addList.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW LIST ENTRY", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addLong = new MenuItem("Add Long");
            addLong.setGraphic(new ImageView(JavaFXHandler.longIcon));
            addLong.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW LONG ENTRY", new ImageView(JavaFXHandler.longIcon), (long) 9001, EntryType.LONG));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addByte = new MenuItem("Add Byte");
            addByte.setGraphic(new ImageView(JavaFXHandler.byteIcon));
            addByte.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW BYTE ENTRY", new ImageView(JavaFXHandler.shortIcon), (byte) 0, EntryType.BYTE));
                    getTreeItem().getChildren().add(newItem);
                }
            });
            
            addShort= new MenuItem("Add Short");
            addShort.setGraphic(new ImageView(JavaFXHandler.shortIcon));
            addShort.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	TreeItem<TreeViewEntry> newItem = new TreeItem<TreeViewEntry>(new TreeViewEntry("NEW SHORT ENTRY", new ImageView(JavaFXHandler.byteIcon), (short) 0, EntryType.SHORT));
                    getTreeItem().getChildren().add(newItem);
                }
            });
                      
            
            rename = new MenuItem("Rename");
            rename.setGraphic(new ImageView(JavaFXHandler.pencilIcon));
            rename.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	modifyOp = Operation.Name;
                	startEdit();
                }
            });
            
            
            remove = new MenuItem("Remove");
            remove.setGraphic(new ImageView(JavaFXHandler.removeIcon));
            remove.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	if (getTreeItem().getParent()!=null){
                		getTreeItem().getParent().getChildren().remove(getTreeItem());
                	}
                }
            });
            
            follow = new MenuItem("Follow");
            follow.setGraphic(new ImageView(JavaFXHandler.rightArrowIcon));
            follow.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                	try{
	                	String[] target = ((String)getTreeItem().getValue().getValue()).split(" ");
	                	if (target.length>1 && target[0].contains("/")){ //IS NOT AT NULL GUID OR IS NOT REFERENCED
	                		Game game = Core.getGame();
	                		for (ResourceLink ebxLink : game.getCurrentSB().getEbx()){
	                			if (ebxLink.getName().toLowerCase().equals(target[0].toLowerCase())){
									byte[] data = CasDataReader.readCas(ebxLink.getBaseSha1(), ebxLink.getDeltaSha1(), ebxLink.getSha1(), ebxLink.getCasPatchType());
									TreeItem<TreeViewEntry> ebx = TreeViewConverter.getTreeView(game.getResourceHandler().getEBXHandler().loadFile(data));
									Core.getJavaFXHandler().setTreeViewStructureRight(ebx);
									Core.getJavaFXHandler().getMainWindow().updateRightRoot();
	                				break;
	                			}
	                		}
	                	}
                	}catch (Exception e){
                		System.out.println("Invaild link to follow. || TODO DELTA PATCH :)");
                	}
                }
            });
                        
            setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                	draggedTreeItem = getTreeItem();
                	if (draggedTreeItem!=null){
	                    ClipboardContent content;
	                    
	                    content = new ClipboardContent();
	                    content.putString(draggedTreeItem.getValue().toString());
	                    
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
                    if (getTreeItem() != draggedTreeItem && getTreeItem() != null && dropType == WorkDropType.DROP_INTO){
                        System.out.println("in to: "+ getTreeItem().getValue().getName());
                        TreeItem<TreeViewEntry> draggedItemParent = draggedTreeItem.getParent();
                        TreeViewEntry draggedWork = draggedTreeItem.getValue();
                        draggedItemParent.getChildren().remove(draggedWork);
                        
                        draggedTreeItem = null;                  
                        event.setDropCompleted(true);
                    }else if(getTreeItem() != draggedTreeItem && getTreeItem() != null && dropType == WorkDropType.REORDER){
                    	System.out.println("reorder!");
                    }
                    event.consume();
                }});
            setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                	if (getTreeItem()!=null){
	                	double height = JavaFXebxTCF.this.getHeight();
	                	Point2D sceneCoordinates = JavaFXebxTCF.this.localToScene(0d, 0d);
	                    
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
            if (modifyOp == Operation.Name){
            	createTextField(getTreeItem());
		        setText(null);
		        setGraphic(textField);
		        textField.selectAll();
            }else if (modifyOp == null){
            	TreeViewEntry entry = getTreeItem().getValue();
    	        if (entry.getType()==EntryType.BOOL){
    	        	entry.setValue(!((Boolean) entry.getValue()));
    	        	commitEdit(getTreeItem().getValue());
    	        }else if(entry.getType()==EntryType.ENUM&&entry.getValue() instanceof HashMap<?,?>){
    	        	HashMap<EBXFieldDescriptor, Boolean> enums = (HashMap<EBXFieldDescriptor, Boolean>) entry.getValue();
	    			Boolean done = false;
	    			Boolean selectNext = false;
	    			for (EBXFieldDescriptor desc : enums.keySet()){
	    				boolean selected = enums.get(desc);
	    				if (selected){
	    					enums.put(desc, false);//TODO workaround to keep index
	    					System.err.println("TODO: workaround to keep index");
	    					selectNext = true;
	    				}else if (selectNext){
	    					enums.put(desc, true);//TODO workaround to keep index
	    					selectNext = false;
	    					done = true;
	    				}
	    			}
	    			if (!done){
	    				for (EBXFieldDescriptor desc : enums.keySet()){
	    					enums.put(desc, true);
		    				break;
		    			}
	    			}
    	        }else{
    	        	//if (textField == null) { //USELESS ?
    		        createTextField(getTreeItem());
    		        //}
    		        setText(null);
    		        setGraphic(textField);
    		        textField.selectAll();
    	        }
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
	                  	textField.setText(convertToString(item));
	                }
	                setText(null);
	                setGraphic(textField);
	            } else {
	            	if (item != null){
		               if (item.getType() == EntryType.ARRAY || item.getType() == EntryType.LIST){
		                	setText(item.getName()+":"+item.getType().toString());
		                }else{
		                	setText(item.getName()+": "+convertToString(item));
		                }
		                if (item.getEBXType()!=0){
		                	setTooltip(new Tooltip("Type: "+FileHandler.bytesToHex(FileHandler.toBytes(item.getEBXType(), ByteOrder.BIG_ENDIAN))));
		                }
		                setGraphic(getTreeItem().getValue().getGraphic());
		                contextMenu.getItems().clear();
		                if (getTreeItem().getValue().getType()==EntryType.ARRAY||getTreeItem().getValue().getType()==EntryType.LIST){
		                  	contextMenu.getItems().addAll(addText, addFloat, addDouble, addInteger, addLong, addByte, addBool, addArray, addList, remove);
		                }else if (getTreeItem().getValue().getType()==EntryType.GUID){
		                  	contextMenu.getItems().addAll(follow ,rename, remove);
		                }else if (getTreeItem()!= null){
		                  	contextMenu.getItems().addAll(rename, remove);
		                }
		                //setUnderline(value);
		                setContextMenu(contextMenu);
		             }
	            }
	        }
        }
        
        private void createTextField(TreeItem<TreeViewEntry> treeItem) {
        	if (modifyOp == Operation.Name){
        		textField = new TextField(treeItem.getValue().getName());
        	}else{
        		textField = new TextField(convertToString(treeItem.getValue()));
        	}
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                    	if (modifyOp == Operation.Name){
	                    	treeItem.getValue().setName(textField.getText());
	                        commitEdit(treeItem.getValue());
	                        modifyOp = null;
                    	}else if(modifyOp == null){
                    		Object obj = convertToObject(textField.getText(), treeItem.getValue());
	                    	if (obj != null && (treeItem.getValue().getType() == EntryType.ARRAY) || treeItem.getValue().getType() == EntryType.LIST){
	                    		treeItem.getValue().setName((String) obj);
	                            commitEdit(treeItem.getValue());
	                            modifyOp = null;
	                    	}else if (obj != null){
	                    		treeItem.getValue().setValue(obj);
	                            commitEdit(treeItem.getValue());
	                            modifyOp = null;
	                    	}else{
	                    		modifyOp = null;
	                    		cancelEdit();
	                    	}
                    	}
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                    	modifyOp = null;
                        cancelEdit();
                    }
                }
            });  
            
        }
        
        public String convertToString(TreeViewEntry item){
        	if (item.value!=null){
        		EBXHandler ebxHandler = Core.getGame().getResourceHandler().getEBXHandler();
	        	switch(item.type){
		    		case STRING:
		    			return (String)item.getValue();
		    		case SHA1:
		    			return (String)item.getValue();
		    		case FLOAT:
		    			return ((Float)item.getValue()).toString();
		    		case DOUBLE:
		    			return ((Double)item.getValue()).toString();
		    		case SHORT:
		    			return ((Short)item.getValue()).toString();
		    		case INTEGER:
		    			return ((Integer)item.getValue()).toString();
		    		case UINTEGER:
		    			return ((Long)item.getValue()).toString();
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
		    		case HEX8:
		    			return (String)item.getValue();
		    		case BYTE:
		    			return byteToHex(((Byte)item.getValue()));
		    		case ENUM:
		    			HashMap<EBXFieldDescriptor, Boolean> enums = (HashMap<EBXFieldDescriptor, Boolean>) item.getValue();
		    			String value = "null";
		    			for (EBXFieldDescriptor desc : enums.keySet()){
		    				boolean selected = enums.get(desc);
		    				if (selected){
		    					value = desc.getName();
		    					break;
		    				}
		    			}
		    			return value;
		    		case RAW:
		    			return FileHandler.bytesToHex((byte[]) item.getValue());
		    		case NULL:
		    			return ("NULL"); //DEFINED NULL
		    		case GUID:
		    			String fileGUIDName = null;
		    			String[] split = ((String)item.getValue()).split(" ");
						if (ebxHandler.getFiles()!=null&&split.length==2){//DEBUG-
							EBXFile file = ebxHandler.getEBXFileByGUID(split[0]);
							if (file!=null){
								return file.getTruePath()+" "+split[1];
							}
						}
						return (String)item.getValue();
		    		case CHUNKGUID:
		    			return (String)item.getValue();
					default:
						return "null"; //UNDEFINED NULL
	        	}
        	}else{
        		return "null";
        	}
        }
        
        public Object convertToObject(String value, TreeViewEntry item){
        	try{
        		EBXHandler ebxHandler = Core.getGame().getResourceHandler().getEBXHandler();
        		if (value.equals("null")){//hasNoPayloadData! aka. undefined null
        			return null;
        		}else{
		        	switch(item.type){
			    		case STRING:
			    			return(value);
			    		case ENUM:
			    			return(item.getValue()/*RETURNS STRING(if null) OR HASHMAP*/);
			    		case HEX8:
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
			    		case UINTEGER:
			    			return(Long.valueOf(value))& 0xffffffffL;
			    		case BYTE:
			    			return(hexToByte(value));
			    		case RAW:
			    			return(FileHandler.hexStringToByteArray(value));
			    		case BOOL:
			    			if (value.equals("TRUE")){
			    				return true;
			    			}else{
			    				return false;
			    			}
			    		case NULL:
			    			return("NULL"); //DEFINED NULL ("NULL")
			    		case GUID:
			    			if (value.contains("/")){
			    				String[] split = value.split(" ");			    				
			    				if (split.length==2){
				    				EBXFile file = ebxHandler.getEBXFileByName(split[0]);
			    					if (file!=null){
			    						return (file.getGuid()+" "+split[1]);
			    					}
								}
			    				System.err.println("EXTERNAL GUID PATH COULD NOT BE FOUND IN DATABASE. NO CONVERTION TO FILEGUID POSSIBLE!");
			    				return("ERROR");
			    			}else{
			    				return(value);
			    			}
			    		case CHUNKGUID:
			    			return(value);
			    		case SHA1:
			    			return(value);
						default:
							return null; //UNDEFINED NULL ("null")
		        	}
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
