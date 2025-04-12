/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Defines the class hallway, a straight corridor for the player to run down.
 * Top left definition: the very top-left part of the hallway.
 */
package barrier;

import java.awt.*;

import barrierNodes.Node;
import barrierNodes.Nodes;
import direction.Direction;
import game.GameConfig;

/**
 * 
 */
public class Hallway extends Barrier implements Nodes {
	// Length of the hallway
	private int len;
	// Thickness of the hallway border lines when drawn
	private final int thickness = 2;
	// How many tiles wide the hallway is
	private final int tileWidth = 2;
	//Walls of the hallway (used for collision checking & drawing)
	private Rectangle[] walls = new Rectangle[2];
	// Hallway border color
	private Color color = Color.white;
	// Nodes that the hallway can have barriers attached to. In this case
	// we only have one (there are two ways to move in/out, and one is
	// already taken)
	private Node node;

	/**
	 * Method for all of the code needed for setting up the hallway instance
	 */
	private void hallwayConstruct(int len){
		this.len = len;
		// Initialize available node. This will depend on what direction the hallway is
		// If north, we will have a north node (south node is already taken), if east we
		// will have an east node (west node is already taken), etc
		node = new Node(dir);
		//Initialize the walls
		if (dir.getDirection() == Direction.NORTH) {
			// North
			walls[0] = new Rectangle(x - (tileWidth * GameConfig.tileSize) - thickness, y - (len * GameConfig.tileSize), thickness, len * GameConfig.tileSize);
			walls[1] = new Rectangle(x, y - (len * GameConfig.tileSize), thickness, (len * GameConfig.tileSize));
		}
		if (dir.getDirection() == Direction.SOUTH) {
			// South
			walls[0] = new Rectangle(x - thickness, y, thickness, len * GameConfig.tileSize);
			walls[1] = new Rectangle(x + (tileWidth * GameConfig.tileSize), y, thickness, len * GameConfig.tileSize);
		}
		if (dir.getDirection() == Direction.EAST) {
			// East
			walls[0] = new Rectangle(x, y, len * GameConfig.tileSize, thickness);
			walls[1] = new Rectangle(x, y - (tileWidth * GameConfig.tileSize) - thickness, len * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.WEST) {
			// West
			walls[0] = new Rectangle(x - len * GameConfig.tileSize, y - thickness, len * GameConfig.tileSize, thickness);
			walls[1] = new Rectangle(x - len * GameConfig.tileSize, y + (tileWidth * GameConfig.tileSize), len * GameConfig.tileSize,
					thickness);
		}
	}
	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 * @param len   The length of the hallway. Defined as the number of tiles the
	 *              hallway extends to.
	 * @param dir   defined in super
	 */
	public Hallway(int tileX, int tileY, int len, Direction dir) {
		super(tileX, tileY, dir);
		hallwayConstruct(len);
	}

	/**
	 * Allows initializing a new hallway so that it will append onto a node of another barrier
	 */
	//Make sure the object is both a barrier & implements the node interface
	public <B extends Barrier & Nodes> Hallway(B prevBarrier, Node targetNode, int len) {
		//Super needs to be the first statement in a constructor, so unfortunately it's going to look ugly like this
		super(prevBarrier.getAttachmentPointTX(targetNode), prevBarrier.getAttachmentPointTY(targetNode), new Direction(targetNode.getDirection()));
		hallwayConstruct(len);
	}

	public boolean inBounds(int objX, int objY) {
		// Boundary checking is different depending on rotation
		if (dir.getDirection() == Direction.NORTH) {
			// North
			// It's outside of the x-axis boundary
			if (objX < x - tileWidth * GameConfig.tileSize || objX > x)
				return false;
			// It's outside of the y-axis boundary
			if (objY < y - len * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.SOUTH) {
			// South
			// It's outside of the x-axis boundary
			if (objX < x || objX > x + tileWidth * GameConfig.tileSize)
				return false;
			// It's outside of the y-axis boundary
			if (objY < y || objY > y + len * GameConfig.tileSize)
				return false;
		}
		if (dir.getDirection() == Direction.EAST) {
			// East
			// It's outside of the x-axis boundary
			if (objX < x || objX > x + len * GameConfig.tileSize)
				return false;
			// It's outside of the y-axis boundary
			if (objY < y - tileWidth * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.WEST) {
			// West
			// It's outside of the x-axis boundary
			if (objX < x - len * GameConfig.tileSize || objX > x)
				return false;
			// It's outside of the y-axis boundary
			if (objY < y || objY > y + tileWidth * GameConfig.tileSize)
				return false;
			;
		}
		// It's in bounds
		return true;
	}

	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.fillRect(walls[0].x, walls[0].y, walls[0].width, walls[0].height);
		g2.fillRect(walls[1].x, walls[1].y, walls[1].width, walls[1].height);
	}

	public Node[] getAvailableNodes() {
		return new Node[] { node };
	}

	public int getAttachmentPointTX(Node targetNode) {
		//Make sure the node is an instance this specific hallway class instance's node
		if (targetNode != node) {
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		}
		int x = tileX;
		//X depends on the direction the hallway is facing
		switch (dir.getDirection()) {
			case Direction.EAST:
				x += len;
				break;
			case Direction.WEST:
				x -= len;
				break;
		}
		return x;
	}

	public int getAttachmentPointTY(Node targetNode) {
		//Make sure the node is an instance this specific hallway class instance's node
		if (targetNode != node) {
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		}
		int y = tileY;
		//Y depends on the direction the hallway is facing
		switch (dir.getDirection()) {
			case Direction.NORTH:
				y -= len;
				break;
			case Direction.SOUTH:
				y += len;
				break;
		}
		return y;
	}

	public boolean isColliding(Rectangle rect) {
		return walls[0].intersects(rect) || walls[1].intersects(rect);
	}
}
