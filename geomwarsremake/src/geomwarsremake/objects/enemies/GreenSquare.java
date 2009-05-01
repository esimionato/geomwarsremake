package geomwarsremake.objects.enemies;

import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;
import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;
import geomwarsremake.objects.Shot;

public class GreenSquare extends Enemy {
	
	private final float AVOID_RADIUS = 400;
	private final float AVOID_FORCE = 50;
	
	public GreenSquare(int posX, int posY){
		setCircle(new Circle(posX, posY, 10));
		setSpeed(0.25f);
		weight = 2;
		score = 50;
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof GreenSquare;
	}
	
	public float getAvoidForce(float distance){
		return AVOID_FORCE*(AVOID_RADIUS- distance)/AVOID_RADIUS;
	}

	@Override
	public void updatePosition(int deltaTime, Level level) {
		//Get the position of the Green
		float greenX = circle.getCenterX();
		float greenY = circle.getCenterY();
		//Get the position of the ship
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		//Calculate the direction angle that the Green want to take
		float directionAngle = (float) GeomWarUtil.findAngle(shipX-greenX, shipY-greenY);
		//Calculate the speed of this object
		speedX = (float) (getSpeed()*Math.cos(directionAngle));
		speedY = (float) (getSpeed()*Math.sin(directionAngle));
		
		//For every AttractionHole
		for(AttractionHole hole : level.holes){
			if(hole.isAttracting){
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float deltaX = holeX - greenX;
				float deltaY = holeY - greenY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
				if(distance < hole.getAttractionRadius()){
					//Green is attract by the AttractionHole
					float ax = hole.getAttractionForce(distance)/weight * (deltaX/distance);
					float ay = hole.getAttractionForce(distance)/weight * (deltaY/distance);
					speedX += ax * deltaTime/1000;
					speedY += ay * deltaTime/1000;
				}
			}
		}
		//For every Shot
		for(Shot shot : level.shots){
			float shotX = shot.getCircle().getCenterX();
			float shotY = shot.getCircle().getCenterY();
			float deltaX = shotX - greenX;
			float deltaY = shotY - greenY;
			float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
			if(distance < AVOID_RADIUS){
				//Green is attract by the AttractionHole
				float ax = -getAvoidForce(distance)/weight * (deltaX/distance);
				float ay = -getAvoidForce(distance)/weight * (deltaY/distance);
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

}
