package src;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Jama.Matrix;



public class Camera {

	private static final int RESOLUTION_WIDTH = 512;
	private static final int RESOLUTION_HEIGHT = 512;
	
	private boolean doSuperSample;
	
	private Point origin;

	private Vector position;
	private Vector lookAt;
	private Vector up;

	private Vector n;
	private Vector v;
	private Vector u;

	private int focalLength; 
	private int viewWidth, viewHeight; 
	private int imageWidth, imageHeight;

	private float pixelWidth, pixelHeight;

	Matrix viewTransform;

	/**
	 * Defines a camera object.
	 * 
	 * @param position - Where the camera is locate.
	 * @param lookAt - Where the camera is currently looking.
	 * @param up - The orientation of the camera.
	 * @param focalLength - The distance to the center of the film plane
	 * @param viewWidth - The width of the film plane.
	 * @param viewHeight - The height of the film plane
	 * @param imageWidth - The amount of horizontal pixels.
	 * @param imageHeight - The amount of vertical pixels.
	 */
	public Camera(Vector position, Vector lookAt, Vector up,
			int focalLength, 
			int viewWidth, int viewHeight, 
			int imageWidth, int imageHeight) {
		this.position = position;
		this.lookAt = lookAt;
		this.up = up;

		origin = new Point(position.getX(), position.getY(), position.getZ());

		viewTransform = Matrix.identity(4, 4);

		// Calculate our 
		calculateNUV();

		this.focalLength = focalLength;
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;

		pixelWidth = (float) viewWidth/ (float) imageWidth;
		pixelHeight = (float) viewHeight/ (float) imageHeight;

	}

	/**
	 * Calculates the values for n, u, and v.
	 */
	private void calculateNUV() {
		n = position.subtract(lookAt);
		n.normalize();

		u = up.cross(n);
		u.normalize();

		v = n.cross(u);

		calculateViewMatrix();

	}

	/**
	 * Calculates the view matrix
	 */
	public void calculateViewMatrix() {
		// Set row one
		viewTransform.set(0, 0, u.getX());
		viewTransform.set(0, 1, u.getY());
		viewTransform.set(0, 2, u.getZ());
		viewTransform.set(0, 3, -position.dot(u));

		// Set row two
		viewTransform.set(1, 0, v.getX());
		viewTransform.set(1, 1, v.getY());
		viewTransform.set(1, 2, v.getZ());
		viewTransform.set(1, 3, -position.dot(v));

		// Set row three
		viewTransform.set(2, 0, n.getX());
		viewTransform.set(2, 1, n.getY());
		viewTransform.set(2, 2, n.getZ());
		viewTransform.set(2, 3, -position.dot(n));
	}

	/**
	 * @return the position
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * @return the lookAt
	 */
	public Vector getLookAt() {
		return lookAt;
	}

	/**
	 * @return the up
	 */
	public Vector getUp() {
		return up;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector position) {
		this.position = position;
		calculateNUV();
		origin = new Point(position.getX(), position.getY(), position.getZ());
	}

	/**
	 * @param lookAt the lookAt to set
	 */
	public void setLookAt(Vector lookAt) {
		this.lookAt = lookAt;
		calculateNUV();
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(Vector up) {
		this.up = up;
		calculateNUV();
	}

	public void superSample(boolean doSample) {
		this.doSuperSample = doSample;
	}
	
	/**
	 * Generates and displays an image dependent on the scene given.
	 * 
	 * @param world - The scene to display.
	 */
	public void render(World world, ToneReproduction tone) {
		// Transform the shapes to camera space

		world.transformAllObjects(viewTransform);

		BufferedImage scene = new BufferedImage((int) imageWidth, (int) imageHeight, BufferedImage.TYPE_INT_RGB);

		// These are the bounds for the image
		float topBound = (float) viewHeight/2f;
		float bottomBound = -1f * (float) viewHeight/2f;
		float leftBound = -1f * (float) viewWidth/2f;
		float rightBound = (float) viewWidth/2f;

		float superSample = 4;
		float superPixel = pixelWidth/4;

		// Spawn a ray at each pixel and set it in our image
		for(float j = leftBound + pixelWidth/2, x = 0; j < rightBound; j += pixelWidth, x++) {
			for(float k = topBound - pixelHeight/2, y = 0; k > bottomBound; k -= pixelHeight, y++) {
				Color pixColor;
				if(doSuperSample) {
					float totR = 0, totG = 0, totB = 0;
					Color hitColor = world.spawn(new Ray(origin, new Vector(j + superPixel, k + superPixel, focalLength)), 1);
					totR += hitColor.getRed();
					totB += hitColor.getBlue();
					totG += hitColor.getGreen();

					hitColor = world.spawn(new Ray(origin, new Vector(j + superPixel, k - superPixel, focalLength)), 1);
					totR += hitColor.getRed();
					totB += hitColor.getBlue();
					totG += hitColor.getGreen();

					hitColor = world.spawn(new Ray(origin, new Vector(j - superPixel, k + superPixel, focalLength)), 1);
					totR += hitColor.getRed();
					totB += hitColor.getBlue();
					totG += hitColor.getGreen();

					hitColor = world.spawn(new Ray(origin, new Vector(j - superPixel, k - superPixel, focalLength)), 1);
					totR += hitColor.getRed();
					totB += hitColor.getBlue();
					totG += hitColor.getGreen();

					pixColor = new Color(totR/superSample,
							totG/superSample, totB/superSample);

				} else {
					pixColor = world.spawn(new Ray(origin, new Vector(j, k, focalLength)), 1);
				}
				scene.setRGB((int) x, (int) y, pixColor.getIntValue());
			}			
		}
		
		if(tone != null) scene = tone.adjust(scene);
		display(scene);
	}

	public void display(BufferedImage scene) {
		JLabel label = new JLabel(new ImageIcon(scene));
		JPanel panel = new JPanel();
		panel.add(label);
		JFrame frame = new JFrame();

		frame.add(panel);
		frame.setSize(scene.getWidth(), scene.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setTitle("Raymond");
		frame.setResizable(false);
		frame.setVisible(true); // This is what displays the image
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		Phong phongModel = new Phong(.25f,2f,10f, new Color(0f,0f,0f));
		Checkerboard checkers = new Checkerboard(2f,new Color(.5f,.5f,0f), new Color(.5f,0f,0f));

		World world = new World(phongModel);

		// The front bigger sphere

		Sphere frontSphere = new Sphere(new Point(0f, 1f, 4f), .75f, new Color(0f, .3f, .1f));
		frontSphere.setReflK(0f);
		frontSphere.setTranK(1.05f);
		world.add(frontSphere);

		// The back right sphere
		Sphere backSphere = new Sphere(new Point(1f, .25f, 6f), .55f, new Color(.1f, .5f, 1f));
		backSphere.setReflK(1f);
		backSphere.setTranK(0f);
		world.add(backSphere);		

		Plane plane = new Plane(new Point(0, 1, 0), 1f, new Color(.65f, 0, 0), checkers);
		plane.setReflK(0f);
		world.add(plane);

		world.add(new Light(new Point(1f, 2f, 0f)));
		//world.add(new Light(new Point(-1f, 2f, 0f)));
		Camera cam = new Camera(
				new Vector(0f, 1f, 0f), 
				new Vector(0f, 0f, 6f),
				new Vector(0f, 0f, 1f),
				6, 5, 5, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);

		cam.superSample(true);
		
		ToneReproduction tone = null;
		tone = new WardReproduction(1f);
		//tone = new ReinhardReproduction(1f);
		
		cam.render(world,tone);
	}
}
