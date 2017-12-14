package src;

import java.awt.image.BufferedImage;

/**
 * Post-processing for tone reproduction using the Ward
 * algorithm.
 * @author smoskal
 *
 */
public class WardReproduction implements ToneReproduction {

	private static final float SIGMA = .00001f;
	private float maxLuminance;
	
	public WardReproduction(float maxLuminance) {
		this.maxLuminance = maxLuminance;
	}
	
	public BufferedImage adjust(BufferedImage scene) {
		
		double sum = 0;
		
		for(int i=0; i < scene.getWidth(); i++) {
			
			for(int j=0; j < scene.getHeight(); j++) {
				
				Color color = new Color(scene.getRGB(i, j));
				sum += Math.log((double)SIGMA+(double)color.getAbsoluteLuminance());
				
			}	
		}
		
		int pixels = scene.getWidth()*scene.getHeight();
		double logAvg = (float)Math.exp((sum/pixels));
		
		double inside = (1.219+Math.pow((double)maxLuminance/2, .4))/(1.219+Math.pow((double)logAvg,.4));
		float scaleFactor = (float)Math.pow(inside,2.5);
		
		for(int i=0; i < scene.getWidth(); i++) {
			
			for(int j=0; j < scene.getHeight(); j++) {
				
				Color color = new Color(scene.getRGB(i, j));
				color.scale(scaleFactor);
				scene.setRGB(i, j, color.getIntValue());
				
			}	
		}
		
		return scene;
		
	}
	
}
