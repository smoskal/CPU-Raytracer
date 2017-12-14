package src;
import java.util.ArrayList;

/**
 * An interface to describe the illumination models.
 * @author smoskal
 *
 */
public interface IlluminationModel {

	// The maximum amount of reflections allowed
	public static final int MAX_DEPTH = 5;
	
	public Color illuminate(HitData hit, Light light, ArrayList<Shape> shapes); 
}
