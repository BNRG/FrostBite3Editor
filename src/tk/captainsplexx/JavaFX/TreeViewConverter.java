package tk.captainsplexx.JavaFX;

import java.util.ArrayList;

import tk.captainsplexx.EBX.EBXComplexDescriptor;
import tk.captainsplexx.EBX.EBXField;
import tk.captainsplexx.EBX.EBXFile;
import tk.captainsplexx.EBX.EBXInstance;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Toc.TocEntry;
import tk.captainsplexx.Toc.TocField;
import tk.captainsplexx.Toc.TocFile;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class TreeViewConverter {
	
	/*TOC*/
	public static TreeItem<TreeViewEntry> getTreeView(TocFile tocFile){
		TreeItem<TreeViewEntry> root = new TreeItem<TreeViewEntry>(new TreeViewEntry("TocFile", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
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
	/*END OF TOC*/
	
	/*EBX*/
	
	public static TreeItem<TreeViewEntry> getTreeView(EBXFile ebx){
		TreeItem<TreeViewEntry> root = new TreeItem<TreeViewEntry>(new TreeViewEntry(ebx.getTruePath(), new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (EBXInstance instance : ebx.getInstances()){
			root.getChildren().add(readInstance(instance));
		}
		return root;
	}
	
	public static TreeItem<TreeViewEntry> readInstance(EBXInstance instance){
		TreeItem<TreeViewEntry> iList = new TreeItem<TreeViewEntry>(new TreeViewEntry(instance.getComplex().getComplexDescriptor().getName()+" "+instance.getGuid().toUpperCase(), new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for(EBXField field : instance.getComplex().getFields()){
			iList.getChildren().add(readField(field));
		}
		return iList;
	}
	
	public static TreeItem<TreeViewEntry> readField(EBXField ebxField){
		TreeViewEntry entry = null;
		switch(ebxField.getType()){
			case ArrayComplex:
				/*entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.arrayIcon), null, EntryType.ARRAY);
				TreeItem<TreeViewEntry> fieldArray = new TreeItem<TreeViewEntry>(entry);
				for (EBXField eF : ebxField.getValueAsComplex().getFields()){
					fieldArray.getChildren().add(readField(eF));
				}
				return fieldArray;*/
				break;
			case Bool:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.boolIcon), (Boolean)ebxField.getValue(), EntryType.BOOL); //0x01 == TRUE
				break;
			case Byte:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.byteIcon), (Byte)ebxField.getValue(), EntryType.BYTE);
				break;
			case Complex:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST);
				TreeItem<TreeViewEntry> fieldComplex = new TreeItem<TreeViewEntry>(entry);
				for (EBXField eF : ebxField.getValueAsComplex().getFields()){
					fieldComplex.getChildren().add(readField(eF));
				}
				return fieldComplex;
			case Enum:
				//entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (String)ebxField.getValue(), EntryType.ENUM);
				break;
			case Float:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.floatIcon), (Float)ebxField.getValue(), EntryType.FLOAT);
				break;
			case Guid:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (String)ebxField.getValue(), EntryType.GUID);
				break;
			case Hex8:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (String)ebxField.getValue(), EntryType.HEX8);
				break;
			case Integer:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.integerIcon), (Integer)ebxField.getValue(), EntryType.INTEGER);
				break;
			case Short:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.shortIcon), (Short)ebxField.getValue(), EntryType.SHORT);
				break;
			case String:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.textIcon), (String)ebxField.getValue(), EntryType.STRING);
				break;
			case UIntegerAsLong:
				entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (Long)ebxField.getValue(), EntryType.UINTEGER);
				break;
			case Unknown:
				break;
			default:
				break;
		}
		
		TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
		return field;
	}
	
	
	/*END OF EBX*/
}
