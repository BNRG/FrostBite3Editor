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
		
		//Entries
		TocEntry bundleEntry = new TocEntry(TocEntryType.ORDINARY);
		for (TocSBLink link : cToc.getBundles()){
			TocField fieldID = new TocField(link.getID(), TocFieldType.STRING, "id");
			bundleEntry.getFields().add(fieldID);
			//TODO add all values from SBLink
		}
		TocField bundles = new TocField(bundleEntry, TocFieldType.LIST, "bundles");
		
		TocEntry chunkEntry = new TocEntry(TocEntryType.ORDINARY);
		for (TocSBLink link : cToc.getChunks()){
			TocField fieldID = new TocField(link.getID(), TocFieldType.STRING, "id");
			chunkEntry.getFields().add(fieldID);
			//TODO add all values from SBLink
		}
		TocField chunks = new TocField(chunkEntry, TocFieldType.LIST, "chunks");
		
		TocField cas = new TocField(cToc.isCas(), TocFieldType.BOOL, "cas");
		TocField name = new TocField(cToc.getName(), TocFieldType.STRING, "name");
		TocField emitSuperBundle = new TocField(cToc.isAlwaysEmitSuperBundle(), TocFieldType.BOOL, "alwaysEmitSuperbundle");
		
		TocEntry rootEntry = new TocEntry(TocEntryType.ORDINARY);
		
		//no observable list for adding as elements :(
		rootEntry.getFields().add(bundles);
		rootEntry.getFields().add(chunks);
		rootEntry.getFields().add(cas);
		rootEntry.getFields().add(name);
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
				entry.addAll(FileHandler.toLEB128List(entryData.size() & 0xFFFFFFFF));
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
				ArrayList<Byte> entry = createEntry((TocEntry) field.getObj());
				data.addAll(FileHandler.toLEB128List(entry.size() & 0xFFFFFFFF));
				data.addAll(entry);
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
		}
		return data;
	}
}
