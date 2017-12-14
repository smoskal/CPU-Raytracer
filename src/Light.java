package src;

import Jama.Matrix;

/**
 * Represents the light as an infinitesimally small, omnidirectional light.
 * @author smoskal
 *
 */
public class Light {

	private Color color; 
	private Point pos;
	
	public Light(Color color, Point pos) {
		this.color = color;
		this.pos = pos;
	}
	
	public Light(Point pos) {
		this.color = Color.WHITE;
		this.pos = pos;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	public void transform(Matrix transform) {
		pos.transform(transform);
	}
	
	public static Vector reflect(Vector source, Vector normal) {
		
		Vector reflection = new Vector(normal);
		
		float dot = source.dot(normal);
		
		float division = (float) (2*(dot/Math.pow(normal.length(), 2)));
		reflection = reflection.multiply(division);
		
		return source.add(reflection);
	}
	
}
