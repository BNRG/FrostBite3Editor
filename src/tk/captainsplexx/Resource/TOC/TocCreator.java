package tk.captainsplexx.Resource.TOC;

import java.io.File;
import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
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

	public static byte[] createSBpart(ConvertedSBpart part){
		ArrayList<Byte> out = new ArrayList<>();

		TocEntry rootEntry = new TocEntry(TocEntryType.ORDINARY);

		TocField path = new TocField(part.getPath(), TocFieldType.STRING, "path");
		rootEntry.getFields().add(path);

		TocField magicSalt = new TocField(part.getMagicSalt(), TocFieldType.INTEGER, "magicSalt");
		rootEntry.getFields().add(magicSalt);

		//EBX
		TocEntry ebxEntry = new TocEntry(TocEntryType.ORDINARY);
		for (ResourceLink link : part.getEbx()){
			TocEntry linkEntry = new TocEntry(TocEntryType.ORDINARY);

			TocField name = new TocField(link.getName(), TocFieldType.STRING, "name");
			linkEntry.getFields().add(name);

			TocField sha1 = new TocField(link.getSha1(), TocFieldType.SHA1, "sha1");
			linkEntry.getFields().add(sha1);

			TocField size = new TocField(link.getSize(), TocFieldType.LONG, "size");
			linkEntry.getFields().add(size);

			TocField originalSize = new TocField(link.getOriginalSize(), TocFieldType.LONG, "originalSize");
			linkEntry.getFields().add(originalSize);


			TocField linkField = new TocField(linkEntry, TocFieldType.ENTRY, null);
			ebxEntry.getFields().add(linkField);
		}
		TocField ebxs = new TocField(ebxEntry, TocFieldType.LIST, "ebx");
		rootEntry.getFields().add(ebxs);

		//DBX
		TocEntry dbxEntry = new TocEntry(TocEntryType.ORDINARY);
		for (ResourceLink link : part.getDbx()){
			TocEntry linkEntry = new TocEntry(TocEntryType.ORDINARY);

			TocField name = new TocField(link.getName(), TocFieldType.STRING, "name");
			linkEntry.getFields().add(name);

			TocField sha1 = new TocField(link.getSha1(), TocFieldType.SHA1, "sha1");
			linkEntry.getFields().add(sha1);

			TocField size = new TocField(link.getSize(), TocFieldType.LONG, "size");
			linkEntry.getFields().add(size);

			TocField originalSize = new TocField(link.getOriginalSize(), TocFieldType.LONG, "originalSize");
			linkEntry.getFields().add(originalSize);

			TocField linkField = new TocField(linkEntry, TocFieldType.ENTRY, null);
			dbxEntry.getFields().add(linkField);
		}
		TocField dbxs = new TocField(ebxEntry, TocFieldType.LIST, "dbx");
		rootEntry.getFields().add(dbxs);

		//RES
		TocEntry resEntry = new TocEntry(TocEntryType.ORDINARY);
		for (ResourceLink link : part.getDbx()){
			TocEntry linkEntry = new TocEntry(TocEntryType.ORDINARY);

			TocField name = new TocField(link.getName(), TocFieldType.STRING, "name");
			linkEntry.getFields().add(name);

			TocField sha1 = new TocField(link.getSha1(), TocFieldType.SHA1, "sha1");
			linkEntry.getFields().add(sha1);

			TocField size = new TocField(link.getSize(), TocFieldType.LONG, "size");
			linkEntry.getFields().add(size);

			TocField originalSize = new TocField(link.getOriginalSize(), TocFieldType.LONG, "originalSize");
			linkEntry.getFields().add(originalSize);			

			//RES-SPEC
			TocField resType = new TocField(link.getResType(), TocFieldType.INTEGER, "resType");
			linkEntry.getFields().add(resType);		

			TocField resMeta = new TocField(link.getResMeta(), TocFieldType.RAW2, "resMeta");
			linkEntry.getFields().add(resMeta);	

			TocField resRid = new TocField(link.getResRid(), TocFieldType.LONG, "resRid");
			linkEntry.getFields().add(resRid);	

			TocField idata = new TocField(link.getIdata(), TocFieldType.RAW2, "idata");
			linkEntry.getFields().add(idata);	


			TocField linkField = new TocField(linkEntry, TocFieldType.ENTRY, null);
			resEntry.getFields().add(linkField);
		}
		TocField ress = new TocField(ebxEntry, TocFieldType.LIST, "res");
		rootEntry.getFields().add(ress);

		//CHUNKS
		TocEntry chunksEntry = new TocEntry(TocEntryType.ORDINARY);
		for (ResourceLink link : part.getDbx()){
			TocEntry linkEntry = new TocEntry(TocEntryType.ORDINARY);
			TocField name = new TocField(link.getName(), TocFieldType.STRING, "name");
			linkEntry.getFields().add(name);

			TocField sha1 = new TocField(link.getSha1(), TocFieldType.SHA1, "sha1");
			linkEntry.getFields().add(sha1);

			TocField size = new TocField(link.getSize(), TocFieldType.LONG, "size");
			linkEntry.getFields().add(size);

			TocField originalSize = new TocField(link.getOriginalSize(), TocFieldType.LONG, "originalSize");
			linkEntry.getFields().add(originalSize);			

			//CHNUNKS-SPEC

			TocField id = new TocField(link.getId(), TocFieldType.GUID, "id");
			linkEntry.getFields().add(id);

			TocField rangeStart = new TocField(link.getRangeStart(), TocFieldType.INTEGER, "rangeStart");
			linkEntry.getFields().add(rangeStart);

			TocField rangeEnd = new TocField(link.getRangeEnd(), TocFieldType.INTEGER, "rangEnd");
			linkEntry.getFields().add(rangeEnd);

			TocField logicalOffset = new TocField(link.getLogicalOffset(), TocFieldType.INTEGER, "logicalOffset");
			linkEntry.getFields().add(logicalOffset);

			TocField logicalSize = new TocField(link.getLogicalSize(), TocFieldType.INTEGER, "logicalSize");
			linkEntry.getFields().add(logicalSize);



			TocField linkField = new TocField(linkEntry, TocFieldType.ENTRY, null);
			chunksEntry.getFields().add(linkField);
		}
		TocField chunks = new TocField(ebxEntry, TocFieldType.LIST, "chunks");
		rootEntry.getFields().add(chunks);

		//CHUNKMETA
		TocEntry chunkMetaEntry = new TocEntry(TocEntryType.ORDINARY);
		for (ResourceLink link : part.getDbx()){
			TocEntry linkEntry = new TocEntry(TocEntryType.ORDINARY);
			TocField name = new TocField(link.getName(), TocFieldType.STRING, "name");
			linkEntry.getFields().add(name);

			TocField sha1 = new TocField(link.getSha1(), TocFieldType.SHA1, "sha1");
			linkEntry.getFields().add(sha1);

			TocField size = new TocField(link.getSize(), TocFieldType.LONG, "size");
			linkEntry.getFields().add(size);

			TocField originalSize = new TocField(link.getOriginalSize(), TocFieldType.LONG, "originalSize");
			linkEntry.getFields().add(originalSize);

			//CHUNKMETA-SPEC

			TocField h32 = new TocField(link.getH32(), TocFieldType.INTEGER, "h32");
			linkEntry.getFields().add(h32);

			TocField meta = new TocField(link.getMeta(), TocFieldType.RAW, "meta");
			linkEntry.getFields().add(meta);

			TocField firstMip = new TocField(link.getFirstMip(), TocFieldType.INTEGER, "firstMip");
			linkEntry.getFields().add(firstMip);

			TocField linkField = new TocField(linkEntry, TocFieldType.ENTRY, null);
			chunkMetaEntry.getFields().add(linkField);
		}
		TocField chunkMetas = new TocField(ebxEntry, TocFieldType.LIST, "chunkMeta");
		rootEntry.getFields().add(chunkMetas);

		//FIELDS
		TocField alignMembers = new TocField(part.isAlignMembers(), TocFieldType.BOOL, "alignMembers");
		rootEntry.getFields().add(alignMembers);

		TocField ridSupport = new TocField(part.isRidSupport(), TocFieldType.BOOL, "ridSupport");
		rootEntry.getFields().add(ridSupport);

		TocField storeCompressedSizes = new TocField(part.isRidSupport(), TocFieldType.BOOL, "storeCompressedSizes");
		rootEntry.getFields().add(storeCompressedSizes);

		TocField totalSize = new TocField(part.getTotalSize(), TocFieldType.LONG, "totalSize");
		rootEntry.getFields().add(totalSize);

		TocField dbxTotalSize = new TocField(part.getDbxTotalSize(), TocFieldType.LONG, "dbxTotalSize");
		rootEntry.getFields().add(dbxTotalSize);

		//PAYLOAD
		out.addAll(createEntry(rootEntry));
		return FileHandler.toByteArray(out);
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

	public static boolean createModifiedSBFile(ConvertedTocFile toc, ConvertedSBpart newBundlePart, boolean existAlready, String destination, boolean override){
		FileSeeker seeker = new FileSeeker();
		File destFile = new File(destination);
		if (destFile.exists()){
			if (!override){
				System.err.println("Can't create a modifieded SB file. File does already exists.");
				return false;
			}else{
				System.out.println("File already exist. Using overriding on: "+destination);
				destFile.delete();
			}
		}
		destFile = null;//Not needed anymore, freeUp memory!

		if (existAlready){
			TocSBLink link = new TocSBLink();
			link.setID(newBundlePart.getPath());
			link.setType(LinkBundleType.BUNDLES);
			toc.getBundles().add(link);
		}

		for (TocSBLink bundle : toc.getBundles()){
			long currentOffset = seeker.getOffset();
			if (bundle.getID().equals(newBundlePart.getPath())){
				System.out.println("Bundle replace/add: "+bundle.getID());
				byte[] newBundleBytes = TocCreator.createSBpart(newBundlePart);
				FileHandler.writeFile(bundle.getSbPath(), newBundleBytes, true);
				bundle.setOffset(currentOffset);
				bundle.setSize(newBundleBytes.length);
			}else{
				boolean success = FileHandler.writeFileFromFile(bundle.getSbPath(), bundle.getOffset(), bundle.getSize(), destination, seeker);
				if (!success){
					System.err.println("Abort: Something went wrong while creating modifited sb file :( (BUNDLES)");
					return false;
				}else{
					bundle.setOffset(currentOffset);
				}
			}
		}
		
		for (TocSBLink chunk : toc.getChunks()){
			long currentOffset = seeker.getOffset();
			boolean success = FileHandler.writeFileFromFile(chunk.getSbPath(), chunk.getOffset(), chunk.getSizeLong(), destination, seeker);
			if (!success){
				System.err.println("Abort: Something went wrong while creating modifited sb file :( (CHUNKS)");
				return false;
			}else{
				chunk.setOffset(currentOffset);
			}
		}
		return true;
	}

}
