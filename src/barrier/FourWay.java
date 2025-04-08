/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: defines the class FourWay, which has 4 openings to let the player choose where to go.
 * Top left definition: the very top-left part of the four way.
 */
package barrier;

import java.awt.Graphics2D;

import direction.Direction;
import game.GameConfig;

/**
 * 
 */
public class FourWay extends Barrier {
	
	//How many tiles wide the four-way is
	private final int tileWidth = 2;

	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 */
	public FourWay(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir);
	}
	
	@Override
	public boolean inBounds(int objX, int objY) {
		//Boundary checking is different depending on rotation
		if (dir.getDirection() == Direction.NORTH) {
			//North
			//Outside of x-axis boundary
			if (objX < x - tileWidth * GameConfig.tileSize|| objX > x)
				return false;
			//Outside of the y-axis boundary
			if (objY < y - tileWidth * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.SOUTH) {
			//South
			//Outside of x-axis boundary
			if (objX < x || objX > x + tileWidth * GameConfig.tileSize)
				return false;
			//Outside of the y-axis boundary
			if (objY < y || objY > y + tileWidth * GameConfig.tileSize)
				return false;
		}
		if (dir.getDirection() == Direction.EAST) {
			//East
			//Outside of x-axis boundary
			if (objX < x || objX > x + tileWidth * GameConfig.tileSize)
				return false;
			//Outside of the y-axis boundary
			if (objY < y - tileWidth * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.WEST) {
			//West
			//Outside of x-axis boundary
			if (objX < x - tileWidth * GameConfig.tileSize|| objX > x)
				return false;
			//Outside of the y-axis boundary
			if (objY < y || objY > y + tileWidth * GameConfig.tileSize)
				return false;
		}
		//It's in bounds
		return true;
	}

	@Override
	public void draw(Graphics2D g2) {
		// Since the four way has 4 openings, we currently don't need to draw anything.
	}
	

}
