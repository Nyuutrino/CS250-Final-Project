package main;

import java.awt.*;
import javax.swing.*;
import barrier.*;
import direction.Direction;
import entity.Player;
import game.GameConfig;

public class GamePanel extends JPanel {

	// SCREEN SETTINGS
	final int orTileSize = GameConfig.orTileSize;
	final int scale = GameConfig.scale;

	public int tileSize = GameConfig.tileSize;
	final int maxScreenCol = GameConfig.maxScreenCol;
	final int maxScreenRow = GameConfig.maxScreenRow;
	final int screenWidth = GameConfig.screenWidth;
	final int screenHeight = GameConfig.screenHeight;

	// FPS variables
	int fps = GameConfig.fps;
	Timer gameTimer;
	long lastSecondTime = 0;

	KeyHandler keyH = new KeyHandler();
	Player player = new Player(this, keyH);

	public Barrier[] barriers;
	Rectangle playerRect = new Rectangle(player.x, player.y, tileSize, tileSize);

	Hallway testHall = new Hallway(20, 10, 5, new Direction(Direction.SOUTH));
	Hallway testHall2 = new Hallway(testHall, testHall.getAvailableNodes()[1], 5);
	Hallway testHall3 = new Hallway(testHall2, testHall2.getAvailableNodes()[1], 5);
	Corner testCorner = new Corner(testHall3, testHall3.getAvailableNodes()[1], Corner.LEFT);
	FourWay testFW = new FourWay(testCorner, testCorner.getAvailableNodes()[1]);
	ThreeWay testTW = new ThreeWay(testHall, testHall.getAvailableNodes()[0]);

	private int drawCount = 0;


	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

		// Link barrier nodes
		testHall.getAvailableNodes()[1].linkNodes(testHall2.getAvailableNodes()[0]);
		testHall2.getAvailableNodes()[1].linkNodes(testHall3.getAvailableNodes()[0]);
		testHall3.getAvailableNodes()[1].linkNodes(testCorner.getAvailableNodes()[0]);
		testCorner.getAvailableNodes()[1].linkNodes(testFW.getAvailableNodes()[0]);
		testHall.getAvailableNodes()[0].linkNodes(testTW.getAvailableNodes()[0]);

		barriers = new Barrier[]{testHall, testHall2, testHall3, testCorner, testFW, testTW};
	}
    //game timer loop
	public void startGameTimer() {
		int delay = 1000 / fps; // milliseconds delay for 60fps
		gameTimer = new Timer(delay, e -> {//loop delay
			update();
			repaint();
			drawCount++;

			// Optional FPS debug
			if (System.currentTimeMillis() - lastSecondTime >= 1000) {
                System.out.println("FPS: " + drawCount);
				drawCount = 0;
				lastSecondTime = System.currentTimeMillis();
			}
		});
		//start timer
		gameTimer.start();
	}

	public void update() {
		player.update();
		playerRect.x = player.x;
		playerRect.y = player.y;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		player.draw(g2);
		//barrier draw
		for (Barrier b : barriers) {
			b.draw(g2);
			//collision testing
		//	if (b.isColliding(playerRect)) {
		//		g2.setColor(Color.WHITE);
		//		g2.setFont(new Font("Tahoe", Font.BOLD, 14));
		//		g2.drawString("Player is colliding!", 10, 10);
		//	}
		}

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
