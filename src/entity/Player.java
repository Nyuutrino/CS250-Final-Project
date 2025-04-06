package entity;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

import game.Drawable;

public class Player extends Entity implements Drawable{
	
	//Class specifically for player entity, further actions only the player will do will be here
	
	GamePanel gp;
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		//method to set the player start location as well as the starting direction of the sprite
		x = 100;
		y= 100;
		speed= 4; //amount of tiles the entity moves
		direction = "down";
	}
	
	public void getPlayerImage() {
		//images for movement, 1 and 2 allow for simple animation loop
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
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
			direction = "up";
			y -= speed;
		}
		
		else if (keyH.downPressed == true) {
			direction = "down";
			y += speed;
		}
		
		else if (keyH.leftPressed == true) {
			direction = "left";
			x -= speed;
		}
		else if (keyH.rightPressed == true) {
			direction = "right";
			x += speed;
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
		}
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
		
	}
}
