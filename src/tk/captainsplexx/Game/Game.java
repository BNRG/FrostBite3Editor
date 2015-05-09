package tk.captainsplexx.Game;

import java.io.File;
import java.util.HashMap;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Maths.Patcher;
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
	
	public int MIP_MAP_LEVEL = 0;
	//Value between 0 and 7//
			
	public Game(){
		modelHandler = new ModelHandler();
		
		playerHandler = new PlayerHandler();
		
		/*DO NOT CARE ABOUT IN THE MOMENT*/
		terrainHandler = new TerrainHandler();
		terrainHandler.generate(0, 0);
		terrainHandler.generate(0, 0);
				
		resourceHandler = new ResourceHandler();
		
		shaderHandler = new ShaderHandler();
		entityHandler = new EntityHandler(modelHandler, resourceHandler);
		
		/*TEST FOR PATCHING BASEDATA USING DELTA*/
		byte[] patchedData = Patcher.getPatchedData(
				FileHandler.readFile("__DOCUMENTATION__/patch_system/decompressed_base"),
				FileHandler.readFile("__DOCUMENTATION__/patch_system/delta")
		);
		FileHandler.writeFile("output/patched_data", patchedData);
		/*END OF TEST*/
		
		/*DDS Conv. test*/
		byte[] splexxDDStest = FileHandler.readFile("res/notFound.dds");
		
		File tga = DDSConverter.storeDDSasTGA(splexxDDStest, "splexxDDStest");
		if (tga != null){
			System.out.println("TGA Success: "+tga.getAbsolutePath());
		}
		/*END OF DDS TEST*/
		
		System.out.println("Please select a game root directory like this one: 'C:/Program Files (x86)/Origin Games/Battlefield Hardline Digital Deluxe'!");
		Main.getJavaFXHandler().getMainWindow().selectGamePath();

		while (true){
			//wait
			System.out.print(""); //Why this has to be here ? Otherwise not working :(
			if (!(Main.gamePath == null)){
				break;
			}
		}
		System.out.println("Building up FrostBite Editor!");
		buildEditor();
	}
	
	
	public void buildEditor(){
		resourceHandler.getCasCatManager().readCat(FileHandler.readFile(Main.gamePath+"/Data/cas.cat"));
		ebxFileGUIDs = new HashMap<String, String>();
		chunkGUIDSHA1 = new HashMap<String, String>();
		buildExplorerTree();
		
		//byte[] data = CasDataReader.readCas("A2C97156565E4C3A9B71F900B37C61EA3D5CE66B", Main.gamePath+"/Data", getResourceHandler().getCasCatManager().getEntries());
		//FileHandler.writeFile("output/decompressed_ebx", data);
		
		
		/*
		byte[] data = CasDataReader.readCas("76 06 C5 5F F0 95 B8 53 9A C6 A5 FC 60 0A E3 25 3D 09 5F 85", gamePath+"/Data", resourceHandler.getCasCatManager().getEntries());
		EbxCasConverter conv = new EbxCasConverter();
		conv.createCAS(data);
		*/
	}

	
	public void update(){
		playerHandler.update();	
		terrainHandler.collisionUpdate(playerHandler);
	}
	
	public void buildExplorerTree(){
		currentToc = null;
		currentSB = null;

		TreeItem<TreeViewEntry> explorerTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(Main.gamePath+"/Update/Patch/Data/", null, null, EntryType.LIST));
		for (File file : FileHandler.listf(Main.gamePath+"/Update/Patch/Data/", ".sb")){
			String relPath = file.getAbsolutePath().replace("\\", "/").replace(".sb", "").replace(Main.gamePath+"/Update/Patch/Data/", "");
			String[] fileName = relPath.split("/");
			TreeItem<TreeViewEntry> convTocTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(fileName[fileName.length-1], new ImageView(JavaFXHandler.documentIcon), file, EntryType.LIST)); 
			TreeViewConverter.pathToTree(explorerTree, relPath, convTocTree);
		}
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
	
	
	
	
	/*End of Game*/
	
	
	
	
	
}
