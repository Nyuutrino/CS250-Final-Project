/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Defines the class hallway, a straight corridor for the player to run down.
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
	// How many tiles wide the hallway is from the center line. The total width will be 2n, where n is the tile width specified
	private final int tileWidth = 1;
	//Walls of the hallway (used for collision checking & drawing)
	private Rectangle[] walls = new Rectangle[2];
	// Hallway border color
	private Color color = Color.white;
	// Nodes that the hallway can have barriers attached to. In this case we have two - one for each end of the hallway.
	//The first node will be the opposite of the hallway's direction (since it is the entrance node)
	//And the second node will be in the same direction as the hallway's direction, on the opposite end
	private Node[] nodes = new Node[2];

	/**
	 * Method for all of the code needed for setting up the hallway instance
	 */
	private void hallwayConstruct(int len){
		this.len = len;
		// Initialize available node. This will depend on what direction the hallway is
		int nodeTX = tileX;
		int nodeTY = tileY;
		nodes[0] = new Node(Direction.opposite(dir), nodeTX, nodeTY);
		if (dir.getDirection() == Direction.NORTH) {
			nodeTY -= len;
		}
		if (dir.getDirection() == Direction.SOUTH) {
			nodeTY += len;
		}
		if (dir.getDirection() == Direction.EAST) {
			nodeTX += len;
		}
		if (dir.getDirection() == Direction.WEST) {
			nodeTX -= len;
		}
		nodes[1] = new Node(dir, nodeTX, nodeTY);
		//Initialize the walls
		if (dir.getDirection() == Direction.NORTH) {
			walls[0] = new Rectangle(x - tileWidth * GameConfig.tileSize - thickness , y - len * GameConfig.tileSize, thickness, len * GameConfig.tileSize);
			walls[1] = new Rectangle(x + tileWidth * GameConfig.tileSize, y - len * GameConfig.tileSize, thickness, len * GameConfig.tileSize);
		}
		if (dir.getDirection() == Direction.SOUTH) {
			walls[0] = new Rectangle(x - tileWidth * GameConfig.tileSize - thickness, y, thickness, len * GameConfig.tileSize);
			walls[1] = new Rectangle(x + tileWidth * GameConfig.tileSize, y, thickness, len * GameConfig.tileSize);
		}
		if (dir.getDirection() == Direction.EAST) {
			walls[0] = new Rectangle(x, y - tileWidth * GameConfig.tileSize - thickness, len * GameConfig.tileSize, thickness);
			walls[1] = new Rectangle(x, y + tileWidth * GameConfig.tileSize, len * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.WEST) {
			walls[0] = new Rectangle(x - len * GameConfig.tileSize, y - tileWidth * GameConfig.tileSize - thickness, len * GameConfig.tileSize, thickness);
			walls[1] = new Rectangle(x - len * GameConfig.tileSize, y + tileWidth * GameConfig.tileSize, len * GameConfig.tileSize, thickness);
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

	public void draw(Graphics2D g2) {
		//TODO: Arrows representing direction?
		//Walls
		g2.setColor(color);
		g2.fillRect(walls[0].x, walls[0].y, walls[0].width, walls[0].height);
		g2.fillRect(walls[1].x, walls[1].y, walls[1].width, walls[1].height);

		//Debug mode stuff
		if (!GameConfig.debug) return;
		//Guiding lines
		int startX = x;
		int startY = y;
		if (dir.getDirection() == Direction.NORTH)
			startY -= len * GameConfig.tileSize;
		if (dir.getDirection() == Direction.WEST)
			startX -= len * GameConfig.tileSize;
		int width = 0;
		int height = 0;
		if (dir.getDirection() == Direction.SOUTH || dir.getDirection() == Direction.NORTH) {
			width = 2;
			height = len * GameConfig.tileSize;
		}
		if (dir.getDirection() == Direction.EAST || dir.getDirection() == Direction.WEST) {
			width = len * GameConfig.tileSize;
			height = 2;
		}
		g2.setColor(Color.GREEN);
		g2.fillRect(startX, startY, width, height);
	}

	public Node[] getAvailableNodes() {
		return nodes;
	}

	/**
	 * Checks if a node belongs to an instance of this class
	 */
	private boolean nodeInstanceofClass(Node targetNode){
		Node node = null;
		//Make sure the node is an instance of one of this class's specific instance's nodes
		for (Node n : nodes) {
			if (n == targetNode) {
				node = n;
			}
		}
		if (node == null) {
			return false;
		}
		return true;
	}

	public int getAttachmentPointTX(Node targetNode) {
		if (!nodeInstanceofClass(targetNode))
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		return targetNode.getNodeTX();
	}

	public int getAttachmentPointTY(Node targetNode) {
		if (!nodeInstanceofClass(targetNode))
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		return targetNode.getNodeTY();
	}

	public boolean isColliding(Rectangle rect) {
		return walls[0].intersects(rect) || walls[1].intersects(rect);
	}
}
