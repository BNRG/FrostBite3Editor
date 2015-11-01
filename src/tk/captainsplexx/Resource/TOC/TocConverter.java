package tk.captainsplexx.Resource.TOC;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.EBX.EBXLoader;
import tk.captainsplexx.Resource.TOC.TocManager.TocFieldType;

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
				}else if (field.getName().toLowerCase().equals("name") && field.getType() == TocFieldType.STRING){ //no name in update folder!
					convToc.setName((String)field.getObj());
				}else if (field.getName().toLowerCase().equals("chunks") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						TocSBLink link = readTocLink(entry, toc.getSBPath());
						if (link != null){
							link.setType(LinkBundleType.CHUNKS);
							convToc.getChunks().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("bundles") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						TocSBLink link = readTocLink(entry, toc.getSBPath());
						if (link != null){
							link.setType(LinkBundleType.BUNDLES);
							convToc.getBundles().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("tag") && field.getType() == TocFieldType.GUID){
					convToc.setTag((String)field.getObj());
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
	
	static TocSBLink readTocLink(TocEntry entry, String pathSb){
		TocSBLink link = new TocSBLink(/*USING NULLCONSTUCTOR*/);
		if (entry != null){
			for (TocField field :  entry.getFields()){
				if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.STRING){
					link.setID((String) field.getObj());
				}else if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.GUID){
					link.setGuid((String) field.getObj());
				}else if (field.getName().toLowerCase().equals("sha1") && field.getType() == TocFieldType.SHA1){
					link.setSha1((String) field.getObj());
				}else if (field.getName().toLowerCase().equals("offset") && field.getType() == TocFieldType.LONG){
					link.setOffset((long) field.getObj());
				}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.INTEGER){
					link.setSize((int) field.getObj());
				}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.LONG){
					link.setSizeLong((long) field.getObj());
				}else if (field.getName().toLowerCase().equals("delta") && field.getType() == TocFieldType.BOOL){
					link.setDelta((boolean) field.getObj());
				}else if (field.getName().toLowerCase().equals("base") && field.getType() == TocFieldType.BOOL){
					link.setBase((boolean) field.getObj());
				}else{
					System.err.println("unexpected field (link) found in toc file while converting: "+field.getName()+" as type "+field.getType());
				}
			}
			link.setSbPath(FileHandler.normalizePath(pathSb));
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
				}else if (field.getName().toLowerCase().equals("res") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						ResourceLink link = readResourceLink(entry, ResourceBundleType.RES);
						if (link != null){
							convSB.getRes().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("chunks") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						ResourceLink link = readResourceLink(entry, ResourceBundleType.CHUNKS);
						if (link != null){
							convSB.getChunks().add(link);
						}
					}
				}else if (field.getName().toLowerCase().equals("chunkmeta") && field.getType() == TocFieldType.LIST){ //chunk(only singular)Meta!
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						ResourceLink link = readResourceLink(entry, ResourceBundleType.CHUNKMETA);
						if (link != null){
							convSB.getChunkMeta().add(link);
						}
					}
				}else{
					System.err.println("TocConverter found an unhandled field: "+field.getName()+" as "+field.getType());
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
						if (field.getName().toLowerCase().equals("sha1") && field.getType() == TocFieldType.SHA1){
							link.setSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("sha1") && field.getType() == TocFieldType.STRING){
							link.setSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.GUID){
							link.setId((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.LONG){
							link.setSize((Long) field.getObj());
						}else if (field.getName().toLowerCase().equals("logicaloffset") && field.getType() == TocFieldType.INTEGER){
							link.setLogicalOffset((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("logicalsize") && field.getType() == TocFieldType.INTEGER){
							link.setLogicalSize((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("rangestart") && field.getType() == TocFieldType.INTEGER){
							link.setRangeStart((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("rangeend") && field.getType() == TocFieldType.INTEGER){
							link.setRangeEnd((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("caspatchtype") && field.getType() == TocFieldType.INTEGER){
							link.setCasPatchType((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("basesha1") && field.getType() == TocFieldType.SHA1){
							link.setBaseSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("deltasha1") && field.getType() == TocFieldType.SHA1){
							link.setDeltaSha1((String) field.getObj());
						}else{
							typeNotHandled(field);
						}
					}
					Core.getGame().getChunkGUIDSHA1().put(link.getId().toLowerCase(), link.getSha1());
					break;
				case CHUNKMETA:
					for (TocField field :  entry.getFields()){
						if (field.getName().toLowerCase().equals("h32") && field.getType() == TocFieldType.INTEGER){
							link.setH32((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("meta") && field.getType() == TocFieldType.RAW){
							link.setMeta((byte[]) field.getObj());
						}else if (field.getName().toLowerCase().equals("firstmip") && field.getType() == TocFieldType.INTEGER){
							link.setFirstMip((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("caspatchtype") && field.getType() == TocFieldType.INTEGER){
							link.setCasPatchType((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("basesha1") && field.getType() == TocFieldType.SHA1){
							link.setBaseSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("deltasha1") && field.getType() == TocFieldType.SHA1){
							link.setDeltaSha1((String) field.getObj());
						}else{
							typeNotHandled(field);
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
						}else if (field.getName().toLowerCase().equals("caspatchtype") && field.getType() == TocFieldType.INTEGER){
							link.setCasPatchType((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("basesha1") && field.getType() == TocFieldType.SHA1){
							link.setBaseSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("deltasha1") && field.getType() == TocFieldType.SHA1){
							link.setDeltaSha1((String) field.getObj());
						}else{
							typeNotHandled(field);
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
						}else if (field.getName().toLowerCase().equals("caspatchtype") && field.getType() == TocFieldType.INTEGER){
							link.setCasPatchType((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("basesha1") && field.getType() == TocFieldType.SHA1){
							link.setBaseSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("deltasha1") && field.getType() == TocFieldType.SHA1){
							link.setDeltaSha1((String) field.getObj());
						}else{
							typeNotHandled(field);
						}
					}
					try{
						
						link.setEbxFileGUID(
							EBXLoader.getGUID(
								CasDataReader.readCas(
										link.getBaseSha1(),
										link.getDeltaSha1(),
										link.getSha1(),
										link.getCasPatchType()
								)
							)
						);
						
						Core.getGame().getResourceHandler().getEBXHandler().getEBXFileGUIDs().put(link.getEbxFileGUID(), link.getName());
						
					}catch (Exception e){
						//Timeout in JavaFX Thread ??
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
							int resType = (Integer) field.getObj();
							link.setType(toResourceType(resType));
							link.setResType(resType);
						}else if (field.getName().toLowerCase().equals("resmeta") && field.getType() == TocFieldType.RAW2){
							link.setResMeta((byte[]) field.getObj());
						}else if (field.getName().toLowerCase().equals("resrid") && field.getType() == TocFieldType.LONG){
							link.setResRid((long) field.getObj());
						}else if (field.getName().toLowerCase().equals("idata") && field.getType() == TocFieldType.RAW2){
							link.setIdata((byte[]) field.getObj());
						}else if (field.getName().toLowerCase().equals("caspatchtype") && field.getType() == TocFieldType.INTEGER){
							link.setCasPatchType((Integer) field.getObj());
						}else if (field.getName().toLowerCase().equals("basesha1") && field.getType() == TocFieldType.SHA1){
							link.setBaseSha1((String) field.getObj());
						}else if (field.getName().toLowerCase().equals("deltasha1") && field.getType() == TocFieldType.SHA1){
							link.setDeltaSha1((String) field.getObj());
						}else{
							typeNotHandled(field);
						}
					}
					break;
			}
			return link;
		}else{
			return null;
		}
	}
	
	public static void typeNotHandled(TocField entry){
		System.err.println("TocConverter found a not handled Field. Type: "+entry.getType()+" Name: "+entry.getName());
	}
	
	public static ResourceType toResourceType(int resType){
		switch(resType){
			case 0x5C4954A6:
				return ResourceType.ITEXTURE;
			case 0x2D47A5FF:
				return ResourceType.GFX;
			case 0x22FE8AC8:
				return ResourceType.UNDEFINED;
			case 0x6BB6D7D2:
				return ResourceType.STREAIMINGSTUB;
			case 0x1CA38E06:
				return ResourceType.UNDEFINED;
			case 0x15E1F32E:
				return ResourceType.UNDEFINED;
			case 0x4864737B:
				return ResourceType.HKDESTRUCTION;
			case 0x91043F65:
				return ResourceType.HKNONDESTRUCTION;
			case 0x51A3C853:
				return ResourceType.ANT;
			case 0xD070EED1:
				return ResourceType.ANIMTRACKDATA;
			case 0x319D8CD0:
				return ResourceType.RAGDOLL;
			case 0x30B4A553:
				return ResourceType.OCCLUSIONMESH;
			case 0x49B156D4:
				return ResourceType.MESH;
			case 0x5BDFDEFE:
				return ResourceType.LIGHTINGSYSTEM;
			case 0x70C5CB3E:
				return ResourceType.ENLIGHTEN;
			case 0xE156AF73:
				return ResourceType.PROBESET;
			case 0x7AEFC446:
				return ResourceType.STATICENLIGHTEN;
			case 0x59CEEB57:
				return ResourceType.SHADERDATERBASE;
			case 0x36F3F2C0:
				return ResourceType.SHADERDB;
			case 0x10F0E5A1:
				return ResourceType.SHADERPROGRAMDB;
			case 0xafecb022:
				return ResourceType.LUAC;
			case 0x957C32B1:
				return ResourceType.UNDEFINED;
			case 0xC6CD3286:
				return ResourceType.UNDEFINED;
			case 0xA23E75DB:
				return ResourceType.UNDEFINED;
			default:
				System.out.println("unknown ResourceType found in TocConverter: "+FileHandler.toHexInteger((resType&0xFFFFFFFF), ByteOrder.BIG_ENDIAN));
				return ResourceType.UNDEFINED;
		}
	}
}
