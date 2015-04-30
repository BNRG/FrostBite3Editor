package tk.captainsplexx.Game;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import tk.captainsplexx.Entity.PlayerEntity;

public class InputHandler {
	
	public int speedMultipShift;
	public void listen() {		
		speedMultipShift = 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))//asdfasdf
	    {
			speedMultipShift = 10;
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
	    
	    
	    
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_ADD))
	    {
	    	Main.getRender().updateProjectionMatrix(Main.FOV+1, Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT, Main.zNear, Main.zFar);
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT))
	    {
	    	Main.getRender().updateProjectionMatrix(Main.FOV-1, Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT, Main.zNear, Main.zFar);
	    }
	    
	    //JComboBox comboBox = Main.getJFrameHandler().getComboBox();
	    /*
	    if (Keyboard.isKeyDown(Keyboard.KEY_ADD))//KEY_ADD down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changeScaling(0.01f*speedMultipShift, 0.01f*speedMultipShift, 0.01f*speedMultipShift);
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT))//KEY_SUBTRACT down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changeScaling(-0.01f*speedMultipShift, -0.01f*speedMultipShift, -0.01f*speedMultipShift);
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_UP))//KEY_NUMPAD8 down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changePosition(0.5f*speedMultipShift, 0, 0);
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))//KEY_NUMPAD2 down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changePosition(-0.5f*speedMultipShift, 0, 0);
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))//KEY_NUMPAD4 down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changePosition(0, 0, 0.5f*speedMultipShift);
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))//KEY_NUMPAD6 down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changePosition(0, 0, -0.5f*speedMultipShift);
	    }
	    
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3))//KEY_NUMPAD1 down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changeRotation(0, -0.01f, 0);
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1))//KEY_NUMPAD1 down
	    {
	    	Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).changeRotation(0, 0.01f, 0);
	    }*/
	    
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))//KEY_ESCAPE down
	    {
	    	Mouse.setGrabbed(false);
	    }
	    if (Mouse.isButtonDown(0)){
	    	Mouse.setGrabbed(true);
	    }
	}
}
