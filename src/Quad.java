package src;

import Jama.Matrix;

public class Quad extends Shape {
	private Triangle triangleOne, triangleTwo;

	public Quad (Point v1, Point v2, Point v3, Point v4, Color material) {
		this.triangleOne = new Triangle(v1, v2, v3, material);
		this.triangleTwo = new Triangle(v4, v2, v3, material);
		
		super.setMaterial(material);
	}
	
	public Quad(Triangle triangleOne, Triangle triangleTwo, Color material) {
		this.triangleOne = triangleOne;
		this.triangleTwo = triangleTwo;
		
		this.triangleOne.setMaterial(material);
		this.triangleTwo.setMaterial(material);
		
		super.setMaterial(material);
	}
	
	@Override
	public HitData intersect(Ray ray) {
		HitData returnData = triangleOne.intersect(ray);
		
		if(returnData.getDistance() == -1) {
			return triangleTwo.intersect(ray);
		}
		
		return returnData;
	}

	@Override
	public void transform(Matrix transform) {
		triangleOne.transform(transform);
		triangleTwo.transform(transform);
	}

}
