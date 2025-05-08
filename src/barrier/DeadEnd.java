/**
 * Author: Nyuutrino
 * Date: Apr 29, 2025
 * Description: Defines the dead-end, which is used at the beginning of the level to prevent the player from escaping the map
 */
package barrier;

import barrierNodes.Node;
import camera.Camera;
import direction.Direction;
import game.GameConfig;

import java.awt.*;

public class DeadEnd extends Barrier {
	//The wall of the dead end
	private Rectangle wall;
	//Border color
	private Color color = Color.WHITE;
	//Thickness of the borderlines when drawn
	private final int thickness = 2;

	private void DeadEndConstruct() {
		//Make the wall
		int startX = x, startY = y, width = 0, height = 0;
		if (this.getDirection() == Direction.NORTH) {
			startX -= tileWidth * GameConfig.tileSize;
			startY -= thickness;
			width = tileWidth * 2 * GameConfig.tileSize;
			height = thickness;
		}
		else if (this.getDirection() == Direction.SOUTH) {
			startX -= tileWidth * GameConfig.tileSize;
			width = tileWidth * 2 * GameConfig.tileSize;
			height = thickness;
		}
		else if (this.getDirection() == Direction.EAST) {
			startY -= tileWidth * GameConfig.tileSize;
			width = thickness;
			height = tileWidth * 2 * GameConfig.tileSize;
		}
		else if (this.getDirection() == Direction.WEST) {
			startY -= tileWidth * GameConfig.tileSize;
			startX -= thickness;
			width = thickness;
			height = tileWidth * 2 * GameConfig.tileSize;
		}
		wall = new Rectangle(startX, startY, width, height);
	}

	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 * @param dir defined in super
	 */
	public DeadEnd(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir);
		DeadEndConstruct();
	}

	/**
	 * Allows initializing a new dead-end so that it will append onto a node of corridor
	 */
	public DeadEnd(Corridor prevCorridor, Node targetNode) {
		//Super needs to be the first statement in a constructor, so unfortunately it's going to look ugly like this
		super(prevCorridor.getAttachmentPointTX(targetNode), prevCorridor.getAttachmentPointTY(targetNode), new Direction(targetNode.getDirection()));
		DeadEndConstruct();
	}

	public void draw(Graphics2D g2) {
		//Wall
		g2.setColor(color);
		Camera.fillRect(wall.x, wall.y, wall.width, wall.height, g2);
	}

	@Override
	public boolean isColliding(Rectangle rect) {
		return wall.intersects(rect);
	}

	@Override
	public Rectangle getIntersection(Rectangle rect) {
		return wall.intersection(rect);
	}
}
