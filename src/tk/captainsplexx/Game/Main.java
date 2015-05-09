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
import tk.captainsplexx.Render.Render;
import tk.captainsplexx.Resource.FileHandler;


public class Main {
	public static Game game;
	public static Render render;
	public static EventHandler eventHandler;
	public static InputHandler inputHandler;
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
		
	public static void main(String[] args){
		
		//clean up folder.
		for (File f : FileHandler.listf("temp/images", "")){
			f.delete();
		}
		
		gamePath = null;
		TICK_RATE = 20;
		
		DISPLAY_WIDTH = 1280; DISPLAY_HEIGHT = 720;
		DISPLAY_RATE = 60;
		
		zNear = 1f;
		zFar = 25000f;
		FOV = 60f;
		
		try {
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("Unofficial FrostBite3Editor by CaptainSpleXx.TK");
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
		
		jfxHandler = new JavaFXHandler();
		
		eventHandler = new EventHandler();
		game = new Game();
		render = new Render(game);	
		inputHandler = new InputHandler();
		
		while(!Display.isCloseRequested()){
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
		System.exit(0);
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
	
}
