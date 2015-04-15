package tk.captainsplexx.Render;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import tk.captainsplexx.Camera.FPCameraController;
import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.Entity.PlayerEntity;
import tk.captainsplexx.Game.Game;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Game.Point;
import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Model.TexturedModel;
import tk.captainsplexx.Terrain.Terrain;
import tk.captainsplexx.Maths.Matrices;

;

public class Render {

	public Game game;

	FPCameraController camera;
	PlayerEntity pe;
	public Matrix4f viewMatrix;
	public Matrix4f projectionMatrix;
	public Matrix4f transformationMatrix;

	public Render(Game game) {
		this.game = game;

		pe = game.getPlayerHandler().getPlayerEntity();
		camera = new FPCameraController(pe);

		updateProjectionMatrix(Main.FOV, Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT, Main.zNear, Main.zFar);
		
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);

	}

	public void update() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.4f, 0.4f, 0.4f, 1.0f);
		glLoadIdentity();

		camera.lookThrough();
		if (Display.isActive()&&Mouse.isGrabbed()){
			camera.dx = Mouse.getDX();
			camera.dy = Mouse.getDY();
		}
		
		camera.yaw(camera.dx * camera.mouseSensitivity);
		camera.pitch(-camera.dy * camera.mouseSensitivity);

		viewMatrix = Matrices.createViewMatrix(camera.getPosition(),
				new Vector3f(camera.getPitch(), camera.getYaw(), 0.0f));

		StaticShader shader = game.getShaderHandler().getStaticShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadViewMatrix(viewMatrix);
		for (Entity e : game.entityHandler.getEntities()) {
			if (e.isVisible){
				if (e.getHighlighted()){
					shader.loadHighlighted(true);
				}
				
				String[] texturedModels = e.getTexturedModelNames();
				for (int i = 0; i < texturedModels.length; i++) {
					shader.loadTransformationMatrix(Matrices.createTransformationMatrix(
							e.getPosition(), e.getRotation(), e.getScaling()));
					TexturedModel tm = game.modelHandler.getTexturedModels().get(
							(texturedModels[i]));
					RawModel raw = tm.rawModel;
					glColor3f(0.25f, 0.25f, 0.25f);
					GL30.glBindVertexArray(raw.vaoID);
					GL20.glEnableVertexAttribArray(0);
					GL20.glEnableVertexAttribArray(1);
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, tm.textureID);
					GL11.glDrawElements(raw.drawMethod, raw.vertexCount,
							GL11.GL_UNSIGNED_INT, 0);
					GL20.glDisableVertexAttribArray(0);
					GL20.glDisableVertexAttribArray(1);
					GL30.glBindVertexArray(0);
				}
				shader.loadHighlighted(false);
			}
		}
		// Terrain Render
		Matrix4f TerrainransformationMatrix = Matrices.createTransformationMatrix(
				new Vector3f(0.0f, -500.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f));
		
		Matrix4f TerrainransformationMatrix1 = Matrices.createTransformationMatrix(
				new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f));
				
		for (Terrain curTerr : game.getTerrainHandler().getTerrainList()) {
			Point[][] points = curTerr.getPoints();
			shader.loadTransformationMatrix(Matrix4f.add(TerrainransformationMatrix, TerrainransformationMatrix1, null));
			TerrainransformationMatrix1.translate(new Vector3f(5000f, 500f, 0f));
			TerrainransformationMatrix1.scale(new Vector3f(5.0f, 5.0f, 5.0f));
			TerrainransformationMatrix1.rotate(45f, new Vector3f(0f, 1f, 0f));
			for (int i1 = 0; i1 < points.length - 1; i1++) {
				glBegin(GL_LINE_STRIP);
				for (int i2 = 0; i2 < points[i1].length - 1; i2++) {
					glColor3f(points[i1][i2].colorR, points[i1][i2].colorG,
							points[i1][i2].colorB);
					glVertex3f(points[i1 + 1][i2].x, points[i1 + 1][i2].y,
							points[i1 + 1][i2].z);
					glVertex3f(points[i1][i2 + 1].x, points[i1][i2 + 1].y,
							points[i1][i2 + 1].z);
				}
				glEnd();
			}
		}
		shader.stop();
		

		Display.update();
		if (Display.wasResized()){
			updateProjectionMatrix(Main.FOV, Display.getWidth(), Display.getHeight(), Main.zNear, Main.zFar);
		}
		Display.sync(Main.DISPLAY_RATE);
	}

	public FPCameraController getCamera() {
		return camera;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void updateProjectionMatrix(float FOV, int DISPLAY_WIDTH, int DISPLAY_HEIGHT, float zNear, float zFar){
		Main.FOV = FOV;
		Main.DISPLAY_WIDTH = DISPLAY_WIDTH;
		Main.DISPLAY_HEIGHT = DISPLAY_HEIGHT;
		Main.zNear = zNear;
		Main.zFar = zFar;
		projectionMatrix = Matrices.createProjectionMatrix(Main.FOV, Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT, Main.zNear, Main.zFar);
	}
}
