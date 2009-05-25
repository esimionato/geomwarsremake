package geomwarsremake.objects;

import geomwarsremake.objects.enemies.AttractionHole;
import geomwarsremake.states.IngameState;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;

public class PlayerShip extends GwrObject{

	private final int WEAPON1_INTERVAL = 100;
	private final int WEAPON2_INTERVAL = 60;
	private final int WEAPON3_INTERVAL = 100;
	private final int TIME_BETWEEN_RESPAWN = 1000;
	private final int TIME_BETWEEN_BOMB = 500;
	private final int WEAPON1 = 1;
	private final int WEAPON2 = 2;
	private final int WEAPON3 = 3;
	
	private int delayBeforeNextShot = 0;
	private int delayBeforeRespawn = 0;
	
	private int numberOfBombs = 3;
	private int delayBeforeNextBomb = 0;
	
	private int lifes = 3;
	
	/** Count the number of enemy the player killed on the current life. */
	private int numberOfEnemyKilled = 0;
	
	private int scores = 0;
	
	private final int SCORE_BETWEEN_LIFE = 75000;
	private final int SCORE_BETWEEN_BOMB = 100000;
	
	private int scoreBeforeNextLife = SCORE_BETWEEN_LIFE;
	private int scoreBeforeNextBomb = SCORE_BETWEEN_BOMB;
	
	private int currentWeapon = 1;
	
	private float currentDrawingAngle;

	public PlayerShip(){
		setCircle(new Circle(400, 400, 13));
		setSpeed(0.3f);
	}

	public int getScores() {
		return scores;
	}

	public void addScores(int add) {
		numberOfEnemyKilled++;
		int tempScore = add*getMultiplier();
		this.scores += tempScore;
		scoreBeforeNextLife -= tempScore;
		scoreBeforeNextBomb -= tempScore;
		if(scoreBeforeNextLife <= 0){
			lifes++;
			scoreBeforeNextLife += SCORE_BETWEEN_LIFE;
		}
		if(scoreBeforeNextBomb <= 0){
			numberOfBombs++;
			scoreBeforeNextBomb += SCORE_BETWEEN_BOMB;
		}
		if(scores >= 10000){
			if(currentWeapon == 1){
				currentWeapon = (int)(Math.random()*2) + 2;
			}else{
				randomWeapon2and3();
			}
		}
	}
	
	private void randomWeapon2and3(){
		if(Math.random() > 0.98){
			currentWeapon = (int)(Math.random()*2) + 2;
		}
	}
	
	public int getMultiplier(){
		int result = 1;
		if(numberOfEnemyKilled > 16){
			result = 2;
		}
		if(numberOfEnemyKilled > 32){
			result = 3;
		}
		if(numberOfEnemyKilled > 64){
			result = 4;
		}
		if(numberOfEnemyKilled > 128){
			result = 5;
		}
		if(numberOfEnemyKilled > 256){
			result = 6;
		}
		if(numberOfEnemyKilled > 512){
			result = 7;
		}
		if(numberOfEnemyKilled > 1024){
			result = 8;
		}
		if(numberOfEnemyKilled > 2048){
			result = 9;
		}
		return result;
	}

	public float getSpeedX(){
		return speedX;
	}

	public float getSpeedY(){
		return speedY;
	}
	
	public int getDirectionX() {
		return directionX;
	}
	
	public int getDirectionY() {
		return directionY;
	}
	
	public int getNumberOfLife(){
		return lifes;
	}
	
	public int getNumberOfBomb(){
		return numberOfBombs;
	}
	
	public void died(){
		delayBeforeRespawn = TIME_BETWEEN_RESPAWN;
		numberOfEnemyKilled = 0;
		lifes--;
	}
	
	public boolean isAlive(){
		return delayBeforeRespawn == 0;
	}
	
	public boolean getCanRevive(){
		return !(lifes == 0);
	}
	
	public void updateRespawnTime(int deltaTime){
		delayBeforeRespawn -= deltaTime;
		if(delayBeforeRespawn < 0){
			delayBeforeRespawn = 0;
		}
	}
	
