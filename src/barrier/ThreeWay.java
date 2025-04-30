/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Defines the class ThreeWay, a T-shaped intersection.
 */
package barrier;

import barrierNodes.Node;
import direction.Direction;
import game.GameConfig;

import java.awt.*;

public class ThreeWay extends Corridor{
	// How many tiles wide the three way is from the center line. The total width will be 2n, where n is the tile width specified
	private final int tileWidth = 1;
	//The wall of the three way
	private Rectangle wall;
	// Three way border color
	private Color color = Color.white;
	// Thickness of the three way border lines when drawn
	private final int thickness = 2;
	//Branch configuration. The Three-way can branch off in three ways - left-right, straight-left & straight-right
	public int branchConfig;
	//Branch configuration values. Use when specifying the branch configuration.
	//Left-right
	public static final int BRANCH_LR = 0;
	//Straight & left
	public static final int BRANCH_SL = 1;
	//Straight & right
	public static final int BRANCH_SR = 2;
	// Nodes of the three way. We have three - one for each side.
	/**
	 * The order will depend on the configuration of the branching, but it should look something like this:
	 * node[0] - the node from the previous barrier/start
	 * node[1] - either the straight branch or the left branch
	 * node[2] - the left/right branch
	*/
	private Node[] nodes = new Node[3];

	/**
	 * Method for all the code needed for setting up the three way instance
	 */
	private void threeWayConstruct(int branchConfig){
		if (branchConfig < 0 || branchConfig > 2)
			throw new IllegalArgumentException("Branch configuration supplied is invalid! Use one of the values defined in this class with the static fields.");
		this.branchConfig = branchConfig;

		// Initialize wall & available nodes & calculate their TX/TY points
		nodes[0] = new Node(Direction.opposite(dir), tileX, tileY);

		//For the wall
		int startX = x, startY = y, width = 0, height = 0;
		if (dir.getDirection() == Direction.NORTH) {
			if (branchConfig == BRANCH_LR) {
				startX -= tileWidth * GameConfig.tileSize;
				startY -= (tileWidth * 2) * GameConfig.tileSize + thickness;
				width = tileWidth * 2 * GameConfig.tileSize;
				height = thickness;
				nodes[1] = new Node(Direction.left(dir), tileX - tileWidth, tileY - tileWidth);
				nodes[2] = new Node(Direction.right(dir), tileX + tileWidth, tileY - tileWidth);
			}
			else if (branchConfig == BRANCH_SL) {
				startX += tileWidth * GameConfig.tileSize;
				startY -= tileWidth * 2 * GameConfig.tileSize;
				width = thickness;
				height = tileWidth * 2 * GameConfig.tileSize;
				nodes[1] = new Node(dir, tileX, tileY - tileWidth * 2);
				nodes[2] = new Node(Direction.left(dir), tileX - tileWidth, tileY - tileWidth);
			}
			else {
				startX -= tileWidth * GameConfig.tileSize + thickness;
				startY -= tileWidth * 2 * GameConfig.tileSize;
				width = thickness;
				height = tileWidth * 2 * GameConfig.tileSize;
				nodes[1] = new Node(dir, tileX, tileY - tileWidth * 2);
				nodes[2] = new Node(Direction.right(dir), tileX + tileWidth, tileY - tileWidth);
			}
		}
		else if (dir.getDirection() == Direction.SOUTH) {
			if (branchConfig == BRANCH_LR) {
				startX -= tileWidth * GameConfig.tileSize;
				startY += (tileWidth * 2) * GameConfig.tileSize;
				width = tileWidth * 2 * GameConfig.tileSize;
				height = thickness;
				nodes[1] = new Node(Direction.left(dir), tileX + tileWidth, tileY + tileWidth);
				nodes[2] = new Node(Direction.right(dir), tileX - tileWidth, tileY + tileWidth);

			}
			else if (branchConfig == BRANCH_SL) {
				startX -= tileWidth * GameConfig.tileSize + thickness;
				width = thickness;
				height = tileWidth * 2 * GameConfig.tileSize;
				nodes[1] = new Node(dir, tileX, tileY + tileWidth * 2);
				nodes[2] = new Node(Direction.left(dir), tileX + tileWidth, tileY + tileWidth);
			}
			else {
				startX += tileWidth * GameConfig.tileSize;
				width = thickness;
				height = tileWidth * 2 * GameConfig.tileSize;
				nodes[1] = new Node(dir, tileX, tileY + tileWidth * 2);
				nodes[2] = new Node(Direction.right(dir), tileX - tileWidth, tileY + tileWidth);
			}
		}
		else if (dir.getDirection() == Direction.EAST) {
			if (branchConfig == BRANCH_LR) {
				startX += tileWidth * 2 * GameConfig.tileSize;
				startY -= tileWidth * GameConfig.tileSize;
				width = thickness;
				height = tileWidth * 2 * GameConfig.tileSize;
				nodes[1] = new Node(Direction.left(dir), tileX + tileWidth, tileY - tileWidth);
				nodes[2] = new Node(Direction.right(dir), tileX + tileWidth, tileY + tileWidth);
			}
			else if (branchConfig == BRANCH_SL) {
				startY += tileWidth * GameConfig.tileSize;
				width = tileWidth * 2 * GameConfig.tileSize;
				height = thickness;
				nodes[1] = new Node(dir, tileX + tileWidth * 2, tileY);
				nodes[2] = new Node(Direction.left(dir), tileX + tileWidth, tileY - tileWidth);
			}
			else {
				startY -= tileWidth * GameConfig.tileSize + thickness;
				width = tileWidth * 2 * GameConfig.tileSize;
				height = thickness;
				nodes[1] = new Node(dir, tileX + tileWidth * 2, tileY);
				nodes[2] = new Node(Direction.right(dir), tileX + tileWidth, tileY + tileWidth);
			}
		}
		else {
			if (branchConfig == BRANCH_LR) {
				startX -= tileWidth * 2 * GameConfig.tileSize + thickness;
				startY -= tileWidth * GameConfig.tileSize;
				width = thickness;
				height = tileWidth * 2 * GameConfig.tileSize;
				nodes[1] = new Node(Direction.left(dir), tileX - tileWidth, tileY + tileWidth);
				nodes[2] = new Node(Direction.right(dir), tileX - tileWidth, tileY - tileWidth);
			}
			else if (branchConfig == BRANCH_SL) {
				startX -= tileWidth * 2 * GameConfig.tileSize;
				startY -= tileWidth * GameConfig.tileSize + thickness;
				width = tileWidth * 2 * GameConfig.tileSize;
				height = thickness;
				nodes[1] = new Node(dir, tileX - tileWidth * 2, tileY);
				nodes[2] = new Node(Direction.left(dir), tileX - tileWidth, tileY + tileWidth);
			}
			else {
				startX -= tileWidth * 2 * GameConfig.tileSize;
				startY += tileWidth * GameConfig.tileSize;
				width = tileWidth * 2 * GameConfig.tileSize;
				height = thickness;
				nodes[1] = new Node(dir, tileX - tileWidth * 2, tileY);
				nodes[2] = new Node(Direction.right(dir), tileX - tileWidth, tileY - tileWidth);
			}
		}

		//Initialize the wall object
		wall = new Rectangle(startX, startY, width, height);
	}

	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 */
	public ThreeWay(int tileX, int tileY, Direction dir, int branchConfig) {
		super(tileX, tileY, dir);
		threeWayConstruct(branchConfig);
	}

