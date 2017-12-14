package src;

public class Color {
	
	public static final Color RED = new Color(1f, 0f, 0f);
	public static final Color GREEN = new Color(0f, 1f, 0f);
	public static final Color BLUE = new Color(0f, 0f, 1f);
	public static final Color WHITE = new Color(1f, 1f, 1f);
	public static final Color BLACK = new Color(0f, 0f, 0f);
	
	private static final float MIN_VAL = 0f;
	private static final float MAX_VAL = 1f;
	
	private float red;
	private float green;
	private float blue;
	
	public Color() {
		red = 0f;
		green = 0f;
		blue = 0f;
	}
	
	public Color(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public Color(Color newColor) {
		this.red = newColor.getRed();
		this.green = newColor.getGreen();
		this.blue = newColor.getBlue();
	}
	
	public Color(int rgb) {
		
		this.blue = (float)(rgb & 255)/255f;
		this.green = (float)((rgb >> 8) & 255)/255f;
		this.red = (float)((rgb >> 16) & 255)/255f;
	
	/*	
	this.red = (rgb >> 16) & 0xFF;
	this.green = (rgb >> 8) & 0xFF;
	this.blue = rgb & 0xFF;
	*/
		
	}

	private float bound(float colorVal) {
		float boundedVal = colorVal;
		boundedVal = Math.min(MAX_VAL, colorVal);
		return Math.max(MIN_VAL, boundedVal);
	}
	
	/**
	 * @return the red
	 */
	public float getRed() {
		return red;
	}

	/**
	 * @return the green
	 */
	public float getGreen() {
		return green;
	}

	/**
	 * @return the blue
	 */
	public float getBlue() {
		return blue;
	}

	/**
	 * @param red the red to set
	 */
	public void setRed(float red) {
		this.red = bound(red);
	}

	/**
	 * @param green the green to set
	 */
	public void setGreen(float green) {
		this.green = bound(green);
	}

	/**
	 * @param blue the blue to set
	 */
	public void setBlue(float blue) {
		this.blue = bound(blue);
	}
	
	/**
	 * Scale the color by the given value
	 * 
	 * @param scalar
	 */
	public void scale(float scalar) {
		setRed(getRed() * scalar);
		setGreen(getGreen() * scalar);
		setBlue(getBlue() * scalar);
	}
	
	/**
	 * Multiply this color by the given color
	 * 
	 * @param newColor
	 */
	public void mulColor(Color newColor) {
		setRed(newColor.getRed() * getRed());
		setGreen(newColor.getGreen() * getGreen());
		setBlue(newColor.getBlue() * getBlue());
	}
	
	/**
	 * Add this color to the new color
	 * 
	 * @param newColor
	 */
	public void addColor(Color newColor) {
		setRed(newColor.getRed() + getRed());
		setGreen(newColor.getGreen() + getGreen());
		setBlue(newColor.getBlue() + getBlue());
	}

	/**
	 * Generate a single integer value that represents the color
	 * 
	 * @return The integer value that represents the color
	 */
	public int getIntValue() {
		int rgb = (int) (red*255);
		rgb = (rgb << 8) + (int) (green*255);
		rgb = (rgb << 8) + (int) (blue*255);
		
		return rgb;
	}
	
	@Override
	public boolean equals(Object obj) {
		Color otherColor = (Color) obj;
		return otherColor.getIntValue() == getIntValue();
	}
	
	public float getAbsoluteLuminance(){
		return red*.27f + green*.67f + blue*.06f;
	}
	
	
	
}
