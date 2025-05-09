package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import barrier.Barrier;
import camera.Camera;
import game.GameConfig;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import game.Drawable;
import object.Boost;
import object.Door;
import object.Key;

public class Player extends Entity implements Drawable{
	
	//Class specifically for player entity, further actions only the player will do will be here
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKeys = 0;
	public int originalSpeed = 4;
	//Used to detect collision
	private Rectangle collisionBox;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = GameConfig.tileSize;
		solidArea.height = GameConfig.tileSize;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		//method to set the player start location as well as the starting direction of the sprite
		//Note: coordinates are based on the image center
		worldx = 0;
		worldy = 0;
		speed = originalSpeed; //amount pixels moved
		direction = "down";
		collisionBox = new Rectangle(0, 0, 0, 0);
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

		//Collision check
		//Easiest way is to use the collision box and see if it collides with an object. If so, check what that object is and react accordingly
		//Make the collision box face in the direction the player will move
		int xOffset = 0;
		int yOffset = 0;
		int width = 1;
		int height = 1;
		if (direction.equals("left")){
			xOffset = solidArea.width / 2 + speed;
			width = solidArea.width / 2 + speed;
		}
		else if (direction.equals("right")) width = solidArea.width / 2 + speed;
		if (direction.equals("up")){
			yOffset = solidArea.height / 2 + speed;
			height = solidArea.height / 2 + speed;
		}
		else if (direction.equals("down")) height = solidArea.height / 2 + speed;
		collisionBox = new Rectangle(worldx - xOffset, worldy - yOffset, width, height);

		//Save for later
		Door door = null;
		//Check for collision with barriers
		Barrier[] barriers = gp.mapGen.getBarriers();
		for (Barrier b : barriers) {
			if (b.isColliding(collisionBox) && !(b instanceof Door)) {
				//It will collide, so calculate where we can move
				Rectangle intersection = b.getIntersection(collisionBox);
				if (direction.equals("left"))
					worldx = (int) intersection.getMaxX() + solidArea.width / 2;
				else if (direction.equals("right"))
					worldx = (int)intersection.getMinX() - solidArea.width / 2;
				else if (direction.equals("up"))
					worldy = (int)intersection.getMinY() + solidArea.height / 2;
				else if (direction.equals("down"))
					worldy = (int)intersection.getMaxY() - solidArea.height / 2;
			}
			if (b instanceof Door) door = (Door)b;
			if (b instanceof Door && door.isColliding(collisionBox)) {
				//It's colliding with the door. Check if it's past the edge & if so, keep the player from crossing
				Rectangle doorRect = door.getRect();
				if (collisionBox.x + collisionBox.width >= doorRect.getMaxX()) {
					worldx -= speed;
				}
				if (collisionBox.y + collisionBox.height >= doorRect.getMaxY()) {
					worldy -= speed;
				}
			}
		}
		//Move the player
		switch (direction) {
			case "up":
				worldy -= speed;
				break;
			case "down":
				worldy += speed;
				break;
			case "left":
				worldx -= speed;
				break;
			case "right":
				worldx += speed;
				break;
		}

		//Reset collision box
		collisionBox = new Rectangle(worldx - solidArea.width / 2, worldy - solidArea.height / 2, solidArea.width, solidArea.height);

		//Now check for collisions with objects

		//Keys
		Key[] keys = gp.mapGen.getKeys();
		for (Key k : keys) {
			if (k.isColliding(collisionBox)) {
				gp.playSE(2);
				hasKeys++;
				gp.ui.showMessage(String.format("Keys: %d/%d", hasKeys, gp.keyCount));
				gp.mapGen.removeKey(k);
				return;
			}
		}

		//Boosts
		Boost[] boosts = gp.mapGen.getBoosts();
		for (Boost b : boosts) {
			if (b.isColliding(collisionBox)) {
				gp.player.speed += 3;
				gp.ui.showMessage("Speed boost!");
				gp.mapGen.removeBoost(b);
				return;
			}
		}

		//Door
		if (door.isColliding(collisionBox)) {
			if (hasKeys == gp.keyCount) {
				gp.playSE(4);
				gp.gameState = gp.completeState;
			}
			else
				gp.ui.showMessage("\"I don't have enough keys...\"");
		}

		//Enemy
		if (gp.enemy.isColliding(collisionBox)) {
			gp.gameState = gp.lossState;
		}

		//Update camera position
		Camera.updateCameraPos();
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
		g2.drawImage(image, screenX, screenY,null);
	}
}