	/**
	 * Allows initializing a new three way so that it will append onto a node of another corridor
	 */
	public ThreeWay(Corridor prevCorridor, Node targetNode, int branchConfig) {
		//Super needs to be the first statement in a constructor, so unfortunately it's going to look ugly like this
		super(prevCorridor.getAttachmentPointTX(targetNode), prevCorridor.getAttachmentPointTY(targetNode), new Direction(targetNode.getDirection()));
		threeWayConstruct(branchConfig);
	}

	@Override
	public void draw(Graphics2D g2) {
		//Wall
		g2.setColor(color);
		g2.fillRect(wall.x, wall.y, wall.width, wall.height);
		//Draw the debug stuff if debug mode is enabled
		if (!GameConfig.debug) return;
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(2));

		//Guiding lines
		if (branchConfig == BRANCH_LR) {
			int lineX = (dir.getDirection() == Direction.NORTH || dir.getDirection() == Direction.SOUTH) ? x : nodes[1].getNodeX();
			int lineY = (dir.getDirection() == Direction.EAST || dir.getDirection() == Direction.WEST) ? x : nodes[1].getNodeY();
			g2.drawLine(x, y,lineX, lineY);
			g2.drawLine(nodes[1].getNodeX(), nodes[1].getNodeY(), nodes[2].getNodeX(), nodes[2].getNodeY());
		}
		else if (branchConfig == BRANCH_SL) {
			g2.drawLine(x, y, nodes[1].getNodeX(), nodes[1].getNodeY());
			int lineX = (dir.getDirection() == Direction.NORTH || dir.getDirection() == Direction.SOUTH) ? x : (nodes[0].getNodeX() + nodes[1].getNodeX()) / 2;
			int lineY = (dir.getDirection() == Direction.NORTH || dir.getDirection() == Direction.SOUTH) ? (nodes[0].getNodeY() + nodes[1].getNodeY()) / 2 : y;
			g2.drawLine(nodes[2].getNodeX(), nodes[2].getNodeY(), lineX, lineY);
		}
		else {
			g2.drawLine(x, y, nodes[1].getNodeX(), nodes[1].getNodeY());
			int lineX = (dir.getDirection() == Direction.NORTH || dir.getDirection() == Direction.SOUTH) ? x : (nodes[0].getNodeX() + nodes[1].getNodeX()) / 2;
			int lineY = (dir.getDirection() == Direction.NORTH || dir.getDirection() == Direction.SOUTH) ? (nodes[0].getNodeY() + nodes[1].getNodeY()) / 2 : y;
			g2.drawLine(nodes[2].getNodeX(), nodes[2].getNodeY(), lineX, lineY);
		}

		//Starting node
		g2.setColor(Color.RED);
		g2.fillOval(nodes[0].getNodeX() - 5, nodes[0].getNodeY() - 5, 10, 10);

		//Other two nodes
		g2.setColor(Color.ORANGE);
		g2.fillOval(nodes[1].getNodeX() - 5, nodes[1].getNodeY() - 5, 10, 10);
		g2.fillOval(nodes[2].getNodeX() - 5, nodes[2].getNodeY() - 5, 10, 10);
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
			throw new IllegalArgumentException("Target node is not a valid node for this corridor type/instance!");
		}
		return targetNode.getNodeTX();
	}

	public int getAttachmentPointTY(Node targetNode) {
		if (!nodeInstanceofClass(targetNode)) {
			throw new IllegalArgumentException("Target node is not a valid node for this corridor type/instance!");
		}
		return targetNode.getNodeTY();
	}

	public boolean isColliding(Rectangle rect) {
		return wall.intersects(rect);
	}
}
