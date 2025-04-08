/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Defines the abstract class barrier used for objects that the player can collide with.
 */
package barrier;

import game.GameConfig;
import direction.Direction;
import game.Drawable;



public abstract class Barrier implements Collidable, Drawable {
	// The location of the upper-left part of the the barrier (what is "upper-left"
	// is defined by each sub-class). The
	// barrier is aligned with the tile-layout of the game. Therefore, x and y
	// represent x grid tiles over and y grid tiles down, respectively.
	protected int tileX, tileY;
	// Actual location of the upper-left part of the of the barrier.
	protected int x, y;
	// Rotation of the barrier defined by direction. Use values defined in the Direction class
	protected final Direction dir;

	/**
	 * TODO: potentially have the system link to a previous node, such that we can
	 * build onto the path without having to know the exact x-position for each
	 * barrier type. E.g: add a straight hallway onto a t-split just by passing the
	 * t-split to a new hallway constructor argument along with the side to connect,
	 * which then calculates the x & y positions for us automatically. This will
	 * also help a lot with automatic maze generation.
	 */
	public Barrier(int tileX, int tileY, Direction dir) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.x = tileX * GameConfig.tileSize;
		this.y = tileY * GameConfig.tileSize;
		this.dir = dir;
	}
	
	//TODO: define getters (& maybe setters)

}
