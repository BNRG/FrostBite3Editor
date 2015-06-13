package tk.captainsplexx.JavaFX;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import tk.captainsplexx.Resource.FileHandler;


public class JavaFXHandler {
	
	JavaFXMainWindow main;
	
	public TreeItem<TreeViewEntry> treeViewStructureLeft;
	public TreeItem<TreeViewEntry> treeViewStructureLeft1;
	public TreeItem<TreeViewEntry> treeViewStructureRight;
	
	public static final Image applicationIcon16 = new Image(FileHandler.getStream("res/icon/16.png"));
	public static final Image applicationIcon32 = new Image(FileHandler.getStream("res/icon/32.png"));
	
	public static final Image textIcon = new Image(FileHandler.getStream("res/images/edit-small-caps.png"));
	public static final Image byteIcon = new Image(FileHandler.getStream("res/images/document-attribute-b.png"));
	public static final Image boolIcon = new Image(FileHandler.getStream("res/images/document-attribute-bool.png"));
	public static final Image doubleIcon = new Image(FileHandler.getStream("res/images/document-attribute-d.png"));
	public static final Image floatIcon = new Image(FileHandler.getStream("res/images/document-attribute-f.png"));
	public static final Image integerIcon = new Image(FileHandler.getStream("res/images/document-attribute-i.png"));
	public static final Image uintegerIcon = new Image(FileHandler.getStream("res/images/document-attribute-i_unsigned.png"));
	public static final Image longIcon = new Image(FileHandler.getStream("res/images/document-attribute-l.png"));
	public static final Image shortIcon = new Image(FileHandler.getStream("res/images/document-attribute-s.png"));
	public static final Image ushortIcon = new Image(FileHandler.getStream("res/images/document-attribute-s_unsigned.png"));
	public static final Image arrayIcon = new Image(FileHandler.getStream("res/images/edit-code.png"));
	public static final Image instanceIcon = new Image(FileHandler.getStream("res/images/box.png"));
	public static final Image listIcon = new Image(FileHandler.getStream("res/images/wooden-box.png"));
	public static final Image hashIcon = new Image(FileHandler.getStream("res/images/hash.png"));
	public static final Image rawIcon = new Image(FileHandler.getStream("res/images/block.png"));
	public static final Image structureIcon = new Image(FileHandler.getStream("res/images/structure.png"));
	public static final Image internalIcon = new Image(FileHandler.getStream("res/images/internal.png"));
	public static final Image resourceIcon = new Image(FileHandler.getStream("res/images/resource.png"));
	public static final Image imageIcon = new Image(FileHandler.getStream("res/images/image.png"));
	public static final Image geometryIcon = new Image(FileHandler.getStream("res/images/xyz.png"));
	public static final Image geometry2Icon = new Image(FileHandler.getStream("res/images/xyz2.png"));
	public static final Image luaIcon = new Image(FileHandler.getStream("res/images/lua.png"));
	public static final Image enumIcon = new Image(FileHandler.getStream("res/images/enum.png"));
	 
	public static final Image pencilIcon = new Image(FileHandler.getStream("res/images/pencil.png"));
	public static final Image removeIcon = new Image(FileHandler.getStream("res/images/cross.png"));
	public static final Image documentIcon = new Image(FileHandler.getStream("res/images/folder-open-document.png"));
	public static final Image leftArrowIcon = new Image(FileHandler.getStream("res/images/arrow-000.png"));
	public static final Image rightArrowIcon = new Image(FileHandler.getStream("res/images/arrow-180.png"));
	
	public JavaFXHandler(){
		main = new JavaFXMainWindow();
		main.runApplication();
	}
	
	public JavaFXMainWindow getMainWindow() {
		return main;
	}
	
	public void setMainWindow(JavaFXMainWindow main){
		this.main = main;
	}
	
	
	/* Getter and setter */
	public TreeItem<TreeViewEntry> getTreeViewStructureLeft() {
		return treeViewStructureLeft;
	}

	public void setTreeViewStructureLeft(
			TreeItem<TreeViewEntry> treeViewStructureLeft) {
		this.treeViewStructureLeft = treeViewStructureLeft;
	}

	public TreeItem<TreeViewEntry> getTreeViewStructureRight() {
		return treeViewStructureRight;
	}

	public void setTreeViewStructureRight(
			TreeItem<TreeViewEntry> treeViewStructureRight) {
		this.treeViewStructureRight = treeViewStructureRight;
	}

	public TreeItem<TreeViewEntry> getTreeViewStructureLeft1() {
		return treeViewStructureLeft1;
	}

	public void setTreeViewStructureLeft1(
			TreeItem<TreeViewEntry> treeViewStructureLeft1) {
		this.treeViewStructureLeft1 = treeViewStructureLeft1;
	}
}
