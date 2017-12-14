package src;

import java.util.ArrayList;

public class SineTexture implements IlluminationModel {

	@Override
	public Color illuminate(HitData hit, Light light, ArrayList<Shape> shapes) {
		float val = hit.getIntersection().getX() + 
				hit.getIntersection().getY() + 
				hit.getIntersection().getZ();
		
		float sinVal = (float) Math.abs(Math.sin(2 * Math.PI * val));
		
		return new Color(1f, sinVal, 0);
	}

}
