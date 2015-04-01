package tk.captainsplexx.JavaFX;

import tk.captainsplexx.JavaFX.Windows.JavaFXMainWindow;

public class JavaFXHandler {
	
	JavaFXMainWindow main;
	
	public JavaFXHandler(){
		main = new JavaFXMainWindow();
		main.runApplication();
	}
	
	public JavaFXMainWindow getMainWindow() {
		return main;
	}
}
