package src;

import java.awt.image.BufferedImage;

public interface ToneReproduction {
	
	/**
	 * Method that will perform the post-processing on the 
	 * scene depending on the algorithm used.
	 * @param scene
	 * @return
	 */
	public BufferedImage adjust(BufferedImage scene);
	
}
