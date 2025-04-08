/**
 * Author: Nyuutrino
 * Date: Apr 7, 2025
 * Description: Defines the direction class, used to store direction information. After instantiation the direction is immutable
 */
package direction;

/**
 * 
 */
public class Direction {
	//Defines values for directions. Use when specifying direction
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	//Direction of the class instance
	private final int dir;
	
	/**
	 * Instantiates a Direction class instance
	 * @param direction The direction of the class. Use the static variables defined in this class for the argument.
	 */
	public Direction(int direction) {
		if (direction < 0 || direction > 3)
			throw new IllegalArgumentException("Direction supplied is invalid! Use one of the values defined in this class with the static fields.");
		dir = direction;
	}
	/**
	 * Gets direction
	 * @return the direction
	 */
	public int getDirection() {
		return dir;
	}

}
