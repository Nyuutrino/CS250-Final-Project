package main;

import java.awt.*;

import javax.swing.*;


import barrier.*;
import camera.Camera;
import entity.Enemy;
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
	public final int maxWorldCol = GameConfig.maxWorldCol;
	public final int maxWorldRow = GameConfig.maxWorldRow;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldCol;



	//GAMESTATEs
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int completeState = 3;
	public final int lossState = 4;

	//setting up game objects and entities
	public CollisionChecker cChecker = new CollisionChecker(this);
	public SuperObject obj[] = new SuperObject[10];
	public MapGen mapGen;
	KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	Thread gameThread;
	public Player player = new Player(this, keyH);
	public Enemy enemy = new Enemy(this);
	//Number of keys to place
	public int keyCount = 5;
	public int boostCount = 2;

	//FPS & time management systems
	int fps = GameConfig.fps;
	private Timer gameTimer;

	Sound music = new Sound();
	Sound se = new Sound();

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
		mapGen = new MapGen(0, 0, maxWorldRow, maxWorldCol, keyCount, boostCount);
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

	public void stopGameTimer() {
		gameTimer.stop();
	}

	public void update() {

		if (gameState == playState) {
			player.update();
			enemy.update();
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

			//Draw boosts
			Boost[] boosts = mapGen.getBoosts();
			for (Boost b : boosts) {
				b.draw(g2);
			}

			//Draw player
			player.draw(g2);

			//Draw enemy
			enemy.draw(g2);

			//Draw UI
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
	
	

