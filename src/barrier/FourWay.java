/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Defines the class FourWay, which has 4 openings to let the player choose where to go.
 */
package barrier;

import java.awt.*;

import barrierNodes.Node;
import direction.Direction;
import game.GameConfig;

/**
 * 
 */
public class FourWay extends Corridor {
	// Nodes of the four way. We have four - one for each side.
	//The order will be the opposite of the direction of the four way (the inlet),
	//The direction of the four way (across from the inlet),
	//The direction left of the opposite (left of the inlet),
	//The direction right of the opposite (right of the inlet)
	private Node[] nodes = new Node[4];

	/**
	 * Method for all the code needed for setting up the four way instance
	 */
	private void fourWayConstruct(){
		// Initialize available nodes & calculate their TX/TY points
		 nodes[0] = new Node(Direction.opposite(dir), tileX, tileY);
		 if (dir.getDirection() == Direction.NORTH) {
			 nodes[1] = new Node(dir, tileX, tileY - 2 * tileWidth);
			 nodes[2] = new Node(Direction.left(dir), tileX - tileWidth, tileY - tileWidth);
			 nodes[3] = new Node(Direction.right(dir), tileX + tileWidth, tileY - tileWidth);
		 }
		 if (dir.getDirection() == Direction.SOUTH) {
			 nodes[1] = new Node(dir, tileX, tileY + 2 * tileWidth);
			 nodes[2] = new Node(Direction.left(dir), tileX + tileWidth, tileY + tileWidth);
			 nodes[3] = new Node(Direction.right(dir), tileX - tileWidth, tileY + tileWidth);
		 }
		 if (dir.getDirection() == Direction.EAST) {
			 nodes[1] = new Node(dir, tileX + 2 * tileWidth, tileY);
			 nodes[2] = new Node(Direction.left(dir), tileX + tileWidth, tileY - tileWidth);
			 nodes[3] = new Node(Direction.right(dir), tileX + tileWidth, tileY + tileWidth);
		 }
		 if (dir.getDirection() == Direction.WEST) {
			 nodes[1] = new Node(dir, tileX - 2 * tileWidth, tileY);
			 nodes[2] = new Node(Direction.left(dir), tileX - tileWidth, tileY + tileWidth);
			 nodes[3] = new Node(Direction.right(dir), tileX - tileWidth, tileY - tileWidth);
		 }
	}
	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 */
	public FourWay(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir);
		fourWayConstruct();
	}

	/**
	 * Allows initializing a new four-way so that it will append onto a node of another corridor
	 */
	public FourWay(Corridor prevCorridor, Node targetNode) {
		//Super needs to be the first statement in a constructor, so unfortunately it's going to look ugly like this
		super(prevCorridor.getAttachmentPointTX(targetNode), prevCorridor.getAttachmentPointTY(targetNode), new Direction(targetNode.getDirection()));
		fourWayConstruct();
	}

	@Override
	public void draw(Graphics2D g2) {
		// Since the four way has 4 openings, we currently don't need to draw anything.
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
		//Always return false (since there are no walls to collide with)
		return false;
	}

	@Override
	public Rectangle getIntersection(Rectangle rect) {
		//No intersection since there's no walls
		return new Rectangle(0, 0, 0, 0);
	}
}
