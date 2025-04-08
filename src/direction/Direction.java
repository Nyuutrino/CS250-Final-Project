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
	// Defines values for directions. Use when specifying direction
	// The number assignments are set up to work with left() and right() functions.
	public static final int NORTH = 1;
	public static final int SOUTH = 3;
	public static final int EAST = 2;
	public static final int WEST = 0;
	// Direction of the class instance
	private final int dir;

	/**
	 * Instantiates a Direction class instance
	 * 
	 * @param direction The direction of the class. Use the static variables defined
	 *                  in this class for the argument.
	 */
	public Direction(int direction) {
		if (direction < 0 || direction > 3)
			throw new IllegalArgumentException(
					"Direction supplied is invalid! Use one of the values defined in this class with the static fields.");
		dir = direction;
	}

	/**
	 * Gets direction
	 * 
	 * @return the direction
	 */
	public int getDirection() {
		return dir;
	}

	/**
	 * Returns the direction to the left of a specified direction (e.g: if you are
	 * facing north and turn left, you would be facing west)
	 */
	public static int left(int dir) {
		// The "4 +" is to ensure that the number will wrap around to 0 (e.g: if we're
		// facing west and go left, it would subtract 1 and normally we'd be negative.
		// But with the + 4 we would get the result 3 (south).
		return (4 + dir - 1) % 4;
	}

	/**
	 * Returns the direction to the right of a specified direction (e.g: if you are
	 * facing north and turn right, you would be facing east)
	 * 
	 * @param dir
	 * @return
	 */
	public static int right(int dir) {
		return (4 + dir + 1) % 4;
	}

	/**
	 * Function overload of left() used to take a Direction class instance
	 */
	public static Direction left(Direction dir) {
		return new Direction((4 + dir.getDirection() - 1) % 4);
	}

	/**
	 * Function overload of right() used to take a Direction class instance
	 */
	public static Direction right(Direction dir) {
		return new Direction((4 + dir.getDirection() + 1) % 4);
	}

}
