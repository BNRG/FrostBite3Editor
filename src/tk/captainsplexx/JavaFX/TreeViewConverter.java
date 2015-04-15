package tk.captainsplexx.JavaFX;

import java.util.ArrayList;

import tk.captainsplexx.EBX.EBXField;
import tk.captainsplexx.EBX.EBXFieldDescriptor;
import tk.captainsplexx.EBX.EBXFile;
import tk.captainsplexx.EBX.EBXInstance;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Toc.ConvertedSBpart;
import tk.captainsplexx.Toc.ConvertedTocFile;
import tk.captainsplexx.Toc.TocEntry;
import tk.captainsplexx.Toc.TocField;
import tk.captainsplexx.Toc.TocFile;
import tk.captainsplexx.Toc.TocManager.TocEntryType;
import tk.captainsplexx.Toc.TocManager.TocFieldType;
import tk.captainsplexx.Toc.TocManager.TocFileType;
import tk.captainsplexx.Toc.TocSBLink;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class TreeViewConverter {
	
	/*FROM TOC*/
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
	@SuppressWarnings("unchecked")
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
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.hashIcon), tocField.getObj(), EntryType.SHA1);
				break;
			case STRING:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.textIcon), tocField.getObj(), EntryType.STRING);
				break;
			case RAW:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.rawIcon), tocField.getObj(), EntryType.RAW);
				break;
		}
		TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
		return field;
	}
	/*END OF TOC*/
	
	
	
	/*FROM EBX*/
	public static TreeItem<TreeViewEntry> getTreeView(EBXFile ebx){
		TreeItem<TreeViewEntry> root = new TreeItem<TreeViewEntry>(new TreeViewEntry(ebx.getTruePath(), new ImageView(JavaFXHandler.documentIcon), null, EntryType.LIST));
		for (EBXInstance instance : ebx.getInstances()){
			root.getChildren().add(readInstance(instance));
		}
		return root;
	}
	static TreeItem<TreeViewEntry> readInstance(EBXInstance instance){
		TreeItem<TreeViewEntry> iList = new TreeItem<TreeViewEntry>(new TreeViewEntry(instance.getComplex().getComplexDescriptor().getName()+" "+instance.getGuid().toUpperCase(), new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for(EBXField field : instance.getComplex().getFields()){
			iList.getChildren().add(readField(field));
		}
		return iList;
	}
	static TreeItem<TreeViewEntry> readField(EBXField ebxField){
		TreeViewEntry entry = null;
		if (ebxField != null){
			switch(ebxField.getType()){
				case ArrayComplex:
					entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.arrayIcon), null, EntryType.ARRAY);
					entry.setEBXType((short) (ebxField.getFieldDescritor().getType()&0xFFFF));
					TreeItem<TreeViewEntry> fieldArray = new TreeItem<TreeViewEntry>(entry);
					for (EBXField eF : ebxField.getValueAsComplex().getFields()){
						fieldArray.getChildren().add(readField(eF));
					}
					return fieldArray;
				case Bool:
					entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.boolIcon), (Boolean)ebxField.getValue(), EntryType.BOOL); //0x01 == TRUE
					break;
				case Byte:
					entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.byteIcon), (Byte)ebxField.getValue(), EntryType.BYTE);
					break;
				case Complex:
					entry = new TreeViewEntry(ebxField.getValueAsComplex().getComplexDescriptor().getName()+" "+ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.instanceIcon), null, EntryType.LIST);
					entry.setEBXType((short) (ebxField.getFieldDescritor().getType()&0xFFFF));
					TreeItem<TreeViewEntry> complexFields = new TreeItem<TreeViewEntry>(entry);
					for (EBXField eF : ebxField.getValueAsComplex().getFields()){
						complexFields.getChildren().add(readField(eF));
					}
					return complexFields;
				case Enum:
					if (ebxField.getValue() instanceof EBXFieldDescriptor){
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, ((EBXFieldDescriptor)ebxField.getValue()).getName(), EntryType.ENUM);
					}else{
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (String)ebxField.getValue(), EntryType.ENUM);
					}
					break;
				case Float:
					entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.floatIcon), (Float)ebxField.getValue(), EntryType.FLOAT);
					break;
				case ChunkGuid:
					entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (String)ebxField.getValue(), EntryType.CHUNKGUID);
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
				case UInteger:
					entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (Integer)ebxField.getValue(), EntryType.UINTEGER);
					break;
				case Unknown:
					break;
				default:
					break;
			}
			entry.setEBXType((short) (ebxField.getFieldDescritor().getType()&0xFFFF));
			TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
			return field;
		}else{
			return null;
		}
	}
	/*END OF EBX*/
	
	/*ENCODE TO TOC*/
	public static TocFile getTocFile(TreeItem<TreeViewEntry> rootEntry, TocFileType type){
		TocFile file = new TocFile(type);
		ArrayList<TocEntry> entries = new ArrayList<TocEntry>();
		for (TreeItem<TreeViewEntry> child : rootEntry.getChildren()){
			entries.add(getTocEntry(child, TocEntryType.ORDINARY));
		}
		file.getEntries().addAll(entries);
		return file;
	}
	
	static TocEntry getTocEntry(TreeItem<TreeViewEntry> entry, TocEntryType type){
		TocEntry tocEntry = new TocEntry(type);
		ArrayList<TocField> fields = new ArrayList<TocField>();
		for (TreeItem<TreeViewEntry> child : entry.getChildren()){
			fields.add(getTocField(child));
		}
		tocEntry.getFields().addAll(fields);
		return tocEntry;
	}
	@SuppressWarnings("incomplete-switch")
	static TocField getTocField(TreeItem<TreeViewEntry> field){
		TocField tf = null;
		switch(field.getValue().getType()){
		case BOOL:
			tf = new TocField(field.getValue().getValue(), TocFieldType.BOOL, field.getValue().getName());
			break;
		case GUID:
			tf = new TocField(field.getValue().getValue(), TocFieldType.GUID, field.getValue().getName());
			break;
		case INTEGER:
			tf = new TocField(field.getValue().getValue(), TocFieldType.INTEGER, field.getValue().getName());
			break;
		case LIST:
			ArrayList<TocEntry> tocEntries = new ArrayList<TocEntry>();
			for (TreeItem<TreeViewEntry> item : field.getChildren()){
				tocEntries.add(getTocEntry(item, TocEntryType.ORDINARY));
			}
			tf = new TocField(tocEntries, TocFieldType.LIST, field.getValue().getName());
			break;
		case LONG:
			tf = new TocField(field.getValue().getValue(), TocFieldType.LONG, field.getValue().getName());
			break;
		case SHA1:
			tf = new TocField(field.getValue().getValue(), TocFieldType.SHA1, field.getValue().getName());
			break;
		case STRING:
			tf = new TocField(field.getValue().getValue(), TocFieldType.STRING, field.getValue().getName());
			break;
		case RAW:
			tf = new TocField(field.getValue().getValue(), TocFieldType.RAW, field.getValue().getName());
			break; 
		}
		return tf;
	}
	/*END OF TO TOC*/

	/*START OF CONVERTED TOC*/
	public static TreeItem<TreeViewEntry> getTreeView(ConvertedTocFile cTocF){
		TreeItem<TreeViewEntry> rootnode = new TreeItem<TreeViewEntry>(new TreeViewEntry(cTocF.getName(), new ImageView(JavaFXHandler.documentIcon), null, EntryType.LIST));
		
		/*BUNDLES*/
		TreeItem<TreeViewEntry> bundles = new TreeItem<TreeViewEntry>(new TreeViewEntry("bundles", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (TocSBLink link : cTocF.getBundles()){
			bundles.getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry(link.getName(), new ImageView(JavaFXHandler.instanceIcon), link, EntryType.STRING)));
		}
		rootnode.getChildren().add(bundles);
		
		/*CHUNKS*/
		TreeItem<TreeViewEntry> chunks = new TreeItem<TreeViewEntry>(new TreeViewEntry("chunks", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (TocSBLink link : cTocF.getChunks()){
			chunks.getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry(link.getName(), new ImageView(JavaFXHandler.instanceIcon), link, EntryType.STRING)));
		}
		rootnode.getChildren().add(chunks);
		
		
		return rootnode;
	}	
	/*END OF CONVERTED TOC*/
	
	/*START OF CONVERTED TOCSBPart*/
	public static TreeItem<TreeViewEntry> getTreeView(ConvertedSBpart part){
		TreeItem<TreeViewEntry> rootnode = new TreeItem<TreeViewEntry>(new TreeViewEntry("sbPart", new ImageView(JavaFXHandler.documentIcon), null, EntryType.LIST));
		
		return rootnode;
	}
	/*END OF CONVERTED TOCSBPart*/
}
