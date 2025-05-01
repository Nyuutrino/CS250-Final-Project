package main;

import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void setObject() {
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldx = 13 * gp.tileSize;
		gp.obj[0].worldy = 13 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Key(gp);
		gp.obj[2].worldx = 15 * gp.tileSize;
		gp.obj[2].worldy = 15 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Door(gp);
		gp.obj[1].worldx = 10 * gp.tileSize;
		gp.obj[1].worldy = 5 * gp.tileSize;
		
	}
	
	
}
