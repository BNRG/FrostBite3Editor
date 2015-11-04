package tk.captainsplexx.JavaFX.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.Windows.EBXWindow;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.EBX.EBXFile;

public class EBXWindowController {
	@FXML
	private TreeView<TreeViewEntry> ebxExplorer;
	
	private EBXWindow window;
	private Stage stage;
	
	
	public void createLayer(){
		Core.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				Core.getGame().getEntityHandler().createEntityLayer(window.getEBXFile());
				System.err.println("--------------Layer creation done!!------------------");
			}
		});
	}
	
	public void close(){
		Core.getJavaFXHandler().getMainWindow().destroyEBXWindow(stage);
	}
	
	public void saveEBX(){
		if (ebxExplorer.getRoot() != null){
			if (Core.getGame().getCurrentMod()!=null&&!Core.isDEBUG){
				
				//EBXFile ebxFile = window.getEBXFile();
				
				/*
				String resPath = ebxExplorer.getRoot().getValue().getName()+".ebx";		
				
				String test = Core.getGame().getCurrentToc().getName();
				Package pack = Core.getModTools().getPackage(test);
				Core.getModTools().extendPackage(
						LinkBundleType.BUNDLES,
						Core.getGame().getCurrentSB().getPath(), 
						ResourceType.EBX,
						resPath,
						pack
				);
				
				//EBXFile ebxFile = TreeViewConverter.getEBXFile(ebxExplorer.getRoot());
				//byte[] ebxBytes = EBXConverter.createEBX(ebxFile);
				FileHandler.writeFile(Core.getGame().getCurrentMod().getPath()+ModTools.RESOURCEFOLDER+resPath, new byte[] {0x00}/*ebxBytes goes here!); //TODO
				
				
				//This will be moved over into main save.
				Core.getModTools().writePackages();
				*/
				
				System.err.println("TODO");
			}else{
				//DEBUG--
				EBXFile ebxFile = TreeViewConverter.getEBXFile(ebxExplorer.getRoot());
				System.err.println("TODO");
				byte[] ebxBytes = Core.getGame().getResourceHandler().getEBXHandler().createEBX(ebxFile);
				FileHandler.writeFile("output/DEBUG.ebx", ebxBytes);
				
				//TEST 2
				EBXFile orig = Core.getGame().getResourceHandler().getEBXHandler().loadFile(FileHandler.readFile("mods/SampleMod/resources/levels/mp/mp_playground/content/layer2_buildings.bak--IGNORE"));
				byte[] origBytes = Core.getGame().getResourceHandler().getEBXHandler().createEBX(orig);
				FileHandler.writeFile("output/ORIG_DEBUG.ebx", origBytes);
				
			}
		}
	}
	
	public TreeView<TreeViewEntry> getEBXExplorer() {
		return ebxExplorer;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public void setWindow(EBXWindow window) {
		this.window = window;
	}
	
	

	
	
	
	
}
