package src;

import java.util.ArrayList;

public class BlinnPhong implements IlluminationModel {

	private float specularFactor;
	private float diffuseFactor;
	private float shine;
	private Color ambientColor;

	public BlinnPhong(float specularFactor, float diffuseFactor, float shine, Color ambientColor) {
		this.specularFactor = specularFactor;
		this.diffuseFactor = diffuseFactor;
		this.shine = shine;
		this.ambientColor = ambientColor;
	}

	public Color illuminate(HitData hit, Light light, ArrayList<Shape> shapes) {

		Color inColor = hit.getMaterial();
		Color diffuse = new Color(0f,0f,0f);
		Color specular = new Color(0f,0f,0f);



		Vector lightRay = new Vector(light.getPos(), hit.getIntersection());
		Vector normal = hit.getNormal();
		lightRay.normalize();

		float diffFactor = diffuseFactor * lightRay.dot(normal); 

		if(diffFactor > 0) {

			// Diffuse
			Color newDiffuse = new Color(light.getColor());

			newDiffuse.mulColor(inColor); 
			newDiffuse.scale(diffFactor);

			// Sum the lights
			diffuse.addColor(newDiffuse);


			Color newSpecular = new Color(0f, 0f, 0f);

			// Specular
			//Vector reflection = Light.reflect(normal, lightRay); 



			Vector viewing = hit.getViewing(); 
			viewing.normalize();
			Vector reflection = lightRay.add(viewing);
			reflection.normalize();


			float specFactor = specularFactor * (float) Math.pow(reflection.dot(normal), shine);

			if(specFactor > 0) {
				newSpecular.addColor(light.getColor());
				newSpecular.scale(specFactor);

				specular.addColor(newSpecular);
			}
		}

		Color outColor = new Color(ambientColor);
		outColor.addColor(diffuse);
		outColor.addColor(specular);

		return outColor;
	}

	private ArrayList<Light> getVisibleLights(HitData hit, ArrayList<Light> lights, ArrayList<Shape> shapes) {
		ArrayList<Light> visibleLights = new ArrayList<Light>();

		for(Light light:lights) {
			Ray shadowRay = new Ray(hit.getIntersection(), new Vector(light.getPos(), hit.getIntersection()));
			boolean shapeHit = false;

			for(Shape shape:shapes) {
				HitData interData = shape.intersect(shadowRay);
				if(interData.hit()) {
					shapeHit = true;
				}

			}

			if(!shapeHit) {
				visibleLights.add(light);
			}
		}
		return visibleLights;
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