	/**
	 * Draw the ship
	 * @param g The graphics we are drawing on.
	 * @param debug Indicate if we are doing testing and we want to see the collision 
	 * circle
	 */
	public void draw(Graphics g, boolean debug){
		if(state.shipImage != null){
			float angle = currentDrawingAngle;
			int width = state.shipImage.getWidth();
			int height = state.shipImage.getHeight();
			state.shipImage.setCenterOfRotation(width/2, height/2);
			state.shipImage.rotate(angle);
			state.shipImage.draw(circle.getCenterX()-width/2, circle.getCenterY()-height/2, 1f);
			state.shipImage.rotate(-angle);
			if(debug){
				g.draw(circle);
			}
		}else{
			g.draw(circle);
		}
	}
	
	public void updateDrawingDirection(int delta){
		float angle = currentDrawingAngle;
		if(directionX != 0 || directionY != 0){
			angle = (float) Math.toDegrees(GeomWarUtil.findAngle(directionX, directionY));
		}
		if(currentDrawingAngle != angle){
			float deltaClockWise = angle - currentDrawingAngle;
			float deltaCounterClockWise = currentDrawingAngle - angle;
			if(deltaClockWise < 0){
				deltaClockWise += 360;
			}else if(deltaClockWise > 360){
				deltaClockWise -= 360;
			}
			if(deltaCounterClockWise < 0){
				deltaCounterClockWise += 360;
			}else if(deltaCounterClockWise > 360){
				deltaCounterClockWise -= 360;
			}
			float deltaAngle = (float) (0.3*delta);
			if(deltaClockWise < deltaCounterClockWise){
				if(deltaAngle > deltaClockWise){
					currentDrawingAngle = angle;
				}else{
					currentDrawingAngle += deltaAngle;
				}
			}else{
				if(deltaAngle > deltaCounterClockWise){
					currentDrawingAngle = angle;
				}else{
					currentDrawingAngle -= deltaAngle;
				}
			}
		}
	}
	
	@Override
	/**
	 * Update the position of the ship
	 * - Check for player input
	 * - Check for attraction from attraction circle
	 * - Change the position of the ship
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime) {
		//Calculate the speed of the ship
		speedX = getSpeed()*getDirectionX();
		speedY = getSpeed()*getDirectionY();
		//If the ship is moving in both axis, reduce his speed in both axis
		if(speedX != 0 && speedY != 0){
			speedX *= 0.707; //Math.sqrt(0.5)
			speedY *= 0.707; //Math.sqrt(0.5)
		}
		//For every AttractionHole
		for(AttractionHole hole : level.holes){
			if(hole.isAttracting){
				float shipX = circle.getCenterX();
				float shipY = circle.getCenterY();
				float holeX = hole.getCircle().getCenterX();
				float holeY = hole.getCircle().getCenterY();
				float deltaX = holeX - shipX;
				float deltaY = holeY - shipY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
				if(distance < hole.getAttractionRadius()){
					//Ship is attract by the AttractionHole
					float ax = hole.getAttractionForce(distance)/weight * (deltaX/distance);
					float ay = hole.getAttractionForce(distance)/weight * (deltaY/distance);
					speedX += ax * deltaTime/1000;
					speedY += ay * deltaTime/1000;
				}
			}
		}
		
		//Get the position difference we need to move the ship this turn;
		float deltaX = speedX*deltaTime;
		float deltaY = speedY*deltaTime;
		//Update the position of the ship
		float shipX = getCircle().getX() + deltaX;
		float shipY = getCircle().getY() + deltaY;
		getCircle().setLocation(shipX, shipY);
	}
	
	@Override
	/**
	 * Check for collision between the ship and :
	 * - Enemies
	 * @param level The level containing all the objects in this game.
	 */
	public void checkForCollision() {
		//For every enemy
		for(Enemy enemy : level.enemies){
			if(enemy.active){
				//Get the position of the ship
				float shipX = circle.getCenterX();
				float shipY = circle.getCenterY();
				//Get the position of the enemy
				float enemyX = enemy.circle.getCenterX();
				float enemyY = enemy.circle.getCenterY();
				//Get the total radius of the ship's circle + enemy's circle
				float totalRadius = circle.getRadius() + enemy.circle.getRadius();

				//Calculate the distance between the ship and the enemy
				float deltaX = shipX - enemyX;
				float deltaY = shipY - enemyY;
				float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

				//Check if we have a collision
				if(distance < totalRadius){
					died();
				}
			}
		}
	}
	
