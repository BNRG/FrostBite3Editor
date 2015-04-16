package tk.captainsplexx.Toc;

import java.util.ArrayList;

import tk.captainsplexx.Toc.TocManager.TocFieldType;

public class TocConverter {
	
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
			e.printStackTrace();
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
				}else if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.GUID){
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
	
	public static ConvertedSBpart convertSBpart(TocFile sbpart){
		ConvertedSBpart convSB = new ConvertedSBpart(/*USING NULLCONSTRUCTOR*/);
		
		
		return null;
	}
}
