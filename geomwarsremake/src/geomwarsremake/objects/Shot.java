package geomwarsremake.objects;

import geomwarsremake.objects.enemies.AttractionHole;
import geomwarsremake.states.IngameState;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;

public class Shot extends GwrObject{
	
	public static final float SHOT_SPEED = 0.6f;
	
	/** Indicate if the shot can hit enemies */
	private boolean canHit = true;
	private float drawingAngle;
	
	/**
	 * Create a shot. The shot speed is affected by the ship movement
	 * @param ship The ship that fired the shot.
	 */
	public Shot(PlayerShip ship, float posX, float posY, float directionAngle, float speed){
		//Set the position of the shot
		setCircle(new Circle(posX, posY, 5));
		drawingAngle = (float) Math.toDegrees(directionAngle);
		
		//Get the speed of the ship
		float shipSpeedX = ship.getSpeedX();
		float shipSpeedY = ship.getSpeedY();
		//Calculate the speed of the shot
		speedX = (float) (speed*Math.cos(directionAngle) + shipSpeedX);
		speedY = (float) (speed*Math.sin(directionAngle) + shipSpeedY);
		setSpeed((float) Math.sqrt(speedX*speedX + speedY*speedY));
		//Set weight
		weight = 10;
	}
	
	/**
	 * @return If the shot can hit an enemy or not
	 */
	public boolean getCanHit(){
		return canHit;
	}
	
	@Override
	/**
	 * Draw the ship
	 * @param g The graphics we are drawing on.
	 * @param debug Indicate if we are doing testing and we want to see the collision 
	 * circle
	 */
	public void draw(Graphics g, boolean debug){
		if(state.shot != null){
			float angle = drawingAngle+90;
			int width = state.shot.getWidth();
			int height = state.shot.getHeight();
			state.shot.setCenterOfRotation(width/2, height/2);
			state.shot.rotate(angle);
			state.shot.draw(circle.getCenterX()-width/2, circle.getCenterY()-height/2, 1f);
			state.shot.rotate(-angle);
			if(debug){
				g.draw(circle);
			}
		}else{
			g.draw(circle);
		}
	}

	@Override
	/**
	 * Update the position of the shot
	 * - Check for repulsion from attraction circle
	 * - Change the position of the shot
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime) {
		//For every AttractionHole
		for(AttractionHole hole : level.holes){
			if(hole.isAttracting){
				float shotX = circle.getCenterX();
				float shotY = circle.getCenterY();
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float delta2X = holeX - shotX;
				float delta2Y = holeY - shotY;
				float distance = (float) Math.sqrt(delta2X*delta2X + delta2Y*delta2Y);
				if(distance < hole.getAttractionRadius()){
					//Shot is repulse by the AttractionHole
					float ax = -hole.getAttractionForce(distance)/weight * (delta2X/distance);
					float ay = -hole.getAttractionForce(distance)/weight * (delta2Y/distance);
					speedX += ax * deltaTime/1000;
					speedY += ay * deltaTime/1000;
				}
			}
		}
		//Adjust speed (The speed must remain constant)
		float speedTotal = (float) Math.sqrt(speedX*speedX + speedY*speedY);
		speedX = speedX/speedTotal*getSpeed();
		speedY = speedY/speedTotal*getSpeed();
		//Calculate the distance we will move the shot.
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Calculate the new position of the shot
		float shotX = getCircle().getX() + deltaX;
		float shotY = getCircle().getY() + deltaY;
		//Update the position of the shot
		getCircle().setLocation(shotX, shotY);	
	}

	@Override
	/**
	 * Check for collision between the shot and :
	 * - Enemies
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision() {
		//For every enemy
		for(Enemy enemy : level.enemies){
			if(!enemy.isDead()){
				//Get the position of the shot
				float shipX = circle.getCenterX();
				float shipY = circle.getCenterY();
				//Get the position of the enemy
				float enemyX = enemy.circle.getCenterX();
				float enemyY = enemy.circle.getCenterY();
				//Get the total radius of the shot's circle + enemy's circle
				float totalRadius = circle.getRadius() + enemy.circle.getRadius();

				//Calculate the distance between the shot and the enemy
				float deltaX = shipX - enemyX;
				float deltaY = shipY - enemyY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

				//Check if we have a collision
				if(distance < totalRadius){
					hited();
					enemy.hited();
					break;
				}
			}
		}
	}
	
	/**
	 * Called when a shot hit an enemy
	 * @param level
	 */
	public void hited(){
		canHit = false;
		state.playHit();
	}
	
	/**
	 * Called when a shot hit the map area
	 * @param level The level containing all the object in this game
	 */
	public void hitMapArea(){
		canHit = false;
		//Add animation when the shot hit the wall
	}

}
