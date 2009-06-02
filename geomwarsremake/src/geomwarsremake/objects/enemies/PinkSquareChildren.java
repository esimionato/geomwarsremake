package geomwarsremake.objects.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;
import geomwarsremake.objects.Bomb;
import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Shot;

public class PinkSquareChildren extends Enemy {

	private static final float THE_SPEED = 0.05f;
	private final float ROTATION_SPEED = 0.2f;
	private final int DISTANCE_FROM_CENTER = 40;
	
	private float posX;
	private float posY;
	
	private float positionAngle;
	
	public PinkSquareChildren(float posX, float posY, float positionAngle){
		this.posX = posX;
		this.posY = posY;
		this.positionAngle = positionAngle;
		
		float difX = (float) (DISTANCE_FROM_CENTER*Math.cos(Math.toRadians(positionAngle)));
		float difY = (float) (DISTANCE_FROM_CENTER*Math.sin(Math.toRadians(positionAngle)));
		float newX = posX + difX - 10;
		float newY = posY + difY - 10;
		setCircle(new Circle(newX, newY, 8));
		
		weight = 1;
		score = 50;
		setSpeed(THE_SPEED);
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof PinkSquareChildren;
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
		//Get the position of the ship
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		//Calculate the direction angle that this object want to take
		float directionAngle = (float) GeomWarUtil.findAngle(shipX-posX, shipY-posY);
		//Calculate the speed of this object
		speedX = (float) (getSpeed()*Math.cos(directionAngle));
		speedY = (float) (getSpeed()*Math.sin(directionAngle));
		
		//For every AttractionHole
		for(AttractionHole hole : level.holes){
			if(hole.isAttracting){
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float deltaX = holeX - posX;
				float deltaY = holeY - posY;
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
		//Calculate the distance we will move this Object
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Update the position of this Object
		posX += deltaX;
		posY += deltaY;
		
		//Rotate the little pink square children
		positionAngle += deltaTime*ROTATION_SPEED;
		float difX = (float) (DISTANCE_FROM_CENTER*Math.cos(Math.toRadians(positionAngle)));
		float difY = (float) (DISTANCE_FROM_CENTER*Math.sin(Math.toRadians(positionAngle)));
		float newX = posX + difX - circle.getRadius();
		float newY = posY + difY - circle.getRadius();
		circle.setLocation(newX, newY);
	}
	
	@Override
	/**
	 * Check for collision between the enemy and :
	 * - Ship
	 * - Shots
	 * - Other enemies of the same type
	 * - Attraction holes 
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision(){
		if(!level.pship.isAlive()){
			return; //All enemies will be clear, no need to check for collision
		}
		
		//Get the position of the enemy
		float enemyX = circle.getCenterX();
		float enemyY = circle.getCenterY();
		
		//Collision with the ship
		if(active){
			//Get the position of the ship
			float shipX = level.pship.getCircle().getCenterX();
			float shipY = level.pship.getCircle().getCenterY();
			//Get the total radius of the enemy's circle + ship's circle
			float totalRadius = circle.getRadius() + level.pship.getCircle().getRadius();
			//Calculate the distance between the enemy and the shot
			float deltaX = enemyX - shipX;
			float deltaY = enemyY - shipY;
			float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
			//Check if we have a collision
			if(distance < totalRadius){
				level.pship.died();
				return;
			}
		}
		
		//Collision with a bomb
		Bomb bomb;
		for(int i=0; i<level.bombs.size(); i++){
			bomb = level.bombs.get(i);
			float difX = bomb.getX() - enemyX;
			float difY = bomb.getY() - enemyY;
			float distance2 = (float) Math.sqrt(difX*difX + difY*difY);
			if(distance2 < bomb.getOuterRadius() && distance2 > bomb.getInnerRadius()){
				died();
			}
		}


		//For every shot
		for(Shot shot : level.shots){
			if(shot.getCanHit()){
				//Get the position of the shot
				float shotX = shot.getCircle().getCenterX();
				float shotY = shot.getCircle().getCenterY();
				//Get the total radius of the enemy's circle + shot's circle
				float totalRadius = circle.getRadius() + shot.getCircle().getRadius();
				//Calculate the distance between the enemy and the shot
				float deltaX = enemyX - shotX;
				float deltaY = enemyY - shotY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

				//Check if we have a collision
				if(distance < totalRadius){
					hited();
					shot.hited();
					return;
				}
			}
		}
		
		//For every enemy
		for(Enemy enemy : level.enemies){
			if(!enemy.isDead()){
				if(enemy instanceof AttractionHole){
					AttractionHole hole = (AttractionHole)enemy;
					if(hole.isAttracting){
						//Get the position of the hole
						float holeX = hole.getCircle().getCenterX();
						float holeY = hole.getCircle().getCenterY();
						//Get the total radius of the hole's circle
						float totalRadius = hole.getCircle().getRadius() + circle.getRadius();
						//Calculate the distance between the enemy and the hole
						float deltaX = enemyX - holeX;
						float deltaY = enemyY - holeY;
						float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

						//Check if we have a collision
						if(distance < totalRadius){
							hole.absorbEnemy(this);
							died();
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * THIS PART IS ABOUT ANIMATION ONLY
	 */
	final int animationTime = 1500;
	
	int currentTime = 0;
	
	int size = 20;
	int s = 1;
	
	private void updateAnimation(int deltaTime){
		currentTime += deltaTime;
		currentTime = currentTime % animationTime;
	}
	
	private void render(Graphics g){
		int cx = (int)circle.getCenterX();
		int cy = (int)circle.getCenterY();
		state.pinkImage.setCenterOfRotation((size+s)/2, (size+s)/2);
		state.pinkImage.rotate(getRotationAngle());
		state.pinkImage.draw(cx-size/2, cy-size/2, 0.6f);
		state.pinkImage.rotate(-getRotationAngle());
	}
	
	private float getRotationAngle(){
		return (float) (1.0*currentTime/animationTime * 360);
	}

}
