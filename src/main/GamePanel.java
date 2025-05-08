package main;

import java.awt.*;

import javax.swing.*;

import barrier.*;
import camera.Camera;
import entity.Player;
import game.GameConfig;
import map.MapGen;
import object.Key;
import object.SuperObject;

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

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		Camera.init(this);
	}

	public void setupGame() {
//		aSetter.setObject();
		//Create map
		//TODO: integrate with key number stored somewhere in the project
		mapGen = new MapGen(0, 0, maxWorldRow, maxWorldRow, keyCount);
		long seed = 12345;
		mapGen.genMap(seed);
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
	}

	public void paintComponent(Graphics g) {
		//method that paints components on the game panel
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

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

		g2.dispose();
	}
}