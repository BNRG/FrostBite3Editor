package tk.captainsplexx.JavaFX;

import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Toc.TocEntry;
import tk.captainsplexx.Toc.TocField;
import tk.captainsplexx.Toc.TocFile;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class TreeViewConverter {
	
	public static TreeItem<TreeViewEntry> getTreeView(TocFile tocFile){
		TreeItem<TreeViewEntry> root = new TreeItem<TreeViewEntry>(new TreeViewEntry("TocFile", null, null, EntryType.LIST));
		for (TocEntry e : tocFile.getEntries()){
			root.getChildren().add(readEntry(e));		
		}
		return root;
	}
	
	static TreeItem<TreeViewEntry> readEntry(TocEntry tocEntry){
		TreeItem<TreeViewEntry> entry = new TreeItem<TreeViewEntry>(new TreeViewEntry("TocEntry", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (TocField f : tocEntry.getFields()){
			entry.getChildren().add(readField(f));
		}
		return entry;
	}
	
	static TreeItem<TreeViewEntry> readField(TocField tocField){
		TreeViewEntry entry = null;
		TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
		switch(tocField.getType()){
			case BOOL:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.boolIcon), tocField.getObj(), EntryType.BOOL);
			case GUID:
				entry = new TreeViewEntry(tocField.getName(), null, tocField.getObj(), EntryType.GUID);
			case INTEGER:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.integerIcon), tocField.getObj(), EntryType.INTEGER);
			/*case LIST:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST);
				field.getChildren().add(readEntry((TocEntry) tocField.getObj()));*/
			case LONG:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.longIcon), tocField.getObj(), EntryType.LONG);
			case SHA1:
				entry = new TreeViewEntry(tocField.getName(), null, tocField.getObj(), EntryType.SHA1);
			case STRING:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.textIcon), tocField.getObj(), EntryType.STRING);
		}
		return field;
	}
}
