/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Defines the abstract class item used for items that the entities interact with
 */

package item;

public abstract class Item {
	//Location of object
	public int x, y;
	public Item(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
