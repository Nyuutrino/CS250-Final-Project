/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Defines the Corner class, a 90-degree bend in the path. Note the default rotation (south) has the bend down and to the right
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
	//Walls of the corner (used for collision checking & drawing)
	private Rectangle[] walls = new Rectangle[2];
	// Bend border color
	private Color color = Color.white;
	// Nodes that the corner can have barriers attached to. In this case
	// we only have one (there are two ways to move in/out, and one is
	// already taken)
	private Node node;

	/**
	 * Method for all of the code needed for setting up the hallway instance
	 */
	private void cornerConstruct(int bendDir){
		if (bendDir != LEFT && bendDir != RIGHT) {
			throw new IllegalArgumentException("bendDir should have a value of '%d' or '%d'!".formatted(LEFT, RIGHT));
		}
		this.bendDir = bendDir;
		// Initialize available node. In this case, it will be to the left/right of the
		// entrance node's direction (depending on the bend direction).
		if (bendDir == LEFT){
			node = new Node(new Direction(Direction.left(dir.getDirection())));
		}
		else {
			node = new Node(new Direction(Direction.right(dir.getDirection())));
		}
		//Initialize the walls
		if (dir.getDirection() == Direction.NORTH) {
			// North
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x, y - tileWidth * GameConfig.tileSize, thickness, tileWidth * GameConfig.tileSize);
			}
			else {
				walls[0] = new Rectangle(x - tileWidth * GameConfig.tileSize - thickness, y - tileWidth * GameConfig.tileSize, thickness, tileWidth * GameConfig.tileSize);
			}
			walls[1] = new Rectangle(x - tileWidth * GameConfig.tileSize, y - tileWidth * GameConfig.tileSize - thickness,
					tileWidth * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.SOUTH) {
			// South
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x - thickness, y, thickness, tileWidth * GameConfig.tileSize);
			}
			else {
				walls[0] = new Rectangle(x + tileWidth * GameConfig.tileSize, y, thickness, tileWidth * GameConfig.tileSize);
			}
			walls[1] = new Rectangle(x, y + tileWidth * GameConfig.tileSize, tileWidth * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.EAST) {
			// East
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x, y, tileWidth * GameConfig.tileSize, thickness);
			}
			else {
				walls[0] = new Rectangle(x, y - tileWidth * GameConfig.tileSize - thickness, tileWidth * GameConfig.tileSize, thickness);
			}
			walls[1] = new Rectangle(x + tileWidth * GameConfig.tileSize, y - tileWidth * GameConfig.tileSize, thickness,
					tileWidth * GameConfig.tileSize);
		}
		if (dir.getDirection() == Direction.WEST) {
			// West
			if (bendDir == LEFT){
				walls[0] = new Rectangle(x - tileWidth * GameConfig.tileSize, y - thickness, tileWidth * GameConfig.tileSize, thickness);
			}
			else {
				walls[0] = new Rectangle(x - tileWidth * GameConfig.tileSize, y + tileWidth * GameConfig.tileSize, tileWidth * GameConfig.tileSize, thickness);
			}
			walls[1] = new Rectangle(x - tileWidth * GameConfig.tileSize - thickness, y, thickness, tileWidth * GameConfig.tileSize);
		}
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
		//X depends on the direction the node is facing
		if (dir.getDirection() == Direction.NORTH){
			if (bendDir == LEFT){
				x -= tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.SOUTH){
			if (bendDir == LEFT){
				x += tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.EAST){
			if (bendDir == LEFT){
				x += tileWidth;
			}
		}
		else if (dir.getDirection() == Direction.WEST){
			if (bendDir == LEFT){
				x -= tileWidth;
			}
		}
		return x;
	}

	public int getAttachmentPointTY(Node targetNode) {
		//Make sure the node is an instance this specific hallway class instance's node
		if (targetNode != node) {
			throw new IllegalArgumentException("Target node is not a valid node for this barrier type/instance!");
		}
		int y = tileY;
		//Y depends on the direction the corner is facing & its bend
		if (dir.getDirection() == Direction.NORTH) {
			if (bendDir == LEFT){
				y -= tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.SOUTH){
			if (bendDir == LEFT){
				y += tileWidth;
			}
		}
		else if(dir.getDirection() == Direction.EAST){
			if (bendDir == LEFT){
				y -= tileWidth;
			}
		}
		else if (dir.getDirection() == Direction.WEST){
			if (bendDir == LEFT){
				y += tileWidth;
			}
		}
		return y;
	}

	public boolean isColliding(Rectangle rect) {
		return walls[0].intersects(rect) || walls[1].intersects(rect);
	}
}
