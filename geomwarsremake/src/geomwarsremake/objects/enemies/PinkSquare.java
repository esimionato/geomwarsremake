package geomwarsremake.objects.enemies;

import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;
import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class PinkSquare extends Enemy {
	
	private final float MAX_SPEED = 0.4f;
	private final float ACCELERATION = 0.01f;

	public PinkSquare(int posX, int posY){
		setCircle(new Circle(posX, posY, 15));
		weight = 10;
		score = 50;
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof PinkSquare;
	}
	
	@Override
	public void updatePosition(int deltaTime, Level level) {
		//Get the position of the Pink
		float pinkX = circle.getCenterX();
		float pinkY = circle.getCenterY();
		//Get the position of the ship
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		//Calculate the direction angle that the Pink want to take
		float directionAngle = (float) GeomWarUtil.findAngle(shipX-pinkX, shipY-pinkY);
		//Calculate the speed of this object
		speedX = (float) (speedX*0.98 + (ACCELERATION*Math.cos(directionAngle)));
		speedY = (float) (speedY*0.98 + (ACCELERATION*Math.sin(directionAngle)));
		//Adjust speed
		float speedTotal = (float) Math.sqrt(speedX*speedX + speedY*speedY);
		if(speedTotal > MAX_SPEED){
			speedX = speedX/speedTotal*MAX_SPEED;
			speedY = speedY/speedTotal*MAX_SPEED;
		}
		
		//For every AttractionHole
		for(AttractionHole hole : level.holes){
			if(hole.isAttracting){
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float deltaX = holeX - pinkX;
				float deltaY = holeY - pinkY;
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
		//Calculate the distance we will move the BlueLozenge
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Update the position of the BlueLozenge
		float newX = circle.getX() + deltaX;
		float newY = circle.getY() + deltaY;
		getCircle().setLocation(newX, newY);
	}

}
