package geomwarsremake.objects;

import org.newdawn.slick.geom.Circle;

public class Shot extends GwrObject{
	
	private static final float SHOT_SPEED = 0.5f;
	
	//Shot speed
	float speedX;
	float speedY;
	public float timeRemain = 5; //shot stay 5 seconds, and then  removed
	private boolean destroyed = false;
	
	/**
	 * Create a shot. The shot speed is affected by the ship movement
	 * @param ship The ship that fired the shot.
	 */
	public Shot(PlayerShip ship){
		//Get the position of the ship
		float shipX = ship.getCircle().getCenterX();
		float shipY = ship.getCircle().getCenterY();
		//Set the position of the shot
		setCircle(new Circle(shipX, shipY, 5));
		
		//Get the speed of the ship
		float shipSpeedX = ship.getSpeedX();
		float shipSpeedY = ship.getSpeedY();
		//setSpeed(1f);
		//Get the direction the shot was fired
		float directionAngle = ship.getFaceAllignment();
		//Calculate the speed of the shot
		speedX = (float) (SHOT_SPEED*Math.cos(directionAngle) + shipSpeedX);
		speedY = (float) (SHOT_SPEED*Math.sin(directionAngle) + shipSpeedY);
	}

	@Override
	/**
	 * Update the position of the shot
	 * - Check for attraction from attraction circle (TODO)
	 * - Change the position of the shot
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime, Level level) {
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
				//We have a collision. Do something
			}
		}
	}
	
	public void setDestroyed() {
	  destroyed = true;
	}
	
	 public boolean getDestroyed() {
	    return destroyed;
	  }
	
	/**
	 * Reduces ttl and return remain time.
	 * @param t - ammount to which is reduced
	 * @return
	 */
	public float reduceLiveTime(float t) {
	  timeRemain -= t;
	  return timeRemain;
	}

}
