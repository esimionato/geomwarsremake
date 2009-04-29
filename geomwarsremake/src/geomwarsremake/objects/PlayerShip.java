package geomwarsremake.objects;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;

public class PlayerShip extends GwrObject{

	//temp weapon
	private int weaponInterval = 200;// 200 = one second
	int delayBeforeNextShot = 0;
	private int bombs = 3;
	private int scores = 0;
	
	public int getScores() {
    return scores;
  }

  public void addScores(int add) {
    this.scores += add;
  }

  //Ship speed
	private float speedX = 0;
	private float speedY = 0;

	public PlayerShip(){
		setCircle(new Circle(400, 400, 10));
		setSpeed(0.3f);
	}
	
	public float getSpeedX(){
		return speedX;
	}
	
	public float getSpeedY(){
		return speedY;
	}
	
	@Override
	/**
	 * Update the position of the ship
	 * - Check for player input
	 * - Check for attraction from attraction circle (TODO)
	 * - Change the position of the ship
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime, Level level) {
		//Calculate the speed of the ship
		speedX = getSpeed()*getDirectionX();
		speedY = getSpeed()*getDirectionY();
		//If the ship is moving in both axis, reduce his speed in both axis
		if(speedX != 0 && speedY != 0){
			speedX *= 0.707; //Math.sqrt(0.5)
			speedY *= 0.707; //Math.sqrt(0.5)
		}
		//Get the position difference we need to move the ship this turn;
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Update the position of the ship
		float shipX = getCircle().getX() + deltaX;
		float shipY = getCircle().getY() + deltaY;
		getCircle().setLocation(shipX, shipY);
	}
	
	@Override
	/**
	 * Check for collision between the ship and :
	 * - Enemies
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision(Level level) {
		//For every enemy
		for(Enemy enemy : level.enemies){
			//Get the position of the ship
			float shipX = circle.getCenterX();
			float shipY = circle.getCenterY();
			//Get the position of the enemy
			float enemyX = enemy.circle.getCenterX();
			float enemyY = enemy.circle.getCenterY();
			//Get the total radius of the ship's circle + enemy's circle
			float totalRadius = circle.getRadius() + enemy.circle.getRadius();
			
			//Calculate the distance between the ship and the enemy
			float deltaX = shipX - enemyX;
			float deltaY = shipY - enemyY;
			float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
			
			//Check if we have a collision
			if(distance < totalRadius){
				//We have a collision. Do something
			}
		}
	}
	
	//Temporary
	public void updateShotTime(int deltaTime){
		delayBeforeNextShot -= deltaTime;
		if(delayBeforeNextShot < 0){
			delayBeforeNextShot = 0;
		}
	}
	
	public ArrayList<Shot> createShot(){
		ArrayList<Shot> list = new ArrayList<Shot>();
		if(delayBeforeNextShot == 0){
			list.add(new Shot(this));
			delayBeforeNextShot = weaponInterval;
		}
		return list;
	}
	
	public void wantDirectionDOWN(boolean wantDown) {
		if(wantDown) {
			directionY++;
		} else {
			directionY--;
		} 
	}

	public void wantDirectionUP(boolean wantUp) {
		if(wantUp) {
			directionY--;
		} else {
			directionY++;
		} 
	}

	public void wantDirectionRIGHT(boolean wantRight) {
		if(wantRight) {
			directionX++;
		} else {
			directionX--;
		} 
	}

	public void wantDirectionLEFT(boolean wantLeft) {
		if(wantLeft) {
			directionX--;
		} else {
			directionX++;
		} 
	}
	
	public void wantUseBomb() {
	  if (bombs > 1) {
	    //activate it
	  }
	}

}
