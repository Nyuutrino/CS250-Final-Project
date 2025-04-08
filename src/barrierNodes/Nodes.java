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
}
