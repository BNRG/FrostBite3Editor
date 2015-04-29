package tk.captainsplexx.Resource.TOC;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.TOC.TocManager.TocEntryType;
import tk.captainsplexx.Resource.TOC.TocManager.TocFieldType;

public class TocCreator {
	public static byte[] createTocFile(ConvertedTocFile cToc){
		ArrayList<Byte> file = new ArrayList<>();
		
		//Header 556Bytes == Type - Sig - Key
		byte[] header = FileHandler.readFile("res/toc/header.hex");
		for (byte b : header){file.add(b);}
		
		
		TocEntry rootEntry = new TocEntry(TocEntryType.ORDINARY);
		
		//Entries
		if (!cToc.getTag().equals("")){
			TocField tag = new TocField(cToc.getTag(), TocFieldType.GUID, "tag");
			rootEntry.getFields().add(tag);
		}
		
		TocEntry bundleEntry = new TocEntry(TocEntryType.ORDINARY);
		for (TocSBLink link : cToc.getBundles()){
			TocEntry linkEntry = new TocEntry(TocEntryType.ORDINARY);
			
			TocField fieldID = new TocField(link.getID(), TocFieldType.STRING, "id");
			linkEntry.getFields().add(fieldID);
			TocField fieldOffset = new TocField(link.getOffset(), TocFieldType.LONG, "offset");
			linkEntry.getFields().add(fieldOffset);
			TocField fieldSize = new TocField(link.getSize(), TocFieldType.INTEGER, "size");
			linkEntry.getFields().add(fieldSize);
				
			TocField linkField = new TocField(linkEntry, TocFieldType.ENTRY, null);
			bundleEntry.getFields().add(linkField);
		}
		TocField bundles = new TocField(bundleEntry, TocFieldType.LIST, "bundles");
		rootEntry.getFields().add(bundles);
		
		TocEntry chunkEntry = new TocEntry(TocEntryType.ORDINARY);
		for (TocSBLink link : cToc.getChunks()){
			TocEntry linkEntry = new TocEntry(TocEntryType.ORDINARY);
			
			TocField fieldID = new TocField(link.getGuid(), TocFieldType.GUID, "id");
			linkEntry.getFields().add(fieldID);
			TocField fieldOffset = new TocField(link.getOffset(), TocFieldType.LONG, "offset");
			linkEntry.getFields().add(fieldOffset);
			TocField fieldSize = new TocField(link.getSize(), TocFieldType.INTEGER, "size");
			linkEntry.getFields().add(fieldSize);
				
			TocField linkField = new TocField(linkEntry, TocFieldType.ENTRY, null);
			chunkEntry.getFields().add(linkField);
		}
		TocField chunks = new TocField(chunkEntry, TocFieldType.LIST, "chunks");
		rootEntry.getFields().add(chunks);
		
		if (cToc.isCas()){
			TocField cas = new TocField(cToc.isCas(), TocFieldType.BOOL, "cas");
			rootEntry.getFields().add(cas);
		}
		
		if (!(cToc.getTotalSize() == -1)){
			TocField totalsize = new TocField(cToc.getTotalSize(), TocFieldType.LONG, "totalSize");
			rootEntry.getFields().add(totalsize);
		}
		
		TocField name = new TocField(cToc.getName(), TocFieldType.STRING, "name");
		rootEntry.getFields().add(name);
				
		TocField emitSuperBundle = new TocField(cToc.isAlwaysEmitSuperBundle(), TocFieldType.BOOL, "alwaysEmitSuperbundle");
		rootEntry.getFields().add(emitSuperBundle);
		
		
		file.addAll(createEntry(rootEntry));
		
		return FileHandler.toByteArray(file);
	}
	
