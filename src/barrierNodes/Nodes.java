/**
 * Author: Nyuutrino
 * Date: Apr 6, 2025
 * Description: Node interface for linking barriers to another. This is useful for generating a network of barriers (e.g: a maze)
 */
package barrierNodes;

/**
 * 
 */
public interface Nodes {
	/**
	 * Gets a barrier's available nodes to attach to
	 * @return an array of nodes
	 */
	public Node[] getAvailableNodes();

	/**
	 * Gets where the attachment point's tileX coordinate should be for the next barrier at a specified node
	 * @return the tileX coordinate of the attachment point
	 */
	public int getAttachmentPointTX(Node targetNode);

	/**
	 * Gets where the attachment point's tileY coordinate should be for the next barrier at a specified node
	 * @return the tileY coordinate of the attachment point
	 */
	public int getAttachmentPointTY(Node targetNode);
}
