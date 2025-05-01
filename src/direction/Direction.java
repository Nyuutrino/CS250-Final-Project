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
	 * Instantiates a Direction class instance
	 *
	 * @param direction The direction of the class. Uses a pre-existing direction class instead of the integer variables
	 */
	public Direction(Direction direction) {
		this.dir = direction.getDirection();
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
	 * 
	 * @param dir The direction to perform the operation on
	 * @return The resulting direction as an integer (conforming to the numerical
	 *         assignments of direction in the Direction class)
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
	 * @param dir The direction to perform the operation on
	 * @return The resulting direction as an integer (conforming to the numerical
	 *         assignments of direction in the Direction class)
	 */
	public static int right(int dir) {
		//No need for the "+ 4" since we are simply adding and not subtracting
		return (dir + 1) % 4;
	}

	/**
	 * Returns the direction opposite of a specified direction (e.g: if you are
	 * facing north, the opposite direction would be south)
	 * 
	 * @param dir The direction to perform the operation on
	 * @return The resulting direction as an integer (conforming to the numerical
	 *         assignments of direction in the Direction class)
	 */
	public static int opposite(int dir) {
		return (dir + 2) % 4;
	}

	/**
	 * Function overload of left() used to take a Direction class instance instead
	 * 
	 * @param dir The direction to perform the operation on
	 * @return The resulting direction as a new instance of the Direction class
	 */
	public static Direction left(Direction dir) {
		return new Direction((4 + dir.getDirection() - 1) % 4);
	}

	/**
	 * Function overload of right() used to take a Direction class instance instead
	 * 
	 * @param dir The direction to perform the operation on
	 * @return The resulting direction as a new instance of the Direction class
	 */
	public static Direction right(Direction dir) {
		return new Direction((dir.getDirection() + 1) % 4);
	}

	/**
	 * Function overload of opposite() used to take a Direction class instance
	 * instead
	 * 
	 * @param dir The direction to perform the operation on
	 * @return The resulting direction as a new instance of the Direction class
	 */

	public static Direction opposite(Direction dir) {
		return new Direction((dir.getDirection() + 2) % 4);
	}

	@Override
	public String toString() {
		switch (dir) {
			case NORTH:
				return "North";
			case SOUTH:
				return "South";
			case EAST:
				return "East";
			case WEST:
				return "West";
			default:
				return "";
		}
	}

}
