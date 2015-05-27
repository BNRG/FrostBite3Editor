package tk.captainsplexx.JavaFX;

import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Mod.Package;
import tk.captainsplexx.Resource.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class RightController {
	@FXML
	public TreeView<TreeViewEntry> ebxExplorer;

	public void saveEBX(){
		if (ebxExplorer.getRoot() != null){
			String resPath = "ebx/"+ebxExplorer.getRoot().getValue().getName()+".ebx";		
			String test = Main.getGame().getCurrentToc().getName();
			Package pack = Main.getModTools().getPackage(test);
			Main.getModTools().extendPackage(
					"bundles",
					Main.getGame().getCurrentSB().getPath(), 
					"EBX", 
					resPath,
					pack
			);
			
			//EBXFile ebxFile = TreeViewConverter.getEBXFile(ebxExplorer.getRoot());
			//byte[] ebxBytes = EBXConverter.createEBX(ebxFile);
			FileHandler.writeFile(Main.getGame().getCurrentMod().getPath()+"/resources/"+resPath, new byte[] {0x00}/*ebxBytes goes here!*/); //TODO
			
			
			//This will be moved over into main save.
			Main.getModTools().writePackages();
			
			
			System.err.println("TODO");
		}
	}
	
	public TreeView<TreeViewEntry> getEBXExplorer() {
		return ebxExplorer;
	}

	
	
}
