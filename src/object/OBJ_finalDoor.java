package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

	
	
public class OBJ_finalDoor extends Entity{
	public OBJ_finalDoor(GamePanel gp){
		super(gp);
		name = "finaldoor";
		collision = true;
		down1 = setup("/Objects/door");
		
	}

	}


