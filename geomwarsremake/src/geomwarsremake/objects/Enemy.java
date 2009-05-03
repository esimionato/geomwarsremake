package geomwarsremake.objects;

import geomwarsremake.objects.enemies.AttractionHole;

public abstract class Enemy extends GwrObject{

	//set active when we add object to map (e.g. we could create it and set coordinates beforehand)
	protected boolean active = false;
	/** indicate that object is dead and will be removed soon */
	protected boolean dead = false;
	/** enemy type (snake, hole, pacman, etc) */
	protected String type = "";
	/** The score that this enemy worth */
	protected int score;
	
	public int getScore(){
		return score;
	}

	public abstract boolean isInstanceOf(Enemy enemy);

	@Override
	public abstract void updatePosition(int deltaTime, Level level);

	@Override
	/**
	 * Check for collision between the enemy and :
	 * - Ship
	 * - Shots
	 * - Other enemies of the same type
	 * - Attraction holes 
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision(Level level){
		if(!level.pship.isAlive()){
			return; //All enemies will be clear, no need to check for collision
		}
		
		//Collision with the ship
		//Get the position of the enemy
		float enemyX = circle.getCenterX();
		float enemyY = circle.getCenterY();
		//Get the position of the ship
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		//Get the total radius of the enemy's circle + ship's circle
		float totalRadius = circle.getRadius() + level.pship.getCircle().getRadius();
		//Calculate the distance between the enemy and the shot
		float deltaX = enemyX - shipX;
		float deltaY = enemyY - shipY;
		float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		//Check if we have a collision
		if(distance < totalRadius){
			level.pship.died();
			return;
		}


		//For every shot
		for(Shot shot : level.shots){
			if(shot.getCanHit()){
				//Get the position of the shot
				float shotX = shot.getCircle().getCenterX();
				float shotY = shot.getCircle().getCenterY();
				//Get the total radius of the enemy's circle + shot's circle
				totalRadius = circle.getRadius() + shot.getCircle().getRadius();
				//Calculate the distance between the enemy and the shot
				deltaX = enemyX - shotX;
				deltaY = enemyY - shotY;
				distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

				//Check if we have a collision
				if(distance < totalRadius){
					hited(level);
					shot.hited(level);
					return;
				}
			}
		}
		
		//For every enemy
		for(Enemy enemy : level.enemies){
			if(!enemy.isDead()){
				if(this.isInstanceOf(enemy)){
					if(enemy != this){
						//Get the position of the blue
						float blueX = enemy.getCircle().getCenterX();
						float blueY = enemy.getCircle().getCenterY();
						//Get the total radius of the enemy's circle + blue's circle
						totalRadius = circle.getRadius() + enemy.getCircle().getRadius();
						//Calculate the distance between the enemy and the blue
						deltaX = enemyX - blueX;
						deltaY = enemyY - blueY;
						distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

						//Check if we have a collision
						if(distance < totalRadius){
							collisionUpdate(deltaX, deltaY, distance, totalRadius);
						}
					}
				}else if(enemy instanceof AttractionHole){
					AttractionHole hole = (AttractionHole)enemy;
					if(hole.isAttracting){
						//Get the position of the hole
						float holeX = hole.getCircle().getCenterX();
						float holeY = hole.getCircle().getCenterY();
						//Get the total radius of the hole's circle
						totalRadius = hole.getCircle().getRadius() + circle.getRadius();
						//Calculate the distance between the enemy and the hole
						deltaX = enemyX - holeX;
						deltaY = enemyY - holeY;
						distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

						//Check if we have a collision
						if(distance < totalRadius){
							hole.absorbEnemy(this);
							died(level);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Move this object outside of the object it just collide with.
	 * 
	 * @param distX The distance in x between this object and the one it collide with.
	 * @param distY The distance in y between this object and the one it collide with.
	 * @param distance The distance between this object and the one it collide with.
	 * @param desiredDistance The distance this object is suppose to be from the object 
	 * it collide with.
	 */
	protected void collisionUpdate(float distX, float distY, float distance, float desiredDistance){
		float x = circle.getX();
		float y = circle.getY();
		if(distance != 0){
			x += (distX/distance) * (desiredDistance-distance + 1);
			y += (distY/distance) * (desiredDistance-distance + 1);
		}else{
			x += desiredDistance + 1;
		}
		circle.setLocation(x, y);
	}


	/**
	 * AI logic here, should be implemented for each specific enemy type separately.
	 * @param level - ingame level
	 */
	public void actAI(Level level){
		//I will put it in updatePosition
	}

	/**
	 * called when enemy is hitted by shot
	 * maybe we should give coordinate of hit, so object will recognize where it was hitted
	 */
	public void hited(Level level){
		level.pship.addScores(this.score);
		died(level);
	}

	// when enemy is died (display animation, remove object)
	public void died(Level level) {
		dead = true;
	}

	public boolean isDead() {
		return dead;
	}

}
