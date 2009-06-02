package geomwarsremake.objects.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;
import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class PinkSquare extends Enemy {
	
	private final float MAX_SPEED = 0.3f;
	private final float ACCELERATION = 0.01f;

	public PinkSquare(int posX, int posY){
		setCircle(new Circle(posX, posY, 13));
		weight = 10;
		score = 100;
		initAnimation();
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof PinkSquare;
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
	
	public void hited(){
		level.pship.addScores(this.score);
		died();
		//Create 3 children
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		float pinkX = circle.getCenterX();
		float pinkY = circle.getCenterY();
		float angle = (float) util.GeomWarUtil.findAngle(pinkX-shipX, pinkY-shipY);
		float cx = (float) (pinkX + 50*Math.cos(angle));
		float cy = (float) (pinkY + 50*Math.sin(angle));
		
		int dist = 80;
		float randomAngle = (float) (Math.random()*Math.PI*2);
		for(int i=0; i<3; i++){
			angle = (float) (randomAngle + Math.PI*2*i/3);
			float posX = (float) (cx + Math.cos(angle)*dist);
			float posY = (float) (cy + Math.sin(angle)*dist);
			float positionAngle = (float) (Math.random()*360);
			level.enemiesToAdd.add(new PinkSquareChildren(posX, posY, positionAngle));
		}
	}
	
	/**
	 * THIS PART IS ABOUT ANIMATION ONLY
	 */
	final int animationTime = 1500;
	
	int currentTime = 0;
	
	int size = 30;
	int s = 2;
	
	private void initAnimation(){
		
	}
	
	private void updateAnimation(int deltaTime){
		currentTime += deltaTime;
		currentTime = currentTime % animationTime;
	}
	
	private void render(Graphics g){
		int cx = (int)circle.getCenterX();
		int cy = (int)circle.getCenterY();
		state.pinkImage.setCenterOfRotation((size+s)/2, (size+s)/2);
		state.pinkImage.rotate(getRotationAngle());
		state.pinkImage.draw(cx-size/2, cy-size/2);
		state.pinkImage.rotate(-getRotationAngle());
	}
	
	private float getRotationAngle(){
		double angle = 1.0*currentTime/animationTime * (2*Math.PI);
		double h = Math.sin(angle);
		return (float)(h * 90);
	}

}
