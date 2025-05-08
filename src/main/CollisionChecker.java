package main;

import barrier.Barrier;
import entity.Entity;
import entity.Player;
import game.GameConfig;

public class CollisionChecker {

	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	/**
	 * Checks if player is colliding with a barrier
	 * @param player The player object
	 */
	public boolean checkBarrier(Player player) {
		//Check if any barrier is colliding with the player
		for (Barrier b : gp.mapGen.getBarriers()){
			if (b.isColliding(player.solidArea)) return true;
		}
		return false;
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
//				gp.obj[i].solidArea.x = gp.obj[i].worldx + gp.obj[i].solidArea.x;
//				gp.obj[i].solidArea.y = gp.obj[i].worldy + gp.obj[i].solidArea.y;
				
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
	//monster collision
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for (int i =0; i<target.length;i++) {
			
			if(target[i] != null) {
				//get entity posistion
				entity.solidArea.x = entity.worldx + entity.solidAreaDefaultX;
				entity.solidArea.y = entity.worldy + entity.solidAreaDefaultY;
				
				//get object position
				target[i].solidArea.x = target[i].worldx + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldy + target[i].solidArea.y;
				
				switch(entity.direction) {
				case"up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;						
					
					break;
					}
				case"down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					}
					break;
					
				case"left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
							entity.collisionOn = true;
							index = i;
					}
					break;
					
				case"right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					}
					break;
				}
			
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			target[i].solidArea.x = target[i].solidAreaDefaultX;
			target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		
		
			
		}
			return index;
		
	}
	
	public void checkPlayer(Entity entity) {
		entity.solidArea.x = entity.worldx + entity.solidAreaDefaultX;
		entity.solidArea.y = entity.worldy + entity.solidAreaDefaultY;
		
		//get object position
		gp.player.solidArea.x = gp.player.worldx + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldy + gp.player.solidArea.y;
		
		switch(entity.direction) {
		case"up":
			entity.solidArea.y -= entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
										
			
			break;
			}
		case"down":
			entity.solidArea.y += entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
				
			}
			break;
			
		case"left":
			entity.solidArea.x -= entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn = true;
					
			}
			break;
			
		case"right":
			entity.solidArea.x += entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
				
			}
			break;
		}
	
	entity.solidArea.x = entity.solidAreaDefaultX;
	entity.solidArea.y = entity.solidAreaDefaultY;
	gp.player.solidArea.x = gp.player.solidAreaDefaultX;
	gp.player.solidArea.y = gp.player.solidAreaDefaultY;
	}


	
	
}
	
	
	

