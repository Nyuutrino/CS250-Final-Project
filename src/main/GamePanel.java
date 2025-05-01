package main;

import java.awt.*;

import javax.swing.JPanel;

import barrier.*;
import entity.Player;
import game.GameConfig;
import map.MapGen;

public class GamePanel extends JPanel implements Runnable {
//SCREEN SETTINGS

	final int orTileSize = GameConfig.orTileSize;
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
	Player player = new Player(this, keyH);
	//Test
	public Barrier[] barriers;
	Rectangle playerRect = new Rectangle(player.x, player.y, tileSize, tileSize);
	//set players default position
	private int playerX = GameConfig.playerX;
	private int playerY = GameConfig.playerY;
	private int playerSpeed = GameConfig.playerSpeed;

	//Map
	private MapGen mapGen;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		barriers = new Barrier[]{};
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		//FPS code
		double drawInterval = 1000000000 / fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		mapGen = new MapGen(0, 2, screenHeight / tileSize / 2 - 2, screenWidth / tileSize / 2 - 2);
		mapGen.genMap(1234567);
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
		playerRect.x = player.x;
		playerRect.y = player.y;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		player.draw(g2);
		Barrier[] mapGenBarriers = mapGen.getBarriers();
		for (int i = 0; i < mapGenBarriers.length; i++) {
			Barrier c = mapGenBarriers[i];
			if (c == null){
				System.out.printf("Barrier %d out of %d is null\n", i + 1, mapGenBarriers.length);
				continue;
			}
			c.draw(g2);
			if (c.isColliding(playerRect)) {
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("Tahoe", Font.BOLD, 14));
				g2.drawString("Player is colliding!", 10, 10);
			}
		}

		//Debug mode stuff
		if (GameConfig.debug) {
			int initialX = 0;
			int initialY;
			int width = 5;
			int height = 5;
			while (initialX < screenWidth) {
				initialY = 0;
				while (initialY < screenHeight) {
					g2.setColor(Color.blue);
					g2.fillOval(initialX - width / 2, initialY - height / 2, width, height);
					initialY += tileSize;
				}
				initialX += tileSize;
			}
		}

		g2.dispose();


	}
}