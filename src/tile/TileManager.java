package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	
	public TileManager(GamePanel gp){
		//contructor for the gamepanel to create tiles
		this.gp = gp;
		
		//this array will be used to store tile images, 10 is arbitrary it just is as big as needed
		tile = new Tile[10];
		//array as big as the screen
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap();
	
	}
	
	public void getTileImage() {
		//loads tile images in this method
		//these items are loaded scaled
			setup(0,"floor01",false);
			setup(1,"wall",true);
			setup(2,"water",false);
	}
	
	public void setup(int index, String imagepath, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/"+imagepath+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collison = collision;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap() {
		try {
			//looks at text file and creates map based on the array
			InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				//read a line
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					//break it by spaces
					String numbers[] = line.split(" ");
					//change to int
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			
			}
			br.close();
		}catch(Exception e) {}
	}
	
	
	public void draw(Graphics2D g2) {
		//draws the tiles
		
		
		int worldCol = 0;
		int worldRow =0;

		
		//while within the screen
		while(worldCol < gp.maxWorldCol  && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			
			//check position of player, set position of screen around player
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldx + gp.player.screenX;
			int screenY = worldY - gp.player.worldy + gp.player.screenY;
			
			
			//draw the row, only renders the screen
			if(worldX + gp.tileSize> gp.player.worldx - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldx + gp.player.screenX &&
				worldY + gp.tileSize> gp.player.worldy - gp.player.screenY &&
				worldY - gp.tileSize< gp.player.worldy + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			worldCol++;
				
			
			//then increment
			if(worldCol == gp.maxScreenCol) {
				worldCol = 0;
				worldRow++;
	
			}
			
			
		}
		
	}
	
}
