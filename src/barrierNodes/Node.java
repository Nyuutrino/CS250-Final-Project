/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Defines the class Node, used for pointing to the next barrier from a given barrier in a specified direction.
 */
package barrierNodes;
import barrier.Barrier;
import direction.Direction;

/**
 * 
 */
public class Node {
	//Indicates the direction of the node
	protected final Direction dir;
	//The barrier attached to this node
	protected Barrier barrierAttachment;
	
	/**
	 * Instantiates the node
	 * @param dir the direction the node will be attached to
	 */
	public Node(Direction dir) {
		this.dir = dir;
	};
	
	/**
	 * Sets the barrier attached to the node
	 * @param barrier the barrier that will be linked with the node
	 */
	public void setBarrier(Barrier barrier) {
		//TODO: ensure barrier direction matches up with node direction
		this.barrierAttachment = barrier;
	}

}
