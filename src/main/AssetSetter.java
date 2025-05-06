package main;

import entity.NPC_Oldman;
import object.OBJ_BOOST;
import object.OBJ_Key;
import object.OBJ_door;
import object.OBJ_finalDoor;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_Oldman(gp);
		gp.npc[0].worldx = 7 * gp.tileSize;
		gp.npc[0].worldy = 5 * gp.tileSize ;
	}
	
	public void setObject() {
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldx = 5 * gp.tileSize;
		gp.obj[0].worldy = 29 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Key(gp);
		gp.obj[2].worldx = 20 * gp.tileSize;
		gp.obj[2].worldy = 4 * gp.tileSize;
		
		gp.obj[3] = new OBJ_Key(gp);
		gp.obj[3].worldx = 27 * gp.tileSize;
		gp.obj[3].worldy = 30 * gp.tileSize;
		
		gp.obj[4] = new OBJ_Key(gp);
		gp.obj[4].worldx = 25 * gp.tileSize;
		gp.obj[4].worldy = 15 * gp.tileSize;
		
		gp.obj[5] = new OBJ_Key(gp);
		gp.obj[5].worldx = 4 * gp.tileSize;
		gp.obj[5].worldy = 3 * gp.tileSize;
		
		gp.obj[1] = new OBJ_finalDoor(gp);
		gp.obj[1].worldx = 10 * gp.tileSize;
		gp.obj[1].worldy = 0 * gp.tileSize;
		
		
		gp.obj[6] = new OBJ_BOOST(gp);
		gp.obj[6].worldx = 11 * gp.tileSize;
		gp.obj[6].worldy = 28 * gp.tileSize;
		
		gp.obj[7] =  new OBJ_door(gp);
		gp.obj[7].worldx = 12*gp.tileSize;
		gp.obj[7].worldy = 1*gp.tileSize;
	}
	
	
}
