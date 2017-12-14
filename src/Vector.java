package src;

public class Vector {
	private float x;
	private float y;
	private float z;
	
	private float length;
	
	/**
	 * Create a point initialized to zero. 
	 */
	public Vector() {
		x = 0f;
		y = 0f;
		z = 0f;
		length = 0f;
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param vec - vector to copy
	 */
	public Vector(Vector vec) {
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
		length = calculateLength();
	}
	
	/**
	 * Create a point initialized to the given values.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		length = calculateLength();
	}
	
	public Vector(Point dest, Point source) {
		this.x = dest.getX() - source.getX();
		this.y = dest.getY() - source.getY();
		this.z = dest.getZ() - source.getZ();
		length = calculateLength();
	}
	
	private float calculateLength() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
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
		length = calculateLength();
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
		length = calculateLength();
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
		length = calculateLength();
	}
	
	/**
	 * Adds the two vectors together.
	 * 
	 * @param inVec
	 * @return A new vector with the added values.
	 */
	public Vector add(Vector inVec) {
		return new Vector(x + inVec.getX(), y + inVec.getY(), z + inVec.getZ());
	}
	
	/**
	 * Subtracts the two vectors together.
	 * 
	 * @param inVec
	 * @return A new vector with the subtracted values.
	 */
	public Vector subtract(Vector inVec) {
		return new Vector(x - inVec.getX(), y - inVec.getY(), z - inVec.getZ());
	}
	
	/**
	 * Calculates the cross product between this vector and the given vector.
	 * 
	 * @param inVec
	 * @return The cross product
	 */
	public Vector cross(Vector inVec) {
		return new Vector(y * inVec.getZ() - z * inVec.getY(), 
				z * inVec.getX() - x * inVec.getZ(),
				x * inVec.getY() - y * inVec.getX());
	}
	
	/**
	 * Calculates the dot product between this vector and the given vector
	 * 
	 * @param inVec
	 * @return The dot product.
	 */
	public float dot(Vector inVec) {
		return x * inVec.getX() + y * inVec.getY() + z * inVec.getZ();
	}
	
	/**
	 * The length of this vector.
	 * 
	 * @return The length of the vector.
	 */
	public float length() {
		return length;
	}
	
	/**
	 * Normalizes this vector.
	 */
	public void normalize() {
		if(length != 0) {
			x = x/length;
			y = y/length;
			z = z/length;
			length = calculateLength();
		}
	}
	
	public float distance(Vector vec) {
		float xd = vec.getX() - x;
		float yd = vec.getY() - y;
		float zd = vec.getZ() - z;
		
		float result = (float)Math.sqrt((double)(xd*xd+yd*yd+zd*zd));
		return result;
	}
	
	public void transform() {
		
	}
	
	public Vector multiply(float m){
		return new Vector(x*m, y*m, z*m);	
	}
	
	@Override
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}
	
	@Override
	public boolean equals(Object obj) {
		Vector newVector = (Vector) obj;
		return x == newVector.getX() && 
			   y == newVector.getY() && 
			   z == newVector.getZ();
	}
}
