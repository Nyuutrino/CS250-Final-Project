/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Ensures that an object can be drawn
 */
package game;

import java.awt.Graphics2D;

/**
 * 
 */
public interface Drawable {
	/**
	 * Draws the entity.
	 * @param g2 the Graphics2D object used for drawing to the screen.
	 */
	public void draw(Graphics2D g2);
}
