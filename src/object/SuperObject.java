package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldx, worldy;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldx - gp.player.worldx + gp.player.screenX;
		int screenY = worldy - gp.player.worldy + gp.player.screenY;
		
		
		//draw the row, only renders the screen
		if(worldx + gp.tileSize> gp.player.worldx - gp.player.screenX && 
			worldx - gp.tileSize < gp.player.worldx + gp.player.screenX &&
			worldy + gp.tileSize> gp.player.worldy - gp.player.screenY &&
			worldy - gp.tileSize< gp.player.worldy + gp.player.screenY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
		
		
	}
	
}

