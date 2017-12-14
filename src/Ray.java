package src;


public class Ray {
	private Point origin;
	private Vector direction;
	private float length;

	private Shape enclosingShape;
	
	public Ray(Ray copyRay) {
		this.origin = copyRay.getOrigin();
		this.direction = copyRay.getDirection();
		this.length = copyRay.getLength();
		this.enclosingShape = copyRay.getEnclosingShape();
	}
	
	public Ray(Point origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
		this.length = direction.length();
		this.direction.normalize();
		this.enclosingShape = null;
	}

	/**
	 * @return the origin
	 */
	public Point getOrigin() {
		return origin;
	}

	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Vector direction) {
		this.direction = direction;
		this.direction.normalize();
	}

	public Shape getEnclosingShape() {
		return enclosingShape;
	}

	public void setEnclosingShape(Shape shape) {
		this.enclosingShape = shape;
	}

	/**
	 * @return the length
	 */
	public float getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(float length) {
		this.length = length;
	}

	public static Ray generateTransRay(Ray inVector, Point intersection, float ni, float nt, Vector normal) {

		if(ni == nt) {
			return new Ray(intersection, inVector.getDirection());
		} else {
			// 
			Vector transVect;
			// d
			Vector d = new Vector(inVector.getDirection());
			// n
			Vector norm = new Vector(normal);

			// d * n
			float dn = d.dot(norm);

			// Inside of right side square root
			float sqrVal = (float) (1 - ( ((ni * ni) * (1 - (dn * dn))) / (nt * nt)));

			transVect = norm.multiply((float) Math.sqrt(sqrVal));

			transVect = transVect.add(d.subtract(norm.multiply(dn)).multiply(ni/nt));

			if(sqrVal >= 0) {
				return new Ray(intersection, transVect);
			} else {
				return new Ray(intersection, Light.reflect(norm.multiply(-1), inVector.getDirection()));
			}
		}
	}
}
