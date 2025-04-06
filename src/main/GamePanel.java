package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

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
				System.out.println("FPS: " + drawCount);
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
		
		g2.dispose();
		
		
	}
	
	}
	

