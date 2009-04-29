package geomwarsremake.objects;

import org.newdawn.slick.geom.Circle;

public abstract class GwrObject {
  //Object position
  protected Circle circle;
  //specify where object is faced (angle)
  private float faceAllignment = 0;
  //basic speed
  private float speed = 1;
  //movement
  protected int directionX = 0;
  protected int directionY = 0;
  
  
  public abstract void updatePosition(int deltaTime, Level level);
  
  public abstract void checkForCollision(Level level);

  public int getDirectionX() {
    return directionX;
  }
  public int getDirectionY() {
    return directionY;
  }
  public Circle getCircle(){
	  return circle;
  }
  public void setCircle(Circle circle){
	  this.circle = circle;
  }
  public float getFaceAllignment() {
    return faceAllignment;
  }
  public void setFaceAllignment(float faceAllignment) {
    this.faceAllignment = faceAllignment;
  }
  public void setSpeed(float speed) {
    this.speed = speed;
  }
  public float getSpeed() {
    return speed;
  }

}
