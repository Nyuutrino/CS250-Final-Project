/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: defines the class FourWay, which has 4 openings to let the player choose where to go.
 * Top left definition: the very top-left part of the four way.
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
public class FourWay extends Barrier implements Nodes {

	// How many tiles wide the four-way is
	private final int tileWidth = 2;
	// Nodes the four-way can have barriers attached to. In this case it will be 3
	// barriers - one for all but the entrance the preceding barrier was attached to
	private Node[] nodes;

	/**
	 * Method for all of the code needed for setting up the hallway instance
	 */
	private void fourWayConstruct(){
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
	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 */
	public FourWay(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir);
		fourWayConstruct();
	}

	/**
	 * Allows initializing a new four-way so that it will append onto a node of another barrier
	 */
	//Make sure the object is both a barrier & implements the node interface
	public <B extends Barrier & Nodes> FourWay(B prevBarrier, Node targetNode) {
		//Super needs to be the first statement in a constructor, so unfortunately it's going to look ugly like this
		super(prevBarrier.getAttachmentPointTX(targetNode), prevBarrier.getAttachmentPointTY(targetNode), new Direction(targetNode.getDirection()));
		fourWayConstruct();
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

	public int getAttachmentPointTX(Node targetNode) {
		Node node = null;
		//Make sure the node is an instance this specific hallway class instance's node
		for (Node n : nodes) {
			if (n == targetNode) {
				node = n;
			}
		}
		if (node == null) {
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		}
		int x = tileX;
		//X changes depending on the direction of the four-way/target node
		if (dir.getDirection() == Direction.NORTH){
			if (targetNode.getDirectionInt() == Direction.WEST){
				x -= tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.SOUTH) {
			if (targetNode.getDirectionInt() == Direction.EAST){
				x += tileWidth;
			}
		}
		else if (dir.getDirection() == Direction.EAST) {
			if (targetNode.getDirectionInt() == Direction.NORTH){
				x += tileWidth;
			}
			else if (targetNode.getDirectionInt() == Direction.EAST){
				x += tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.WEST) {
			if (targetNode.getDirectionInt() == Direction.SOUTH){
				x -= tileWidth;
			}
			else if(targetNode.getDirectionInt() == Direction.WEST){
				x -= tileWidth;
			}
		}
		return x;
	}

	public int getAttachmentPointTY(Node targetNode) {
		Node node = null;
		//Make sure the node is an instance this specific hallway class instance's node
		for (Node n : nodes) {
			if (n == targetNode) {
				node = n;
			}
		}
		if (node == null) {
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		}
		int y = tileY;
		//Y changes depending on the direction of the four-way/target node
		if (dir.getDirection() == Direction.NORTH) {
			if (targetNode.getDirectionInt() == Direction.NORTH) {
				y -= tileWidth;
			}
			else if (targetNode.getDirectionInt() == Direction.WEST) {
				y -= tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.SOUTH) {
			if (targetNode.getDirectionInt() == Direction.SOUTH) {
				y += tileWidth;
			}
			else if(targetNode.getDirectionInt() == Direction.EAST){
				y += tileWidth;
			}
		}
		else if (dir.getDirection() == Direction.EAST) {
			if (targetNode.getDirectionInt() == Direction.NORTH){
				y -= tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.WEST) {
			if (targetNode.getDirectionInt() == Direction.SOUTH){
				y += tileWidth;
			}
		}
		return y;
	}

	public boolean isColliding(Rectangle rect) {
		//Always return false (since there are no walls to collide with)
		return false;
	}
}
