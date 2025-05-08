/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Ensures that an object can check collisions with entities (e.g: player)
 */
package barrier;

import java.awt.*;

/**
 * 
 */
public interface Collidable {

	/**
	 * Determines whether a given rectangle bounding box is colliding with the object.
	 * What is considered "colliding" is defined on a per-barrier basis, but usually
	 * involves colliding with the walls of the object (e.g: the walls of a hallway)
	 * @param rect The bounding box to compare
	 * @return True if colliding, false if not
	 */
	public boolean isColliding(Rectangle rect);

	/**
	 * Calculates the intersection of two rectangles.
	 */
	public Rectangle getIntersection(Rectangle rect);
}
