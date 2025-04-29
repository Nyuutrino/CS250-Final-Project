/**
 * Author: Nyuutrino
 * Date: Apr 20, 2025
 * Description: Defines the abstract Corridor class used for defining objects that the player can go through and has nodes to attach other corridor objects to.
 */
package barrier;

import barrierNodes.Node;
import barrierNodes.Nodes;
import direction.Direction;

public abstract class Corridor extends Barrier implements Nodes {
	public Corridor(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir);
	}
}
