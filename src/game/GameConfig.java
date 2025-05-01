/**
 * Author: Nyuutrino
 * Date: Apr 5, 2025
 * Description: Contains configuration information about the game.
 */
package game;

/**
 * 
 */
public final class GameConfig {
	//Screen settings
	public static final int orTileSize = 16;
	public static final int scale = 3;
	
	public static final int tileSize = orTileSize * scale;
	public static final int maxScreenCol = 30;
	public static final int maxScreenRow = 20 ;
	public static final int screenWidth = tileSize * maxScreenCol;
	public static final int screenHeight = tileSize * maxScreenRow;
	
	//FPS
	public static final int fps = 60;
	
	//Default player settings (settings that are applied at the beginning of the game)
	public static final int playerX = 100;
	public static final int playerY = 100;
	public static final int playerSpeed = 7;

}
