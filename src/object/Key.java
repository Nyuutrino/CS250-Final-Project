package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import direction.Direction;
import game.GameConfig;
import main.GamePanel;

//Yes, key is a "barrier." The barrier class has stuff that makes implementation easier, so I'll use it
public class Key extends SuperObject{
	
		public Key(int tileX, int tileY, Direction dir) {
			super(tileX, tileY, dir, 1, 1);
			this.center = true;
			name = "key";
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/Objects/key.png"));
				uTool.scaleImage(image, GameConfig.tileSize, GameConfig.tileSize);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
}
