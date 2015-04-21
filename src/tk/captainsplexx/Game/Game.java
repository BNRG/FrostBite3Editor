package tk.captainsplexx.Game;

import java.io.File;
import java.util.HashMap;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import tk.captainsplexx.Render.ModelHandler;
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
	public String gamePath;
	public String currentFile;
	public ConvertedTocFile currentToc;
	public ConvertedSBpart currentSB;
	public HashMap<String, String> ebxFileGUIDs;
			
	public Game(){
		gamePath = "C:/Program Files (x86)/Origin Games/Battlefield 4";
		//gamePath = "D:/Battlefield Hardline Digital Deluxe";
		
		
		modelHandler = new ModelHandler();
		
		playerHandler = new PlayerHandler();
		
		/*DO NOT CARE ABOUT IN THE MOMENT*/
		terrainHandler = new TerrainHandler();
		//terrainHandler.generate(0, 0);
		//terrainHandler.generate(0, 0);
				
		resourceHandler = new ResourceHandler("res/externalFileGUIDs"); //<--not needed in future
		
		shaderHandler = new ShaderHandler();
		entityHandler = new EntityHandler(modelHandler, resourceHandler);
		
		
		resourceHandler.getCasCatManager().readCat(FileHandler.readFile(gamePath+"/Data/cas.cat"));
		ebxFileGUIDs = new HashMap<String, String>();
		buildExplorerTree();
		
		
		
		//7D 28 E8 12 01 C3 03 97 FB 99 FF 3B BA 3A 75 1A E6 C0 4E 02 mesh
		
		
		/*
		byte[] data = CasDataReader.readCas("76 06 C5 5F F0 95 B8 53 9A C6 A5 FC 60 0A E3 25 3D 09 5F 85", gamePath+"/Data", resourceHandler.getCasCatManager().getEntries());
		TreeItem<TreeViewEntry> testebx = TreeViewConverter.getTreeView(resourceHandler.getEBXHandler().loadFile(data));
		Main.getJavaFXHandler().setTreeViewStructureRight(testebx);
		Main.getJavaFXHandler().getMainWindow().updateRightRoot();
		*/
		//EbxCasConverter conv = new EbxCasConverter();
		//conv.createCAS(data);
		
		/*guid to sha1 -> sb converter first*/
		//byte[] ddsTexture = ItextureHandler.getDSS(FileHandler.readFile("D:/dump_bf4_fs/bundles_more_info/res/objects/architecture/apartmentbuilding_modules/t_apartmentbuilding_modules_04_d 2495893419b2e1d1 0b000000030000000000000000000000.itexture"), gamePath+"/Data", cCatManager.getEntries());
		
		
		
		
		/*Main.getEventHander().addEvent(new Event(1, -1, new Runnable() {
		void run() {
			Entity e = entityHandler.getEntities().get(0);
			e.setRotation(new Vector3f(0.0f,e.getRotation().getY()+0.25f,0.0f));
		}
		})); */
	}

	
	public void update(){
		playerHandler.update();	
		terrainHandler.collisionUpdate(playerHandler);
	}
	
	public void buildExplorerTree(){
		currentToc = null;
		currentSB = null;
		
		/*Build up Explorer*/
		TreeItem<TreeViewEntry> explorerTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(gamePath+"/Data/", null, null, EntryType.LIST));
		for (File file : FileHandler.listf(gamePath+"/Data/", ".sb")){
			String relPath = file.getAbsolutePath().replace("\\", "/").replace(".sb", "").replace(gamePath+"/Data/", "");
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
	public String getGamePath() {
		return gamePath;
	}


	public void setGamePath(String gamePath) {
		this.gamePath = gamePath;
	}


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
	
	
	
	/*End of Game*/
	
	
	
	
	
}
