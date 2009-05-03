package geomwarsremake.objects;

import geomwarsremake.objects.enemies.AttractionHole;

import org.newdawn.slick.geom.Circle;

public class Shot extends GwrObject{
	
	public static final float SHOT_SPEED = 0.6f;
	
	//Shot speed
	float speedX;
	float speedY;
	/** Indicate if the shot can hit enemies */
	private boolean canHit = true;
	
	/**
	 * @return If the shot can hit an enemy or not
	 */
	public boolean getCanHit(){
		return canHit;
	}
	
	/**
	 * Create a shot. The shot speed is affected by the ship movement
	 * @param ship The ship that fired the shot.
	 */
	public Shot(PlayerShip ship, float posX, float posY, float directionAngle, float speed){
		//Set the position of the shot
		setCircle(new Circle(posX, posY, 5));
		
		//Get the speed of the ship
		float shipSpeedX = ship.getSpeedX();
		float shipSpeedY = ship.getSpeedY();
		//Calculate the speed of the shot
		speedX = (float) (speed*Math.cos(directionAngle) + shipSpeedX);
		speedY = (float) (speed*Math.sin(directionAngle) + shipSpeedY);
		setSpeed((float) Math.sqrt(speedX*speedX + speedY*speedY));
		//Set weight
		weight = 10;
	}

	@Override
	/**
	 * Update the position of the shot
	 * - Check for repulsion from attraction circle
	 * - Change the position of the shot
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime, Level level) {
		//For every AttractionHole
		for(AttractionHole hole : level.holes){
			if(hole.isAttracting){
				float shipX = circle.getCenterX();
				float shipY = circle.getCenterY();
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float delta2X = holeX - shipX;
				float delta2Y = holeY - shipY;
				float distance = (float) Math.sqrt(delta2X*delta2X + delta2Y*delta2Y);
				if(distance < hole.getAttractionRadius()){
					//Shot is repulse by the AttractionHole
					float ax = -hole.getAttractionForce(distance)/weight * (delta2X/distance);
					float ay = -hole.getAttractionForce(distance)/weight * (delta2Y/distance);
					speedX += ax * deltaTime/1000;
					speedY += ay * deltaTime/1000;
				}
			}
		}
		//Adjust speed (The speed must remain constant)
		float speedTotal = (float) Math.sqrt(speedX*speedX + speedY*speedY);
		speedX = speedX/speedTotal*getSpeed();
		speedY = speedY/speedTotal*getSpeed();
		//Calculate the distance we will move the shot.
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Calculate the new position of the shot
		float shotX = getCircle().getX() + deltaX;
		float shotY = getCircle().getY() + deltaY;
		//Update the position of the shot
		getCircle().setLocation(shotX, shotY);	
	}

	@Override
	/**
	 * Check for collision between the shot and :
	 * - Enemies
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision(Level level) {
		//For every enemy
		for(Enemy enemy : level.enemies){
			if(!enemy.isDead()){
				//Get the position of the shot
				float shipX = circle.getCenterX();
				float shipY = circle.getCenterY();
				//Get the position of the enemy
				float enemyX = enemy.circle.getCenterX();
				float enemyY = enemy.circle.getCenterY();
				//Get the total radius of the shot's circle + enemy's circle
				float totalRadius = circle.getRadius() + enemy.circle.getRadius();

				//Calculate the distance between the shot and the enemy
				float deltaX = shipX - enemyX;
				float deltaY = shipY - enemyY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

				//Check if we have a collision
				if(distance < totalRadius){
					hited(level);
					enemy.hited(level);
					break;
				}
			}
		}
	}
	
	/**
	 * Called when a shot hit an enemy
	 * @param level
	 */
	public void hited(Level level){
		canHit = false;
	}
	
	/**
	 * Called when a shot hit the map area
	 * @param level The level containing all the object in this game
	 */
	public void hitMapArea(Level level){
		canHit = false;
		//Add animation when the shot hit the wall
	}

}
