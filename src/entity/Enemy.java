/**
 * Author: Nyuutrino
 * Date: May 8th, 2025
 * Description: Defines the Enemy class, the antagonist to the player
 */
package entity;

import barrier.Collidable;
import camera.Camera;
import game.GameConfig;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends Entity implements Collidable {
	//Used to detect collision
	private Rectangle collisionBox;
	public Enemy (GamePanel gp) {
		super(gp);
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = GameConfig.tileSize;
		solidArea.height = GameConfig.tileSize;
		setDefaultValues();
		getPlayerImage();
		speed = 2;
	}

	public void setDefaultValues() {
		//Note: coordinates are based on the image center
		worldx = GameConfig.maxWorldCol * GameConfig.tileSize;
		worldy = GameConfig.maxWorldRow * GameConfig.tileSize;
		collisionBox = new Rectangle(GameConfig.maxWorldCol * GameConfig.tileSize - solidArea.width / 2, GameConfig.maxWorldRow * GameConfig.tileSize - solidArea.width / 2, solidArea.width, solidArea.height);
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
			image = uTool.scaleImage(image, GameConfig.tileSize, GameConfig.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}

	public void update() {
		//Catch up to the player

		//Calculate angle between player and enemy
		int dx = gp.player.worldx - worldx;
		int dy = gp.player.worldy - worldy;
		//If the distance is 0, return
		if (dx == 0 && dy == 0) return;
		double angle = Math.atan2(dy, dx);
		//Get ratio of hyp to x and y axis
		double xRat = Math.cos(angle);
		double yRat = Math.sin(angle);
		//Hyp will be the speed, so calculate the respective coordinates from that
		int x = (int) Math.floor(speed * xRat);
		int y = (int) Math.floor(speed * yRat);
		//Move the enemy
		worldx += x;
		worldy += y;
		collisionBox.translate(x, y);
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
				if (spriteNum == 1)
					image = left1;
				if (spriteNum == 2)
					image = left2;
				break;
			case "right":
				if (spriteNum == 1)
					image = right1;
				if (spriteNum ==2)
					image = right2;
				break;
			case "standing":
				image = down1;
		}
		Camera.drawImage(image, worldx - solidArea.width / 2, worldy - solidArea.height / 2, solidArea.width, solidArea.height, g2);
	}

	@Override
	public boolean isColliding(Rectangle rect) {
		return collisionBox.intersects(rect);
	}

	@Override
	public Rectangle getIntersection(Rectangle rect) {
		return collisionBox.intersection(rect);
	}
}
