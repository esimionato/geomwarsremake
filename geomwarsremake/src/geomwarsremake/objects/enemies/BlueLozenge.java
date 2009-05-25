package geomwarsremake.objects.enemies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;

import geomwarsremake.objects.Enemy;

public class BlueLozenge extends Enemy {
	
	private static final float INITIAL_SPEED = 0.1f;
	private static final float SPEED_INCREASE_PER_SECOND = 0.002f;
	
	private int timeBeforeNextSpeedUpdate = 1000;

	/**
	 * Create a BlueLozenge at the specified position;
	 * @param posX The position on the x axis.
	 * @param posY The position on the y axis.
	 */
	public BlueLozenge(int posX, int posY){
		setCircle(new Circle(posX, posY, 12));
		setSpeed(INITIAL_SPEED);
		weight = 2;
		score = 25;
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof BlueLozenge;
	}
	
	public void draw(Graphics g, boolean debug){
		if(state.blueLozenge != null){
			float scaling = 1.1f;
			float width = state.blueLozenge.getWidth()*scaling;
			float height = state.blueLozenge.getHeight()*scaling;
			state.blueLozenge.draw(circle.getCenterX()-width/2, circle.getCenterY()-height/2, scaling);
			if(debug){
				g.draw(circle);
			}
		}else{
			g.draw(circle);
		}
	}
	
	@Override
	/**
	 * Update the position of the BlueLozenge
	 * - Check for attraction from attraction circle
	 * - Update the speed of the BlueLozenge if it's the time
	 * - Update the position of the BlueLozenge
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime) {
		//Update the time before the next speed update
		timeBeforeNextSpeedUpdate -= deltaTime;
		//If it's time to update the speed, do it
		while(timeBeforeNextSpeedUpdate < 0){
			timeBeforeNextSpeedUpdate += 1000;
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
			if(hole.isAttracting){
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float deltaX = holeX - blueX;
				float deltaY = holeY - blueY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
				if(distance < hole.getAttractionRadius()){
					//BlueLozenge is attract by the AttractionHole
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
