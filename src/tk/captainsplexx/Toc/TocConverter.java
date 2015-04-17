package tk.captainsplexx.Toc;

import java.util.ArrayList;

import tk.captainsplexx.Toc.TocManager.TocFieldType;

public class TocConverter {
	public static enum ResourceBundleType{EBX, DBX, RES, CHUNKS, CHUNKMETA};
	
	@SuppressWarnings("unchecked")
	public static ConvertedTocFile convertTocFile(TocFile toc){ //FOR TOC ONLY  -> NOT SBPART <-
		try{
			ConvertedTocFile convToc = new ConvertedTocFile(/*USING NULLCONSTUCTOR*/);
			TocEntry masterEntry = toc.getEntries().get(0);
			for (TocField field :  masterEntry.getFields()){
				if (field.getName().toLowerCase().equals("alwaysemitsuperbundle") && field.getType() == TocFieldType.BOOL){
					convToc.setAlwaysEmitSuperBundle((boolean) field.getObj());
				}else if (field.getName().toLowerCase().equals("cas") && field.getType() == TocFieldType.BOOL){
					convToc.setCas((boolean) field.getObj());
				}else if (field.getName().toLowerCase().equals("name") && field.getType() == TocFieldType.STRING){
					convToc.setName((String)field.getObj());
				}else if (field.getName().toLowerCase().equals("chunks") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						TocSBLink link = readTocLink(entry);
						if (link != null){
							convToc.getChunks().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("bundles") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						TocSBLink link = readTocLink(entry);
						if (link != null){
							convToc.getBundles().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("tag") && field.getType() == TocFieldType.GUID){
					convToc.setName((String)field.getObj());
				}else if (field.getName().toLowerCase().equals("totalsize") && field.getType() == TocFieldType.LONG){
					convToc.setTotalSize((Long)field.getObj());
				}else{
					System.err.println("unexpected field found in toc file while converting: "+field.getName()+" as type: "+field.getType());
				}
			}
			return convToc;
		}catch (Exception e){
			//e.printStackTrace();
			System.err.println("Could not convert TocFile!");
			return null;
		}
	}
	
	static TocSBLink readTocLink(TocEntry entry){
		TocSBLink link = new TocSBLink(/*USING NULLCONSTUCTOR*/);
		if (entry != null){
			for (TocField field :  entry.getFields()){
				if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.STRING){
					link.setID((String) field.getObj());
				}else if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.GUID){ //WHATS ABOUT CHUNKS? //TODO
					link.setID((String) field.getObj());
				}else if (field.getName().toLowerCase().equals("offset") && field.getType() == TocFieldType.LONG){
					link.setOffset((long) field.getObj());
				}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.INTEGER){
					link.setSize((int) field.getObj());
				}else{
					System.err.println("unexpected field (link) found in toc file while converting: "+field.getName());
				}
			}
			return link;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ConvertedSBpart convertSBpart(TocFile sbpart){
		try{
			ConvertedSBpart convSB = new ConvertedSBpart(/*USING NULLCONSTRUCTOR*/);
			TocEntry masterEntry = sbpart.getEntries().get(0);
			for (TocField field :  masterEntry.getFields()){
				if (field.getName().toLowerCase().equals("path") && field.getType() == TocFieldType.STRING){
					convSB.setPath((String) field.getObj());
				}else if (field.getName().toLowerCase().equals("magicsalt") && field.getType() == TocFieldType.INTEGER){
					convSB.setMagicSalt((Integer) field.getObj());
				}else if (field.getName().toLowerCase().equals("alignmembers") && field.getType() == TocFieldType.BOOL){
					convSB.setAlignMembers((Boolean) field.getObj());
				}else if (field.getName().toLowerCase().equals("ridsupport") && field.getType() == TocFieldType.BOOL){
					convSB.setRidSupport((Boolean) field.getObj());
				}else if (field.getName().toLowerCase().equals("storecompressedsizes") && field.getType() == TocFieldType.BOOL){
					convSB.setStoreCompressedSizes((Boolean) field.getObj());
				}else if (field.getName().toLowerCase().equals("totalsize") && field.getType() == TocFieldType.LONG){
					convSB.setTotalSize((long) field.getObj());
				}else if (field.getName().toLowerCase().equals("dbxtotalsize") && field.getType() == TocFieldType.LONG){
					convSB.setDbxTotalSize((long) field.getObj());
				}
				else if (field.getName().toLowerCase().equals("ebx") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						ResourceLink link = readResourceLink(entry, ResourceBundleType.EBX);
						if (link != null){
							convSB.getEbx().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("dbx") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						ResourceLink link = readResourceLink(entry, ResourceBundleType.DBX);
						if (link != null){
							convSB.getDbx().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("chunks") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						ResourceLink link = readResourceLink(entry, ResourceBundleType.CHUNKS);
						if (link != null){
							convSB.getChunks().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("chunkMeta") && field.getType() == TocFieldType.LIST){ //chunk(only singular)Meta!
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						ResourceLink link = readResourceLink(entry, ResourceBundleType.CHUNKMETA);
						if (link != null){
							convSB.getChunkMeta().add(link);
						}
					}
				}
			}
			return convSB;
		}catch (Exception e){
			//e.printStackTrace();
			System.err.println("Could not convert SBpart! ("+e.getCause()+")");
			return null;
		}
	}

	static ResourceLink readResourceLink(TocEntry entry, ResourceBundleType type) {
		if (entry != null){
			ResourceLink link = new ResourceLink(/*USING NULLCONSTUCTOR*/);
			link.setBundleType(type);
			//type-specific
			switch(type){
				case CHUNKS:
					for (TocField field :  entry.getFields()){
						if (field.getName().toLowerCase().equals("sha1") && field.getType() == TocFieldType.STRING){
							link.setSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.GUID){
							link.setId((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.LONG){
							link.setSize((Long) field.getObj());
						}else if (field.getName().toLowerCase().equals("logicaloffset") && field.getType() == TocFieldType.INTEGER){
							link.setLogicalOffset((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("logicalsize") && field.getType() == TocFieldType.INTEGER){
							link.setLogicalSize((Integer) field.getObj());
						}
					}
					break;
				case CHUNKMETA:
					for (TocField field :  entry.getFields()){
						if (field.getName().toLowerCase().equals("h32") && field.getType() == TocFieldType.INTEGER){
							link.setH32((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("meta") && field.getType() == TocFieldType.RAW){
							link.setMeta((byte[]) field.getObj());
						}
					}
					break;
				case DBX:
					for (TocField field :  entry.getFields()){
						if (field.getName().toLowerCase().equals("name") && field.getType() == TocFieldType.STRING){
							link.setName((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("sha1") && field.getType() == TocFieldType.SHA1){
							link.setSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.LONG){
							link.setSize((Long) field.getObj());
						}else if (field.getName().toLowerCase().equals("originalsize") && field.getType() == TocFieldType.LONG){
							link.setOriginalSize((Long) field.getObj());
						}
					}
					break;
				case EBX:
					for (TocField field :  entry.getFields()){
						if (field.getName().toLowerCase().equals("name") && field.getType() == TocFieldType.STRING){
							link.setName((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("sha1") && field.getType() == TocFieldType.SHA1){
							link.setSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.LONG){
							link.setSize((Long) field.getObj());
						}else if (field.getName().toLowerCase().equals("originalsize") && field.getType() == TocFieldType.LONG){
							link.setOriginalSize((Long) field.getObj());
						}
					}
					break;
				case RES:
					for (TocField field :  entry.getFields()){
						if (field.getName().toLowerCase().equals("name") && field.getType() == TocFieldType.STRING){
							link.setName((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("sha1") && field.getType() == TocFieldType.SHA1){
							link.setSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.LONG){
							link.setSize((Long) field.getObj());
						}else if (field.getName().toLowerCase().equals("originalsize") && field.getType() == TocFieldType.LONG){
							link.setOriginalSize((Long) field.getObj());
						}else if (field.getName().toLowerCase().equals("restype") && field.getType() == TocFieldType.INTEGER){
							link.setResType((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("resmeta") && field.getType() == TocFieldType.RAW){
							link.setResMeta((byte[]) field.getObj());
						}else if (field.getName().toLowerCase().equals("resrid") && field.getType() == TocFieldType.LONG){
							link.setResRid((long) field.getObj());
						}else if (field.getName().toLowerCase().equals("idata") && field.getType() == TocFieldType.RAW){
							link.setIdata((byte[]) field.getObj());
						}
					}
					break;
			}
			return link;
		}else{
			return null;
		}
	}
}