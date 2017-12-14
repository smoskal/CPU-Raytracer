package src;

/**
 * A class to represent the what happened when the Ray was shot.
 * @author smoskal
 *
 */
public class HitData { 
	
	/**
	 * The ray that was shot at this time
	 */
	private Ray ray;
	
	/**
	 * Color of the object hit.
	 */
	private Color material;
	
	/**
	 * Distance from the ray to the intersection point
	 */
	private float distance;
	
	/**
	 * Point at which the intersection took place.
	 */
	private Point intersection;
	private Vector normal;
	private Vector viewing;
	private Shape shape;
	
	/**
	 * UV mappings for the texture if applicable.
	 */
	private float u;
	private float v;
	
	
	private boolean hit;
	
	/**
	 * Default constructor for when nothing was hit.  (Set to default color)
	 * @param ray
	 */
	public HitData(Ray ray) {
		this.ray 			= ray;
		this.material 		= World.DEFAULT_COLOR;
		this.distance 		= -1;
		this.intersection 	= null;
		this.normal 		= null;
		this.hit 			= false;
	}
	
	public HitData(Ray ray, Color material) {
		this.ray			= ray;
		this.material 		= material;
		this.distance		= -1;
		this.intersection 	= null;
		this.normal			= null;
		this.hit 			= false;
	}
	
	public HitData(Ray ray, Color material, float distance,
			Point intersection, Shape shape, Vector normal) {
		this.ray 			= ray;
		this.material 		= material;
		this.distance 		= distance;
		this.intersection 	= intersection;
		this.shape 			= shape;
		this.normal 		= normal;
		this.viewing = new Vector(ray.getOrigin(), intersection);
		this.hit			= true;
	}
	
	public HitData(Ray ray, Color material, float distance,
			Point intersection, Shape shape, Vector normal, float u, float v) {
		this.ray 			= ray;
		this.material 		= material;
		this.distance 		= distance;
		this.intersection 	= intersection;
		this.setShape(shape);
		this.normal 		= normal;
		this.viewing = new Vector(ray.getOrigin(), intersection);
		this.hit			= true;
		this.u				= u;
		this.v 				= v;
	}

	public Ray getRay() {
		return ray;
	}

	public void setRay(Ray ray) {
		this.ray = ray;
	}

	public Color getMaterial() {
		return material;
	}

	public void setMaterial(Color material) {
		this.material = material;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public Point getIntersection() {
		return intersection;
	}

	public void setIntersection(Point intersection) {
		this.intersection = intersection;
	}

	/**
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Vector getNormal() {
		return normal;
	}

	public void setNormal(Vector normal) {
		this.normal = normal;
	}

	/**
	 * @return the viewing
	 */
	public Vector getViewing() {
		return viewing;
	}

	/**
	 * @param viewing the viewing to set
	 */
	public void setViewing(Vector viewing) {
		this.viewing = viewing;
	}

	/**
	 * @return whether there was a hit or not
	 */
	public boolean hit() {
		return hit;
	}

	/**
	 * @param hit did we hit an object
	 */
	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public float getU() {
		return u;
	}

	public void setU(float u) {
		this.u = u;
	}

	public float getV() {
		return v;
	}

	public void setV(float v) {
		this.v = v;
	}
	
	
	
}
