package tk.captainsplexx.Mod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Resource.FileHandler;

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
				if (line.equals("")){continue;}
				pack.getEntries().add(line);
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
		Collections.sort(pack.getEntries());

		if (!FileHandler.writeLine(pack.getEntries(), file)){
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
	
	public Package getPackage(String name){//TODO
		for (Package pack : packages){
			if (pack.getName().equals(name)){
				return pack;
			}
		}
		Package pack = new Package(name);
		packages.add(pack);
		return null;
	}
	
	public boolean extendPackage(String bundle, String sbPart, String type, String path, Package pack){
		return extendPackage(bundle, sbPart, type, path, null, pack);
	}
	
	public boolean extendPackage(String bundle, String sbPart, String type, String path, String targetPath, Package pack){
		String fullEntry = bundle+"|"+sbPart+"|"+type+"|"+path;
		if (targetPath!=null){
			/*Additional, if someone whats to have a diffrent resources for a package with the same path.*/
			fullEntry+="|"+targetPath;
		}
		for (String str : pack.getEntries()){
			if (str.equals(fullEntry)){
				return true;
			}
		}
		pack.getEntries().add(fullEntry);
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
