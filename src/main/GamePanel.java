package main;

import java.awt.*;

import javax.swing.JPanel;

import barrier.*;
import entity.Player;
import game.GameConfig;
import map.MapGen;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
//SCREEN SETTINGS

	final int orTileSize = GameConfig.orTileSize;
	final int scale = GameConfig.scale;
	
	public int tileSize = GameConfig.tileSize;
	public int maxScreenCol = GameConfig.maxScreenCol;
	public int maxScreenRow = GameConfig.maxScreenRow;
	public int screenWidth = GameConfig.screenWidth;
	public int screenHeight = GameConfig.screenHeight;
	
	//FPS
	int fps = GameConfig.fps;

	//WORLD SETTINGS
	public final int maxWorldCol = 30;
	public final int maxWorldRow = 32;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldCol;


	public CollisionChecker cChecker = new CollisionChecker(this);
	public SuperObject obj[] = new SuperObject[10];
	public AssetSetter aSetter = new AssetSetter(this);
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	public UI ui = new UI(this);
	Thread gameThread;
	public Player player = new Player(this,keyH);
	//Test
	//Hallway testHall = new Hallway(10, 5, 5, new Direction(Direction.EAST));


	
	//set players default position
	//private int playerX = GameConfig.playerX;
	//private int playerY = GameConfig.playerY;
	//private int playerSpeed = GameConfig.playerSpeed;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		
		
	}

	public void setupGame() {
		aSetter.setObject();
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
		//method that paints componets on the game panel
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;



		//draws tiels
		tileM.draw(g2);

		//objects
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		//draws player
		player.draw(g2);

		//UI
		ui.draw(g2);

		
		g2.dispose();
		}


		
	}
	


