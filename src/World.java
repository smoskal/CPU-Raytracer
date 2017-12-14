package src;

import java.util.ArrayList;

import Jama.Matrix;

public class World {


	// A default color to be used as the background if no ray is hit. 
	public static final Color DEFAULT_COLOR = new Color(.5f,.8f,.99f);
	public static final float TRANS_K = 1f;
	private final float EPSILON = .001f;
	
	// These are the shapes that populate the world
	private ArrayList<Shape> shapeList;
	private ArrayList<Light> lightList;
	private IlluminationModel illuminationModel;

	/**
	 * Creates an empty world.
	 */
	public World() {
		shapeList = new ArrayList<Shape>();
		lightList = new ArrayList<Light>();
		this.illuminationModel = new Phong(1f,1f,1f, Color.WHITE);
	}

	/**
	 * Creates an empty world.
	 */
	public World(IlluminationModel model) {
		shapeList = new ArrayList<Shape>();
		lightList = new ArrayList<Light>();
		this.illuminationModel = model;
	}

	/**
	 * Creates a world filled with the given shapes.
	 * 
	 * @param shapeList - The shapes to fill the world with.
	 */
	public World(ArrayList<Shape> shapeList, ArrayList<Light> lightList) {
		this.shapeList = shapeList;
		this.lightList = lightList;
	}

	/**
	 * Add a new shape to the world.
	 * 
	 * @param shape - The shape to be added.
	 */
	public void add(Shape shape) {
		shapeList.add(shape);
	}

	/**
	 * Add the light to list
	 * @param light - DA LIGHT
	 */
	public void add(Light light) {
		lightList.add(light);
	}

	/**
	 * Transform all of the shapes according to the given matrix.
	 * 
	 * @param transformMatrix - The matrix to transform the shapes with.
	 */
	public void transformAllObjects(Matrix transformMatrix) {
		//		for(Shape shape:shapeList) {
		//			shape.transform(transformMatrix);
		//		}
		//		
		//		for(Light light:lightList) {
		//			light.transform(transformMatrix);
		//		}
	}


	private ArrayList<Light> getVisibleLights(HitData hit, ArrayList<Light> lights, ArrayList<Shape> shapes) {
		ArrayList<Light> visibleLights = new ArrayList<Light>();

		for(Light light:lights) {
			Ray shadowRay = new Ray(hit.getIntersection(), new Vector(light.getPos(), hit.getIntersection()));
			boolean shapeHit = false;

			for(Shape shape:shapes) {
				HitData interData = shape.intersect(shadowRay);
				if(interData.hit()) {
					if(EPSILON < hit.getIntersection().distance(interData.getIntersection()))
						shapeHit = true;
				}

			}

			if(!shapeHit) {
				visibleLights.add(light);
			}
		}
		return visibleLights;
	}

	/**
	 * Spawns a ray in the world and reports back what the ray hit.
	 * 
	 * @param ray - The ray to travel in the world.
	 * @return The color of the point that was hit.
	 */
	public Color spawn(Ray ray, int depth) {
		HitData closestData = new HitData(ray);
		IlluminationModel texture = null;
		float distance = 1000f;

		for(Shape shape:shapeList) {

			HitData currData = shape.intersect(ray);

			if(currData.hit() && 
					distance != Math.min(distance, currData.getDistance()) &&
					ray.getOrigin().distance(currData.getIntersection()) > EPSILON) {
				distance = currData.getDistance();
				closestData = currData;
				texture = shape.getTexture();
			}

		}

		if(!closestData.hit()){
			return World.DEFAULT_COLOR;
		} else {


			if(texture != null) {
				closestData.setMaterial(texture.illuminate(closestData, null, null));
			}

			ArrayList<Light> visibleLights = getVisibleLights(closestData, lightList, shapeList);

			Color illumColor = new Color(0, 0, 0);

			for(Light light : visibleLights) {
				
				illumColor.addColor(illuminationModel.illuminate(closestData, light, shapeList));

				if(depth < IlluminationModel.MAX_DEPTH) {
					Shape hitShape = closestData.getShape();
					
					if(hitShape.getReflK() > 0) {
						Vector lightRay = new Vector(light.getPos(), closestData.getIntersection());
						
						Vector reflection = Light.reflect(closestData.getNormal(), lightRay);
						Color refColor = spawn(new Ray(closestData.getIntersection(), reflection), depth + 1);
						if(!refColor.equals(World.DEFAULT_COLOR))
							refColor.scale(hitShape.getReflK());
						illumColor.addColor(refColor);
					}

					if(hitShape.getTranK() > 0) {
						float ni, nt;
						Vector normal;
						if(closestData.getNormal().dot(ray.getDirection()) < 0) {
							ni = hitShape.getTranK();
							nt = World.TRANS_K;
							normal = new Vector(closestData.getNormal().multiply(-1));
						} else {
							ni = World.TRANS_K;
							nt = hitShape.getTranK();
							normal = new Vector(closestData.getNormal());
						}
						
						
						Ray transRay = Ray.generateTransRay(ray, closestData.getIntersection(), 
								ni, nt, normal);
						
						Color refColor = spawn(transRay, depth + 1);
						if(!refColor.equals(World.DEFAULT_COLOR))
							refColor.scale(nt);
						illumColor.addColor(refColor);
					}
				}

			}
			return illumColor;

		}
	}
}
