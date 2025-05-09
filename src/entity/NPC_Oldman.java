package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Oldman extends Entity{

	public NPC_Oldman(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
	}
	
	public void getImage() {
		//images for movement, 1 and 2 allow for simple animation loop
		//buffered in setup
		up1 = setup("/npc/oldman_up_1");
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1");
		down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1");
		left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1");
		right2 = setup("/npc/oldman_right_2");
	}
	
	public void setActions() {
		actionLockCounter ++;
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1;
		
			if(i <= 25) {
			direction = "up";
			}	
		
			if(i > 25 && i <= 50) {
			direction = "down";
			}
		
			if(i > 50 && i <= 75) {
			direction = "left";
			}
		
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
		
	}
	
	
	

}
