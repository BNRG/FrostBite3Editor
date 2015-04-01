package tk.captainsplexx.JavaFX;


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
