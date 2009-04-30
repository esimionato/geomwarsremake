package geomwarsremake.objects.enemies;

import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;
import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class AttractionHole extends Enemy {
	
	public boolean isAttracting = false;
	
	private float attractionRadius = 600;
	private float attractionForce = 10;
	
	/**
	 * Create an AttractionHole at the specified position;
	 * @param posX The position on the x axis.
	 * @param posY The position on the y axis.
	 */
	public AttractionHole(int posX, int posY){
		setCircle(new Circle(posX, posY, 20));
		setSpeed(0.01f);
	}
	
	public float getAttractionRadius(){
		return attractionRadius;
	}
	
	public float getAttractionForce(float distance){
		//This formula is subject to change
		return (float) (attractionForce * Math.pow((attractionRadius - distance)/attractionRadius, 2));
	}

	@Override
	/**
	 * Update the position of the AttractionHole
	 * - Check for attraction from other attraction hole (TODO)
	 * - Update the position of the AttractionHole
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime, Level level) {
		//Get the position of the AttractionHole
		float thisX = circle.getCenterX();
		float thisY = circle.getCenterY();
		//Get the position of the ship
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		//Calculate the direction angle that the AttractionHole want to take
		float directionAngle = (float) GeomWarUtil.findAngle(shipX-thisX, shipY-thisY);
		//Calculate the distance we will move the AttractionHole
		float deltaX = (float) (getSpeed()*Math.cos(directionAngle)*deltaTime);
		float deltaY = (float) (getSpeed()*Math.sin(directionAngle)*deltaTime);
		//Update the position of the BlueLozenge
		float newX = circle.getX() + deltaX;
		float newY = circle.getY() + deltaY;
		getCircle().setLocation(newX, newY);
	}
	
	@Override
	public void checkForCollision(Level level) {
		
	}

}
