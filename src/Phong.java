package src;

import java.util.ArrayList;

/**
 * A class to represent the Phong shading model;
 * 
 * @author smoskal
 *
 */
public class Phong implements IlluminationModel{	

	private float specularFactor;
	private float diffuseFactor;
	private float shine;
	private Color ambientColor;

	public Phong(float specularFactor, float diffuseFactor, float shine, Color ambientColor) {
		this.specularFactor = specularFactor;
		this.diffuseFactor = diffuseFactor;
		this.shine = shine;
		this.ambientColor = ambientColor;
	}

	public Color getDiffuseColor(HitData hit, Light light, Vector lightRay) {
		Vector normal = hit.getNormal();

		float diffFactor = diffuseFactor * lightRay.dot(normal); 

		if(diffFactor > 0) {

			// Diffuse
			Color newDiffuse = new Color(light.getColor());

			newDiffuse.mulColor(hit.getMaterial()); 
			newDiffuse.scale(diffFactor);

			// Sum the lights
			return newDiffuse;
		} else {
			return Color.BLACK;
		}
	}

	public Color getSpecularColor(HitData hit, Light light, Vector lightRay) {
		Vector normal = hit.getNormal();

		// Specular
		Vector reflection = Light.reflect(normal, lightRay); 
		Vector viewing = hit.getViewing(); 

		reflection.normalize();
		viewing.normalize();

		float specFactor = specularFactor * (float) Math.pow(reflection.dot(viewing), shine);

		if(specFactor > 0) {
			Color newSpecular = new Color(1f, 1f, 1f);
			newSpecular.mulColor(light.getColor());
			newSpecular.scale(specFactor);

			return newSpecular;
		} else {
			return Color.BLACK;
		}
	}

	public Color localIlluminate(HitData hit, Light light, ArrayList<Shape> shapes) {
		Vector lightRay = new Vector(light.getPos(), hit.getIntersection());
		lightRay.normalize();

		Color outColor = new Color(ambientColor);
		outColor.addColor(getDiffuseColor(hit, light, lightRay));
		outColor.addColor(getSpecularColor(hit, light, lightRay));

		return outColor;
	}

	public Color illuminate(HitData hit, Light light, ArrayList<Shape> shapes) {
		return localIlluminate(hit, light, shapes);
	}


	public float getSpecularFactor() {
		return specularFactor;
	}

	public void setSpecularFactor(float specularFactor) {
		this.specularFactor = specularFactor;
	}

	public float getDiffuseFactor() {
		return diffuseFactor;
	}

	public void setDiffuseFactor(float diffuseFactor) {
		this.diffuseFactor = diffuseFactor;
	}

	public float getShine() {
		return shine;
	}

	public void setShine(float shine) {
		this.shine = shine;
	}

	public Color getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Color ambientColor) {
		this.ambientColor = ambientColor;
	}

}
