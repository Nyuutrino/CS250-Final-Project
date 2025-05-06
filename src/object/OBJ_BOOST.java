package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_BOOST extends Entity{


	
		public OBJ_BOOST(GamePanel gp){
			super(gp);
			name = "Boost";
			down1 = setup("/Objects/boots");
			
			
		}

	
	
}
