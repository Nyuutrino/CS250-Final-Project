package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;


public class OBJ_door extends Entity{
		
		public OBJ_door(GamePanel gp){
			super(gp);
			name = "door";
			collision = true;
			down1 = setup("/Objects/door");
			
			}
	
}