	//Temporary
	public void updateShotTime(int deltaTime){
		delayBeforeNextShot -= deltaTime;
		if(delayBeforeNextShot < 0){
			delayBeforeNextShot = 0;
		}
	}
	
	public ArrayList<Shot> createShot(){
		ArrayList<Shot> list = new ArrayList<Shot>();
		if(delayBeforeNextShot == 0){
			if(currentWeapon == WEAPON1){
				weapon1(list);
				delayBeforeNextShot = WEAPON1_INTERVAL;
			}else if(currentWeapon == WEAPON2){
				weapon2(list);
				delayBeforeNextShot = WEAPON2_INTERVAL;
			}else if(currentWeapon == WEAPON3){
				weapon3(list);
				delayBeforeNextShot = WEAPON3_INTERVAL;
			}
		}
		return list;
	}
	
	/**
	 * Create two shots side to side with no angle between the direction
	 * of the shots.
	 * @param list
	 */
	private void weapon1(ArrayList<Shot> list){
		float posX;
		float posY;
		float angle = getFaceAllignment();
		//Create first shot
		angle += Math.PI/2;
		posX = (float) (circle.getCenterX() + 5*Math.cos(angle));
		posY = (float) (circle.getCenterY() + 5*Math.sin(angle));
		list.add(new Shot(this, posX, posY, getFaceAllignment(), Shot.SHOT_SPEED));
		//Create second shot
		angle -= Math.PI;
		posX = (float) (circle.getCenterX() + 5*Math.cos(angle));
		posY = (float) (circle.getCenterY() + 5*Math.sin(angle));
		list.add(new Shot(this, posX, posY, getFaceAllignment(), Shot.SHOT_SPEED));
	}
	
	private void weapon2(ArrayList<Shot> list){
		float posX = circle.getCenterX();
		float posY = circle.getCenterY();
		float angle = getFaceAllignment();
		//Create first shot
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED*1.8f));
		//Create second shot
		angle += Math.PI/32;
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED*1.3f));
		//Create third shot
		angle -= Math.PI/16;
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED*1.3f));
	}
	
	private void weapon3(ArrayList<Shot> list){
		float posX = circle.getCenterX();
		float posY = circle.getCenterY();
		float angle = getFaceAllignment();
		//Create first shot
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED));
		//Create second shot
		angle += Math.PI/64;
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED));
		//Create third shot
		angle += Math.PI/64;
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED*0.95f));
		//Create third shot
		angle -= (Math.PI/32 + Math.PI/64);
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED));
		//Create third shot
		angle -= Math.PI/64;
		list.add(new Shot(this, posX, posY, angle, Shot.SHOT_SPEED*0.95f));
	}
	
	/**
	 * Update the player movement direction
	 * @param input The input...
	 */
	public void setDirection(Input input){
		directionX = 0;
		directionY = 0;
		if(input.isKeyDown(Input.KEY_W)){
			directionY--;
		}
		if(input.isKeyDown(Input.KEY_S)){
			directionY++;
		}
		if(input.isKeyDown(Input.KEY_A)){
			directionX--;
		}
		if(input.isKeyDown(Input.KEY_D)){
			directionX++;
		}
	}
	
	public void wantUseBomb() {
		if(delayBeforeNextBomb <= 0 && numberOfBombs > 0) {
			level.bombs.add(new Bomb(level));
			numberOfBombs--;
			delayBeforeNextBomb = TIME_BETWEEN_BOMB;
		}
	}
	
	public void updateBombTime(int delta){
		if(!(delayBeforeNextBomb <= 0)){
			delayBeforeNextBomb -= delta;
		}
	}

}
