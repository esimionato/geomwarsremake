package geomwarsremake.objects;

import org.newdawn.slick.geom.Circle;

public class Shot extends GwrObject{
	
	float directionAngle;
	
	public Shot(PlayerShip ship){
		float tempX = ship.getCircle().getCenterX();
		float tempY = ship.getCircle().getCenterY();
		setCircle(new Circle(tempX, tempY, 5));
		setSpeed(1f);
		directionAngle = ship.getFaceAllignment();
	}

	@Override
	public void updatePosition(int deltaTime) {
		float deltaX = (float) (getSpeed()*Math.cos(directionAngle)*deltaTime);
		float deltaY = (float) (getSpeed()*Math.sin(directionAngle)*deltaTime);
		float shotX = getCircle().getX() + deltaX;
		float shotY = getCircle().getY() + deltaY;
		getCircle().setLocation(shotX, shotY);	
	}

}
