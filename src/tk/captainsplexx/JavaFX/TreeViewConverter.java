package tk.captainsplexx.JavaFX;

import java.io.File;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.Windows.MainWindow.EntryType;
import tk.captainsplexx.Mod.ModTools;
import tk.captainsplexx.Mod.Package;
import tk.captainsplexx.Mod.PackageEntry;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.EBX.EBXComplex;
import tk.captainsplexx.Resource.EBX.EBXComplexDescriptor;
import tk.captainsplexx.Resource.EBX.EBXField;
import tk.captainsplexx.Resource.EBX.EBXFieldDescriptor;
import tk.captainsplexx.Resource.EBX.EBXFile;
import tk.captainsplexx.Resource.EBX.EBXHandler.FieldValueType;
import tk.captainsplexx.Resource.EBX.EBXInstance;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;
import tk.captainsplexx.Resource.TOC.ResourceLink;
import tk.captainsplexx.Resource.TOC.TocEntry;
import tk.captainsplexx.Resource.TOC.TocField;
import tk.captainsplexx.Resource.TOC.TocFile;
import tk.captainsplexx.Resource.TOC.TocManager.TocEntryType;
import tk.captainsplexx.Resource.TOC.TocManager.TocFieldType;
import tk.captainsplexx.Resource.TOC.TocManager.TocFileType;
import tk.captainsplexx.Resource.TOC.TocSBLink;

public class TreeViewConverter {
	
	/*FROM TOC*/
	public static TreeItem<TreeViewEntry> getTreeView(TocFile tocFile){
		TreeItem<TreeViewEntry> root = new TreeItem<TreeViewEntry>(new TreeViewEntry("TocFile", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (TocEntry e : tocFile.getEntries()){
			TreeItem<TreeViewEntry> entry = readEntry(e);
			if (entry != null){
				root.getChildren().add(entry);
			}
		}
		return root;
	}
	static TreeItem<TreeViewEntry> readEntry(TocEntry tocEntry){
		if (tocEntry != null){
			TreeItem<TreeViewEntry> entry = new TreeItem<TreeViewEntry>(new TreeViewEntry("TocEntry", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
			for (TocField f : tocEntry.getFields()){
				entry.getChildren().add(readField(f));
			}
			return entry;
		}else{
			return null;
		}
	}
	@SuppressWarnings({ "unchecked", "incomplete-switch" })
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
			case RAW2:
				entry = new TreeViewEntry(tocField.getName(), new ImageView(JavaFXHandler.rawIcon), tocField.getObj(), EntryType.RAW2);
				break;
		}
		TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
		return field;
	}
	/*END OF TOC*/
	
	
	
	/*FROM EBX*/
	public static TreeItem<TreeViewEntry> getTreeView(EBXFile ebx){
		TreeItem<TreeViewEntry> root = new TreeItem<TreeViewEntry>(new TreeViewEntry(ebx.getTruePath(), new ImageView(JavaFXHandler.documentIcon), ebx.getGuid(), EntryType.LIST));
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
			try{
				switch(ebxField.getType()){
					case ArrayComplex:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.arrayIcon), null, EntryType.ARRAY);
						entry.setEBXType((short) (ebxField.getFieldDescritor().getType()&0xFFFF));
						if (ebxField.getValue() instanceof String){
							entry.setValue("*nullArray*");
							TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
							return field;
						}else{
							TreeItem<TreeViewEntry> fieldArray = new TreeItem<TreeViewEntry>(entry);
							for (EBXField eF : ebxField.getValueAsComplex().getFields()){
								fieldArray.getChildren().add(readField(eF));
							}
							return fieldArray;
						}
						//array in array does not work right ?
					case Bool:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.boolIcon), (Boolean)ebxField.getValue(), EntryType.BOOL); //0x01 == TRUE
						break;
					case Byte:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.byteIcon), (Byte)ebxField.getValue(), EntryType.BYTE);
						break;
					case Complex:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName()+"::"+ebxField.getValueAsComplex().getComplexDescriptor().getName(), new ImageView(JavaFXHandler.instanceIcon), null, EntryType.LIST);
						entry.setEBXType((short) (ebxField.getFieldDescritor().getType()&0xFFFF));
						TreeItem<TreeViewEntry> complexFields = new TreeItem<TreeViewEntry>(entry);
						for (EBXField eF : ebxField.getValueAsComplex().getFields()){
							complexFields.getChildren().add(readField(eF));
						}
						return complexFields;
					case Enum:
