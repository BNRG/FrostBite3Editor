package tk.captainsplexx.Game;

import java.io.File;
import java.util.HashMap;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.JavaFXMainWindow;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Mod.Mod;
import tk.captainsplexx.Render.ModelHandler;
import tk.captainsplexx.Resource.DDSConverter;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Resource.ITEXTURE.ItextureHandler;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;

public class Game {
	public ModelHandler modelHandler;
	public TerrainHandler terrainHandler;
	public PlayerHandler playerHandler;
	public ResourceHandler resourceHandler;
	public EntityHandler entityHandler;
	public ShaderHandler shaderHandler;
	public ItextureHandler itextureHandler;
	public String currentFile;
	public ConvertedTocFile currentToc;
	public ConvertedSBpart currentSB;
	public HashMap<String, String> ebxFileGUIDs;
	public HashMap<String, String> chunkGUIDSHA1;
	public Mod currentMod;
				
	public Game(){
		currentMod = null;
		/*LEVEL OF DETAIL
		 * 0=100%
		 * 1=50%
		 * 2=25%
		 * 3=12.5%
		 * ....
		 */
		DDSConverter.MIP_MAP_LEVEL = 0;
		
		modelHandler = new ModelHandler();
		
		playerHandler = new PlayerHandler();
		
		/*DO NOT CARE ABOUT IN THE MOMENT*/
		terrainHandler = new TerrainHandler();
		terrainHandler.generate(0, 0);
		terrainHandler.generate(0, 0);
				
		resourceHandler = new ResourceHandler();
		
		shaderHandler = new ShaderHandler();
		entityHandler = new EntityHandler(modelHandler, resourceHandler);
		
		/*
		TEST FOR PATCHING BASEDATA USING DELTA
		byte[] patchedData = Patcher.getPatchedData(
				FileHandler.readFile("__DOCUMENTATION__/patch_system/decompressed_base"),
				FileHandler.readFile("__DOCUMENTATION__/patch_system/delta")
		);
		FileHandler.writeFile("output/patched_data", patchedData);
		END OF TEST*/
				
		//Client.cloneClient("C:/Program Files (x86)/Origin Games/Battlefield 4/", "Battlefield 4 SPLEXX");
		
		System.out.println("Please select a game root directory like this one: 'C:/Program Files (x86)/Origin Games/Battlefield Hardline Digital Deluxe'!");
		Main.getJavaFXHandler().getMainWindow().selectGamePath();

		while (true){
			//wait
			System.out.print("");
			if (!(Main.gamePath == null)){
				break;
			}
		}
		JavaFXMainWindow mainWindow = Main.getJavaFXHandler().getMainWindow();
		mainWindow.getModLoaderController().setGamepath(FileHandler.normalizePath(Main.gamePath));
		mainWindow.toggleModLoaderVisibility();
		File cascat = new File(Main.gamePath+"/Data/cas.cat");
		if (!cascat.exists()){
			System.err.println("Invalid gamepath selected.");
			System.exit(0);
		}
		System.out.println("Building up FrostBite Editor!");
		buildEditor();
	}
	
	
	public void buildEditor(){
		resourceHandler.getCasCatManager().readCat(FileHandler.readFile(Main.gamePath+"/Data/cas.cat"), "normal");
		File patchedCasCat = new File(Main.gamePath+"/Update/Patch/Data/cas.cat");
		if (patchedCasCat.exists()){
			resourceHandler.getPatchedCasCatManager().readCat(FileHandler.readFile(patchedCasCat.getAbsolutePath()), "patched");
		}
		ebxFileGUIDs = new HashMap<String, String>();
		chunkGUIDSHA1 = new HashMap<String, String>();
				
		/*
		CasManager.createCAS("output/cas_99.cas");
		String sha1_1 = CasManager.extendCAS(FileHandler.readFile("res/filler.hex"), new File("output/cas_99.cas"), resourceHandler.getPatchedCasCatManager());
		String sha1_2 = CasManager.extendCAS(FileHandler.readFile("res/filler.hex"), new File("output/cas_99.cas"), resourceHandler.getPatchedCasCatManager());
		*/
	}

	
	public void update(){
		playerHandler.update();	
		terrainHandler.collisionUpdate(playerHandler);
	}
	
