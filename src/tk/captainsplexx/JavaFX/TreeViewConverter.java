package tk.captainsplexx.JavaFX;

import java.util.ArrayList;

import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Toc.TocEntry;
import tk.captainsplexx.Toc.TocField;
import tk.captainsplexx.Toc.TocFile;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class TreeViewConverter {
	
	public static TreeItem<TreeViewEntry> getTreeView(TocFile tocFile){
		TreeItem<TreeViewEntry> root = new TreeItem<TreeViewEntry>(new TreeViewEntry("TocFile", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (TocEntry e : tocFile.getEntries()){
			root.getChildren().add(readEntry(e));		
		}
		root.getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry("Stop", new ImageView(JavaFXHandler.textIcon), "RIGHT SIDE WORKS!", EntryType.STRING)));
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
		switch(tocField.getType()){
			case BOOL:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.boolIcon), tocField.getObj(), EntryType.BOOL);
				break;
			case GUID:
				entry = new TreeViewEntry(tocField.getName(), null, tocField.getObj(), EntryType.GUID);
				break;
			case INTEGER:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.integerIcon), tocField.getObj(), EntryType.INTEGER);
				break;
			case LIST:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST);
				TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
				for (TocEntry tocE : (ArrayList<TocEntry>) tocField.getObj()){
					field.getChildren().add(readEntry(tocE));
				}
				return field;
			case LONG:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.longIcon), tocField.getObj(), EntryType.LONG);
				break;
			case SHA1:
				entry = new TreeViewEntry(tocField.getName(), null, tocField.getObj(), EntryType.SHA1);
				break;
			case STRING:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.textIcon), tocField.getObj(), EntryType.STRING);
				break;
		}
		TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
		return field;
	}
}
