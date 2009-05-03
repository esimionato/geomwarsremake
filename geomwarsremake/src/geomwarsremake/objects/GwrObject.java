package geomwarsremake.objects;

import geomwarsremake.states.IngameState;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;

public abstract class GwrObject {
	//Object position
	protected Circle circle;
	//specify where object is faced (angle)
	private float faceAllignment = 0;
	//basic speed
	private float speed = 1;
	//movement
	protected int directionX = 0;
	protected int directionY = 0;
	/** The weight of this object */
	protected float weight = 1;
	protected float speedX = 0;
	protected float speedY = 0;


	public abstract void updatePosition(int deltaTime, Level level);

	public abstract void checkForCollision(Level level);

	/** 
	 * Keep all object inside the map area 
	 * @param level The level containing all the objects of this game
	 */
	public void collisionMapArea(Level level){
		float x = circle.getX();
		float y = circle.getY();
		//West
		if(circle.getMinX() < 0){
			x = 0;
			speedX = -speedX;
			hitMapArea(level);
		}
		//North
		if(circle.getMinY() < 0){
			y = 0;
			speedY = -speedY;
			hitMapArea(level);
		}
		//East
		if(circle.getMaxX() > level.mapWidth){
			x = level.mapWidth - circle.getRadius()*2;
			speedX = -speedX;
			hitMapArea(level);
		}
		//South
		if(circle.getMaxY() > level.mapHeight){
			y = level.mapHeight - circle.getRadius()*2;
			speedY = -speedY;
			hitMapArea(level);
		}
		//Move the circle
		circle.setLocation(x, y);
	}
	
	/**
	 * The action that is performed when an object hit the map area. For the ship and
	 * enemies it does nothing but shot are destroy.
	 */
	public void hitMapArea(Level level){
		//Only implement in shot
	}

	public Circle getCircle(){
		return circle;
	}
	public void setCircle(Circle circle){
		this.circle = circle;
	}
	public float getFaceAllignment() {
		return faceAllignment;
	}
	public void setFaceAllignment(float faceAllignment) {
		this.faceAllignment = faceAllignment;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getSpeed() {
		return speed;
	}

}