	static ArrayList<Byte> createEntry(TocEntry tocE){
		ArrayList<Byte> entry = new ArrayList<>();
		switch(tocE.getType()){
			case ORDINARY:
				entry.add((byte) 0x82);
				ArrayList<Byte> entryData = new ArrayList<Byte>();
				for (TocField field : tocE.getFields()){
					ArrayList<Byte> data = createField(field);
					entryData.addAll(data);
				}
				entry.addAll(FileHandler.toLEB128List((entryData.size()+1) & 0xFFFFFFFF));
				for (byte b : entryData){
					entry.add(b);
				}
				entry.add((byte) 0x00);
				break;
			default:
				System.err.println("Unknown type of Entry found in TocCreator :( "+tocE.getType());
				return null;
			}
		return entry;
	}
	
	static ArrayList<Byte> createField(TocField field){
		if (field.getType() == null){return null;}
		
		ArrayList<Byte> data = new ArrayList<Byte>();
		
		//name
		String name = field.getName();
		ArrayList<Byte> nameBytes = new ArrayList<Byte>();
		for (byte b : name.getBytes()){
			nameBytes.add(b);
		}
		nameBytes.add((byte) 0x00); //tailing null
		
		//type
		switch(field.getType()){
			case BOOL:
				data.add((byte) 0x06);
				data.addAll(nameBytes);
				boolean v = (boolean) field.getObj();
				if (v){
					data.add((byte) 0x01);
				}else{
					data.add((byte) 0x00);
				}
				break;
			case GUID:
				data.add((byte) 0x0F);
				data.addAll(nameBytes);
				byte[] guid = FileHandler.hexStringToByteArray(((String) field.getObj()).toUpperCase());
				for (byte b : guid){
					data.add(b);
				}
				break;
			case INTEGER:
				data.add((byte) 0x08);
				data.addAll(nameBytes);
				byte[] intB = FileHandler.toBytes((int) field.getObj(), ByteOrder.LITTLE_ENDIAN);
				for (byte b : intB){
					data.add(b);
				}
				break;
			case LIST:
				data.add((byte) 0x01);
				data.addAll(nameBytes);
				TocEntry entries = (TocEntry) field.getObj();
				ArrayList<Byte> list = new ArrayList<Byte>();
				for (TocField f : entries.getFields()){
					if (f.getType() == TocFieldType.ENTRY){
						TocEntry fieldEntry = (TocEntry) f.getObj();
						ArrayList<Byte> entry = createEntry(fieldEntry);
						list.addAll(entry);
					}else{
						ArrayList<Byte> entry = createField(f);
						list.addAll(entry);
					}
				}
				data.addAll(FileHandler.toLEB128List((list.size()+1) & 0xFFFFFFFF));
				data.addAll(list);
				data.add((byte) 0x00);
				break;
			case LONG:
				data.add((byte) 0x09);
				data.addAll(nameBytes);
				byte[] longB = FileHandler.toBytes((long) field.getObj(), ByteOrder.LITTLE_ENDIAN);
				for (byte b : longB){
					data.add(b);
				}
				break;
			case RAW:
				data.add((byte) 0x02);
				data.addAll(nameBytes);
				byte[] raw = (byte[]) field.getObj();
				data.addAll(FileHandler.toLEB128List(raw.length));
				for (byte b : raw){
					data.add(b);
				}
				break;
			case RAW2:
				data.add((byte) 0x13);
				data.addAll(nameBytes);
				byte[] raw2 = (byte[]) field.getObj();
				data.addAll(FileHandler.toLEB128List(raw2.length));
				for (byte b : raw2){
					data.add(b);
				}
				break;
			case SHA1:
				data.add((byte) 0x10);
				data.addAll(nameBytes);
				byte[] sha1 = FileHandler.hexStringToByteArray(((String) field.getObj()).toUpperCase());
				for (byte b : sha1){
					data.add(b);
				}
				break;
			case STRING:
				data.add((byte) 0x07);
				data.addAll(nameBytes);
				String value = (String) field.getObj();
				data.addAll(FileHandler.toLEB128List((value.length()+1) & 0xFFFFFFFF));
				//tailing null is also inculded!
				for (byte b : value.getBytes()){
					data.add(b);
				}
				data.add((byte) 0x00);
				break;
			case ENTRY:
					System.err.println("ONLY USED FOR RECREATION, NORMALY THIS SHOULD NOT HAPPEN :(");
				break;
		default:
			break;
		}
		return data;
	}
}
