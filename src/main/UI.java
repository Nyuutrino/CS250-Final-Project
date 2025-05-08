package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import direction.Direction;
import object.Key;

public class UI {
	Font arial_40;
	Font arial_80;
	BufferedImage keyImage;
	GamePanel gp;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("0.00");

	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80 = new Font("Arial", Font.BOLD, 80);
		Key key = new Key(0, 0, new Direction(Direction.EAST));
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		if(gameFinished == true) {
			
			g2.setFont(arial_80);
			g2.setPaint(Color.white);
			
			String text;
			int textLength;
			int x;
			int y;
			
			text = "YOU ESCAPED with a time of " + dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2- textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
		}
		
		
		g2.setFont(arial_40);
		g2.setPaint(Color.white);
		g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2,gp.tileSize,gp.tileSize,null);
		g2.drawString("x " + gp.player.hasKeys, 74, 65);
		
		playTime += (double)1/60;
		g2.drawString("Time: " + dFormat.format(playTime) ,174, 65);
		
		if(messageOn == true) {
	
			int textLength;
			int x;
			int y;
			
			textLength = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();
			x = gp.screenWidth/2- textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(message, x, y);
		
			
			messageCounter ++;
			
			if(messageCounter > 120) {
				messageCounter =0;
				messageOn = false;
			}
		}
		
	}
	
}
