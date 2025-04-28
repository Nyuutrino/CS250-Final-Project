/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Node interface for linking corridors to another. This is useful for generating a network of corridors (e.g: a maze)
 */
package barrierNodes;

/**
 * 
 */
public interface Nodes {
	/**
	 * Gets a corridor's available nodes to attach to
	 * @return an array of nodes
	 */
	public Node[] getAvailableNodes();

	/**
	 * Gets where a node's attachment point's tileY coordinate is
	 * @return the tileX coordinate of the attachment point
	 */
	public int getAttachmentPointTX(Node targetNode);

	/**
	 * Gets where a node's attachment point's tileY coordinate is
	 * @return the tileY coordinate of the attachment point
	 */
	public int getAttachmentPointTY(Node targetNode);
}
