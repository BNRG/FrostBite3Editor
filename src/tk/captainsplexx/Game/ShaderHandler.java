package tk.captainsplexx.Game;
import tk.captainsplexx.Render.StaticShader;

public class ShaderHandler {
	public StaticShader staticShader;
	
	public ShaderHandler(){
		this.staticShader = null;
	}
	
	public void init(){
		this.staticShader = new StaticShader();
	}

	public StaticShader getStaticShader() {
		return staticShader;
	}	
}
