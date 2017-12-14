package src;
import Jama.Matrix;

/**
 * A class to represent the shape of a sphere math mathematically.
 * @author smoskal
 *
 */
public class Sphere extends Shape{

	private final float EPSILON = .000001f;

	private Point center;
	private float radius;

	/**
	 * Constuctor to represent the sphere.
	 * @param center- Center point of the sphere.
	 * @param radius- Radius of the sphere.
	 * @param material- Color/material of the sphere.
	 */
	public Sphere(Point center, float radius, Color material) {
		super.setMaterial(material);
		super.setTexture(null);
		this.center = center;
		this.radius = radius;
		
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public HitData intersect(Ray ray) {
		Vector dir = ray.getDirection();

		//Get the direction
		float dx = dir.getX();
		float dy = dir.getY();
		float dz = dir.getZ();

		//Get the origin of the Ray
		float x0 = ray.getOrigin().getX();
		float y0 = ray.getOrigin().getY();
		float z0 = ray.getOrigin().getZ();

		//Get the center of the sphere.
		float xc = center.getX();
		float yc = center.getY();
		float zc = center.getZ();

		float A = dx*dx + dy*dy + dz*dz;

		float B = 2*(dx*(x0-xc)+dy*(y0-yc)+dz*(z0-zc));

		float C = (float)Math.pow((double)(x0-xc), 2) + 
				(float)Math.pow((double)(y0-yc), 2) + 
				(float)Math.pow((double)(z0-zc), 2) -
				radius * radius;

		float radicand = B*B - 4*A*C;

		//If the radicand is less than zero, there is no real root and 
		//the ray did not intersect the sphere.
		if(radicand < 0) {
			return new HitData(ray, World.DEFAULT_COLOR);
		}
		//If the radicand is equal to 0, there is only one root.
		else if(radicand < EPSILON && radicand > -EPSILON) {

			float w = (-B-(float)Math.sqrt(radicand))/(2*A);

			if(w < 0) {
				return new HitData(ray, World.DEFAULT_COLOR);
			}
			
			float xi = x0+dx*w;
			float yi = y0+dy*w;
			float zi = z0+dz*w;
			Point intersection = new Point(xi,yi,zi);
			Vector normal = new Vector(intersection, center);

			ray.setEnclosingShape(this);
			
			return new HitData(ray, super.getMaterial(),
					ray.getOrigin().distance(intersection),intersection, this, normal);
		}
		//Else we have 2 roots, in which we will take the closest.
		else {

			float wp = (-B+(float)Math.sqrt(radicand))/(2*A);
			float wn = (-B-(float)Math.sqrt(radicand))/(2*A);
			float xi,yi,zi;

			if(wp < wn && wp > 0) {
				xi = x0+dx*wp;
				yi = y0+dy*wp;
				zi = z0+dz*wp;
			}else if (wn < wp && wn > 0){
				xi = x0+dx*wn;
				yi = y0+dy*wn;
				zi = z0+dz*wn;
			} else {
				return new HitData(ray, World.DEFAULT_COLOR);
			}
			
			Point intersection = new Point(xi,yi,zi);
			Vector normal = new Vector(intersection, center);

			ray.setEnclosingShape(this);
			
			return new HitData(ray, super.getMaterial(),
					ray.getOrigin().distance(intersection),
					intersection, this, normal);
		}

	}

	public void transform(Matrix transform) {
		center.transform(transform);
	}


}
