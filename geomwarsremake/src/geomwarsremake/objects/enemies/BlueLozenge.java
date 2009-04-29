package geomwarsremake.objects.enemies;

import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;

import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class BlueLozenge extends Enemy {
	
	private static final float INITIAL_SPEED = 0.1f;
	private static final float SPEED_INCREASE_PER_SECOND = 0.01f;
	
	private int timeForNextSpeedUpdate = 1000;

	/**
	 * Create a BlueLozenge at the specified position;
	 * @param posX The position on the x axis.
	 * @param posY The position on the y axis.
	 */
	public BlueLozenge(int posX, int posY){
		setCircle(new Circle(posX, posY, 10));
		setSpeed(INITIAL_SPEED);
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
		//Calculate the distance we will move the BlueLozenge
		float deltaX = (float) (getSpeed()*Math.cos(directionAngle)*deltaTime);
		float deltaY = (float) (getSpeed()*Math.sin(directionAngle)*deltaTime);
		//Update the position of the BlueLozenge
		float newX = circle.getX() + deltaX;
		float newY = circle.getY() + deltaY;
		getCircle().setLocation(newX, newY);
	}
	
	@Override
	public void checkForCollision(Level level) {
		// TODO Auto-generated method stub
		
	}
	
	 public void hited(){
	    //set as not active, remove from play field
	   dead = true;
	  }
}
