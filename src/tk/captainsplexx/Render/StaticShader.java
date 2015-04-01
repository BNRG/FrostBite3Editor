package tk.captainsplexx.Render;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
	
	public int transMatrixID;
	public int projeMatrixID;
	public int viewMatrixID;
	public int highlightedID;

	public StaticShader() {
		super("res/shader/StaticShader.vert", "res/shader/StaticShader.frag");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoord");
	}

	@Override
	protected void getAllUniformLocations() {
		transMatrixID = super.getUniformLocation("transformationMatrix");
		projeMatrixID = super.getUniformLocation("projectionMatrix");
		viewMatrixID = super.getUniformLocation("viewMatrix");
		highlightedID = super.getUniformLocation("isHighlighted");
	}
	
	public void loadTransformationMatrix(Matrix4f mtx){
		super.loadMatrix(transMatrixID, mtx);
	}
	
	public void loadProjectionMatrix(Matrix4f mtx){
		super.loadMatrix(projeMatrixID, mtx);
	}
	
	public void loadViewMatrix(Matrix4f mtx){
		super.loadMatrix(viewMatrixID, mtx);
	}
	
	public void loadHighlighted(boolean bool){
		super.loadBoolean(highlightedID, bool);
	}
}
