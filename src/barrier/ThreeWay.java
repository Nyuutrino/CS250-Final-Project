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

public class ThreeWay extends Barrier implements Nodes{
    // How many tiles wide the three way is from the center line. The total width will be 2n, where n is the tile width specified
    private final int tileWidth = 1;
    //The wall of the three way
    private Rectangle wall;
    // Three way border color
    private Color color = Color.white;
    // Thickness of the three way border lines when drawn
    private final int thickness = 2;
    // Nodes of the three way. We have three - one for each side.
    //The order will be the opposite of the direction of the three way (the inlet),
    //The direction left of the opposite (left of the inlet),
    //The direction right of the opposite (right of the inlet)
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
				startY += (tileWidth * 2) * GameConfig.tileSize + thickness;
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
    public ThreeWay(int tileX, int tileY, Direction dir) {
        super(tileX, tileY, dir);
        threeWayConstruct();
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
        //Guiding lines
        int startTX1 = tileX, startTY1 = tileY, startTX2 = tileX, startTY2 = tileY;
        int width1 = 0, height1 = 0, width2 = 0, height2 = 0;
        if (dir.getDirection() == Direction.NORTH) {
            startTY1 -= tileWidth;
            width1 = 2;
            height1 = tileWidth * GameConfig.tileSize;
            startTX2 -= tileWidth;
            startTY2 -= tileWidth;
            height2 = 2;
            width2 = (tileWidth * 2) * GameConfig.tileSize;
        }
        if (dir.getDirection() == Direction.SOUTH) {
            width1 = 2;
            height1 = tileWidth * GameConfig.tileSize;
            startTX2 -= tileWidth;
            startTY2 += tileWidth;
            height2 = 2;
            width2 = (tileWidth * 2) * GameConfig.tileSize;
        }
        if (dir.getDirection() == Direction.EAST) {
            width1 = tileWidth * GameConfig.tileSize;
            height1 = 2;
            startTX2 += tileWidth;
            startTY2 -= tileWidth;
            width2 = 2;
            height2 = (tileWidth * 2) * GameConfig.tileSize;
        }
        if (dir.getDirection() == Direction.WEST) {
            startTX1 -= tileWidth;
            width1 = tileWidth * GameConfig.tileSize;
            height1 = 2;
            startTX2 -= tileWidth;
            startTY2 -= tileWidth;
            width2 = 2;
            height2 = (tileWidth * 2) * GameConfig.tileSize;
        }
        g2.setColor(Color.GREEN);
        g2.fillRect(startTX1 * GameConfig.tileSize, startTY1 * GameConfig.tileSize, width1, height1);
        g2.fillRect(startTX2 * GameConfig.tileSize, startTY2 * GameConfig.tileSize, width2, height2);
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
