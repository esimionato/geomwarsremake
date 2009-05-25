package geomwarsremake.objects.enemies;

import org.newdawn.slick.geom.Circle;

import util.GeomWarUtil;
import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class AttractionHole extends Enemy {
	
	public boolean isAttracting = false;
	
	private float attractionRadius = 600;
	private float attractionForce = 1000;
	
	private int life = 6;
	
	/**
	 * Create an AttractionHole at the specified position;
	 * @param posX The position on the x axis.
	 * @param posY The position on the y axis.
	 */
	public AttractionHole(int posX, int posY){
		setCircle(new Circle(posX, posY, 20));
		setSpeed(0.01f);
		score = 50;
	}
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof AttractionHole;
	}
	
	public float getAttractionRadius(){
		return attractionRadius;
	}
	
	public float getAttractionForce(float distance){
		//This formula is subject to change
		float dist = distance;
		if(dist < 15){
			dist = 15;
		}
		float force = attractionForce / (dist);
		return force;
	}
	
	public void absorbEnemy(Enemy enemy){
		score += enemy.getScore();
		life++;
		if(life >= 10){
			explode();
			died();
		}
	}
	
	@Override
	public void hited(){
		if(!isAttracting){
			isAttracting = true;
		}else{
			life--;
			if(life <= 0){
				super.hited();
			}
		}
	}
	
	/**
	 * Create 10 AttractionHoleChildren and throw them in different direction
	 */
	private void explode(){
		int thisX = (int) circle.getCenterX();
		int thisY = (int) circle.getCenterY();
		for(int i=0; i<10; i++){
			float angle = (float) ((i/10.0) * (2*Math.PI));
			float speedX = (float) (0.45 * Math.cos(angle));
			float speedY = (float) (0.45 * Math.sin(angle));
			System.out.println(speedX + " " + speedY);
			AttractionHoleChildren ahc = new AttractionHoleChildren(thisX, thisY);
			ahc.setSpeedX(speedX);
			ahc.setSpeedY(speedY);
			level.enemiesToAdd.add(ahc);
		}
	}

	@Override
	/**
	 * Update the position of the AttractionHole
	 * - Check for attraction from other attraction hole (TODO)
	 * - Update the position of the AttractionHole
	 * @param deltaTime The time delay since the last update.
	 * @param level The level containing all the objects in this game.
	 */
	public void updatePosition(int deltaTime) {
		//Get the position of the AttractionHole
		float thisX = circle.getCenterX();
		float thisY = circle.getCenterY();
		//Get the position of the ship
		float shipX = level.pship.getCircle().getCenterX();
		float shipY = level.pship.getCircle().getCenterY();
		//Calculate the direction angle that the AttractionHole want to take
		float directionAngle = (float) GeomWarUtil.findAngle(shipX-thisX, shipY-thisY);
		//Calculate the distance we will move the AttractionHole
		float deltaX = (float) (getSpeed()*Math.cos(directionAngle)*deltaTime);
		float deltaY = (float) (getSpeed()*Math.sin(directionAngle)*deltaTime);
		//Update the position of the BlueLozenge
		float newX = circle.getX() + deltaX;
		float newY = circle.getY() + deltaY;
		getCircle().setLocation(newX, newY);
	}

}
