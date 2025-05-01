package main;

import entity.Entity;
import game.GameConfig;

public class CollisionChecker {

	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {//checks for solid area
		int entityLeftWorldX = entity.worldx + entity.solidArea.x;
		int entityRightWorldX = entity.worldx + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldy + entity.solidArea.y;
		int entityBottomY = entity.worldy + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize; 
		int entityRightCol = entityRightWorldX/gp.tileSize; 
		int entityTopRow = entityTopWorldY/gp.tileSize; 
		int entityBottomRow = entityBottomY/gp.tileSize;
		
		int tileNum1 , tileNum2;
		
		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1= gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2= gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			if(gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true){
				entity.collisionOn = true;
			}
			else if(gp.tileM.tile[tileNum1].collison == false || gp.tileM.tile[tileNum2].collison == false){
				entity.collisionOn = false;
				}
			break;
			
		case "down":
			entityBottomRow = (entityBottomY - entity.speed)/gp.tileSize;
			tileNum1= gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2= gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if(gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true){
				entity.collisionOn = true;
			}
			else if(gp.tileM.tile[tileNum1].collison == false || gp.tileM.tile[tileNum2].collison == false){
				entity.collisionOn = false;
				}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1= gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2= gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			
			if(gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true){
				entity.collisionOn = true;
			}
			else if(gp.tileM.tile[tileNum1].collison == false || gp.tileM.tile[tileNum2].collison == false){
				entity.collisionOn = false;
				}
			break;
		case "right":
			entityRightCol = (entityRightWorldX - entity.speed)/gp.tileSize;
			tileNum1= gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2= gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if(gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true){
				entity.collisionOn = true;
			}
			else if(gp.tileM.tile[tileNum1].collison == false || gp.tileM.tile[tileNum2].collison == false){
				entity.collisionOn = false;
				}
			break;
		}
		
		
	}
	
	//method that specifically checks for object location, uses the same logic as other collision detection, but much smaller
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for (int i =0; i<gp.obj.length;i++) {
			
			if(gp.obj[i] != null) {
				//get entity posistion
				entity.solidArea.x = entity.worldx + entity.solidAreaDefaultX;
				entity.solidArea.y = entity.worldy + entity.solidAreaDefaultY;
				
				//get object position
				gp.obj[i].solidArea.x = gp.obj[i].worldx + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldy + gp.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case"up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) entity.collisionOn = true;
						if(player == true) index = i;						
					
					break;
					}
				case"down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) entity.collisionOn = true;
						if(player == true) index = i;
					}
					break;
					
				case"left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true)
							entity.collisionOn = true;
						if(player == true) index = i;
					}
					break;
					
				case"right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) entity.collisionOn = true;
						if(player == true) index = i;
					}
					break;
				}
			
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
			gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		
		
			
		}
			return index;
	}

}
