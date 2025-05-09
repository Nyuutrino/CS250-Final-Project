package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import direction.Direction;
import game.GameConfig;
import main.GamePanel;

public class Boost extends SuperObject{

	public Boost(int tileX, int tileY, Direction dir) {
		super(tileX, tileY, dir, 1, 1);
		this.center = true;
		name = "key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Objects/boots.png"));
			uTool.scaleImage(image, GameConfig.tileSize, GameConfig.tileSize);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
