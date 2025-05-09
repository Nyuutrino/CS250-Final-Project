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
	//Images
	private BufferedImage up, down, left, right;
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
		up = setup("ghost_up");
		down = setup("ghost_down");
		left = setup("ghost_left");
		right = setup("ghost_right");

	}

	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/enemy/" + imageName + ".png"));
			image = uTool.scaleImage(image, GameConfig.tileSize, GameConfig.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}

	public void update() {
		direction = "standing";
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
		//Change direction as well
		if (x > 0 && x >= Math.abs(y))
			direction = "right";
		if (x < 0 && Math.abs(x) >= Math.abs(y))
			direction = "left";
		if (y > 0 && y >= Math.abs(x))
			direction = "down";
		if (y < 0 && Math.abs(y) >= Math.abs(x))
			direction = "up";
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch (direction) {
			case "up":
				image = up;
				break;
			case "down":
				image = down;
				break;
			case "left":
				image = left;
				break;
			case "right":
				image = right;
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
