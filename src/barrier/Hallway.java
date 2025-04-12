/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Defines the class hallway, a straight corridor for the player to run down.
 * Top left definition: the very top-left part of the hallway.
 */
package barrier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;

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
	// Hallway border color
	private Color color = Color.white;
	// Nodes that the hallway can have barriers attached to. In this case
	// we only have one (there are two ways to move in/out, and one is
	// already taken)
	private Node node;

	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 * @param len   The length of the hallway. Defined as the number of tiles the
	 *              hallway extends to.
	 * @param dir   defined in super
	 */
	public Hallway(int tileX, int tileY, int len, Direction dir) {
		super(tileX, tileY, dir);
		this.len = len;
		// Initialize available node. This will depend on what direction the hallway is
		// If north, we will have a north node (south node is already taken), if east we
		// will have an east node (west node is already taken), etc
		node = new Node(dir);
	}

	/**
	 * Allows initializing a new hallway so that it will append onto a node of another barrier
	 */
	//Make sure the object is both a barrier & implements the node interface
	public <B extends Barrier & Nodes> Hallway(B prevBarrier, Node targetNode, int len) {
		//Super needs to be the first statement in a constructor, so unfortunately it's going to look ugly like this
		super(prevBarrier.getAttachmentPointTX(targetNode), prevBarrier.getAttachmentPointTY(targetNode), new Direction(targetNode.getDirection()));
		this.len = len;
		// Initialize available node. This will depend on what direction the hallway is
		// If north, we will have a north node (south node is already taken), if east we
		// will have an east node (west node is already taken), etc
		node = new Node(dir);
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
		if (dir.getDirection() == Direction.NORTH) {
			// North
			g2.fillRect(x - (tileWidth * GameConfig.tileSize) - thickness, y - (len * GameConfig.tileSize), thickness,
					(len * GameConfig.tileSize));
			g2.fillRect(x, y - (len * GameConfig.tileSize), thickness, (len * GameConfig.tileSize));
		}
		if (dir.getDirection() == Direction.SOUTH) {
			// South
			g2.fillRect(x - thickness, y, thickness, len * GameConfig.tileSize);
			g2.fillRect(x + (tileWidth * GameConfig.tileSize), y, thickness, len * GameConfig.tileSize);
		}
		if (dir.getDirection() == Direction.EAST) {
			// East
			g2.fillRect(x, y, len * GameConfig.tileSize, thickness);
			g2.fillRect(x, y - (tileWidth * GameConfig.tileSize) - thickness, len * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.WEST) {
			// West
			g2.fillRect(x - len * GameConfig.tileSize, y - thickness, len * GameConfig.tileSize, thickness);
			g2.fillRect(x - len * GameConfig.tileSize, y + (tileWidth * GameConfig.tileSize), len * GameConfig.tileSize,
					thickness);
		}
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

}
