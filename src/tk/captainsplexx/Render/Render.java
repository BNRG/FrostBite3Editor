package tk.captainsplexx.Render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Camera.FPCameraController;
import tk.captainsplexx.Entity.Entity;
import tk.captainsplexx.Game.Game;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Game.Point;
import tk.captainsplexx.Maths.Matrices;
import tk.captainsplexx.Model.RawModel;
import tk.captainsplexx.Model.TexturedModel;
import tk.captainsplexx.Player.PlayerEntity;
import tk.captainsplexx.Render.Gui.GuiRenderer;
import tk.captainsplexx.Shader.StaticShader;
import tk.captainsplexx.Terrain.Terrain;
import tk.captainsplexx.Terrain.TerrainShader;

public class Render {

	public Game game;

	FPCameraController camera;
	PlayerEntity pe;
	public Matrix4f viewMatrix;
	public Matrix4f projectionMatrix;
	public Matrix4f transformationMatrix;
	GuiRenderer guiRenderer;
	
	public static Matrix4f identityMatrix = new Matrix4f(); 

	public Render(Game game) {
		this.game = game;
		guiRenderer = new GuiRenderer(game.getModelHandler().getLoader());
		pe = game.getPlayerHandler().getPlayerEntity();
		camera = new FPCameraController(pe);

		updateProjectionMatrix(Core.FOV, Core.DISPLAY_WIDTH,
				Core.DISPLAY_HEIGHT, Core.zNear, Core.zFar);

		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);

	}

	public void drawAxisAlignedBoundingBox(Vector3f minCoords,
			Vector3f maxCoords) {
		glColor3f(0.7f, 0.0f, 0.0f);
		// Bottom
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(minCoords.x, minCoords.y, minCoords.z);
		glVertex3f(maxCoords.x, minCoords.y, minCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(maxCoords.x, minCoords.y, minCoords.z);
		glVertex3f(maxCoords.x, minCoords.y, maxCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(maxCoords.x, minCoords.y, maxCoords.z);
		glVertex3f(minCoords.x, minCoords.y, maxCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(minCoords.x, minCoords.y, maxCoords.z);
		glVertex3f(minCoords.x, minCoords.y, minCoords.z);
		glEnd();
		// Top
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(minCoords.x, maxCoords.y, minCoords.z);
		glVertex3f(maxCoords.x, maxCoords.y, minCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(maxCoords.x, maxCoords.y, minCoords.z);
		glVertex3f(maxCoords.x, maxCoords.y, maxCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(maxCoords.x, maxCoords.y, maxCoords.z);
		glVertex3f(minCoords.x, maxCoords.y, maxCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(minCoords.x, maxCoords.y, maxCoords.z);
		glVertex3f(minCoords.x, maxCoords.y, minCoords.z);
		glEnd();
		// Connection Lines
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(minCoords.x, minCoords.y, minCoords.z);
		glVertex3f(minCoords.x, maxCoords.y, minCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(maxCoords.x, minCoords.y, minCoords.z);
		glVertex3f(maxCoords.x, maxCoords.y, minCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(maxCoords.x, minCoords.y, maxCoords.z);
		glVertex3f(maxCoords.x, maxCoords.y, maxCoords.z);
		glEnd();
		glBegin(GL11.GL_LINE_STRIP);
		glVertex3f(minCoords.x, minCoords.y, maxCoords.z);
		glVertex3f(minCoords.x, maxCoords.y, maxCoords.z);
		glEnd();
	}

	public FPCameraController getCamera() {
		return camera;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void update() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.4f, 0.4f, 0.4f, 1.0f);
		glLoadIdentity();

		camera.lookThrough();
		if (Display.isActive() && Mouse.isGrabbed()) {
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
		RenderEntities(game.entityHandler.getEntities(), identityMatrix, shader);	
		shader.stop();
		
		RenderTerrains(game.getShaderHandler().getTerrainShader());
		guiRenderer.update(game.getGuis());

		Display.update();
		if (Display.wasResized()) {
			updateProjectionMatrix(Core.FOV, Display.getWidth(),
					Display.getHeight(), Core.zNear, Core.zFar);
		}
		Display.sync(Core.DISPLAY_RATE);
	}
	public void RenderEntity(Entity e, Matrix4f parentMtx, StaticShader shader){
		if (e.isVisible) {
			
			Matrix4f matrix = Matrices
					.createTransformationMatrix(e.getPosition(),
							e.getRotation(), e.getScaling());
			
			Matrix4f stackMtx = Matrix4f.mul(matrix, parentMtx, null);
			
			if (e.isShowBoundingBox()) {
				shader.loadTransformationMatrix(Matrices
					.createTransformationMatrix(
							e.getPosition(),
							new Vector3f(0f, 0f, 0f),
							e.getScaling())
				);
				drawAxisAlignedBoundingBox(e.getMinCoords(), e.getMaxCoords());
			}
			if (e.getHighlighted()) {
				shader.loadHighlighted(true);
			}
			shader.loadHeighlightedColor(e.getHeighlightedColor());
			String[] texturedModels = e.getTexturedModelNames();
			shader.loadTransformationMatrix(stackMtx);
			for (int i = 0; i < texturedModels.length; i++) {
				TexturedModel tm = game.modelHandler.getTexturedModels()
						.get((texturedModels[i]));
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
			RenderEntities(e.getChildrens(), stackMtx, shader);
			shader.loadHighlighted(false);
		}
	}
	
	public void RenderEntities(ArrayList<Entity> entities, Matrix4f parentMtx, StaticShader shader){
		for (Entity e : entities) {
			RenderEntity(e, parentMtx, shader);
		}
	}
	
	public void RenderTerrains(TerrainShader terrainShader){
		terrainShader.start();
		terrainShader.loadProjectionMatrix(projectionMatrix);
		terrainShader.loadViewMatrix(viewMatrix);
		Matrix4f TerrainransformationMatrix = Matrices
			.createTransformationMatrix(
				new Vector3f(0.0f, 0.0f, 0.0f),
				new Vector3f(0.0f, 0.0f, 0.0f),
				new Vector3f(1.0f, 1.0f, 1.0f)
		);


		for (Terrain curTerr : game.getTerrainHandler().getTerrainList()) {
			Point[][] points = curTerr.getPoints();
			terrainShader.loadTransformationMatrix(TerrainransformationMatrix);

			for (int i1 = 0; i1 < points.length - 1; i1++) {
				glBegin(GL_LINE_STRIP);
				for (int i2 = 0; i2 < points[i1].length - 1; i2++) {
					glVertex3f(points[i1 + 1][i2].x, points[i1 + 1][i2].y,
							points[i1 + 1][i2].z);
					glVertex3f(points[i1][i2 + 1].x, points[i1][i2 + 1].y,
							points[i1][i2 + 1].z);
				}
				glEnd();
			}
		}
		terrainShader.stop();
	}

	public void updateProjectionMatrix(float FOV, int DISPLAY_WIDTH,
			int DISPLAY_HEIGHT, float zNear, float zFar) {
		System.out.println("Projection Matrix gets an update!");
		Core.FOV = FOV;
		Core.DISPLAY_WIDTH = DISPLAY_WIDTH;
		Core.DISPLAY_HEIGHT = DISPLAY_HEIGHT;
		Core.zNear = zNear;
		Core.zFar = zFar;
		projectionMatrix = Matrices.createProjectionMatrix(Core.FOV,
				Core.DISPLAY_WIDTH, Core.DISPLAY_HEIGHT, Core.zNear, Core.zFar);
	}

	public GuiRenderer getGuiRenderer() {
		return guiRenderer;
	}
	
	
}