//						if (ebxField.getValue() instanceof EBXFieldDescriptor){
//							entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.enumIcon), ((EBXFieldDescriptor)ebxField.getValue()).getName(), EntryType.ENUM);
//						}else{
//							entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.enumIcon), (String)ebxField.getValue(), EntryType.ENUM);
//						}
						if (ebxField.getValue() instanceof HashMap<?,?>){
							entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.enumIcon), (HashMap<EBXFieldDescriptor, Boolean>)ebxField.getValue(), EntryType.ENUM);	
						}else{
							entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.enumIcon), (String)ebxField.getValue(), EntryType.ENUM);
						}
						break;
					case Float:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.floatIcon), (Float)ebxField.getValue(), EntryType.FLOAT);
						break;
					case ChunkGuid:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), null, (String)ebxField.getValue(), EntryType.CHUNKGUID);
						break;
					case Guid:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.internalIcon), (String)ebxField.getValue(), EntryType.GUID);
						break;
					case ExternalGuid:
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.structureIcon), (String)ebxField.getValue(), EntryType.GUID);
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
						entry = new TreeViewEntry(ebxField.getFieldDescritor().getName(), new ImageView(JavaFXHandler.uintegerIcon), (Long)ebxField.getValue(), EntryType.UINTEGER);
						break;
					case Unknown:
						break;
					default:
						break;
				}
				if (entry != null){
					entry.setEBXType((short) (ebxField.getFieldDescritor().getType()&0xFFFF));
				}
				TreeItem<TreeViewEntry> field = new TreeItem<TreeViewEntry>(entry);
				return field;
			}catch (Exception e){
				if (ebxField.getType() == null && ebxField.getFieldDescritor() != null){
					System.out.println(ebxField.getFieldDescritor().getName());
					System.err.println("EBXFile to TreeView: Field does not have a type defined.");
				}else{
					e.printStackTrace();
					System.err.println("Error while converting EBXFile to TreeView.");
				}
				return null;
			}
		}else{
			return null;
		}
	}
	/*END OF EBX*/
	
	/*ENCODE TO EBX*/
	public static EBXFile getEBXFile(TreeItem<TreeViewEntry> rootEntry){
		try{
			ArrayList<EBXInstance> instances = new ArrayList<>();
			for (TreeItem<TreeViewEntry> instance : rootEntry.getChildren()){
				EBXInstance ebxInstance = getEBXInstance(instance);
				if (ebxInstance != null){
					instances.add(ebxInstance);
				}
			}
			EBXFile file = new EBXFile(rootEntry.getValue().getName(), instances, (String) rootEntry.getValue().getValue());
			return file;
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("Error while converting TreeViewStructure to EBXFile.");
		}
		return null;
	}
	
	static EBXInstance getEBXInstance(TreeItem<TreeViewEntry> childEntry){
		try{
			EBXComplex complex = getEBXComplex(childEntry);
			EBXInstance ebxInstance = new EBXInstance(childEntry.getValue().getName().split(" ")[1], complex);
			return ebxInstance;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error while converting TreeViewStructure to EBXInstance.");
			return null;
		}
		
	}
	
	static EBXComplex getEBXComplex(TreeItem<TreeViewEntry> complexEntry){
		try{
			EBXComplexDescriptor complexDescriptor = null;
			
			String name = complexEntry.getValue().getName();
			String[] split = name.split("::");
			String[] splitInstanceComplex = complexEntry.getValue().getName().split(" ");
			if (split.length==2 && splitInstanceComplex.length==1){ //if it has ::
				complexDescriptor = new EBXComplexDescriptor(split[1]);//GET RIGHT PART AS COMPLEX NAME
			}else if (splitInstanceComplex.length == 2 && split.length == 1){//if it is a instanceComplex
				complexDescriptor = new EBXComplexDescriptor(splitInstanceComplex[0]); //First part is -> ReferencedObjectData //Second is Guid -> 00000001
			}else{
				complexDescriptor = new EBXComplexDescriptor(split[0]);//GET LEFT PART AS COMPLEX NAME
				
			}
			complexDescriptor.setType(complexEntry.getValue().getEBXType());
			
			
			//complexDescriptor.setType(type);
			EBXComplex complex = new EBXComplex(complexDescriptor);
			complex.setFields(new EBXField[complexEntry.getChildren().size()]);
			EBXField[] fields = complex.getFields();
			int index = 0;
			for (TreeItem<TreeViewEntry> twField : complexEntry.getChildren()){
				EBXField ebxField = getEBXField(twField);
				if (ebxField!=null){
					fields[index] = ebxField;
				}else{
					System.err.println("Error while converting TreeViewStructure to EBXComplex. 'null'.");
				}
				index++;
			}
			return complex;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error while converting TreeViewStructure to EBXComplex.");
			return null;
		}
	}
	
	static EBXField getEBXField(TreeItem<TreeViewEntry> fieldEntry){
		TreeViewEntry entry = fieldEntry.getValue();
		EBXFieldDescriptor desc = new EBXFieldDescriptor(entry.getName(), (short) 0, (short) 0, 0, 0);
		EBXField field = new EBXField(desc, 0);
		switch (fieldEntry.getValue().getType()) {
			case ARRAY:
				if (fieldEntry.getValue().getValue() instanceof String){
					field.setValue("*nullArray*", FieldValueType.ArrayComplex);
					desc.setType(entry.getEBXType());
					break;
				}else{
					EBXComplex complex0 = getEBXComplex(fieldEntry);
					field.setValue(complex0, FieldValueType.ArrayComplex);
					desc.setType(entry.getEBXType());
					break;
				}
			case BOOL:
				field.setValue(entry.getValue(), FieldValueType.Bool);
				desc.setType(entry.getEBXType());
				break;
			case BYTE:
				field.setValue(entry.getValue(), FieldValueType.Byte);
				desc.setType(entry.getEBXType());
				break;
			case CHUNKGUID:
				field.setValue(entry.getValue(), FieldValueType.ChunkGuid);
				desc.setType(entry.getEBXType());
				break;
			case ENUM:
				field.setValue(entry.getValue(), FieldValueType.Enum);//value is string(if null) or hashmap.
				desc.setType(entry.getEBXType());
				break;
			case FLOAT:
				field.setValue(entry.getValue(), FieldValueType.Float);
				desc.setType(entry.getEBXType());
				break;
			case GUID:
				field.setValue(entry.getValue(), FieldValueType.Guid);
				desc.setType(entry.getEBXType());
				break;
			case HEX8:
				field.setValue(entry.getValue(), FieldValueType.Hex8);
				desc.setType(entry.getEBXType());
				break;
			case INTEGER:
				field.setValue(entry.getValue(), FieldValueType.Integer);
				desc.setType(entry.getEBXType());
				break;
			case LIST:
				EBXComplex complex1 = getEBXComplex(fieldEntry);
				
				/*IF FIELD AS PARENT: CHANGE DESCRIPTOR NAME!*/
				desc.setName(desc.getName().split("::")[0]);
				
				field.setValue(complex1, FieldValueType.Complex);
				desc.setType(entry.getEBXType());
				break;
			case SHORT:
				field.setValue(entry.getValue(), FieldValueType.Short);
				desc.setType(entry.getEBXType());
				break;
			case STRING:
				field.setValue(entry.getValue(), FieldValueType.String);
				desc.setType(entry.getEBXType());
				return field;
			case UINTEGER:
				field.setValue(entry.getValue(), FieldValueType.UInteger);
				desc.setType(entry.getEBXType());
				return field;
			default:
				System.err.println("Error while converting TreeViewStructure to EBXField.");
				break;
			}
		return field;
	}
	/*END OF ENCODE TO EBX*/
	
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
	@SuppressWarnings("incomplete-switch") //Toc files only handle these fields.
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
		TreeItem<TreeViewEntry> bundles = new TreeItem<TreeViewEntry>(new TreeViewEntry("bundles - "+cTocF.getBundles().size()+" Children", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (TocSBLink link : cTocF.getBundles()){
			String[] name  = null;
			if (!link.getID().equals("")){
				name = link.getID().split("/");
			}
			if (!link.getGuid().equals("")){
				name = link.getGuid().split("/");
			}
			TreeViewEntry childEntry = new TreeViewEntry(name[name.length-1], new ImageView(JavaFXHandler.instanceIcon), link, EntryType.STRING);
			TreeItem<TreeViewEntry> child = new TreeItem<TreeViewEntry>(childEntry);
			childEntry.setTooltip("Offset: 0x"+FileHandler.bytesToHex(FileHandler.toBytes(link.getOffset(), ByteOrder.BIG_ENDIAN))+
					" Size: 0x"+FileHandler.bytesToHex(FileHandler.toBytes(link.getSize(), ByteOrder.BIG_ENDIAN)));
			pathToTree(bundles, link.getID(), child);
		}
		rootnode.getChildren().add(bundles);
		
		/*CHUNKS*/
		TreeItem<TreeViewEntry> chunks = new TreeItem<TreeViewEntry>(new TreeViewEntry("chunks - "+cTocF.getChunks().size()+" Children", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (TocSBLink link : cTocF.getChunks()){
			String name = "";
			if (!link.getID().equals("")){
				name = link.getID();
			}
			if (!link.getGuid().equals("")){
				name = link.getGuid();
			}
			chunks.getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry(name, new ImageView(JavaFXHandler.instanceIcon), link, EntryType.STRING)));
		}
		rootnode.getChildren().add(chunks);
		
		
		return rootnode;
	}	
	/*END OF CONVERTED TOC*/
	
	/*START OF CONVERTED TOCSBPart*/
	public static TreeItem<TreeViewEntry> getTreeView(ConvertedSBpart part){
		TreeItem<TreeViewEntry> rootnode = new TreeItem<TreeViewEntry>(new TreeViewEntry(part.getPath(), new ImageView(JavaFXHandler.documentIcon), null, EntryType.LIST));
		
		File modFilePack = new File(FileHandler.normalizePath(
				Core.getGame().getCurrentMod().getPath()+ModTools.PACKAGEFOLDER+
				Core.getGame().getCurrentFile().replace(Core.gamePath, "")+ModTools.PACKTYPE)
		);
		Package modPackage = null;
		if (modFilePack.exists()){
			modPackage = Core.getModTools().readPackageInfo(modFilePack);
		}
		
		/*EBX*/
		TreeItem<TreeViewEntry> ebx = new TreeItem<TreeViewEntry>(new TreeViewEntry("ebx - "+part.getEbx().size()+" Children", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (ResourceLink link : part.getEbx()){
			if (modPackage!=null){
				for (PackageEntry entry : modPackage.getEntries()){
					if (entry.getSubPackage().equalsIgnoreCase(part.getPath())&&//mp_playground/content
							entry.getResourcePath().equalsIgnoreCase(link.getName()+".ebx")//levels/mp_playground/content/layer2_buildings.ebx
					){
						link.setHasModFile(true);
						break;
					}
				}
				//has NO mod file
			}
			String[] name = link.getName().split("/");
			TreeViewEntry childEntry = new TreeViewEntry(name[name.length-1]+" ("+link.getSha1()+")", new ImageView(JavaFXHandler.structureIcon), link, EntryType.STRING);
			if (link.getCasPatchType()!=0){
				childEntry.setName(childEntry.getName()+"_(Patched: "+link.getCasPatchType()+")");
			}
			String isReferenced = " ref. needs work treeviewconverter";
			/*if (Core.getGame().getEBXFileGUIDs().get(link.getEbxFileGUID().toUpperCase()) != null){
				isReferenced += " (referenced) ";
			}*/
			childEntry.setTooltip("GUID: "+link.getEbxFileGUID()+isReferenced);
			TreeItem<TreeViewEntry> child = new TreeItem<TreeViewEntry>(childEntry);
			pathToTree(ebx, link.getName(), child);
		}
		rootnode.getChildren().add(ebx);
		
		/*DBX*/
		TreeItem<TreeViewEntry> dbx = new TreeItem<TreeViewEntry>(new TreeViewEntry("dbx - "+part.getDbx().size()+" Children", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (ResourceLink link : part.getDbx()){
			String[] name = link.getName().split("/");
			TreeViewEntry childEntry = new TreeViewEntry(name[name.length-1]+" ("+link.getSha1()+")", new ImageView(JavaFXHandler.structureIcon), link, EntryType.STRING);
			TreeItem<TreeViewEntry> child = new TreeItem<TreeViewEntry>(childEntry);
			pathToTree(dbx, link.getName(), child);
		}
		rootnode.getChildren().add(dbx);
		
		/*RES*/
		TreeItem<TreeViewEntry> res = new TreeItem<TreeViewEntry>(new TreeViewEntry("res - "+part.getRes().size()+" Children", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (ResourceLink link : part.getRes()){
			/*//TODO Res has mod file's too!
			if (modPackage!=null){
				for (PackageEntry entry : modPackage.getEntries()){
					if (entry.getSubPackage().equalsIgnoreCase(part.getPath())&&//mp_playground/uiloading_sp
							entry.getResourcePath().startsWith(link.getName())//levels/mp_playground/uiloading/cuteKitties.itexture
					){
						link.setHasModFile(true);
						break;
					}
				}
				//has NO mod file
			}*/
			String[] name = link.getName().split("/");
			TreeViewEntry childEntry = new TreeViewEntry(name[name.length-1]+" ("+link.getSha1()+", "+link.getType()+")", null, link, EntryType.STRING);
			ImageView graphic = null;
			switch (link.getType()) {
				case CHUNK:
					graphic = new ImageView(JavaFXHandler.rawIcon);
					break;
				case ITEXTURE:
					graphic = new ImageView(JavaFXHandler.imageIcon);
					break;
				case LUAC:
					graphic = new ImageView(JavaFXHandler.luaIcon);
					break;
				case OCCLUSIONMESH:
					graphic = new ImageView(JavaFXHandler.geometry2Icon);
					break;
				case MESH:
					graphic = new ImageView(JavaFXHandler.geometryIcon);
					break;
				default:
					graphic = new ImageView(JavaFXHandler.resourceIcon);
					break;
			}
			childEntry.setGraphic(graphic);
			TreeItem<TreeViewEntry> child = new TreeItem<TreeViewEntry>(childEntry);
			pathToTree(res, link.getName(), child);
		}
		rootnode.getChildren().add(res);
		
		/*CHUNKS*/
		TreeItem<TreeViewEntry> chunks = new TreeItem<TreeViewEntry>(new TreeViewEntry("chunks - "+part.getChunks().size()+" Children", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (ResourceLink link : part.getChunks()){
			chunks.getChildren().add(new TreeItem<TreeViewEntry>(
					new TreeViewEntry(link.getId()+" SHA1: "+link.getSha1()+" (offset: 0x"+FileHandler.toHexInteger(link.getLogicalOffset())+" , size: 0x"+FileHandler.toHexInteger(link.getLogicalSize())+")", 
							new ImageView(JavaFXHandler.instanceIcon), link, EntryType.STRING)));
		}
		rootnode.getChildren().add(chunks);
		
		/*CHUNKMETA*/
		TreeItem<TreeViewEntry> chunkmeta = new TreeItem<TreeViewEntry>(new TreeViewEntry("chunkmeta - "+part.getChunkMeta().size()+" Children", new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
		for (ResourceLink link : part.getChunkMeta()){
			chunkmeta.getChildren().add(new TreeItem<TreeViewEntry>(new TreeViewEntry(link.getH32()+"", new ImageView(JavaFXHandler.rawIcon), link, EntryType.RAW)));
		}
		rootnode.getChildren().add(chunkmeta);
		
		return rootnode;
	}
	/*END OF CONVERTED TOCSBPart*/
	
	public static void pathToTree(TreeItem<TreeViewEntry> root, String path, TreeItem<TreeViewEntry> child){
		String[] splittedPart = path.split("/");
		
		TreeItem<TreeViewEntry> parentNode = null;
		int counter = 1;
		for (String part : splittedPart){
			if (part.equals("")){
				break; //DONE
			}
			if (parentNode == null){ //FIRST RUN
				for (TreeItem<TreeViewEntry> rootChildren : root.getChildren()){
					if (rootChildren.getValue().getName().equals(part) && rootChildren.getValue().getType() == EntryType.LIST){
						parentNode = rootChildren;
						break;
					}
				}
				if (parentNode == null){
					//NO PARENT EXISTS :(
					TreeItem<TreeViewEntry> newNode = null;
					if (counter>=splittedPart.length){
						newNode = child;
					}else{
						newNode = new TreeItem<TreeViewEntry>(new TreeViewEntry(part, new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
					}
					root.getChildren().add(newNode);
					parentNode = newNode;
				}
			}else{//PARENT DOES EXIST.
				boolean found = false;
				for (TreeItem<TreeViewEntry> parentChild : parentNode.getChildren()){//DOES IT HAS A EXISTING CHILD ALREADY ?
					if (parentChild.getValue().getName().equals(part) && parentChild.getValue().getType() == EntryType.LIST){
						parentNode = parentChild;
						found = true;
						break;
					}
				}
				if (found == false){
					//CHILD DOES NOT EXIST ALREADY, SO CREATE IT!
					TreeItem<TreeViewEntry> newNode = null;
					if (counter>=splittedPart.length){
						newNode = child;
					}else{
						newNode = new TreeItem<TreeViewEntry>(new TreeViewEntry(part, new ImageView(JavaFXHandler.listIcon), null, EntryType.LIST));
					}
					parentNode.getChildren().add(newNode);
					parentNode = newNode;
				}
			}
			counter++;
		}
	}
}
