/**
 * Author: Nyuutrino
 * Date: May 6, 2025
 * Description: Camera class for managing drawing relative to a camera.
 * Functions as a singleton (needs to be instantiated manually) so other methods can call it without having to provide extra arguments
 */
package camera;

import entity.Player;
import game.GameConfig;
import main.GamePanel;

import java.awt.*;

public class Camera {
	//Instance of the camera class
	private static Camera instance;

	//Instance-specific data
	private GamePanel gamePanel;
	private Rectangle worldBounds = new Rectangle(0, 0, GameConfig.maxWorldCol * GameConfig.tileSize, GameConfig.maxWorldRow * GameConfig.tileSize);
	private Rectangle cameraBounds = new Rectangle(0, 0, GameConfig.maxScreenCol * GameConfig.tileSize, GameConfig.maxScreenRow * GameConfig.tileSize);

	/**
	 * Constructor. Used for instantiation
	 */
	public Camera(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		//Translate cameraBounds to where the player is
		cameraBounds.setLocation(gamePanel.player.worldx - cameraBounds.width / 2, gamePanel.player.worldy - cameraBounds.height / 2);
//		cameraBounds.setLocation(gamePanel.player.worldx, gamePanel.player.worldy);
	}

	/**
	 * Instantiates the camera with what it needs.
	 * Needs to be called before getInstance()
	 * If an instance already exists, it silently returns without doing anything
	 * @param gp The game panel
	 */
	public static synchronized void init(GamePanel gp){
		if (instance != null)
			return;
		instance = new Camera(gp);
	}

	/**
	 * Returns the instance of the camera
	 * @throws RuntimeException if instance has not been initiated
	 * @return Camera instance
	 */
	public static Camera getInstance(){
		if (instance == null)
			throw new RuntimeException("Instance not initialized! Make sure to call init() on Camera");
		return instance;
	}

	/**
	 * Draws a rectangle relative to the camera
	 * @param x The x coordinate of the rectangle (not the tile x)
	 * @param y The y coordinate of the rectangle (not the tile y)
	 * @param w The width of the rectangle (not tile-based)
	 * @param h The height of the rectangle (not tile-based)
	 * @param g2 The Graphics2D object to draw with
	 */
	public static void fillRect(int x, int y, int w, int h, Graphics2D g2) {
		Camera inst = getInstance();
		//Create rectangle with the coordinates
		Rectangle rect = new Rectangle(x, y, w, h);
		//Find rectangle in bounds of the camera and draw relative to the camera's POV
		Rectangle withinBounds = inst.cameraBounds.intersection(rect);
		//Translate so it's relative to the camera
		int translateX = inst.worldBounds.x - inst.cameraBounds.x;
		int translateY = inst.worldBounds.y - inst.cameraBounds.y;
		withinBounds.translate(translateX, translateY);
		//Only draw if intersection isn't empty
		if(!withinBounds.isEmpty()) {
			g2.fillRect(withinBounds.x, withinBounds.y, withinBounds.width, withinBounds.height);
		}
	}

	/**
	 * Draws an image relative to the camera
	 * @param image The image to draw
	 * @param x The x coordinate of the rectangle (not the tile x)
	 * @param y The y coordinate of the rectangle (not the tile y)
	 * @param w The width of the rectangle (not tile-based)
	 * @param h The height of the rectangle (not tile-based)
	 * @param g2 The Graphics2D object to draw with
	 */
	public static void drawImage(Image image, double x, double y, int w, int h, Graphics2D g2) {
		Camera inst = getInstance();
		//Create rectangle with the coordinates
		Rectangle rect = new Rectangle((int)x, (int)y, w, h);
		//Find rectangle in bounds of the camera and draw relative to the camera's POV
		Rectangle withinBounds = inst.cameraBounds.intersection(rect);
		//Translate so it's relative to the camera
		int translateX = inst.worldBounds.x - inst.cameraBounds.x;
		int translateY = inst.worldBounds.y - inst.cameraBounds.y;
		withinBounds.translate(translateX, translateY);
		//Only draw if intersection isn't empty
		if(!withinBounds.isEmpty()) {
			g2.drawImage(image, withinBounds.x, withinBounds.y, w, h, null);
		}
	}

	/**
	 * Updates camera bounds with player's location
	 */
	public static void updateCameraPos() {
		Camera inst = getInstance();
		Player p = inst.gamePanel.player;
		inst.cameraBounds.setLocation(p.worldx - inst.cameraBounds.width / 2, p.worldy - inst.cameraBounds.height / 2);
//		inst.cameraBounds.setLocation(p.worldx, p.worldy);
	}
}
