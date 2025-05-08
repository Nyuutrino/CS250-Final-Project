package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import barrier.Barrier;
import barrier.Collidable;
import barrierNodes.Node;
import camera.Camera;
import direction.Direction;
import game.GameConfig;
import main.UtilityTool;

public class SuperObject extends Barrier implements Collidable {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public Rectangle solidArea;
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	private Rectangle location;
	//Whether to center the image
	protected boolean center = false;

	/**
	 *
	 * @param tileX The tile x location
	 * @param tileY The tile y location
	 * @param dir The direction the object is facing
	 * @param width The width of the object (tile-based)
	 * @param height The height of the object (tile-based)
	 */
	public SuperObject(int tileX, int tileY, Direction dir, int width, int height) {
		super(tileX, tileY, dir);
		solidArea = new Rectangle(tileX, tileY, width * GameConfig.tileSize, height * GameConfig.tileSize);
		location = new Rectangle(x, y, solidArea.width, solidArea.height);
	}

	public void draw(Graphics2D g2) {
		double offsetX = center ? solidArea.width / 2 : 0;
		double offsetY = center ? solidArea.height / 2 : 0;
		Camera.drawImage(image, x - offsetX, y - offsetY, solidArea.width, solidArea.height, g2);
	}

	@Override
	public boolean isColliding(Rectangle rect) {
		return location.intersects(rect);
	}

	@Override
	public Rectangle getIntersection(Rectangle rect) {
		return location.intersection(rect);
	}
}

