/**
 * Author: Nyuutrino
 * Date: Apr 20, 2025
 * Description: Defines the abstract Corridor class used for defining objects that the player can go through and has nodes to attach other corridor objects to.
 */
package barrier;

import barrierNodes.Node;
import barrierNodes.Nodes;
import direction.Direction;

import java.awt.*;

public abstract class Corridor extends Barrier implements Nodes {
	public Corridor(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir);
	}

	/**
	 * Returns the midpoint of the corridor.
	 * @return The point containing the tile coordinates of the midpoint
	 */
	public Point getMidpoint(){
		Point pt = new Point(tileX, tileY);
		switch (dir.getDirection()){
			case Direction.NORTH:
				pt.translate(0, -tileWidth);
				break;
			case Direction.SOUTH:
				pt.translate(0, tileWidth);
				break;
			case Direction.EAST:
				pt.translate(tileWidth, 0);
				break;
			case Direction.WEST:
				pt.translate(-tileWidth, 0);
		}
		return pt;
	}
}
