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
	 * Determines whether or not a given coordinate is within the bounds of the
	 * boundary. Used for if the player is within the confines of the current
	 * boundary and the game needs to check if the player is running into a wall.
	 * 
	 * @param objX the x coordinate to check
	 * @param objY the y coordinate to check
	 * @return true if in bounds, false otherwise.
	 */
	public boolean inBounds(int objX, int objY);

	/**
	 * Determines whether a given rectangle bounding box is colliding with the object.
	 * What is considered "colliding" is defined on a per-barrier basis, but usually
	 * involves colliding with the walls of the object (e.g: the walls of a hallway)
	 * @param rect The bounding box to compare
	 * @return True if colliding, false if not
	 */
	public boolean isColliding(Rectangle rect);
}
