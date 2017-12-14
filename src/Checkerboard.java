package src;

import java.util.ArrayList;
import java.util.Random;

/**
 * The illumination model that will represent the checkerboard texture.
 * @author smoskal
 *
 */
public class Checkerboard implements IlluminationModel {

	private Color color1;
	private Color color2;
	private float size;
	private boolean hasNoise;
	
	public Checkerboard(float size, Color color1, Color color2) {
		this.color1 = color1;
		this.color2 = color2;
		this.size = size;
		this.hasNoise = false;
	}
	
	public Checkerboard(float size, Color color1, Color color2, boolean hasNoise) {
		this.color1 = color1;
		this.color2 = color2;
		this.size = size;
		this.hasNoise = hasNoise;
	}
	
	
	public Color illuminate(HitData hit, Light light, ArrayList<Shape> shapes) {
		
		if((Math.floor((double)hit.getIntersection().getX()/size)+
				Math.floor((double)hit.getIntersection().getY()/size)+
				Math.floor((double)hit.getIntersection().getZ()/size)) % 2 == 0) {
		
			
			if(hasNoise) return noise(color1);
			else return color1;
		}
		else {
			if(hasNoise) return noise(color2);
			return color2;
		}
		
	}
	
	private Color noise(Color color) {
		Random rand = new Random();
		double rangeMin = -.15;
		double rangeMax = .15;
		double gauss = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();;
		double gauss1 = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();;
		double gauss2 = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();;
		
		return new Color((color.getRed()+(float)gauss),
			(color.getGreen()+(float)gauss1),
			(color.getBlue()+(float)gauss2));
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
	
	
}
