package tk.captainsplexx.Game;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import tk.captainsplexx.Event.EventHandler;
import tk.captainsplexx.JavaFX.JavaFXHandler;
import tk.captainsplexx.Render.JFrameHandler;
import tk.captainsplexx.Render.Render;


public class Main {
	public static Game game;
	public static Render render;
	public static EventHandler eventHandler;
	public static InputHandler inputHandler;
	public static JFrameHandler jFrameHandler;
	public static JavaFXHandler jfxHandler;
	
	public static int DISPLAY_WIDTH;
	public static int DISPLAY_HEIGHT;
	public static int DISPLAY_RATE;
	public static int TICK_RATE;
	
	public static float zNear;
	public static float zFar;
	
	public static int currentTick = -1;
	public static int currentTime = 0;
	public static int oldTime = -1;
		
	public static void main(String[] args){
		TICK_RATE = 20;
		
		DISPLAY_WIDTH = 1280; DISPLAY_HEIGHT = 720;
		DISPLAY_RATE = 60;
		
		zNear = 1f;
		zFar = 25000f;
		
		try {
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("Unofficial FrostBite3 Editor Tools by CAPTAINSPLEXX");
            Display.create();
            Mouse.setClipMouseCoordinatesToWindow(true);
            Mouse.setGrabbed(true);
        } catch (Exception e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
		
		//jFrameHandler = new JFrameHandler();
		jfxHandler = new JavaFXHandler();
		
		eventHandler = new EventHandler();
		game = new Game();
		render = new Render(game, zNear, zFar);	
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
	
	public static JFrameHandler getJFrameHandler(){
		return jFrameHandler;
	}
}
