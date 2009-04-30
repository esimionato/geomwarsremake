package geomwarsremake.objects.enemies;

import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;

import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;
import geomwarsremake.objects.Shot;

public class BlueLozenge extends Enemy {
	
	private static final float INITIAL_SPEED = 0.1f;
	private static final float SPEED_INCREASE_PER_SECOND = 0.002f;
	
	private int timeForNextSpeedUpdate = 1000;

	/**
	 * Create a BlueLozenge at the specified position;
	 * @param posX The position on the x axis.
	 * @param posY The position on the y axis.
	 */
	public BlueLozenge(int posX, int posY){
		setCircle(new Circle(posX, posY, 10));
		setSpeed(INITIAL_SPEED);
		weight = 2;
	}
	
	@Override
	/**
	 * Update the position of the BlueLozenge
	 * - Check for attraction from attraction circle (TODO)
	 * - Update the speed of the BlueLozenge if it's the time
	 * - Update the position of the BlueLozenge
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime, Level level) {
		//Update the time before the next speed update
		timeForNextSpeedUpdate -= deltaTime;
		//If it's time to update the speed, do it
		while(timeForNextSpeedUpdate < 0){
			timeForNextSpeedUpdate += 1000;
			setSpeed(getSpeed() + SPEED_INCREASE_PER_SECOND);
		}
		//Get the position of the BlueLozenge
		float blueX = circle.getCenterX();
		float blueY = circle.getCenterY();
		//Get the position of the ship
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		//Calculate the direction angle that the BlueLozenge want to take
		float directionAngle = (float) GeomWarUtil.findAngle(shipX-blueX, shipY-blueY);
		//Calculate the speed of this object
		speedX = (float) (getSpeed()*Math.cos(directionAngle));
		speedY = (float) (getSpeed()*Math.sin(directionAngle));
		
		//For every AttractionHole
		for(AttractionHole hole : level.holes){
			float holeX = hole.getCircle().getCenterX();
			float holeY = hole.getCircle().getCenterY();
			float deltaX = holeX - blueX;
			float deltaY = holeY - blueY;
			float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
			if(distance < hole.getAttractionRadius()){
				//Ship is attract by the AttractionHole
				float ax = hole.getAttractionForce(distance)/weight * (deltaX/distance);
				float ay = hole.getAttractionForce(distance)/weight * (deltaY/distance);
				speedX += ax * deltaTime/1000;
				speedY += ay * deltaTime/1000;
			}
		}
		//Calculate the distance we will move the BlueLozenge
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Update the position of the BlueLozenge
		float newX = circle.getX() + deltaX;
		float newY = circle.getY() + deltaY;
		getCircle().setLocation(newX, newY);
	}
	
	@Override
	/**
	 * Check for collision between the enemy and :
	 * - Ship
	 * - Shots
	 * - Attraction holes 
	 * - Other BlueLozenge
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision(Level level) {
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
			//We have a collision. Do something
		}
		
		
		//For every shot
		for(Shot shot : level.shots){
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
				//We have a collision. Do something
			}
		}
		
		//For every enemy
		for(Enemy enemy : level.enemies){
			if(enemy instanceof BlueLozenge){
				BlueLozenge blue = (BlueLozenge)enemy;
				if(blue != this){
					//Get the position of the blue
					float blueX = blue.getCircle().getCenterX();
					float blueY = blue.getCircle().getCenterY();
					//Get the total radius of the enemy's circle + blue's circle
					totalRadius = circle.getRadius() + blue.getCircle().getRadius();
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
					totalRadius = hole.getCircle().getRadius();
					//Calculate the distance between the enemy and the hole
					deltaX = enemyX - holeX;
					deltaY = enemyY - holeY;
					distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

					//Check if we have a collision
					if(distance < totalRadius){
						//We have a collision. Do something
					}
				}
			}
		}
		
	}
	
	 public void hited(){
	    //set as not active, remove from play field
	   dead = true;
	  }
}
