package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import camera.Camera;
import main.GamePanel;
import main.UtilityTool;

public class Entity {


	public int worldx, worldy;
	public int speed;

	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction = "down";

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	GamePanel gp;

	public int actionLockCounter;

	//OBJECT STUFF
	public BufferedImage image;
	public String name;
	public boolean collision = false;


	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setActions() {

	}

	public void update() {
		setActions();
		switch(direction) {
			case "up": worldy -= speed; break;
			case "down": worldy+= speed; break;
			case "left": worldx -= speed; break;
			case "right": worldx += speed; break;
		}

		spriteCounter++;
		if (spriteCounter > 12){
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}

	public void draw(Graphics2D g2) {
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
		Camera.drawImage(image, worldx, worldy, solidArea.width, solidArea.height, null);
	}

	public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;

	}

}
