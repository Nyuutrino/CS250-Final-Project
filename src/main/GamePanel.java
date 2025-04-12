package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import barrier.Corner;
import barrier.FourWay;
import barrier.Hallway;
import direction.Direction;
import entity.Player;
import game.GameConfig;

public class GamePanel extends JPanel implements Runnable{
//SCREEN SETTINGS
	
	final int orTileSize = GameConfig.orTileSize ;
	final int scale = GameConfig.scale;
	
	public int tileSize = GameConfig.tileSize;
	final int maxScreenCol = GameConfig.maxScreenCol;
	final int maxScreenRow = GameConfig.maxScreenRow;
	final int screenWidth = GameConfig.screenWidth;
	final int screenHeight = GameConfig.screenHeight;
	
	//FPS
	int fps = GameConfig.fps;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this,keyH);
	//Test
	Hallway testHall = new Hallway(15, 5, 5, new Direction(Direction.SOUTH));
	Hallway testHall2 = new Hallway(testHall, testHall.getAvailableNodes()[0], 5);
	Corner testCorner = new Corner(testHall2, testHall2.getAvailableNodes()[0], Corner.RIGHT);
	Hallway testHall3 = new Hallway(testCorner, testCorner.getAvailableNodes()[0], 5);
	FourWay testFW = new FourWay(testHall3, testHall3.getAvailableNodes()[0]);
	Hallway testHall4 = new Hallway(testFW, testFW.getAvailableNodes()[2], 5);
	
	//set players default position
	private int playerX = GameConfig.playerX;
	private int playerY = GameConfig.playerY;
	private int playerSpeed = GameConfig.playerSpeed;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		testHall.getAvailableNodes()[0].setBarrier(testHall2);
		testHall2.getAvailableNodes()[0].setBarrier(testCorner);
		testCorner.getAvailableNodes()[0].setBarrier(testHall3);
		testHall3.getAvailableNodes()[0].setBarrier(testFW);
		testFW.getAvailableNodes()[2].setBarrier(testHall4);
	}
	
	public void startGameThread() {
		gameThread =  new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		//FPS code
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		
		while (gameThread != null) {
			//System.out.println("game is running");
			
			currentTime = System.nanoTime();
			
			//Delta method
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
//				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
		
	}
	public void update() {
		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);
		testHall.draw(g2);
		testHall2.draw(g2);
		testCorner.draw(g2);
		testHall3.draw(g2);
		testFW.draw(g2);
		testHall4.draw(g2);
		if(!testHall.inBounds(player.x, player.y)) {
			g2.setFont(new Font("Tahoe", Font.BOLD, 14));
			g2.drawString("Player out of bounds!", 10, 10);
		}
		
		g2.dispose();
		
		
	}
	
	}
	

