package tk.captainsplexx.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Mod.Package;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.EBX.EBXFile;

public class RightController {
	@FXML
	public TreeView<TreeViewEntry> ebxExplorer;

	public void saveEBX(){
		if (ebxExplorer.getRoot() != null){
			if (Main.getGame().getCurrentMod()!=null&&!Main.isDEBUG){
				String resPath = ebxExplorer.getRoot().getValue().getName()+".ebx";		
				String test = Main.getGame().getCurrentToc().getName();
				Package pack = Main.getModTools().getPackage(test);
				Main.getModTools().extendPackage(
						LinkBundleType.BUNDLES,
						Main.getGame().getCurrentSB().getPath(), 
						ResourceType.EBX,
						resPath,
						pack
				);
				
				//EBXFile ebxFile = TreeViewConverter.getEBXFile(ebxExplorer.getRoot());
				//byte[] ebxBytes = EBXConverter.createEBX(ebxFile);
				FileHandler.writeFile(Main.getGame().getCurrentMod().getPath()+"/resources/"+resPath, new byte[] {0x00}/*ebxBytes goes here!*/); //TODO
				
				
				//This will be moved over into main save.
				Main.getModTools().writePackages();
				
				
				System.err.println("TODO");
			}else{
				//DEBUG--
				EBXFile ebxFile = TreeViewConverter.getEBXFile(ebxExplorer.getRoot());
				System.err.println("TODO");
				byte[] ebxBytes = Main.getGame().getResourceHandler().getEBXHandler().createEBX(ebxFile);
				FileHandler.writeFile("output/DEBUG.ebx", ebxBytes);
				
				//TEST 2
				EBXFile orig = Main.getGame().getResourceHandler().getEBXHandler().loadFile(FileHandler.readFile("__DOCUMENTATION__/ebx/sample_ebx/layer0_default.ebx"));
				byte[] origBytes = Main.getGame().getResourceHandler().getEBXHandler().createEBX(orig);
				FileHandler.writeFile("output/ORIG_DEBUG.ebx", origBytes);
				
			}
		}
	}
	
	public TreeView<TreeViewEntry> getEBXExplorer() {
		return ebxExplorer;
	}

	
	
}
