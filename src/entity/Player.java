package entity;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import game.Drawable;

public class Player extends Entity implements Drawable{
	
	//Class specifically for player entity, further actions only the player will do will be here
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKeys = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 -(gp.tileSize/2);
		screenY= gp.screenHeight/2 -(gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		
		solidAreaDefaultX =solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		//method to set the player start location as well as the starting direction of the sprite
		worldx = gp.tileSize* 10;
		worldy= gp.tileSize * 10;
		speed= 4; //amount pixels moved
		direction = "down";
	}
	
	public void getPlayerImage() {
		//images for movement, 1 and 2 allow for simple animation loop
		//buffered in setup
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");

	}
		
	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;
	
	}	
	
	
	public void update() {
		//update method to check for key press or for no press, also alternates every few tics
		if(keyH.upPressed == true | keyH.downPressed == true | keyH.leftPressed == true | keyH.rightPressed == true) {
			spriteCounter++;
			if (spriteCounter >12){
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum ==2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
		if (keyH.upPressed == true) {
			direction = "up";}	
		else if (keyH.downPressed == true) {
			direction = "down";}
		else if (keyH.leftPressed == true) {
			direction = "left";}
		else if (keyH.rightPressed == true) {
			direction = "right";}
		else 
			direction = "standing";
		
		//collision check
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		//object collision
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		
		//if false
		if(collisionOn == false) {
			
			switch(direction) {
			
				case "up": worldy -= speed; break;
				case "down": worldy+= speed; break;
				case "left": worldx -= speed; break;
				case "right": worldx += speed; break;
			}
		}
		}
	
		//method that checks for a object name, and an index, then removes or allows for interaction with that item
	public void pickUpObject(int i) {

		if(i != 999) {
			String objectName = gp.obj[i].name;
			switch(objectName) {
			case "key":
				gp.obj[i] = null;
				hasKeys++;
				gp.ui.showMessage("You got a key!");
				break;
				
			case "door":
				if(hasKeys > 1) {
					gp.obj[i] = null;
					hasKeys --;
					gp.ui.gameFinished = true;
				}
				else
					gp.ui.showMessage("You dont have enough Keys!");
				break;
			}
			
		}
		
	}
	
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.blue);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		switch (direction) {
		case "up":
			if (spriteNum ==1)
				image = up1;
			if (spriteNum ==2)
				image = up2;
			break;
		case "down":
			if (spriteNum ==1)
				image = down1;
			if (spriteNum ==2)
				image = down2;
			break;
		case "left":
			if (spriteNum ==1)
				image = left1;
			if (spriteNum ==2)
				image = left2;
			break;
		case "right":
			if (spriteNum ==1)
				image = right1;
			if (spriteNum ==2)
				image = right2;
			break;
		case "standing":
			image = down1;
		}
		g2.drawImage(image, screenX, screenY,null);
		
	}
}
