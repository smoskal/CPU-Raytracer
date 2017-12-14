package src;
import Jama.Matrix;

/**
 * A simple class to represent a plane and its intersection characteristics.
 * @author smoskal
 *
 */
public class Plane extends Shape {

	private Point normal;
	private float F;
	
	/**
	 * A very basic constructor for the plane.
	 * @param normal-  The normal 
	 * @param F
	 * @param material
	 */
	public Plane(Point normal, float F, Color material) {
		
		super.setMaterial(material);
		this.normal = normal;
		this.F = F;
		super.setTexture(null);
		
	}
		
	public Plane(Point normal, float F, Color material, IlluminationModel texture) {
		
		super.setMaterial(material);
		this.normal = normal;
		this.F = F;
		super.setTexture(texture);
		
	}
	
	public Point getNormal() {
		return normal;
	}

	public void setNormal(Point normal) {
		this.normal = normal;
	}

	public float getF() {
		return F;
	}

	public void setF(float f) {
		F = f;
	}

	public HitData intersect(Ray ray) {
		Vector dir = ray.getDirection();
		dir.normalize();
		
		//Get the direction
		float dx = dir.getX();
		float dy = dir.getY();
		float dz = dir.getZ();
		
		//Get the origin of the Ray
		float x0 = ray.getOrigin().getX();
		float y0 = ray.getOrigin().getY();
		float z0 = ray.getOrigin().getZ();
		
		float A = normal.getX();
		float B = normal.getY();
		float C = normal.getZ();
		
		float w = (-(A*x0+B*y0+C*z0+F))/(A*dx+B*dy+C*dz);
		
		if(w == 0 || w < 0) {
			return new HitData(ray,World.DEFAULT_COLOR);
		} else {
			float xi = x0+dx*w;
			float yi = y0+dy*w;
			float zi = z0+dz*w;
			Point intersection = new Point(xi,yi,zi);
			Vector normal = new Vector(A,B,C); 
			
			float u = (zi+1)/2;
			float v = (xi+1)/2;
			
			ray.setEnclosingShape(this);
			
			return new HitData(ray, super.getMaterial(),
					ray.getOrigin().distance(intersection), intersection, this, normal,u,v);
		}
		
	}
	
	public void transform(Matrix transform) {
		normal.transform(transform);
	}
	
}
