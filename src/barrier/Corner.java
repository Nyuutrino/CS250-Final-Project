/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Defines the Corner class, a 90-degree bend in the path.
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
public class Corner extends Barrier implements Nodes {
	// Thickness of the hallway border lines when drawn
	private final int thickness = 2;
	// How many tiles wide the corner is from the center line. The total width will be 2n, where n is the tile width specified
	private final int tileWidth = 1;
	//Bend direction. 0 for left & 1 for right. Use corner static variables when passing in the argument to the constructor for extra readability
	private int bendDir = 0;
	//Variables to make the left/right assignment more readable in constructor arguments
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	//Walls of the corner (used for collision checking & drawing)
	private Rectangle[] walls = new Rectangle[2];
	// Bend border color
	private Color color = Color.white;
	// Nodes that the corner can have barriers attached to. In this case we have two - one for each end of the corner.
	//The first node will be the opposite of the corner direction (since it is the entrance node)
	//And the second node will be at the end of the corner, in the direction that end is facing
	private Node[] nodes = new Node[2];

	/**
	 * Method for all the code needed for setting up the corner instance
	 */
	private void cornerConstruct(int bendDir){
		if (bendDir != LEFT && bendDir != RIGHT) {
			throw new IllegalArgumentException("bendDir should have a value of '%d' or '%d'!".formatted(LEFT, RIGHT));
		}
		this.bendDir = bendDir;
		// Initialize available nodes. In this case, it will be to the left/right of the
		// entrance node's direction (depending on the bend direction).
		int nodeTX = tileX;
		int nodeTY = tileY;
		Direction nodeBendDir = null;
		nodes[0] = new Node(Direction.opposite(dir), nodeTX, nodeTY);
		if (bendDir == LEFT) {
			nodeBendDir = new Direction(Direction.left(this.dir));
		}
		else {
			nodeBendDir = new Direction(Direction.right(this.dir));
		}
		//Initialize the walls (and the second node)
		if (dir.getDirection() == Direction.NORTH) {
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x + tileWidth * GameConfig.tileSize, y - (tileWidth * 2) * GameConfig.tileSize, thickness, (tileWidth * 2) * GameConfig.tileSize);
				nodeTX -= tileWidth;
			}
			else {
				walls[0] = new Rectangle(x - tileWidth * GameConfig.tileSize - thickness, y - (tileWidth * 2) * GameConfig.tileSize, thickness, (tileWidth * 2) * GameConfig.tileSize);
				nodeTX += tileWidth;
			}
			walls[1] = new Rectangle(x - tileWidth * GameConfig.tileSize, y - (tileWidth * 2) * GameConfig.tileSize - thickness,
					(tileWidth * 2) * GameConfig.tileSize, thickness);
			nodeTY -= tileWidth;
		}
		if (dir.getDirection() == Direction.SOUTH) {
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x - tileWidth * GameConfig.tileSize - thickness, y, thickness, (tileWidth * 2) * GameConfig.tileSize);
				nodeTX += tileWidth;
			}
			else {
				walls[0] = new Rectangle(x + tileWidth * GameConfig.tileSize, y, thickness, (tileWidth * 2) * GameConfig.tileSize);
				nodeTX -= tileWidth;
			}
			walls[1] = new Rectangle(x - tileWidth * GameConfig.tileSize, y + (tileWidth * 2) * GameConfig.tileSize, (tileWidth * 2 ) * GameConfig.tileSize, thickness);
			nodeTY += tileWidth;
		}
		if (dir.getDirection() == Direction.EAST) {
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x, y + tileWidth * GameConfig.tileSize, (tileWidth  * 2) * GameConfig.tileSize, thickness);
				nodeTY -= tileWidth;
			}
			else {
				walls[0] = new Rectangle(x, y - tileWidth * GameConfig.tileSize - thickness, (tileWidth * 2) * GameConfig.tileSize, thickness);
				nodeTY += tileWidth;
			}
			walls[1] = new Rectangle(x + (tileWidth * 2) * GameConfig.tileSize, y - tileWidth * GameConfig.tileSize, thickness,
					(tileWidth * 2) * GameConfig.tileSize);
			nodeTX += tileWidth;
		}
		if (dir.getDirection() == Direction.WEST) {
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x - (tileWidth * 2) * GameConfig.tileSize, y - tileWidth * GameConfig.tileSize - thickness, (tileWidth * 2) * GameConfig.tileSize, thickness);
				nodeTY += tileWidth;
			}
			else {
				walls[0] = new Rectangle(x - (tileWidth * 2) * GameConfig.tileSize, y + tileWidth * GameConfig.tileSize, (tileWidth * 2) * GameConfig.tileSize, thickness);
				nodeTY -= tileWidth;
			}
			walls[1] = new Rectangle(x - (tileWidth * 2) * GameConfig.tileSize - thickness, y - GameConfig.tileSize, thickness, (tileWidth * 2) * GameConfig.tileSize);
			nodeTX -= tileWidth;
		}
		nodes[1] = new Node(nodeBendDir, nodeTX, nodeTY);
	}
	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 * @param dir   defined in super
	 */
	public Corner(int tileX, int tileY, Direction dir, int bendDir) {
		super(tileX, tileY, dir);
		cornerConstruct(bendDir);
	}

	/**
	 * Allows initializing a new corner so that it will append onto a node of another barrier
	 */
	//Make sure the object is both a barrier & implements the node interface
	public <B extends Barrier & Nodes> Corner(B prevBarrier, Node targetNode, int bendDir) {
		//Super needs to be the first statement in a constructor, so unfortunately it's going to look ugly like this
		super(prevBarrier.getAttachmentPointTX(targetNode), prevBarrier.getAttachmentPointTY(targetNode), new Direction(targetNode.getDirection()));
		cornerConstruct(bendDir);
	}

	@Override
	public void draw(Graphics2D g2) {
		//Walls
		g2.setColor(color);
		g2.fillRect(walls[0].x, walls[0].y, walls[0].width, walls[0].height);
		g2.fillRect(walls[1].x, walls[1].y, walls[1].width, walls[1].height);

		//Debug mode stuff
		if (!GameConfig.debug) return;
		//Guiding lines
		int startTX = tileX, startTY = tileY;
		int width = 0, height = 0;
		if (dir.getDirection() == Direction.NORTH) {
			startTY -= tileWidth;
			width = 2;
			height = tileWidth * GameConfig.tileSize;
		}
		if (dir.getDirection() == Direction.SOUTH) {
			width = 2;
			height = tileWidth * GameConfig.tileSize;
		}
		if (dir.getDirection() == Direction.EAST) {
			width = tileWidth * GameConfig.tileSize;
			height = 2;
		}
		if (dir.getDirection() == Direction.WEST) {
			startTX -= tileWidth;
			width = tileWidth * GameConfig.tileSize;
			height = 2;
		}
		g2.setColor(Color.GREEN);
		g2.fillRect(startTX * GameConfig.tileSize, startTY * GameConfig.tileSize, width, height);
		if (nodes[1].getDirectionInt() == Direction.NORTH) {
			startTX = nodes[1].getNodeTX();
			startTY = nodes[1].getNodeTY();
			width = 2;
			height = tileWidth * GameConfig.tileSize;
		}
		if (nodes[1].getDirectionInt() == Direction.SOUTH) {
			startTX = nodes[1].getNodeTX();
			startTY = nodes[1].getNodeTY() - tileWidth;
			width = 2;
			height = tileWidth * GameConfig.tileSize;
		}
		if (nodes[1].getDirectionInt() == Direction.EAST) {
			startTX = nodes[1].getNodeTX() - tileWidth;
			startTY = nodes[1].getNodeTY();
			width = tileWidth * GameConfig.tileSize;
			height = 2;
		}
		if (nodes[1].getDirectionInt() == Direction.WEST) {
			startTX = nodes[1].getNodeTX();
			startTY = nodes[1].getNodeTY();
			width = tileWidth * GameConfig.tileSize;
			height = 2;
		}
		g2.fillRect(startTX * GameConfig.tileSize, startTY * GameConfig.tileSize, width, height);
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
		if (!nodeInstanceofClass(targetNode)) {
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		}
		return targetNode.getNodeTX();
	}

	public int getAttachmentPointTY(Node targetNode) {
		if (!nodeInstanceofClass(targetNode)) {
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		}
		return targetNode.getNodeTY();
	}

	public boolean isColliding(Rectangle rect) {
		return walls[0].intersects(rect) || walls[1].intersects(rect);
	}
}
