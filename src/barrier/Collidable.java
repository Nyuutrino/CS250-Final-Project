/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Ensures that an object can check collisions with entities (e.g: player)
 */
package barrier;

/**
 * 
 */
public interface Collidable {
	/**
	 * Determines whether or not a given coordinate is within the bounds of the
	 * boundary. Used for if the player is within the confines of the current
	 * boundary and the game needs to check if the player is running into a wall.
	 * 
	 * @param x the x coordinate to check
	 * @param y the y coordinate to check
	 * @return true if in bounds, false otherwise.
	 */
	public boolean inBounds(int x, int y);
}
