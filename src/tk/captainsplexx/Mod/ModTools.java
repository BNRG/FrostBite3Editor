package tk.captainsplexx.Mod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import antonsmirnov.javafx.dialog.Dialog;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;
import tk.captainsplexx.Resource.TOC.ResourceLink;
import tk.captainsplexx.Resource.TOC.TocConverter;
import tk.captainsplexx.Resource.TOC.TocCreator;
import tk.captainsplexx.Resource.TOC.TocFile;
import tk.captainsplexx.Resource.TOC.TocManager;
import tk.captainsplexx.Resource.TOC.TocSBLink;
import tk.captainsplexx.Resource.CAS.CasCatEntry;
import tk.captainsplexx.Resource.CAS.CasCatManager;
import tk.captainsplexx.Resource.CAS.CasManager;

public class ModTools {
	public ArrayList<Mod> mods;
	public ArrayList<Package> packages;

	public ModTools() {
		init();
	}
	public void init(/*AKA. RESET*/){
		this.mods = new ArrayList<>();
		this.packages = new ArrayList<>();
		fetchMods();
		Main.getJavaFXHandler().getMainWindow().updateModsList();
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
		ArrayList<File> files = FileHandler.listf(Main.getGame().getCurrentMod().getPath()+"/packages/", ".pack");
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
	
	Package readPackageInfo(File file){
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			Package pack = new Package(FileHandler.normalizePath(file.getAbsolutePath()).replace(Main.getGame().getCurrentMod().getPath()+"/packages", "").replace(".pack", ""));
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
		if (Main.getGame().getCurrentMod()==null){
			return false;
		}
		for (Package pack : packages){
			if (!writePackage(pack, new File(Main.getGame().getCurrentMod().getPath()+"/packages/"+pack.getName()+".pack"))){
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
	
	public boolean playMod(){
		String path = "test"; //Client.cloneClient(Main.gamePath+"/", Main.getGame().getCurrentMod().getGame()+"_"+Main.getGame().getCurrentMod().getFolderName());
		if (path!=null){
			String casCatPath = path+"/Update/Patch/Data/cas_99.cas";
			CasCatManager man = Main.getGame().getResourceHandler().getPatchedCasCatManager();
			CasManager.createCAS(casCatPath);
			Mod currentMod = Main.getGame().getCurrentMod();
			//TODO MOD CLIENT LOGIC!
			for (Package pack : packages){
				Main.getGame().setCurrentFile(FileHandler.normalizePath((Main.gamePath+"/"+pack.getName())));
				TocFile toc = TocManager.readToc(Main.getGame().getCurrentFile());
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
								byte[] data = FileHandler.readFile(currentMod.getPath()+"/resources/"+sortedEntry.getResourcePath());
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
					String newPath = ((String) Main.getGame().getCurrentFile()+".sb").replace(Main.gamePath, path);
					TocCreator.createModifiedSBFile(convToc, currentSBpart, false/*TODO*/, newPath, true);
				}
				byte[] tocBytes = TocCreator.createTocFile(convToc);
				FileHandler.writeFile(((String) Main.getGame().getCurrentFile()+".toc").replace(Main.gamePath, path), tocBytes);//TODO currently is uses temp data, but we can change this later on :)
				
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
			//Main.getJavaFXHandler().getDialogBuilder().showInfo("INFO", "Ready to Play!\nOrigin DRM Files needs to be replaced manually!");
			//Main.getJavaFXHandler().getMainWindow().toggleModLoaderVisibility();
			Main.keepAlive = false;
			return true;
		}
		Main.getJavaFXHandler().getDialogBuilder().showError("ERROR", "Something went wrong :(");
		return false;
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
				link.setSize(originalSize);//TODO test size ?? maybe procc size ??
				link.setOriginalSize(casCatEntry.getProcSize());//TODO test original size ??
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
		link.setCasPatchType(1);//Patching using data from update cas
		link.setSha1(casCatEntry.getSHA1().toLowerCase());
		link.setSize(originalSize);//TODO test size ?? maybe procc size ??
		link.setOriginalSize(casCatEntry.getProcSize());//TODO test original size ??
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
