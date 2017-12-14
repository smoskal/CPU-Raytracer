package src;

import Jama.Matrix;

public class Triangle extends Shape{
	
	private final float EPSILON = .000001f;
	private Point v1, v2, v3;
	
	public Triangle(Point v1, Point v2, Point v3, Color material) {
		this.v1 = new Point(v1);
		this.v2 = new Point(v2);
		this.v3 = new Point(v3);
		super.setMaterial(material);
		super.setTexture(null);
	}
	
	public Point getV1() {
		return v1;
	}

	public void setV1(Point v1) {
		this.v1 = v1;
	}

	public Point getV2() {
		return v2;
	}

	public void setV2(Point v2) {
		this.v2 = v2;
	}

	public Point getV3() {
		return v3;
	}
	
	public void setV3(Point v3) {
		this.v3 = v3;
	}
	
	/**
	 * The intersect function for a ray and triangle. 
	 *  
	 * Source: http://en.wikipedia.org/wiki/
	 * M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
	 * 
	 * @param ray- the ray
	 * @return the color if intersected.
	 */
	public HitData intersect(Ray ray) {
		
		Vector e1, e2, P, Q, T;
		float det, inv_det, u, v, t;
		
		e1 = new Vector(v2, v1);
		e2 = new Vector(v3, v1);
		
		P = ray.getDirection().cross(e2);
		det = e1.dot(P);
		
		// Miss
		if(det > -EPSILON && det < EPSILON) return new HitData(ray,World.DEFAULT_COLOR);
		inv_det = 1.0f / det;
		
		T = new Vector(ray.getOrigin(), v1);
		
		u = T.dot(P) * inv_det;
		
		// Miss
		if(u < 0.0f || u > 1.0f) return new HitData(ray,World.DEFAULT_COLOR);
		
		Q = T.cross(e1);
		
		v = ray.getDirection().dot(Q) * inv_det;
		if(v < 0.0f || u + v  > 1.0f) return new HitData(ray,World.DEFAULT_COLOR);
		
		t = Math.abs(e2.dot(Q) * inv_det);
		
		if(t > EPSILON) {
			
			Vector normal = e1.cross(e2);
			Point intersection = new Point(u,v,t);

			// Hit
			return new HitData(ray, super.getMaterial(),
					ray.getOrigin().distance(intersection),intersection, this, normal);
			
		}
		
		// Miss
		return new HitData(ray,World.DEFAULT_COLOR);

	}
	
	public void transform(Matrix transform) {
		v1.transform(transform);
		v2.transform(transform);
		v3.transform(transform);
	}
	
}