	public void buildExplorerTree(){
		currentToc = null;
		currentSB = null;

		TreeItem<TreeViewEntry> explorerTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(Main.gamePath, null, null, EntryType.LIST));
		for (File file : FileHandler.listf(Main.gamePath+"/Data/", ".sb")){
			File patched = new File(file.getAbsolutePath().replace("\\", "/").replace(Main.gamePath, Main.gamePath+"/Update/Patch"));
			if (!patched.exists()){
				String relPath = file.getAbsolutePath().replace("\\", "/").replace(".sb", "").replace(Main.gamePath+"/", "");
				//System.out.println("NOPATCH "+relPath);
				String[] fileName = relPath.split("/");
				TreeItem<TreeViewEntry> convTocTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(fileName[fileName.length-1], new ImageView(JavaFXHandler.documentIcon), file, EntryType.LIST)); 
				TreeViewConverter.pathToTree(explorerTree, relPath, convTocTree);
			}else{
				String relPath = patched.getAbsolutePath().replace("\\", "/").replace(".sb", "").replace(Main.gamePath+"/", "");
				//System.out.println("PATCH "+relPath);
				String[] fileName = relPath.split("/");
				TreeItem<TreeViewEntry> convTocTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(fileName[fileName.length-1], new ImageView(JavaFXHandler.documentIcon), patched, EntryType.LIST)); 
				TreeViewConverter.pathToTree(explorerTree, relPath, convTocTree);
			}
		}
		explorerTree.getValue();
		for (TreeItem<TreeViewEntry> child : explorerTree.getChildren()){
			if (child.getChildren().size()>0){
				child.setExpanded(true);
			}
		}
		Main.getJavaFXHandler().setTreeViewStructureLeft(explorerTree);
		Main.getJavaFXHandler().getMainWindow().updateLeftRoot();
		
		Main.getJavaFXHandler().setTreeViewStructureLeft1(null);
		Main.getJavaFXHandler().getMainWindow().updateLeftRoot1();
		
		Main.getJavaFXHandler().setTreeViewStructureRight(null);
		Main.getJavaFXHandler().getMainWindow().updateRightRoot();
	}
	
	
	//<----------GETTER AND SETTER--------------->//
	
	/*Handler*/
	public ResourceHandler getResourceHandler() {
		return resourceHandler;
	}
		
	public PlayerHandler getPlayerHandler(){
		return playerHandler;
	}

	public ModelHandler getModelHandler() {
		return modelHandler;
	}

	public TerrainHandler getTerrainHandler() {
		return terrainHandler;
	}

	public EntityHandler getEntityHandler() {
		return entityHandler;
	}

	public ShaderHandler getShaderHandler() {
		return shaderHandler;
	}

	public ItextureHandler getItextureHandler() {
		return itextureHandler;
	}
	/*End of Handler*/
	
	
	/*Game*/

	public String getCurrentFile() {
		return currentFile;
	}


	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
	}


	public HashMap<String, String> getEBXFileGUIDs() {
		return ebxFileGUIDs;
	}


	public ConvertedTocFile getCurrentToc() {
		return currentToc;
	}


	public void setCurrentToc(ConvertedTocFile currentToc) {
		this.currentToc = currentToc;
	}


	public ConvertedSBpart getCurrentSB() {
		return currentSB;
	}


	public void setCurrentSB(ConvertedSBpart currentSB) {
		this.currentSB = currentSB;
	}


	public HashMap<String, String> getChunkGUIDSHA1() {
		return chunkGUIDSHA1;
	}


	public Mod getCurrentMod() {
		return currentMod;
	}


	public void setCurrentMod(Mod currentMod) {
		this.currentMod = currentMod;
	}


	
	
	
	/*End of Game*/
	
	
	
	
	
}
