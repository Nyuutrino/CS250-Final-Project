/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Defines the Corner class, a 90-degree bend in the path. Note the default rotation (south) has the bend down and to the right
 * Top left definition: the very top-left part of the hallway.
 */
package barrier;

import java.awt.Color;
import java.awt.Graphics2D;

import barrierNodes.Node;
import barrierNodes.Nodes;
import direction.Direction;
import game.GameConfig;

/**
 * 
 */
public class Corner extends Barrier implements Nodes {
	// Thickness of the hallway border lines when drawn
	private final int thickness = 2;
	// How many tiles wide the corner is
	private final int tileWidth = 2;
	//Bend direction. 0 for left & 1 for right. Use corner static variables when passing in the argument to the constructor for extra readability
	private int bendDir = 0;
	//Variables to make the left/right assignment more readable in constructor arguments
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	// Bend border color
	private Color color = Color.white;
	// Nodes that the corner can have barriers attached to. In this case
	// we only have one (there are two ways to move in/out, and one is
	// already taken)
	private Node node;

	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 * @param dir   defined in super
	 */
	public Corner(int tileX, int tileY, Direction dir, int bendDir) {
		super(tileX, tileY, dir);
		// Initialize available node. In this case, it will be to the left of the
		// entrance node's direction.
		node = new Node(new Direction(Direction.left(dir.getDirection())));
		if (bendDir != LEFT && bendDir != RIGHT) {
			throw new IllegalArgumentException("bendDir should have a value of '%d' or '%d'!".formatted(LEFT, RIGHT));
		}
		this.bendDir = bendDir;
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
		g2.setColor(color);
		if (dir.getDirection() == Direction.NORTH) {
			// North
			if (bendDir == LEFT){
				g2.fillRect(x, y - tileWidth * GameConfig.tileSize, thickness, tileWidth * GameConfig.tileSize);
			}
			else {
				g2.fillRect(x - tileWidth * GameConfig.tileSize - thickness, y - tileWidth * GameConfig.tileSize, thickness, tileWidth * GameConfig.tileSize);
			}
			g2.fillRect(x - tileWidth * GameConfig.tileSize, y - tileWidth * GameConfig.tileSize,
					tileWidth * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.SOUTH) {
			// South
			if (bendDir == LEFT){
				g2.fillRect(x - thickness, y, thickness, tileWidth * GameConfig.tileSize);
			}
			else {
				g2.fillRect(x + tileWidth * GameConfig.tileSize, y, thickness, tileWidth * GameConfig.tileSize);
			}
			g2.fillRect(x, y + tileWidth * GameConfig.tileSize, tileWidth * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.EAST) {
			// East
			if (bendDir == LEFT){
				g2.fillRect(x, y, tileWidth * GameConfig.tileSize, thickness);
			}
			else {
				g2.fillRect(x, y - tileWidth * GameConfig.tileSize - thickness, tileWidth * GameConfig.tileSize, thickness);
			}
			g2.fillRect(x + tileWidth * GameConfig.tileSize, y - tileWidth * GameConfig.tileSize, thickness,
					tileWidth * GameConfig.tileSize);
		}
		if (dir.getDirection() == Direction.WEST) {
			// West
			if (bendDir == LEFT){
				g2.fillRect(x - tileWidth * GameConfig.tileSize, y - thickness, tileWidth * GameConfig.tileSize, thickness);
			}
			else {
				g2.fillRect(x - tileWidth * GameConfig.tileSize, y + tileWidth * GameConfig.tileSize, tileWidth * GameConfig.tileSize, thickness);
			}
			g2.fillRect(x - tileWidth * GameConfig.tileSize - thickness, y, thickness, tileWidth * GameConfig.tileSize);
		}

	}
	
	public Node[] getAvailableNodes() {
		return new Node[] { node };
	}
}
