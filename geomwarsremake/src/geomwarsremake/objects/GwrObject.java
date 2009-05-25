package geomwarsremake.objects;

import geomwarsremake.states.IngameState;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
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
	
	protected IngameState state;
	protected Level level;

	public Circle getCircle(){
		return circle;
	}
	public float getFaceAllignment() {
		return faceAllignment;
	}
	public float getSpeed() {
		return speed;
	}
	public float getSpeedX(){
		return speedX;
	}
	public float getSpeedY(){
		return speedY;
	}
	public void setCircle(Circle circle){
		this.circle = circle;
	}
	public void setFaceAllignment(float faceAllignment) {
		this.faceAllignment = faceAllignment;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public void setStateAndLevel(IngameState state, Level level){
		this.state = state;
		this.level = level;
	}
	
	public void draw(Graphics g){
		draw(g, true);
	}
	public abstract void draw(Graphics g, boolean debug);

	public abstract void updatePosition(int deltaTime);

	public abstract void checkForCollision();

	/** 
	 * Keep all object inside the map area 
	 * @param level The level containing all the objects of this game
	 */
	public void collisionMapArea(){
		float x = circle.getX();
		float y = circle.getY();
		//West
		if(circle.getMinX() < 0){
			x = 0;
			speedX = -speedX;
			hitMapArea();
		}
		//North
		if(circle.getMinY() < 0){
			y = 0;
			speedY = -speedY;
			hitMapArea();
		}
		//East
		if(circle.getMaxX() > level.mapWidth){
			x = level.mapWidth - circle.getRadius()*2;
			speedX = -speedX;
			hitMapArea();
		}
		//South
		if(circle.getMaxY() > level.mapHeight){
			y = level.mapHeight - circle.getRadius()*2;
			speedY = -speedY;
			hitMapArea();
		}
		//Move the circle
		circle.setLocation(x, y);
	}
	
	/**
	 * The action that is performed when an object hit the map area. For the ship and
	 * enemies it does nothing but shot are destroy.
	 */
	public void hitMapArea(){
		//Only implement in shot and spinning
	}

}
