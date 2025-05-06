package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import barrier.Corner;
import barrier.FourWay;
import barrier.Hallway;
import direction.Direction;
import entity.Entity;
import entity.Player;
import game.GameConfig;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
//SCREEN SETTINGS
	
	final int orTileSize = GameConfig.orTileSize ;
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
	
	
	//GAMESTATEs
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int completeState = 3;
	
	//setting up game objects and entities
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	ArrayList<Entity> entityList = new ArrayList<>();
	
	
	public AssetSetter aSetter = new AssetSetter(this);
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	public Player player = new Player(this,keyH);
	Sound music = new Sound();
	Sound se = new Sound();
	Thread gameThread;

	//GAME PANEL
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	//SETUP OBJECTS NPCs MUSIC AND STATE
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		gameState = titleState;
	}
	
	//GAMETHREAD
	public void startGameThread() {
		gameThread =  new Thread(this);
		gameThread.start();
	}

	
	public void run() {
		
		//FPS code
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		//METHOD FOR CHECKING HOW MANY PER SEC
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
		
		if (gameState == playState) {
			player.update();
			for(int i = 0; i < npc.length;i++) {
				if (npc[i] != null) {
				npc[i].update();}
				}
		}
		if (gameState == pauseState) {
			//nothing
		}
	}
	
	public void paintComponent(Graphics g) {
		//method that paints componets on the game panel
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
	
		//TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}
		//PLAYSTATE
		else {
			//Tiles
			tileM.draw(g2);
			
			//ENTITY LIST TO AVOID BUG
			entityList.add(player);
			for (int i=0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for (int i=0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			//SORT
			Collections.sort(entityList, new Comparator <Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldy, e2.worldy);
					return result;
				}
				
			});
			
			//DRAWSORTEDLIST
			for( int i = 0; i < entityList.size();i++) {
				entityList.get(i).draw(g2);
			}
			//RESETLSIT
			for( int i = 0; i < entityList.size();i++) {
				entityList.remove(i);
			}
			
			//UI
			ui.draw(g2);

			g2.dispose();
		}
	}
	
	//music
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	//stop music
	public void stopMusic() {
		music.stop();
	}
	//method only for short sounds
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	

		
		
	}
	
	

