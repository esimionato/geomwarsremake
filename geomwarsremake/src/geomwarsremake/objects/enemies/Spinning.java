package geomwarsremake.objects.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import geomwarsremake.objects.Enemy;

public class Spinning extends Enemy {

	private final int TIME_BETWEEN_DIRECTION_CHANGE = 4000;
	
	private int timeBeforeNextDirectionChange = 0;
	private float currentDirection = 0;
	
	/**
	 * Create a Spinning at the specified position;
	 * @param posX The position on the x axis.
	 * @param posY The position on the y axis.
	 */
	public Spinning(int posX, int posY){
		setCircle(new Circle(posX, posY, 13));
		setSpeed(0.05f);
		weight = 2;
		score = 25;
		initAnimation();
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof Spinning;
	}
	
	/**
	 * Draw this object
	 * @param g The graphics we are drawing on.
	 * @param debug Indicate if we are doing testing and we want to see the collision 
	 * circle
	 */
	public void draw(Graphics g, boolean debug){
		render(g);
		g.setColor(Color.white);
		if(debug){
			g.draw(circle);
		}
	}
	
	public void update(int deltaTime){
		super.update(deltaTime);
		updateAnimation(deltaTime);
	}

	@Override
	public void updatePosition(int deltaTime) {
		timeBeforeNextDirectionChange -= deltaTime;
		if(timeBeforeNextDirectionChange <= 0){
			timeBeforeNextDirectionChange = TIME_BETWEEN_DIRECTION_CHANGE;
			currentDirection = (float) (Math.random()* (2*Math.PI));
		}
		
		//Get the position of this object
		float thisX = circle.getCenterX();
		float thisY = circle.getCenterY();
		//Calculate the speed of this object
		speedX = (float) (getSpeed()*Math.cos(currentDirection));
		speedY = (float) (getSpeed()*Math.sin(currentDirection));
		
		//Calculate the attraction from the attraction hole
		for(AttractionHole hole : level.holes){
			if(hole.isAttracting){
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float deltaX = holeX - thisX;
				float deltaY = holeY - thisY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
				if(distance < hole.getAttractionRadius()){
					//This object is attract by the AttractionHole
					float ax = hole.getAttractionForce(distance)/weight * (deltaX/distance);
					float ay = hole.getAttractionForce(distance)/weight * (deltaY/distance);
					speedX += ax * deltaTime/1000;
					speedY += ay * deltaTime/1000;
				}
			}
		}
		//Calculate the distance we will move this object
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Update the position of this object
		float newX = circle.getX() + deltaX;
		float newY = circle.getY() + deltaY;
		getCircle().setLocation(newX, newY);
	}
	
	public void hitMapArea(){
		currentDirection = (float) (Math.random() * (2*Math.PI));
	}
	
	/**
	 * THIS PART IS ABOUT ANIMATION ONLY
	 */
	int animationTime = 2000;
	
	int currentTime = 0;
	
	int size = 40;
	int s = 2;
	
	float rotationSpeed;
	float angle = 0;
	
	private void initAnimation(){
		animationTime -= Math.random()*1000;
	}
	
	private void updateAnimation(int deltaTime){
		currentTime += deltaTime;
		currentTime = currentTime % animationTime;
		state.spinningImage.setCenterOfRotation((size+s)/2, (size+s)/2);
	}
	
	private void render(Graphics g){
		int cx = (int)circle.getCenterX();
		int cy = (int)circle.getCenterY();
		state.spinningImage.rotate(getRotationAngle());
		state.spinningImage.draw(cx-size/2, cy-size/2);
		state.spinningImage.rotate(-getRotationAngle());
	}
	
	private float getRotationAngle(){
		return (float) (1.0*currentTime/animationTime * 360);
	}

}
