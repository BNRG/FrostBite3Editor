package tk.captainsplexx.Mod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.CAS.CasCatEntry;
import tk.captainsplexx.Resource.CAS.CasCatManager;
import tk.captainsplexx.Resource.CAS.CasManager;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;
import tk.captainsplexx.Resource.TOC.ResourceLink;
import tk.captainsplexx.Resource.TOC.TocConverter;
import tk.captainsplexx.Resource.TOC.TocCreator;
import tk.captainsplexx.Resource.TOC.TocFile;
import tk.captainsplexx.Resource.TOC.TocManager;
import tk.captainsplexx.Resource.TOC.TocSBLink;

public class ModTools {
	public ArrayList<Mod> mods;
	public ArrayList<Package> packages;
	public static final String RESOURCEFOLDER = "/resources/";
	public static final String PACKAGEFOLDER = "/packages/";
	public static final String PACKTYPE = ".pack";

	public ModTools() {
		init();
	}
	public void init(/*AKA. RESET*/){
		this.mods = new ArrayList<>();
		this.packages = new ArrayList<>();
		fetchMods();
		Core.getJavaFXHandler().getMainWindow().updateModsList();
	}
	
	public void fetchMods(){
		File modsDir = new File("mods/");
		for (File f : modsDir.listFiles()){
			if (f.isDirectory()){
				Mod mod = new Mod();
				mod.setPath(FileHandler.normalizePath(f.getAbsolutePath()));
				String[] splitPath = mod.getPath().split("/");
				mod.setFolderName(splitPath[splitPath.length-1]);
				File info = new File(f.getAbsolutePath()+"\\info.txt");
				if (info.exists()){
					readModInfo(mod, info);
				}
				if (mod.getAuthor() != null){
					String[] split = Core.gamePath.split("/");
					int length = split.length;
					if (Core.gamePath.endsWith("/")){
						length--;
					}
					String destFolderPath = "";
					for (int i=0; i<length-1;i++){
						destFolderPath +=split[i]+"/";
					}
					destFolderPath += mod.getGame()+"_"+mod.getFolderName();
					mod.setDestFolderPath(destFolderPath);
					mods.add(mod);
				}
			}
		}
	}
	
	void readModInfo(Mod mod, File info){
		try {
			FileReader fr = new FileReader(info);
	
			BufferedReader br = new BufferedReader(fr);
		    mod.setName(br.readLine());
		    mod.setAuthor(br.readLine());
		    mod.setGame(br.readLine());
		    String line = "";
		    String text = "";
		    while ((line = br.readLine()) != null){
		    	text += line+"\n";
		    }
		    mod.setDesc(text);
		    br.close();
		    fr.close();
		    
		    
		}catch (Exception e){
			System.err.println("Could not read Info from Mod. "+info.getAbsolutePath());
		}
	}
	
	//TODO Extract sources from zip once. delete zip. sharing mod-> compress.
		
	
	
	public void fetchPackages(){
		int entries = 0;
		ArrayList<File> files = FileHandler.listf(Core.getGame().getCurrentMod().getPath()+PACKAGEFOLDER, ".pack");
		for (File f : files){
			if (!f.isDirectory()){
				Package pack = readPackageInfo(f);
				if (pack!=null){
					packages.add(pack);
					entries+=pack.getEntries().size();
				}
			}
		}
		System.out.println(packages.size()+" Packages where found in current mod with a total of "+entries+" entries.");
	}
	
