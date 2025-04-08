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
public class Hallway extends Barrier {
	//Length of the hallway
	private int len;
	//Thickness of the hallway border lines when drawn
	private final int thickness = 2;
	//How many tiles wide the hallway is
	private final int tileWidth = 2;
	//Hallway border color
	private Color color = Color.white;
	
	/**
	 * @param tileX defined in super
	 * @param tileY defined in super
	 * @param len The length of the hallway. Defined as the number of tiles the hallway extends to.
	 * @param dir defined in super
	 */
	public Hallway(int tileX, int tileY, int len, Direction dir) {
		super(tileX, tileY, dir);
		this.len = len;
		
	}

	public boolean inBounds(int objX, int objY) {
		//Boundary checking is different depending on rotation
		if(dir.getDirection() == Direction.NORTH) {
			//North
			//It's outside of the x-axis boundary
			if (objX < x - tileWidth * GameConfig.tileSize || objX > x)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y - len * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.SOUTH) {
			//South
			//It's outside of the x-axis boundary
			if (objX < x || objX > x + tileWidth * GameConfig.tileSize)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y || objY > y + len * GameConfig.tileSize)
				return false;
		}
		if (dir.getDirection() == Direction.EAST) {
			//East
			//It's outside of the x-axis boundary
			if (objX < x || objX > x + len * GameConfig.tileSize)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y - tileWidth * GameConfig.tileSize || objY > y)
				return false;
		}
		if (dir.getDirection() == Direction.WEST) {
			//West
			//It's outside of the x-axis boundary
			if (objX < x - len * GameConfig.tileSize || objX > x)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y || objY > y + tileWidth * GameConfig.tileSize)
				return false;;
		}
		//It's in bounds
		return true;
	}
	
	
	public void draw(Graphics2D g2){
		g2.setColor(color);
		if (dir.getDirection() == Direction.NORTH) {
			//North
			g2.fillRect(x - (tileWidth * GameConfig.tileSize) - thickness, y - (len * GameConfig.tileSize), thickness, (len * GameConfig.tileSize));
			g2.fillRect(x, y - (len * GameConfig.tileSize), thickness, (len * GameConfig.tileSize));
		}
		if (dir.getDirection() == Direction.SOUTH) {
			//South
			g2.fillRect(x - thickness, y, thickness, len * GameConfig.tileSize);
			g2.fillRect(x + (tileWidth * GameConfig.tileSize), y, thickness, len * GameConfig.tileSize);
		}
		if (dir.getDirection() == Direction.EAST) {
			//East
			g2.fillRect(x, y, len * GameConfig.tileSize, thickness);
			g2.fillRect(x, y - (tileWidth * GameConfig.tileSize) - thickness, len * GameConfig.tileSize, thickness);
		}
		if (dir.getDirection() == Direction.WEST) {
			//West
			g2.fillRect(x - len * GameConfig.tileSize, y - thickness, len * GameConfig.tileSize, thickness);
			g2.fillRect(x - len * GameConfig.tileSize, y + (tileWidth * GameConfig.tileSize), len * GameConfig.tileSize, thickness);
		}
	}
	

}
