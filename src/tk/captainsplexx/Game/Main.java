package tk.captainsplexx.Game;

import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.ImageIOImageData;

import tk.captainsplexx.Event.EventHandler;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.Maths.Hash;
import tk.captainsplexx.Mod.ModTools;
import tk.captainsplexx.Render.Render;
import tk.captainsplexx.Resource.FileHandler;


public class Main {
	public static Game game;
	public static Render render;
	public static EventHandler eventHandler;
	public static InputHandler inputHandler;
	public static ModTools modTools;
	public static JavaFXHandler jfxHandler;
	
	public static int DISPLAY_WIDTH;
	public static int DISPLAY_HEIGHT;
	public static int DISPLAY_RATE;
	public static int TICK_RATE;
	
	public static float zNear;
	public static float zFar;
	
	public static float FOV;
	
	public static int currentTick = -1;
	public static int currentTime = 0;
	public static int oldTime = -1;
	
	public static String gamePath;
	public static boolean keepAlive;
	public static boolean runEditor;
	
	public static String buildVersion;
		
	public static void main(String[] args){
		buildVersion = "0.01A";
		keepAlive = true;
		runEditor = false;
		
		FileHandler.cleanFolder("temp/mods");
		FileHandler.cleanFolder("temp/images");
		FileHandler.cleanFolder("output");
		
		gamePath = null;
		TICK_RATE = 20;
				
		DISPLAY_WIDTH = 1280; DISPLAY_HEIGHT = 720;
		DISPLAY_RATE = 60;
		
		zNear = 1f;
		zFar = 25000f;
		FOV = 60f;
		
		jfxHandler = new JavaFXHandler();
		eventHandler = new EventHandler();
		game = new Game();
		modTools = new ModTools();
		
		while (true){
			//Wait for starting editor
			System.out.print("");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (runEditor){
				jfxHandler.getMainWindow().toggleLeftVisibility();
				jfxHandler.getMainWindow().toggleRightVisibility();
				
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
				render = new Render(game);	
				inputHandler = new InputHandler();
				
				while(!Display.isCloseRequested() && keepAlive){
					currentTime = (int) (System.currentTimeMillis()%1000/(1000/TICK_RATE));
					if (currentTime != oldTime){
						oldTime = currentTime;
						currentTick++;
						
						//update at rate
						game.update();
						eventHandler.listen();
					}
					//update instantly
					inputHandler.listen();
					render.update();
				}
				game.modelHandler.loader.cleanUp(); //CleanUp GPU-Memory!
				game.shaderHandler.getStaticShader().cleanUp();
				
				//Thank u for using...
				
				System.exit(0);
			}
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
	
	
}
