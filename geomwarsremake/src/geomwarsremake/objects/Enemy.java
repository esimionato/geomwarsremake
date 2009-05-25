package geomwarsremake.objects;

import org.newdawn.slick.Graphics;

import geomwarsremake.objects.enemies.AttractionHole;
import geomwarsremake.states.IngameState;

public abstract class Enemy extends GwrObject{

	/** Indicate that the object can hit the ship */
	protected boolean active = false;
	protected int delayBeforeActivation = 300;
	/** indicate that object is dead and will be removed soon */
	protected boolean dead = false;
	/** enemy type (snake, hole, pacman, etc) */
	protected String type = "";
	/** The score that this enemy worth */
	protected int score;
	
	/** Indicate if 2 enemy of the same sort can be above each other */
	protected boolean canOverlap = false;
	
	public int getScore(){
		return score;
	}
	
	public void draw(Graphics g, boolean debug){
		g.draw(circle);
	}

	public abstract boolean isInstanceOf(Enemy enemy);

	@Override
	public abstract void updatePosition(int deltaTime);

	@Override
	/**
	 * Check for collision between the enemy and :
	 * - Ship
	 * - Shots
	 * - Other enemies of the same type
	 * - Attraction holes 
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision(){
		if(!level.pship.isAlive()){
			return; //All enemies will be clear, no need to check for collision
		}
		
		//Get the position of the enemy
		float enemyX = circle.getCenterX();
		float enemyY = circle.getCenterY();
		//Collision with the ship
		if(active){
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
		}
		
		//Collision with a bomb
		Bomb bomb;
		for(int i=0; i<level.bombs.size(); i++){
			bomb = level.bombs.get(i);
			float difX = bomb.getX() - enemyX;
			float difY = bomb.getY() - enemyY;
			float distance2 = (float) Math.sqrt(difX*difX + difY*difY);
			if(distance2 < bomb.getOuterRadius() && distance2 > bomb.getInnerRadius()){
				died();
			}
		}


		//For every shot
		for(Shot shot : level.shots){
			if(shot.getCanHit()){
				//Get the position of the shot
				float shotX = shot.getCircle().getCenterX();
				float shotY = shot.getCircle().getCenterY();
				//Get the total radius of the enemy's circle + shot's circle
				float totalRadius = circle.getRadius() + shot.getCircle().getRadius();
				//Calculate the distance between the enemy and the shot
				float deltaX = enemyX - shotX;
				float deltaY = enemyY - shotY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

				//Check if we have a collision
				if(distance < totalRadius){
					hited();
					shot.hited();
					return;
				}
			}
		}
		
		//For every enemy
		for(Enemy enemy : level.enemies){
			if(!enemy.isDead()){
				if(this.isInstanceOf(enemy) && !canOverlap){
					if(enemy != this){
						//Get the position of the blue
						float blueX = enemy.getCircle().getCenterX();
						float blueY = enemy.getCircle().getCenterY();
						//Get the total radius of the enemy's circle + blue's circle
						float totalRadius = circle.getRadius() + enemy.getCircle().getRadius();
						//Calculate the distance between the enemy and the blue
						float deltaX = enemyX - blueX;
						float deltaY = enemyY - blueY;
						float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

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
						float totalRadius = hole.getCircle().getRadius() + circle.getRadius();
						//Calculate the distance between the enemy and the hole
						float deltaX = enemyX - holeX;
						float deltaY = enemyY - holeY;
						float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

						//Check if we have a collision
						if(distance < totalRadius){
							hole.absorbEnemy(this);
							died();
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
	public void hited(){
		level.pship.addScores(this.score);
		died();
	}

	/** when enemy is died (display animation, remove object) */
	public void died() {
		dead = true;

	}

	public boolean isDead() {
		return dead;
	}
	
	public void updateActivationTime(int deltaTime){
		delayBeforeActivation -= deltaTime;
		if(delayBeforeActivation <= 0){
			active = true;
			delayBeforeActivation = 0;
		}
	}

}
