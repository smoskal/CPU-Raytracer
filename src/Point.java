package src;

import Jama.Matrix;


public class Point {
	private float x;
	private float y;
	private float z;
	
	/**
	 * Create a point initialized to zero. 
	 */
	public Point() {
		x = 0f;
		y = 0f;
		z = 0f;
	}
	
	/**
	 * Create a point initialized to the given values.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(Point newPoint) {
		this.x = newPoint.getX();
		this.y = newPoint.getY();
		this.z = newPoint.getZ();
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * Calculates the distance between two points.
	 * 
	 * @param destPoint - The point to calculate against.
	 * @return The distance between the two points.
	 */
	public float distance(Point destPoint) {
		return (float) Math.sqrt(Math.pow(x - destPoint.getX(), 2) +
				Math.pow(y - destPoint.getY(), 2) +
				Math.pow(z - destPoint.getZ(), 2));
	}
	
	public Point add(Point addPoint) {
		return new Point(x + addPoint.getX(),
						 y + addPoint.getY(),
						 z + addPoint.getZ());
	}
	
	public Point subtract(Point subPoint) {
		return new Point(x - subPoint.getX(),
						 y - subPoint.getY(),
						 z - subPoint.getZ());
	}
	
	public Point scale(float scalar) {
		return new Point(scalar * x,
				scalar * y,
				scalar * z);
	}
	
	public void transform(Matrix transform) {
		Matrix pt = Matrix.identity(1,4);
		pt.set(0, 0, x);
		pt.set(0, 1, y);
		pt.set(0, 2, z);
		pt.set(0, 3, 1);
		
		Matrix transformed = pt.times(transform);
		
		x = (float) transformed.get(0, 0);
		y = (float) transformed.get(0, 1);
		z = (float) transformed.get(0, 2);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
