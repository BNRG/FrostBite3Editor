package tk.captainsplexx.Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.ImageIOImageData;

import tk.captainsplexx.Event.EventHandler;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.Mod.ModTools;
import tk.captainsplexx.Render.Render;
import tk.captainsplexx.Render.Gui.GuiTexture;
import tk.captainsplexx.Resource.FileHandler;

public class Core {
	/*Main Components*/
	public static Game game;
	public static Render render;
	public static EventHandler eventHandler;
	public static InputHandler inputHandler;
	public static ModTools modTools;
	public static JavaFXHandler jfxHandler;
		
	/*Defaults for Screen Configuration.*/
	public static int DISPLAY_WIDTH;
	public static int DISPLAY_HEIGHT;
	public static int DISPLAY_RATE;
	public static float zNear;
	public static float zFar;
	public static float FOV;
	
	/*Boring stuff to make Ticks possible.*/
	public static int TICK_RATE;
	public static int currentTick = -1;
	public static int currentTime = 0;
	public static int oldTime = -1;
	
	/*Active session*/
	public static String gamePath;
	public static boolean keepAlive;
	public static boolean runEditor;
	public static boolean isDEBUG;
	public static String currentDir;
	
	/*You w00t mate ? :D*/
	public static String buildVersion;
		
	/*Support for Cross-Threading*/
	private static ArrayList<Runnable> runnables;
	private static ArrayList<Runnable> runnablesQ;
	private static boolean isExecutingRunnables;
	public static Object[] sharedObjs;
		
	public static void main(String[] args){
		/*Everything's happen from here. Even your parents meet here!*/
		
		
		/* Starwars battlefront beta DEBUG
		 * 
		 * CasCat file got changed and first 8 bytes after header is a little_endian long containing the number of entries.
		 * 
		 * 
		byte[] blockdata = FileHandler.readFile("C:\\Users\\SpleXx\\Desktop\\settings_win32_cas_chunks");
		byte[] finaldata = CasDataReader.convertToRAWData(blockdata);
		FileHandler.writeFile("C:\\Users\\SpleXx\\Desktop\\settings_win32.ebx", finaldata);
		
		System.exit(0);
		*/
		
		
		
		
		//ITextureConverter.getITextureHeader(FileHandler.readFile("mods/SampleMod/resources/objects/architecture/housesettlement_01/t_housesettlement_01_railing_d.dds"), "01 10 96 C2 D2 DA DF 9B 39 31 23 20 14 07 C1 E7".replace(" ", ""));
		
		/*
		byte[] file = FileHandler.readFile("D:\\dump_bf4_fs\\bundles_more_info\\ebx\\objects\\architecture\\housesettlement_01\\pf_housesettlement_01_medium_01_generic_mpnaval_nongroupable_autogen_Win32.ebx");
		 "D:\\dump_bf4_fs\\bundles_more_info\\ebx\\levels\\mp\\mp_playground\\content\\layer2_buildings.ebx"
				
		
		EBXFile ebxFile = new EBXHandler().loadFile(file);
		EBXStructureFile structFile = EBXStructureReader.readStructure(ebxFile);
		for (EBXStructureEntry entry : structFile.getEntries()){
			switch (entry.getType()){
				case ReferenceObjectData:
					EBXStrReferencedObjectData en = (EBXStrReferencedObjectData) entry;
					//System.out.println(en.getBlueprintTransform().getTransformation(Component.TRANS).getY());
					continue;
				case SpatialPrefabBlueprint:
					System.out.println("Found SpatialPrefabBlueprint!");
					EBXStrSpatialPrefabBlueprint spBlueprint = (EBXStrSpatialPrefabBlueprint) entry;
					for (Object obj : spBlueprint.getObjectArray().getObjects()){
						EBXObjInstanceGUID instanceGUID = (EBXObjInstanceGUID) obj;
						EBXStructureEntry targetOBJ = instanceGUID.followInternalGUID(structFile);
						if (targetOBJ!=null){
							System.out.println(targetOBJ.getType()+" - "+targetOBJ.getGuid());
						}
					}
				}
		}
		
		System.exit(0); */
		
		
		/*Initialize Variables*/
		runnables = new ArrayList<Runnable>();
		runnablesQ = new ArrayList<Runnable>();
		isExecutingRunnables = false;
		sharedObjs = null;
		gamePath = null;
		checkVersion();
		
		currentDir = FileHandler.normalizePath(Paths.get("").toAbsolutePath().toString());
		keepAlive = true;
		runEditor = false;
		
		FileHandler.cleanFolder("temp/images");
		FileHandler.cleanFolder("output");
		
		TICK_RATE = 20;
				
		DISPLAY_WIDTH = 1280; DISPLAY_HEIGHT = 720;
		DISPLAY_RATE = 60;
		
		zNear = 1f;
		zFar = 25000f;
		FOV = 60f;
		
		jfxHandler = new JavaFXHandler();
		eventHandler = new EventHandler();
		game = new Game();
		if (buildVersion.contains("NEW VERSION")){
			jfxHandler.getDialogBuilder().showInfo("Info",
					"Make sure to run the latest version!\n"+
						"http://captainsplexx.tk/");
		}
		modTools = new ModTools();
				
		/*Let's loop until user's exit request was initialized*/
		while (keepAlive){
			/* Wait for starting editor.
			 * JavaFX is running on a different Thread,
			 * so we can slow this one down in the mean time.
			 */
			System.out.print("");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			/*Mod was selected -> Run Editor*/
			if (runEditor){
				jfxHandler.getMainWindow().toggleLeftVisibility();
				
				jfxHandler.getMainWindow().toggleModLoaderVisibility();
				
				try {
		            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
		            Display.setTitle("Unofficial FrostBite 3 Editor "+buildVersion);
		            Display.setResizable(true);
		            Display.create();
		            Display.setIcon(new ByteBuffer[] {
		            	new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/icon/16.png")), false, false, null),
		                new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/icon/32.png")), false, false, null)
		            });
		            Mouse.setClipMouseCoordinatesToWindow(true);
		            //Mouse.setGrabbed(true);
		        } catch (Exception e) {
		            e.printStackTrace();
		            Display.destroy();
		            System.exit(1);
		        }
				game.getModelHandler().getLoader().init();
				game.getShaderHandler().init();
				game.buildExplorerTree();
				game.getGuis().add(new GuiTexture(game.getModelHandler().getLoader().getCrosshairID(), new Vector2f(0.0f, 0.0f), new Vector2f(0.15f, 0.15f)));
				render = new Render(game);	
				inputHandler = new InputHandler();
				
				while(!Display.isCloseRequested() && keepAlive){
					currentTime = (int) (System.currentTimeMillis()%1000/(1000/TICK_RATE));
					if (currentTime != oldTime){
						oldTime = currentTime;
						currentTick++;
						
						//update at rate
						game.update();
						if (currentTick%(TICK_RATE/4)==0){//0.25xTICK_RATE
							game.lowRateUpdate();
						}
						eventHandler.listen();
					}
					//update instantly
					inputHandler.listen();
					render.update();
					
					/*Proccess all runnables*/
					isExecutingRunnables = true;
					for (Runnable runna : runnables){
						runna.run();
					}
					runnables.clear();
					isExecutingRunnables = false;
					for (Runnable runnaQ : runnablesQ){
						runnables.add(runnaQ);
					}
					runnablesQ.clear();
					/*End of Runnable section*/
					
					
					
				}
				game.modelHandler.loader.cleanUp(); //CleanUp GPU-Memory!
				game.shaderHandler.cleanUpAll();
				//Thank u for using...
			}
		}
		System.out.println("Thanks for using this Editor/ModLoader.\n"
				 + "If u have noticed any Bugs, make sure to report them\n"
				 + "directly on Github to @CaptainSpleXx."
				 + "\n\n"
				 + "Have a good one, Bye!");

		System.exit(0);
	}
	public static void runOnMainThread(Runnable run){
		if (isExecutingRunnables){
			runnablesQ.add(run);
		}else{
			runnables.add(run);
		}
	}
	
