package geomwarsremake.objects;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;

public class PlayerShip extends GwrObject{

	//temp weapon
	private int weaponInterval = 200;// 200 = one second
	int delayBeforeNextShot = 0;

	public PlayerShip(){
		setCircle(new Circle(400, 400, 10));
		setSpeed(0.3f);
	}
	
	@Override
	public void updatePosition(int deltaTime) {
		float deltaX = getSpeed()*getDirectionX()*deltaTime;
		float deltaY = getSpeed()*getDirectionY()*deltaTime;
		float shipX = getCircle().getX() + deltaX;
		float shipY = getCircle().getY() + deltaY;
		getCircle().setLocation(shipX, shipY);
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
			list.add(new Shot(this));
			delayBeforeNextShot = weaponInterval;
		}
		return list;
	}
	
	public void wantDirectionDOWN(boolean wantDown) {
		if(wantDown) {
			directionY++;
		} else {
			directionY--;
		} 
	}

	public void wantDirectionUP(boolean wantUp) {
		if(wantUp) {
			directionY--;
		} else {
			directionY++;
		} 
	}

	public void wantDirectionRIGHT(boolean wantRight) {
		if(wantRight) {
			directionX++;
		} else {
			directionX--;
		} 
	}

	public void wantDirectionLEFT(boolean wantLeft) {
		if(wantLeft) {
			directionX--;
		} else {
			directionX++;
		} 
	}

}
