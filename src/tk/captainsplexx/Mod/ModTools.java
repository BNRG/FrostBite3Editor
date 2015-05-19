package tk.captainsplexx.Mod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Resource.FileHandler;

public class ModTools {
	public ArrayList<Mod> mods;

	public ModTools() {
		init();
	}
	void init(/*AKA. RESET*/){
		this.mods = new ArrayList<>();
		fetchMods();
		Main.getJavaFXHandler().getMainWindow().updateModsList();
	}
	
	public void fetchMods(){
		File modsDir = new File("mods/");
		for (File f : modsDir.listFiles()){
			if (f.isDirectory()){
				Mod mod = new Mod();
				mod.setPath(FileHandler.normalizePath(f.getAbsolutePath()));
				File info = new File(f.getAbsolutePath()+"\\info.txt");
				if (info.exists()){
					readInfo(mod, info);
				}
				if (mod.getAuthor() != null){
					mods.add(mod);
				}
			}
		}
	}
	
	public void readInfo(Mod mod, File info){
		try {
			FileReader fr = new FileReader(info);
	
		    @SuppressWarnings("resource")
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
		    
		}catch (Exception e){
			System.err.println("Could not read Info from Mod. "+info.getAbsolutePath());
		}
	}
	
	//TODO 7za.exe extraction of sources in temp folder and use for patching.
		
	//Getter n Setter
	public ArrayList<Mod> getMods() {
		return mods;
	}
}