	public static Game getGame(){
		return game;
	}
	
	public static Render getRender(){
		return render;
	}
	
	public static EventHandler getEventHander(){
		return eventHandler;
	}

	public static JavaFXHandler getJavaFXHandler() {
		return jfxHandler;
	}

	public static ModTools getModTools() {
		return modTools;
	}
	public static Object[] getSharedObjs() {
		return sharedObjs;
	}
	public static void setSharedObjs(Object[] sharedObjs) {
		Core.sharedObjs = sharedObjs;
	}
	
	public static void checkVersion(){
		/*VERSION CHECK*/
		String newVersion = "";
		try{
			URL url = new URL("https://raw.githubusercontent.com/CaptainSpleXx/FrostBite3Editor/master/version");
			URLConnection ec = url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                ec.getInputStream(), "UTF-8"));
	        String inputLine;
	        StringBuilder a = new StringBuilder();
	        while ((inputLine = in.readLine()) != null)
	            a.append(inputLine);
	        in.close();

	        newVersion += a.toString();
		}catch(Exception e){
			System.err.println("Could not get version info from GitHub...");
		}
		try{
			FileReader fr = new FileReader("version");
			
			BufferedReader br = new BufferedReader(fr);
			buildVersion = br.readLine();
			br.close();
			fr.close();
		}catch (Exception e){
			buildVersion = "n/a";
			System.err.println("NO VERSION FILE FOUND!");
		}
		System.out.println("Version: "+buildVersion);
		if (buildVersion.contains("|")){
			isDEBUG = true;
			System.err.println("RUNNING IN DEBUG MODE!");
			String[] versionArgs = buildVersion.split("\\|");
			gamePath = versionArgs[1];
			buildVersion = versionArgs[0]+" DEBUG MODE! ";
		}else if (!buildVersion.equalsIgnoreCase(newVersion) && !newVersion.equalsIgnoreCase("")){
			buildVersion += " | [NEW VERSION ADVILABLE]";
		}
		/*END OF VERSION CHECK*/
	}
	public static void keepAlive(boolean b) {
		keepAlive = b;
	}
}
