package src;

import java.awt.image.BufferedImage;

public class ReinhardReproduction implements ToneReproduction{
	
	private static final float ZONE = .18f;
	private static final float SIGMA = .00000001f;
	private float maxLuminance;
	
	public ReinhardReproduction(float maxLuminance) {
		this.maxLuminance = maxLuminance;
	}
	
	public BufferedImage adjust(BufferedImage scene) {
		
		float sum = 0;
		
		for(int i=0; i < scene.getWidth(); i++) {
			
			for(int j=0; j < scene.getHeight(); j++) {
				
				Color color = new Color(scene.getRGB(i, j));
				sum += Math.log((double)SIGMA+(double)color.getAbsoluteLuminance());
				
			}	
		}
		
		float pixels = scene.getWidth()*scene.getHeight();
		float logAvg = (float)Math.exp((double)(sum/pixels));
				
		for(int i=0; i < scene.getWidth(); i++) {
			
			for(int j=0; j < scene.getHeight(); j++) {
				
				Color color = new Color(scene.getRGB(i, j));
				color.scale(ZONE/logAvg);
				
				color.setRed(color.getRed()/(1+color.getRed()));
				color.setGreen(color.getGreen()/(1+color.getGreen()));
				color.setBlue(color.getBlue()/(1+color.getBlue()));
				
				color.scale(maxLuminance);
				
				scene.setRGB(i, j, color.getIntValue());
				
			}	
		}
		
		return scene;
		
	}
	

}
