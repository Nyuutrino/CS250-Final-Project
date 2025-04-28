/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Defines the class Node, used for pointing to the next corridor from a given corridor in a specified direction.
 */
package barrierNodes;
import direction.Direction;
import game.GameConfig;

/**
 * 
 */
public class Node {
	//Indicates the direction of the node
	protected final Direction dir;
	//Coordinates of the node (both tile coordinates & real coordinates)
	protected final int nodeTX;
	protected final int nodeTY;
	protected final int nodeX;
	protected final int nodeY;
	//The corridor node attached to this node
	protected Node corridorAttachmentNode;
	
	/**
	 * Instantiates the node
	 * @param dir the direction the node will be attached to
	 */
	public Node(Direction dir, int nodeTX, int nodeTY) {
		this.dir = dir;
		this.nodeTX = nodeTX;
		this.nodeTY = nodeTY;
		this.nodeX = nodeTX * GameConfig.tileSize;
		this.nodeY = nodeTY * GameConfig.tileSize;
	}

	/**
	 * Links two nodes together from different corridors
	 * @param targetNode the other corridor's target node that will be linked with the node
	 */
	public void linkNodes(Node targetNode) {
		//We allow directions from the same/opposite direction. However, we also need to make sure that the attachment points match
		if (targetNode.getDirectionInt() != dir.getDirection() && targetNode.getDirectionInt() != Direction.opposite(dir.getDirection())){
			throw new IllegalArgumentException("Directions not compatible! Expected '%d' or '%d', got '%d'.".formatted(dir.getDirection(), Direction.opposite(dir).getDirection(), targetNode.getDirectionInt()));
		}
		if (targetNode.getNodeX() != nodeX || targetNode.getNodeY() != nodeY){
			throw new IllegalArgumentException("Node x and y locations do not match! Node to attach to has coordinates %d, %d, while node to be attached has coordinates %d, %d.".formatted(nodeX, nodeY, targetNode.getNodeX(), targetNode.getNodeY()));
		}
		this.corridorAttachmentNode = targetNode;
	}

	/**
	 * Gets the direction of the node
	 * @return the direction class of the node
	 */
	public Direction getDirection() {
		return this.dir;
	}

	/**
	 * Gets the direction of the node as an integer
	 * @return the direction integer of the node
	 */
	public int getDirectionInt(){
		return this.dir.getDirection();
	}

	/**
	 * Gets the tile x location of the node
	 */
	public int getNodeTX() {
		return nodeTX;
	}

	/**
	 * Gets the tile y location of the node
	 */
	public int getNodeTY() {
		return nodeTY;
	}

	/**
	 * Gets the x location of the node
	 */
	public int getNodeX() {
		return nodeX;
	}

	/**
	 * Gets the y location of the node
	 */
	public int getNodeY() {
		return nodeY;
	}

}
