/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Defines the class hallway, a straight corridor for the player to run down.
 */
package barrier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;

import game.GameConfig;

/**
 * 
 */
public class Hallway extends Barrier {
	//Length & rotation of the hallway
	private int len, rot;
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
	 * @param rot Rotation of the hallway defined by direction. Possible values are 0 - north, 1 - south, 2 - east & 3 - west.
	 */
	public Hallway(int tileX, int tileY, int len, int rot) {
		super(tileX, tileY);
		this.len = len;
		if (rot < 0 || rot > 3) {
			throw new IllegalArgumentException("Rotation must be either 0, 1, 2 or 3. Got " + rot + " instead!");
		}
		this.rot = rot;
		
	}

	public boolean inBounds(int objX, int objY) {
		//Boundary checking is different depending on rotation
		if(rot == 0) {
			//North
			//It's outside of the x-axis boundary
			if (objX < x - tileWidth * GameConfig.tileSize || objX > x)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y - len * GameConfig.tileSize || objY > y)
				return false;
			//It's in-bounds
			return true;
		}
		if (rot == 1) {
			//South; Most straightforward to check
			//It's outside of the x-axis boundary
			if (objX < x || objX > x + tileWidth * GameConfig.tileSize)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y || objY > y + len * GameConfig.tileSize)
				return false;
			//It's in-bounds
			return true;
		}
		if (rot == 2) {
			//East
			//It's outside of the x-axis boundary
			if (objX < x || objX > x + len * GameConfig.tileSize)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y - tileWidth * GameConfig.tileSize || objY > y)
				return false;
			//It's in-bounds
			return true;
		}
		if (rot == 3) {
			//West
			//It's outside of the x-axis boundary
			if (objX < x - len * GameConfig.tileSize || objX > x)
				return false;
			//It's outside of the y-axis boundary
			if(objY < y || objY > y + tileWidth * GameConfig.tileSize)
				return false;
			//It's in-bounds
			return true;
		}
		//Temporary to keep the pesky error away
		return false;
	}
	
	
	public void draw(Graphics2D g2){
		g2.setColor(color);
		//Top left is the very top left of the hallway (south direction). If it rotates it will rotate around this point
		if (rot == 0) {
			//North
			g2.fillRect(x - (tileWidth * GameConfig.tileSize) - thickness, y - (len * GameConfig.tileSize), thickness, (len * GameConfig.tileSize));
			g2.fillRect(x - thickness, y - (len * GameConfig.tileSize), thickness, (len * GameConfig.tileSize));
		}
		if (rot == 1) {
			//South; Most straightforward to draw
			g2.fillRect(x - thickness, y, thickness, len * GameConfig.tileSize);
			g2.fillRect(x + (tileWidth * GameConfig.tileSize) - thickness, y, thickness, len * GameConfig.tileSize);
		}
		if (rot == 2) {
			//East
			g2.fillRect(x, y - thickness, len * GameConfig.tileSize, thickness);
			g2.fillRect(x, y - (tileWidth * GameConfig.tileSize) - thickness, len * GameConfig.tileSize, thickness);
		}
		if (rot == 3) {
			//West
			g2.fillRect(x - len * GameConfig.tileSize, y - thickness, len * GameConfig.tileSize, thickness);
			g2.fillRect(x - len * GameConfig.tileSize, y + (tileWidth * GameConfig.tileSize) - thickness, len * GameConfig.tileSize, thickness);
		}
	}

}
