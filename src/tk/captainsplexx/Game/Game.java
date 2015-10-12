package tk.captainsplexx.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import tk.captainsplexx.Entity.EntityHandler;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.JavaFX.TreeViewConverter;
import tk.captainsplexx.JavaFX.TreeViewEntry;
import tk.captainsplexx.JavaFX.Windows.MainWindow.EntryType;
import tk.captainsplexx.Mod.Mod;
import tk.captainsplexx.Model.ModelHandler;
import tk.captainsplexx.Player.PlayerEntity;
import tk.captainsplexx.Player.PlayerHandler;
import tk.captainsplexx.Render.Gui.GuiTexture;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Resource.ITEXTURE.ITextureHandler;
import tk.captainsplexx.Resource.TOC.ConvertedSBpart;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;
import tk.captainsplexx.Resource.TOC.TocConverter;
import tk.captainsplexx.Resource.TOC.TocFile;
import tk.captainsplexx.Resource.TOC.TocManager;
import tk.captainsplexx.Shader.ShaderHandler;
import tk.captainsplexx.Terrain.TerrainHandler;

public class Game {
	public ModelHandler modelHandler;
	public TerrainHandler terrainHandler;
	public PlayerHandler playerHandler;
	public ResourceHandler resourceHandler;
	public EntityHandler entityHandler;
	public ShaderHandler shaderHandler;
	public ITextureHandler itextureHandler;
	public String currentFile;
	public ConvertedTocFile currentToc;
	public ConvertedSBpart currentSB;
	//public HashMap<String, String> ebxFileGUIDs /*GUID, FileName*/;;
	public HashMap<String, String> chunkGUIDSHA1;
	public Mod currentMod;
	public ArrayList<GuiTexture> guis;
	
	public ArrayList<ConvertedTocFile> commonChunks;
				
	public Game(){
		currentMod = null;
		/*LEVEL OF DETAIL
		 * 0=100%
		 * 1=50%
		 * 2=25%
		 * 3=12.5%
		 * ....
		 * MAX 9!
		 */
		
		modelHandler = new ModelHandler();
		
		playerHandler = new PlayerHandler();
		
		/*DO NOT CARE ABOUT IN THE MOMENT*/
		terrainHandler = new TerrainHandler();
		terrainHandler.generate(0, 0);//defined from terrain4k.decals -> terrain subpackage
		//terrainHandler.generate(0, 1);
				
		resourceHandler = new ResourceHandler();
		
		shaderHandler = new ShaderHandler();
		entityHandler = new EntityHandler(modelHandler, resourceHandler);
		
		guis = new ArrayList<>();
		
		if (!Core.isDEBUG){
			System.out.println("Please select a game root directory like this one: 'C:/Program Files (x86)/Origin Games/Battlefield 4'!");
			Core.getJavaFXHandler().getMainWindow().selectGamePath();
		}else{
			
			/*TEST FOR PATCHING BASEDATA USING DELTA
			byte[] patchedData = Patcher.getPatchedData(
					FileHandler.readFile("__DOCUMENTATION__/patch_system/decompressed_base"),
					FileHandler.readFile("__DOCUMENTATION__/patch_system/delta")
			);
			FileHandler.writeFile("output/patched_data", patchedData);
			END OF TEST*/
		}
		
		while (true){
			//wait
			System.out.print("");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (Core.gamePath != null && Core.getJavaFXHandler().getMainWindow().getModLoaderWindow().getController() != null){
				break;
			}
		}
		Core.getJavaFXHandler().getMainWindow().getModLoaderWindow().getController().setGamepath(FileHandler.normalizePath(Core.gamePath));
		Core.getJavaFXHandler().getMainWindow().toggleModLoaderVisibility();
		File cascat = new File(Core.gamePath+"/Data/cas.cat");
		if (!cascat.exists()){
			System.err.println("Invalid gamepath selected.");
			Core.keepAlive(false);
		}else{
			System.out.println("Building up FrostBite Editor!");
			buildEditor();
			
			if (Core.isDEBUG){//EBX-DEBUG
				Core.getJavaFXHandler().getMainWindow().toggleModLoaderVisibility();
				currentMod = null;
				//ebxFileGUIDs = new HashMap<>();
				//ebxFileGUIDs.put("EA830D5EFFB3EE489D44963370D466B1", "test/test1/test2");
				/*byte[] bytes = FileHandler.readFile("__DOCUMENTATION__/ebx/sample_ebx/layer0_default.ebx");
				byte[] bytes = FileHandler.readFile("mods/SampleMod/resources/levels/mp/mp_playground/content/layer2_buildings.bak--IGNORE");
				EBXFile ebxFile = resourceHandler.getEBXHandler().loadFile(bytes);
				TreeItem<TreeViewEntry> treeView = TreeViewConverter.getTreeView(ebxFile);
				Core.getJavaFXHandler().setTreeViewStructureRight(treeView);
				Core.getJavaFXHandler().getMainWindow().updateRightRoot();*/
			}
		}
	}
	
	
	public void buildEditor(){
		resourceHandler.getCasCatManager().readCat(FileHandler.readFile(Core.gamePath+"/Data/cas.cat"), "normal");
		File patchedCasCat = new File(Core.gamePath+"/Update/Patch/Data/cas.cat");
		if (patchedCasCat.exists()){
			resourceHandler.getPatchedCasCatManager().readCat(FileHandler.readFile(patchedCasCat.getAbsolutePath()), "patched");
		}
		//ebxFileGUIDs = new HashMap<String, String>();
		chunkGUIDSHA1 = new HashMap<String, String>();
		
		/*Use this to fetch common chunks!*/
		commonChunks = new ArrayList<ConvertedTocFile>();
		for (File file : FileHandler.listf(Core.gamePath+"/", "Chunks")){
			if (file.getAbsolutePath().endsWith(".toc")){
				String relPath = file.getAbsolutePath().replace("\\", "/").replace(".toc", "");
				TocFile toc = TocManager.readToc(relPath);
				ConvertedTocFile convToc = TocConverter.convertTocFile(toc);
				commonChunks.add(convToc);
			}
		}
		/*End of common chunks!*/
	}

	
	public void update(){
		playerHandler.update();	
		terrainHandler.collisionUpdate(playerHandler);
	}
	
