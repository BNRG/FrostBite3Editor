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
						convToc.getChunks().add(readLink(entry));
					}
				}else if (field.getName().toLowerCase().equals("bundles") && field.getType() == TocFieldType.LIST){
					for (TocEntry entry : (ArrayList<TocEntry>) field.getObj()){
						convToc.getBundles().add(readLink(entry));
					}
				}
			}
			return convToc;
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("Could not convert TocFile!");
			return null;
		}
	}
	
	static TocSBLink readLink(TocEntry entry){
		TocSBLink link = new TocSBLink(/*USING NULLCONSTUCTOR*/);
		for (TocField field :  entry.getFields()){
			if (field.getName().toLowerCase().equals("id") && field.getType() == TocFieldType.STRING){
				link.setName((String) field.getObj());
			}else if (field.getName().toLowerCase().equals("offset") && field.getType() == TocFieldType.LONG){
				link.setOffset((long) field.getObj());
			}else if (field.getName().toLowerCase().equals("size") && field.getType() == TocFieldType.INTEGER){
				link.setSize((int) field.getObj());
			}
		}
		return link;
	}
}
