//package main;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.text.DecimalFormat;
//
//import direction.Direction;
//import object.Key;
//
//public class UI {
//	Font arial_40;
//	Font arial_80;
//	BufferedImage keyImage;
//	GamePanel gp;
//	Graphics2D g2;
//	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;
//	public boolean gameFinished = false;
//	double playTime;
//	DecimalFormat dFormat = new DecimalFormat("0.00");
//	public int commandNum = 0;
//
//	//GAMEPANEL
//	public UI(GamePanel gp) {
//		this.gp = gp;
//		arial_40 = new Font("Arial", Font.PLAIN, 40);
//		arial_80 = new Font("Arial", Font.BOLD, 80);
//		Key key = new Key(0, 0, new Direction(Direction.EAST));
//		keyImage = key.image;
//	}
//
//	//SHOWMESSAGE TOGGLE
//	public void draw(Graphics2D g2) {
//		this.g2 = g2;
//		//DefaultFont
//		g2.setFont(arial_40);
//		g2.setColor(Color.white);
//
//		//PLAY
//		if(gp.gameState == gp.playState) {
//			//POPUP MESSAGE
//			if (messageOn == true) {
//				g2.drawString(message, getXforcentertext(message),(gp.screenHeight/2)-gp.tileSize);
//
//				messageCounter++;
//				if(messageCounter > 120) {
//					messageOn = false;
//					messageCounter = 0;
//					gp.player.speed = gp.player.originalSpeed;
//				}
//			}
//		}
//
//		//PAUSED
//		if(gp.gameState == gp.pauseState) {
//			drawPauseScreen();
//		}
//		//TITLE
//		if(gp.gameState == gp.titleState) {
//			drawTitleScreen();
//		}
//		//END
//		if(gp.gameState == gp.completeState) {
//			drawCompleteScreen();
//		}
//	}
//
//	//DRAWMETHODS
//	public void showMessage(String text) {
//		message = text;
//		messageOn = true;
//	}
//
//	public int getXforcentertext(String text) {
//		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//		int x = gp.screenWidth/2 - length/2;
//		return x;
//	}
//
//	public void drawTitleScreen() {
//		//BACKGROUND COLOR
//		g2.setColor(Color.black);
//		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//
//		//TITLE
//		String text = "PHANTOM DASH";
//		int x = getXforcentertext(text)-gp.tileSize*5;
//		int y = gp.tileSize*3;
//
//		//SHADOW
//		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
//		g2.setColor(Color.gray);
//		g2.drawString(text, x+5, y+5);
//
//		//TITLEFONT
//		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
//		g2.setColor(Color.white);
//		g2.drawString(text, x, y);
//
//		//TITLESPRITE
//		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
//		y += gp.tileSize*2;
//		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
//
//		//MENU
//		//START
//		g2.setFont(arial_40);
//		text = "START GAME";
//		x = getXforcentertext(text);
//		y += gp.tileSize*5;
//		g2.drawString(text, x, y);
//		if (commandNum == 0) {
//			g2.drawString(">",x-gp.tileSize,y);
//		}
//
//		//EXIT
//		g2.setFont(arial_40);
//		text = "EXIT GAME";
//		x = getXforcentertext(text);
//		y += gp.tileSize;
//		g2.drawString(text, x, y);
//		if (commandNum == 1) {
//			g2.drawString(">",x-gp.tileSize,y);
//		}
//	}
//
//	public void drawCompleteScreen() {
//		g2.setFont(arial_80);
//		g2.setColor(Color.white);
//
//		String text = "YOU ESCAPED";
//		int x = getXforcentertext(text);
//		int y = gp.screenHeight/2 - gp.tileSize;
//		gp.stopMusic();
//		gp.playMusic(1);
//
//		g2.drawString(text, x, x);
//		gp.gameThread = null;
//	}
//
//
//	public void drawPauseScreen() {
//
//		String text = "PAUSED";
//		int x = getXforcentertext(text);
//		int y = gp.screenHeight/2;
//
//
//		g2.drawString(text, x, y);
//	}
//
//
//}
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	Font arial_40;
	Font arial_80;
	BufferedImage keyImage;
	GamePanel gp;
	Graphics2D g2;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("0.00");
	public int commandNum = 0;

	//GAMEPANEL
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80 = new Font("Arial", Font.BOLD, 80);
	}

	//SHOWMESSAGE TOGGLE
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		//DefaultFont
		g2.setFont(arial_40);
		g2.setColor(Color.white);

		//PLAY
		if(gp.gameState == gp.playState) {
			//POPUP MESSAGE
			if (messageOn == true) {
				g2.drawString(message, getXforcentertext(message),(gp.screenHeight/2)-gp.tileSize);

				messageCounter++;
				if(messageCounter > 120) {
					messageOn = false;
					messageCounter = 0;
					gp.player.speed = 4;
				}
			}
		}

		//PAUSED
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		//TITLE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		//END
		if(gp.gameState == gp.completeState) {
			drawCompleteScreen();
		}
		//LOSS
		if(gp.gameState == gp.lossState) {
			drawLossScreen();
		}


	}

	//DRAWMETHODS
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public int getXforcentertext(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}

	public void drawTitleScreen() {
		//BACKGROUND COLOR
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		//TITLE
		String text = "PHANTOM DASH";
		int x = getXforcentertext(text)-gp.tileSize*5;
		int y = gp.tileSize*3;

		//SHADOW
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);

		//TITLEFONT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		//TITLESPRITE
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

		//MENU
		//START
		g2.setFont(arial_40);
		text = "START GAME";
		x = getXforcentertext(text);
		y += gp.tileSize*5;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">",x-gp.tileSize,y);
		}

		//EXIT
		g2.setFont(arial_40);
		text = "EXIT GAME";
		x = getXforcentertext(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">",x-gp.tileSize,y);
		}
	}

	public void drawLossScreen() {
		//BACKGROUND COLOR
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		//LOSS TEXT
		String text = "YOU DID NOT ESCAPE!!";
		int x = getXforcentertext(text)-gp.tileSize*5;
		int y = gp.tileSize*3;

		//SHADOW
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);

		//TITLEFONT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		//TITLESPRITE
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

		//MENU
		//START
		g2.setFont(arial_40);
		text = "NEW GAME?";
		x = getXforcentertext(text);
		y += gp.tileSize*5;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">",x-gp.tileSize,y);
		}

		//EXIT
		g2.setFont(arial_40);
		text = "EXIT GAME";
		x = getXforcentertext(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">",x-gp.tileSize,y);
		}
	}

	public void drawCompleteScreen() {
		g2.setFont(arial_80);
		g2.setColor(Color.white);

		String text = "YOU ESCAPED";
		int x = getXforcentertext(text);
		int y = gp.screenHeight/2 - gp.tileSize;
		gp.stopMusic();
		gp.playMusic(1);

		g2.drawString(text, x, x);
		gp.gameThread = null;
	}


	public void drawPauseScreen() {

		String text = "PAUSED";
		int x = getXforcentertext(text);
		int y = gp.screenHeight/2;


		g2.drawString(text, x, y);
	}


}