	public void lowRateUpdate(){
		PlayerEntity pe = playerHandler.getPlayerEntity();
		entityHandler.getFocussedEntity(pe.getPos(), pe.getRot());
	}
	
	public void buildExplorerTree(){		
		currentToc = null;
		currentSB = null;

		TreeItem<TreeViewEntry> explorerTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(Core.gamePath, null, null, EntryType.LIST));
		for (File file : FileHandler.listf(Core.gamePath+"/Data/", ".sb")){
			File patched = new File(file.getAbsolutePath().replace("\\", "/").replace(Core.gamePath, Core.gamePath+"/Update/Patch"));
			if (!patched.exists()){
				String relPath = file.getAbsolutePath().replace("\\", "/").replace(".sb", "").replace(Core.gamePath+"/", "");
				//System.out.println("NOPATCH "+relPath);
				String[] fileName = relPath.split("/");
				TreeItem<TreeViewEntry> convTocTree = new TreeItem<TreeViewEntry>(new TreeViewEntry(fileName[fileName.length-1], new ImageView(JavaFXHandler.documentIcon), file, EntryType.LIST)); 
				TreeViewConverter.pathToTree(explorerTree, relPath, convTocTree);
			}else{
				String relPath = patched.getAbsolutePath().replace("\\", "/").replace(".sb", "").replace(Core.gamePath+"/", "");
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
		Core.getJavaFXHandler().setTreeViewStructureLeft(explorerTree);
		Core.getJavaFXHandler().getMainWindow().updateLeftRoot();
		
		Core.getJavaFXHandler().setTreeViewStructureLeft1(null);
		Core.getJavaFXHandler().getMainWindow().updateLeftRoot1();
		
		/*
		Core.getJavaFXHandler().setTreeViewStructureRight(null);
		Core.getJavaFXHandler().getMainWindow().updateRightRoot();
		
		*/
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

	public ITextureHandler getITextureHandler() {
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


	/*public HashMap<String, String> getEBXFileGUIDs() {
		return ebxFileGUIDs;
	}*/


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


	public ArrayList<ConvertedTocFile> getCommonChunks() {
		return commonChunks;
	}


	public ArrayList<GuiTexture> getGuis() {
		return guis;
	}

	
	
	
	/*End of Game*/
	
	
	
	
	
}