	public Package readPackageInfo(File file){
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			Package pack = new Package(FileHandler.normalizePath(file.getAbsolutePath()).replace(Core.getGame().getCurrentMod().getPath()+"/packages", "").replace(PACKTYPE, ""));
			String line = "";
			while ((line = br.readLine()) != null){
			
					String[] parts = line.split("\\|");
					
					/*| is treated as an OR in RegEx. So you need to escape it:
					 * String[] separated = line.split("\\|");
					 */
					
					if (parts.length<4||parts.length>5){continue;}
					PackageEntry entry = new PackageEntry(
						LinkBundleType.valueOf(parts[0]),
						parts[1],
						ResourceType.valueOf(parts[2]),
						parts[3]
					);
					if (parts.length==5){//additional
						entry.setTargetPath(parts[4]);
					}
					pack.getEntries().add(entry);
			}
			br.close();
			fr.close();
			
			return pack;
		}catch (Exception e){
			System.err.println("Could not read Info from Package. "+file);
			return null;
		}
	}
	
	public boolean writePackage(Package pack, File file){
		if (file.exists()){
			file.delete();
		}
		ArrayList<String> entries = new ArrayList<>();
		for (PackageEntry entry : pack.getEntries()){
			String fullEntry = entry.getBundleType()+"|"+entry.getSubPackage()+"|"+entry.getResType()+"|"+entry.getResourcePath();
			if (entry.getTargetPath()!=null){
				fullEntry += "|"+entry.getTargetPath();
			}
			entries.add(fullEntry);
		}
		Collections.sort(entries);
		
		if (!FileHandler.writeLine(entries, file)){
			return false;
		}
		return true;
	}
	
	public boolean writePackages(){
		if (Core.getGame().getCurrentMod()==null){
			return false;
		}
		for (Package pack : packages){
			if (!writePackage(pack, new File(Core.getGame().getCurrentMod().getPath()+"/packages/"+pack.getName()+PACKTYPE))){
				return false;
			}
		}
		return false;
	}
	
	public Package getPackage(String name){
		for (Package pack : packages){
			if (pack.getName().equals(name)){
				return pack;
			}
		}
		Package pack = new Package(name);
		packages.add(pack);
		return null;
	}
	
	public boolean playMod(boolean recompile){
		if (recompile){
			String path = Client.cloneClient(Core.gamePath+"/", Core.getGame().getCurrentMod().getGame()+"_"+Core.getGame().getCurrentMod().getFolderName(), true);
			if (path!=null){
				System.out.println("Compile Client...");
				String casCatPath = path+"/Update/Patch/Data/cas_99.cas";
				CasCatManager man = Core.getGame().getResourceHandler().getPatchedCasCatManager();
				CasManager.createCAS(casCatPath);
				Mod currentMod = Core.getGame().getCurrentMod();
				//TODO MOD CLIENT LOGIC! multi subpackages dont work ;(
				for (Package pack : packages){
					Core.getGame().setCurrentFile(FileHandler.normalizePath((Core.gamePath+"/"+pack.getName())));
					TocFile toc = TocManager.readToc(Core.getGame().getCurrentFile());
					ConvertedTocFile convToc = TocConverter.convertTocFile(toc);
					
					//SORT
					HashMap<String, ArrayList<PackageEntry>> sorted = new HashMap<>();
					for (PackageEntry entry : pack.getEntries()){
						ArrayList<PackageEntry> subPackageEntries = sorted.get(entry.getSubPackage());
						if (subPackageEntries==null){
							subPackageEntries = new ArrayList<PackageEntry>();
							sorted.put(entry.getSubPackage(), subPackageEntries);
							entry.setSubPackage(null);//why not free up some memory here :)
						}
						subPackageEntries.add(entry);
					}
					
					//PROCC
					for (String subPackageName : sorted.keySet()){
						ConvertedSBpart currentSBpart = null;
						for (TocSBLink link : convToc.getBundles()){
							if (link.getID().equals(subPackageName)){
																				//link.setSbPath(sbPath); change the sb path once one subpackage is already done
								TocFile part = link.getLinkedSBPart();
								currentSBpart = TocConverter.convertSBpart(part);
								break;
							}
						}
						if (currentSBpart==null){
							System.err.println("Mod.ModTools.playMod can't handle new subpackages at this time. ");
							return false;
						}
						int originalSize = 0;
						ArrayList<PackageEntry> subPackageEntries = sorted.get(subPackageName);
						for (PackageEntry sortedEntry : subPackageEntries){
							CasCatEntry casCatEntry = null;
							switch(sortedEntry.getResType()){
								case ANIMTRACKDATA:
									break;
								case ANT:
									break;
								case CHUNK:
									break;
								case EBX:
									byte[] data = FileHandler.readFile(currentMod.getPath()+RESOURCEFOLDER+sortedEntry.getResourcePath());
									originalSize = data.length;
									casCatEntry = CasManager.extendCAS(data, new File(casCatPath), man);
									break;
								case ENLIGHTEN:
									break;
								case GFX:
									break;
								case HKDESTRUCTION:
									break;
								case HKNONDESTRUCTION:
									break;
								case ITEXTURE:
									break;
								case LIGHTINGSYSTEM:
									break;
								case LUAC:
									break;
								case MESH:
									break;
								case OCCLUSIONMESH:
									break;
								case PROBESET:
									break;
								case RAGDOLL:
									break;
								case SHADERDATERBASE:
									break;
								case SHADERDB:
									break;
								case SHADERPROGRAMDB:
									break;
								case STATICENLIGHTEN:
									break;
								case STREAIMINGSTUB:
									break;
								case UNDEFINED:
									break;
								default:
									break;
							}
							
							if (sortedEntry.getResType()==ResourceType.EBX){
								modifyResourceLink(sortedEntry, casCatEntry, originalSize, currentSBpart.getEbx());
							}else if (sortedEntry.getResType()==ResourceType.ITEXTURE||sortedEntry.getResType()==ResourceType.MESH){
								modifyResourceLink(sortedEntry, casCatEntry, originalSize, currentSBpart.getRes());
							}else{
								System.err.println(sortedEntry.getResType()+" isn't defined in (Mod.ModTools.playMod) for modifyResourceL1nk!");
							}
							man.getEntries().add(casCatEntry);
						}
						//TODO convToc.setTotalSize(totalSize);
						String newPath = ((String) Core.getGame().getCurrentFile()+".sb").replace(Core.gamePath, path);
						TocCreator.createModifiedSBFile(convToc, currentSBpart, false/*TODO*/, newPath, true/*delete first*/);
					}
					byte[] tocBytes = TocCreator.createTocFile(convToc);
					File newTocFile = new File(((String) Core.getGame().getCurrentFile()+".toc").replace(Core.gamePath, path));
					if (newTocFile.exists()){
						newTocFile.delete();//delete do remove hardlink.
					}
					FileHandler.writeFile(newTocFile.getAbsolutePath(), tocBytes);
					
				}
				//CREATE CAS.CAT
				byte[] patchedCasCatBytes = man.getCat();
				File casCatFile = new File(path+"/Update/Patch/Data/cas.cat");
				if (casCatFile.exists()){
					casCatFile.delete();
				}
				FileHandler.writeFile(casCatFile.getAbsolutePath(), patchedCasCatBytes);
				
				//DONE OPEN FOLDER!
				FileHandler.openFolder(path);
				//Core.getJavaFXHandler().getDialogBuilder().showInfo("INFO", "Ready to Play!\nOrigin DRM Files needs to be replaced manually!");
				//Core.getJavaFXHandler().getMainWindow().toggleModLoaderVisibility();
				Core.keepAlive = false;
				return true;
			}
			Core.getJavaFXHandler().getDialogBuilder().showError("ERROR", "Something went wrong :(");
			return false;
		}else{
			FileHandler.openFolder(Core.getGame().getCurrentMod().getDestFolderPath());
			Core.getJavaFXHandler().getDialogBuilder().showInfo("INFO", "Have fun =)");
			return false;
		}
	}
	
	public boolean modifyResourceLink(PackageEntry packEntry, CasCatEntry casCatEntry, int originalSize, ArrayList<ResourceLink> targetList){
	/*ALREADY EXIST*/
		for (ResourceLink link : targetList){
			String targetObject = packEntry.getTargetPath();//has a special targetPath defined, use this.
			if (targetObject==null){
				targetObject = packEntry.getResourcePath();//otherwise use the resourcePath as target.
			}
			if (link.getName().equals(targetObject.replace(".", "-").split("-")[0])){
				link.setCasPatchType(1);//Patching using data from update cas
				//link.setResType(resType);
				//link.setLogicalOffset(logicalOffset);
				link.setSha1(casCatEntry.getSHA1().toLowerCase());
				link.setBaseSha1(null);
				link.setDeltaSha1(null);
				link.setSize(casCatEntry.getProcSize());
				link.setOriginalSize(originalSize);
				return true;
			}
		}
		
	/*NEW ONE*/
		String targetObject = packEntry.getTargetPath();//has a special targetPath defined, use this.
		if (targetObject==null){
			targetObject = packEntry.getResourcePath();//otherwise use the resourcePath as target.
		}
		ResourceLink link = new ResourceLink();
		link.setName(targetObject.replace(".", "-").split("-")[0]);
		link.setType(packEntry.getResType());
		//link.setResType(resType);
		//link.setLogicalOffset(logicalOffset);
		link.setBaseSha1(null);
		link.setDeltaSha1(null);
		link.setCasPatchType(1);//Patching using data from update cas
		link.setSha1(casCatEntry.getSHA1().toLowerCase());
		link.setSize(casCatEntry.getProcSize());
		link.setOriginalSize(originalSize);
		targetList.add(link);
		return false;
	}
	
	
	public boolean extendPackage(LinkBundleType bundle, String sbPart, ResourceType type, String path, Package pack){
		return extendPackage(bundle, sbPart, type, path, null, pack);
	}
	
	public boolean extendPackage(LinkBundleType bundle, String sbPart, ResourceType type, String path, String targetPath, Package pack){
		PackageEntry entry = new PackageEntry(bundle, sbPart, type, path);
		if (targetPath!=null){
			/*Additional, if someone whats to have a diffrent resources for a package with the same path.*/
			entry.setTargetPath(targetPath);
		}
		for (PackageEntry pEntry: pack.getEntries()){
			if (pEntry.getBundleType()==bundle && pEntry.getSubPackage()==sbPart &&
					pEntry.getResourcePath()==path && pEntry.getResType()==type &&
						pEntry.getTargetPath()==targetPath){
				//entry does already exist.
				return true;
			}
		}
		pack.getEntries().add(entry);
		return true;
	}
	
	//i dont really have to create a function for removing one line from a ".pack" file? huh.
	
	//Getter n Setter
	public ArrayList<Mod> getMods() {
		return mods;
	}
	public ArrayList<Package> getPackages() {
		return packages;
	}
	
}
