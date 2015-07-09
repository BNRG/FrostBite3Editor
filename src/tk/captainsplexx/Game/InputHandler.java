package tk.captainsplexx.Game;


import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.Entity.PlayerEntity;
import tk.captainsplexx.Entity.PlayerHandler;
import tk.captainsplexx.Maths.VectorMath;

public class InputHandler {
	
	public float speedMultipShift;
	public void listen() {		
		Entity en = Main.getGame().getEntityHandler().getFocussedEntity();
		speedMultipShift = 1f;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
	    {
			speedMultipShift = 10f;
	    }
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
	    {
			speedMultipShift = 0.1f;
	    }
		PlayerHandler pl = Main.getGame().getPlayerHandler();
		PlayerEntity pe = pl.getPlayerEntity();
		if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
	    {
	        pe.velZ -= 0.1f*speedMultipShift;
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
	    {
	    	pe.velZ += 0.1f*speedMultipShift;
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left
	    {
	    	pe.velX -= 0.1f*speedMultipShift;
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right
	    {
	    	pe.velX += 0.1f*speedMultipShift;
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))//move up
	    {
	    	pe.velY += pe.jumpStrength*speedMultipShift;
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))//move down
	    {
	    	pe.velY -= pe.jumpStrength*speedMultipShift;
	    }
	    
	    if (Mouse.isButtonDown(2)){//middle click
	    	System.err.println("Middle mouse click TODO!");
	    }
	    
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_ADD))
	    {
	    	Main.getRender().updateProjectionMatrix(Main.FOV+1, Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT, Main.zNear, Main.zFar);
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT))
	    {
	    	Main.getRender().updateProjectionMatrix(Main.FOV-1, Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT, Main.zNear, Main.zFar);
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0))
	    {
	    	Main.getGame().getEntityHandler().getEntities().clear();
	    	Main.getGame().getModelHandler().getLoader().cleanUp();
	    	Main.getGame().getModelHandler().getLoader().init();//for loading the notFoundTexture!
	    }
	    
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))//KEY_ESCAPE down
	    {
	    	Mouse.setGrabbed(false);
	    }
	    if (Mouse.isButtonDown(0)){
	    	Mouse.setGrabbed(true);
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
	    {
	    	if (en!=null){
	    		Vector2f relCoords = pe.moveLeft(10f*speedMultipShift);
	    		en.changePosition(new Vector3f(relCoords.x, 0, relCoords.y));
	    	}
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
	    {
	    	if (en!=null){
	    		Vector2f relCoords = pe.moveRight(10f*speedMultipShift);
	    		en.changePosition(new Vector3f(relCoords.x, 0, relCoords.y));
	    	}
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_UP))
	    {
	    	if (en!=null){
	    		Vector2f relCoords = pe.moveForward(10f*speedMultipShift);
	    		en.changePosition(new Vector3f(relCoords.x, 0, -relCoords.y));
	    	}
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
	    {
	    	if (en!=null){
	    		Vector2f relCoords = pe.moveBackwards(10f*speedMultipShift);
	    		en.changePosition(new Vector3f(-relCoords.x, 0, relCoords.y));
	    	}
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_Q))
	    {
	    	if (en!=null){
	    		en.changeRotation(0f, speedMultipShift*0.01f, 0f);
	    	}
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_E))
	    {
	    	if (en!=null){
	    		en.changeRotation(0f, speedMultipShift*0.01f, 0f);
	    	}
	    }
	}
}
