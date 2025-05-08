package main;

import java.awt.*;

import javax.swing.*;

import direction.Direction;
import entity.Entity;
import barrier.*;
import camera.Camera;
import entity.Player;
import game.GameConfig;
import map.MapGen;
import object.*;

public class GamePanel extends JPanel{
//SCREEN SETTINGS

	final int orTileSize = GameConfig.orTileSize;
	final int scale = GameConfig.scale;

	public int tileSize = GameConfig.tileSize;
	public int maxScreenCol = GameConfig.maxScreenCol;
	public int maxScreenRow = GameConfig.maxScreenRow;
	public int screenWidth = GameConfig.screenWidth;
	public int screenHeight = GameConfig.screenHeight;

	//WORLD SETTINGS
	public final int maxWorldCol = 30;
	public final int maxWorldRow = 32;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldCol;



	//GAMESTATEs
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int completeState = 3;

	//setting up game objects and entities
	public CollisionChecker cChecker = new CollisionChecker(this);
	public SuperObject obj[] = new SuperObject[10];
	public MapGen mapGen;
	KeyHandler keyH = new KeyHandler();
	public UI ui = new UI(this);
	Thread gameThread;
	public Player player = new Player(this,keyH);
	//Number of keys to place
	public int keyCount = 5;

	//FPS & time management systems
	int fps = GameConfig.fps;
	private Timer gameTimer;

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
		Camera.init(this);
	}


	//SETUP OBJECTS NPCs MUSIC AND STATE
	public void setupGame() {
		//Create map
		//TODO: integrate with key number stored somewhere in the project
		mapGen = new MapGen(0, 0, maxWorldRow, maxWorldRow, keyCount);
		long seed = 12345;
		mapGen.genMap(seed);
		gameState = titleState;
	}

	//Game timer loop
	public void startGameTimer() {
		int delay = 1000 / fps; // milliseconds delay for 60fps
		gameTimer = new Timer(delay, e -> {
			update();
			repaint();
		});
		//Start timer
		gameTimer.start();
	}

	public void update() {
		player.update();

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
		//method that paints components on the game panel
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
	
		//TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}
		//PLAYSTATE
		else {
			//Draw barriers
			Barrier[] barriers = mapGen.getBarriers();
			for(Barrier b : barriers) {
				b.draw(g2);
			}
			//Draw keys
			Key[] keys = mapGen.getKeys();
			for (Key k : keys) {
				k.draw(g2);
			}

			//Draw player
			player.draw(g2);

			//Draw UI
			ui.draw(g2);

//			//ENTITY LIST TO AVOID BUG
//			entityList.add(player);
//			for (int i=0; i < npc.length; i++) {
//				if (npc[i] != null) {
//					entityList.add(npc[i]);
//				}
//			}
//
//			for (int i=0; i < obj.length; i++) {
//				if (obj[i] != null) {
//					entityList.add(obj[i]);
//				}
//			}
//
//			//SORT
//			Collections.sort(entityList, new Comparator <Entity>() {
//
//				@Override
//				public int compare(Entity e1, Entity e2) {
//					int result = Integer.compare(e1.worldy, e2.worldy);
//					return result;
//				}
//
//			});
//
//			//DRAWSORTEDLIST
//			for( int i = 0; i < entityList.size();i++) {
//				entityList.get(i).draw(g2);
//			}
//			//RESETLSIT
//			for( int i = 0; i < entityList.size();i++) {
//				entityList.remove(i);
//			}
//
//			//UI
//			ui.draw(g2);

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
	
	

