package src;
import Jama.Matrix; 

/**
 * An abstract class to represent the Shape.  Abstract for enforce the 
 * Encapsulation of the material.
 * @author smoskal
 *
 */

public abstract class Shape {

	private Color material;
	private IlluminationModel texture;
	
	// The reflection constant
	private float reflK;
	// The transmission constant
	private float tranK;
	
	public Shape() {
		this.reflK = 0.0f;
		this.tranK = 0.0f;
	}
	
	public Color getMaterial() {
		return material;
	}

	public void setMaterial(Color material) {
		this.material = material;
	}

	/**
	 * @return the reflK
	 */
	public float getReflK() {
		return reflK;
	}

	/**
	 * @param reflK the reflK to set
	 */
	public void setReflK(float reflK) {
		if(reflK < 1 || reflK > 0) {
			this.reflK = reflK;
		} else {
			throw new NumberFormatException("The reflection constant requires a value between 0 and 1: " + reflK);
		}
	}

	/**
	 * @return the tranK
	 */
	public float getTranK() {
		return tranK;
	}

	/**
	 * @param tranK the tranK to set
	 */
	public void setTranK(float tranK) {
		if(tranK < 1 || tranK > 0) {
			this.tranK = tranK;
		} else {
			throw new NumberFormatException("The transmission constant requires a value between 0 and 1: " + tranK);
		}
	}

	/**
	 * Defines the intersection on the object and will return its color.
	 * @param ray-  The ray containing the origin and direction
	 * @return- Color of the point, null if no intersection.
	 */
	abstract HitData intersect(Ray ray);
	
	/**
	 * Defines the transform function going into camera coordinates.
	 * @param matrix- the transform matrix;
	 */
	abstract void transform(Matrix matrix);

	public IlluminationModel getTexture() {
		return texture;
	}

	public void setTexture(IlluminationModel texture) {
		this.texture = texture;
	}
	
}
