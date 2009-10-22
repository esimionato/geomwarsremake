package geomwarsremake.objects.enemies;

import org.newdawn.slick.Color;
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
		setCircle(new Circle(posX, posY, 13));
		setSpeed(INITIAL_SPEED);
		weight = 2;
		score = 50;
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof BlueLozenge;
	}
	
	public void draw(Graphics g, boolean debug){
		render(g);
		g.setColor(Color.white);
		if(debug){
			g.draw(circle);
		}
	}
	
	@Override
	public void update(int deltaTime){
		super.update(deltaTime);
		currentTime += deltaTime;
		currentTime = currentTime % animationTime;
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
	
	/**
	 * THIS PART IS ABOUT ANIMATION ONLY
	 */
	final int animationTime = 1500;
	
	final double initialWidth = 25;
	final double initialHeight = 45;
	final double changeWidthSpeed = 20.0/animationTime;
	final double changeHeightSpeed = 15.0/animationTime;
	
	int currentTime = 0;
	
	public void render(Graphics g){
		float w = g.getLineWidth();
		int cx = (int) circle.getCenterX();
		int cy = (int) circle.getCenterY();
		g.setColor(new Color(80, 237, 237));
		g.setLineWidth(6);
		//g.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		double width = getWidth();
		double height = getHeight();
		//Top-Left stroke
		g.drawLine((int)(cx-width/2), cy, cx, (int)(cy-height/2));
		//Top-Right
		g.drawLine(cx, (int)(cy-height/2), (int)(cx+width/2), cy);
		//Bottom-Right
		g.drawLine((int)(cx+width/2), cy, cx, (int)(cy+height/2));
		//Bottom-Left
		g.drawLine(cx, (int)(cy+height/2), (int)(cx-width/2), cy);
		g.setLineWidth(w);
	}
	
	private double getWidth(){
		if(currentTime < animationTime/2){
			return initialWidth +  currentTime*changeWidthSpeed;
		}else{
			return initialWidth + animationTime/2*changeWidthSpeed - (currentTime-animationTime/2)*changeWidthSpeed;
		}
	}
	
	private double getHeight(){
		if(currentTime < animationTime/2){
			return initialHeight -  currentTime*changeHeightSpeed;
		}else{
			return initialHeight - animationTime/2*changeHeightSpeed + (currentTime-animationTime/2)*changeHeightSpeed;
		}
	}
}
