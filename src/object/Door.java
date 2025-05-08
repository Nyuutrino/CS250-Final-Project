package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import barrier.Corridor;
import barrierNodes.Node;
import barrierNodes.Nodes;
import direction.Direction;
import game.GameConfig;


public class Door extends SuperObject {

	/**
	 * Method for all of the code needed for setting up the door instance
	 */
	private void doorConstruct(){
		name = "door";
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Objects/door.png"));
			uTool.scaleImage(image, solidArea.width, solidArea.height);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs a door
	 * @param tileX defined in super
	 * @param tileY defined in super
	 * @param dir   defined in super
	 */
	public Door(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir, 2, 2);
		doorConstruct();
	}
}


