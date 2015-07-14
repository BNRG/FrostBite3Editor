package tk.captainsplexx.Maths;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Matrices {
	public static Matrix4f createProjectionMatrix(Float fieldOfView, int WIDTH, int HEIGHT, float near_plane, float far_plane){
		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.setIdentity();
		float aspectRatio = (float)WIDTH / (float)HEIGHT;
		 
		float y_scale = (float) (1/Math.tan(Math.toRadians(fieldOfView / 2f)));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;
		 
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scaling){
		Matrix4f transformationMatrix = new Matrix4f();
		transformationMatrix.setIdentity();
		Matrix4f.translate(translation, transformationMatrix, transformationMatrix);
		Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0), transformationMatrix, transformationMatrix);
		Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0), transformationMatrix, transformationMatrix);
		Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1), transformationMatrix, transformationMatrix);
		Matrix4f.scale(scaling, transformationMatrix, transformationMatrix);
		return transformationMatrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Vector3f position, Vector3f rotation){
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1,0,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.y), new Vector3f(0,1,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.z), new Vector3f(0,0,1), viewMatrix, viewMatrix);
		Matrix4f.translate(position, viewMatrix, viewMatrix);
		return viewMatrix;
	}
}
