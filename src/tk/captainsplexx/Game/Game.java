package tk.captainsplexx.Game;


import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.CAS.CasCatManager;
import tk.captainsplexx.CAS.CasDataReader;
import tk.captainsplexx.CAS.EbxCasConverter;
import tk.captainsplexx.EBX.EBXArrayRepeater;
import tk.captainsplexx.EBX.EBXComplex;
import tk.captainsplexx.EBX.EBXComplexDescriptor;
import tk.captainsplexx.EBX.EBXField;
import tk.captainsplexx.EBX.EBXFieldDescriptor;
import tk.captainsplexx.EBX.EBXFile;
import tk.captainsplexx.EBX.EBXHandler;
import tk.captainsplexx.EBX.EBXInstance;
import tk.captainsplexx.EBX.EBXInstanceRepeater;
import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.Game.EntityHandler.Type;
import tk.captainsplexx.Maths.Matrices;
import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Render.ModelHandler;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.MeshChunkLoader;
import tk.captainsplexx.Resource.MeshVariationDatabaseEntry;
import tk.captainsplexx.Resource.ResourceHandler;
import tk.captainsplexx.Toc.TocFile;
import tk.captainsplexx.Toc.TocManager;

public class Game {
	public ModelHandler modelHandler;
	public TerrainHandler terrainHandler;
	public PlayerHandler playerHandler;
	public ResourceHandler resourceHandler;
	public EntityHandler entityHandler;
	public ShaderHandler shaderHandler;
	public String gamePath;
		
	public Game(){
		gamePath = "C:/Program Files (x86)/Origin Games/Battlefield 4"; //hardcoded for now//TODO
		modelHandler = new ModelHandler();
		
		playerHandler = new PlayerHandler();
		
		terrainHandler = new TerrainHandler();
		terrainHandler.generate(0, 0);
		terrainHandler.generate(0, 0);
				
		resourceHandler = new ResourceHandler("D:/dump_bf4_fs/chunks/", "res/externalFileGUIDs");	
		
		shaderHandler = new ShaderHandler();
		entityHandler = new EntityHandler(modelHandler, resourceHandler);
				
		/*LZ4 unpacker = new LZ4();///OUTDATED
		EbxCasConverter conv = new EbxCasConverter();
		System.out.println(unpacker.getHexString(conv.createCAS(unpacker.decompress(resourceHandler.getFileReader().readFile("res/cas_99.lz77"))))); ///OUTDATED
		*/
		
		CasCatManager ccManager = new CasCatManager();
		ccManager.readCAT(FileHandler.readFile(gamePath+"/Data/cas.cat"));
		
		/*
		TocManager tocMan = new TocManager();
		tocMan.readToc(FileHandler.readFile("D:/dump_bf4_fs/MP_Playground.toc"));
		//TODO TocFile sb = tocMan.readSbPart(FileHandler.readFile("D:/dump_bf4_fs/MP_Playground.sb", 0x10, 0xAD954));
		*/
		
		byte[] data = CasDataReader.readCas("72CEA8EE09AC2467B5B31561D0CDEFB96519A514", gamePath+"/Data", ccManager.getEntries());
		
		resourceHandler.getEBXHandler().getLoader().loadEBX(data);
		
		
		
		
		
		
		
		/*
		ArrayList<File> files1 = new ArrayList<File>();
		listf("C:/Users/Jannik/Desktop/Battlefield Modding/dump_bf4_fs/bundles_more_info/ebx/", files1, "meshvariationdb_win32.ebx");
		
		
		for (int i=1; i<files1.size();i++) {
			try{
				resourceHandler.getEBXHandler().getLoader().loadEBX(files1.get(i).getAbsolutePath().replace('\\', '/'));//TAKES BYTEARRAY NOW!!
				resourceHandler.getMeshVariationDatabaseHandler().addDatabase(resourceHandler.getEBXHandler().getLoader().getInstances().get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
		}*/
		//entityHandler.createEntityFromMesh("res/objects/architecture/housechina_01/housechina_01_destruction_Mesh.mesh", "C:/Users/Jannik/Desktop/Battlefield Modding/dump_bf4_fs/bundles/", "C:/Users/Jannik/Desktop/Battlefield Modding/dump_bf4_fs/bundles/");
		entityHandler.createEntityFromMesh("res/objects/architecture/housesettlement_01/housesettlement_01_medium_01_destruction_Mesh.mesh", "D:/dump_bf4_fs/bundles/", "D:/dump_bf4_fs/bundles/");
		entityHandler.createEntityFromMesh("res/objects/architecture/housesettlement_01/housesettlement_01_medium_01_door_Mesh.mesh", "D:/dump_bf4_fs/bundles/", "D:/dump_bf4_fs/bundles/");
		
		/*for (MeshVariationDatabaseEntry s : resourceHandler.getMeshVariationDatabaseHandler().entries.values()){
			entityHandler.createEntityFromMesh("res/"+s.getName().toLowerCase()+".mesh", "C:/Users/Jannik/Desktop/Battlefield Modding/dump_bf4_fs/bundles/");
		}*/
		
		
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
	
	//Getters and Setters
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
}
