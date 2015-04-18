package tk.captainsplexx.Game;


import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.Render.ModelHandler;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Resource.ITEXTURE.ItextureHandler;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;
import tk.captainsplexx.Resource.TOC.TocConverter;
import tk.captainsplexx.Resource.TOC.TocFile;
import tk.captainsplexx.Resource.TOC.TocManager;

public class Game {
	public ModelHandler modelHandler;
	public TerrainHandler terrainHandler;
	public PlayerHandler playerHandler;
	public ResourceHandler resourceHandler;
	public EntityHandler entityHandler;
	public ShaderHandler shaderHandler;
	public ItextureHandler itextureHandler;
	public String gamePath;
	public String gamePlatform;
	
	public String currentTocPath;
	
		
	public Game(){
		gamePath = "C:/Program Files (x86)/Origin Games/Battlefield 4";
		gamePlatform = "Win32";
		currentTocPath = "";
		
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
		
		currentTocPath = gamePath+"/Data/"+gamePlatform+"/Levels/MP/MP_Prison/MP_Prison";
		//currentTocPath = gamePath+"/Data/"+gamePlatform+"/WeaponChunks";
		TocFile toc = TocManager.readToc(FileHandler.readFile(currentTocPath+".toc"));
		//TocFile toc2 = TocManager.readToc(FileHandler.readFile(gamePath+"/Data/"+gamePlatform+"/WeaponChunks.toc"));
		
		//TocFile sb = TocManager.readSbPart(FileHandler.readFile(currentTocPath+".sb", 0x12, 0x3E8)); 
		//Data is now comming from Converted TOC! (DEBUG ONLY)
		
		ConvertedTocFile convToc = TocConverter.convertTocFile(toc);
		//ConvertedTocFile convToc2 = TocConverter.convertTocFile(toc2);
		TreeItem<TreeViewEntry> convTocTree = TreeViewConverter.getTreeView(convToc);
		//convTocTree.getChildren().add(TreeViewConverter.getTreeView(convToc2));
		
		//TreeItem<TreeViewEntry> test = TreeViewConverter.getTreeView(toc);
		
		//TocFile newToc = TreeViewConverter.getTocFile(test, TocFileType.Sig);
		//TocFile newSb = TreeViewConverter.getTocFile(test2, TocFileType.SbPart);
		Main.getJavaFXHandler().setTreeViewStructureLeft(convTocTree);
		Main.getJavaFXHandler().getMainWindow().updateLeftRoot();
		
		
		
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
	
	public void listf(String directoryName, ArrayList<File> files, String endsWith) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	        	if (file.getName().endsWith(endsWith)){
	        		files.add(file);
	        	}
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files, endsWith);
	        }
	    }
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


	public String getGamePlatform() {
		return gamePlatform;
	}


	public void setGamePlatform(String gamePlatform) {
		this.gamePlatform = gamePlatform;
	}


	public String getCurrentTocPath() {
		return currentTocPath;
	}


	public void setCurrentTocPath(String currentTocPath) {
		this.currentTocPath = currentTocPath;
	}
	
	
	
	/*End of Game*/
	
	
	
	
	
}
