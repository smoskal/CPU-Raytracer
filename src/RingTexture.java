package src;

import java.util.ArrayList;

public class RingTexture implements IlluminationModel {

	@Override
	public Color illuminate(HitData hit, Light light, ArrayList<Shape> shapes) {

		float x = hit.getIntersection().getX();
		float y = hit.getIntersection().getY();
		float z = hit.getIntersection().getZ();
		
		float distVal = (float) Math.sqrt( x*x + y*y + z*z);
		float sinVal = (float) Math.sin(2 * Math.PI * distVal);
		
		return new Color(1f, sinVal, 0);
	
	}

}
