package tk.captainsplexx.Terrain;

import java.nio.ByteOrder;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Game.Point;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;

public class Terrain {
	public Point[][] points;
	
	public Point[][] getPoints() {
		return points;
	}

	public Terrain(Vector3f pos, int maxX, int maxZ, float distance) {
		
		@SuppressWarnings("unused")
		Random random = new Random();
		points = new Point[maxX][maxZ];
		byte[] heightmapdata = FileHandler.readFile("res/height");
		int[] heights = new int[heightmapdata.length/2];
		FileSeeker seeker = new FileSeeker();
		for (int i=0; i<heights.length; i++){
			heights[i] = (FileHandler.readShort(heightmapdata, seeker, ByteOrder.LITTLE_ENDIAN)&0xFFFF)/100;
		}
		
		int index = 0;
        for (int i1 = 0; i1<points.length; i1++){
        	Point[] pointsZ = new Point[points[0].length];
        	for(int i2 = 0; i2 < pointsZ.length; i2++){
        		pointsZ[i2] = new Point(i1*distance, heights[index], i2*distance, distance, 0.5f, 0.5f, 0.5f);
        		index++;
        		/*
        		//pointsZ[i2] = new Point(i1*distance, i1*i2*0.2f, i2*distance, distance, random.nextFloat(), 0.1f, 0.1f);
        		//pointsZ[i2] = new Point(i1*distance, 0.5f*((i1*i1)+(i2*i2)), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f); //WAVE
        		//pointsZ[i2] = new Point(i1*distance, random.nextFloat()*100f, i2*distance, distance, random.nextFloat(), 0.1f, 0.1f);
        		//pointsZ[i2] = new Point(i1*distance, (i1*i1)-(i2*i2), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f);
        		//pointsZ[i2] = new Point(i1*distance, (i1*(i1-i2))+(i2*(i2-i1)), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f);
        		//pointsZ[i2] = new Point(i1*distance, ((i1*(i2-distance))+(i2*(i1-distance))), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f);
        		//pointsZ[i2] = new Point(i1*distance, (i1*(i1-distance)), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f); //CURVE
        		//pointsZ[i2] = new Point(i1*distance, -(i1*(i1-distance)), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f); //CURVE
        		//pointsZ[i2] = new Point(i1*distance, -(i1*(i1-distance))+(i2*(i2-distance)), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f);
        		//pointsZ[i2] = new Point(i1*distance, (i1*(i1-distance))+(i2*(i2-distance)), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f); //HOLE
        		//pointsZ[i2] = new Point(i1*distance, (i1*(i1-(i1%10))), i2*distance, distance, random.nextFloat(), 0.1f, 0.1f); //MODULO CURVE
        		//pointsZ[i2] = new Point(i1*distance, ((i1*(i1-distance))+(i2*(i2-distance)))%500, i2*distance, distance, random.nextFloat(), 0.1f, 0.1f); //HOLE WAVE
        		//pointsZ[i2] = new Point(i1*distance, (float) Math.sin((double)i1*0.25f)*100, i2*distance, distance, random.nextFloat(), 0.1f, 0.1f); //SIN WAVE
        		pointsZ[i2] = new Point(i1*distance, ((float) Math.sin((double)i1*0.25f)*(float) Math.sin((double)i2*0.25f)*75f), i2*distance, distance, i1/(i2+1), 0.2f, 0.1f); //TERRAIN SIN WAVE
        		//pointsZ[i2] = new Point(i1*distance, (float) Math.sin((double)i1*i2)*100f, i2*distance, distance, random.nextFloat(), 0.1f, 0.1f);
        		*/
        		pointsZ[i2].setX(pointsZ[i2].getX()+pos.getX());
        		pointsZ[i2].setY(pointsZ[i2].getY()+pos.getY());
        		pointsZ[i2].setZ(pointsZ[i2].getZ()+pos.getZ());
        	}
        	points[i1] = pointsZ;
        };
	}
	
}
