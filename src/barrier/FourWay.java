/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: defines the class FourWay, which has 4 openings to let the player choose where to go.
 * Top left definition: the very top-left part of the four way.
 */
package barrier;

import java.awt.Graphics2D;

import barrierNodes.Node;
import barrierNodes.Nodes;
import direction.Direction;
import game.GameConfig;

/**
 * 
 */
public class FourWay extends Barrier implements Nodes {

	// How many tiles wide the four-way is
	private final int tileWidth = 2;
	// Nodes the four-way can have barriers attached to. In this case it will be 3
	// barriers - one for all but the entrance the preceding barrier was attached to
	private Node[] nodes;

	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 */
	public FourWay(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir);
		// Initialize available nodes. We basically add all but the opposite direction
		// of the direction it is facing (e.g: if the direction is north, the south side
		// will be the entrance, so exclude that
		switch (dir.getDirection()) {
		case Direction.NORTH:
			nodes = new Node[] {new Node(new Direction(Direction.NORTH)), new Node(new Direction(Direction.EAST)), new Node(new Direction(Direction.WEST))};
			break;
		case Direction.SOUTH:
			nodes = new Node[] {new Node(new Direction(Direction.SOUTH)), new Node(new Direction(Direction.EAST)), new Node(new Direction(Direction.WEST))};
			break;
		case Direction.EAST:
			nodes = new Node[] {new Node(new Direction(Direction.NORTH)), new Node(new Direction(Direction.SOUTH)), new Node(new Direction(Direction.EAST))};
			break;
		case Direction.WEST:
			nodes = new Node[] {new Node(new Direction(Direction.NORTH)), new Node(new Direction(Direction.SOUTH)), new Node(new Direction(Direction.WEST))};
			break;
		}
	}

	@Override
	public boolean inBounds(int objX, int objY) {
		// Boundary checking is different depending on rotation
		if (dir.getDirection() == Direction.NORTH) {
			// North
			// Outside of x-axis boundary
			if (objX < x - tileWidth * GameConfig.tileSize || objX > x)
				return false;
			// Outside of the y-axis boundary
			if (objY < y - tileWidth * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.SOUTH) {
			// South
			// Outside of x-axis boundary
			if (objX < x || objX > x + tileWidth * GameConfig.tileSize)
				return false;
			// Outside of the y-axis boundary
			if (objY < y || objY > y + tileWidth * GameConfig.tileSize)
				return false;
		}
		if (dir.getDirection() == Direction.EAST) {
			// East
			// Outside of x-axis boundary
			if (objX < x || objX > x + tileWidth * GameConfig.tileSize)
				return false;
			// Outside of the y-axis boundary
			if (objY < y - tileWidth * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.WEST) {
			// West
			// Outside of x-axis boundary
			if (objX < x - tileWidth * GameConfig.tileSize || objX > x)
				return false;
			// Outside of the y-axis boundary
			if (objY < y || objY > y + tileWidth * GameConfig.tileSize)
				return false;
		}
		// It's in bounds
		return true;
	}

	@Override
	public void draw(Graphics2D g2) {
		// Since the four way has 4 openings, we currently don't need to draw anything.
	}
	
	public Node[] getAvailableNodes() {
		return nodes;
	}

}
