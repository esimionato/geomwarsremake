package geomwarsremake.objects;

import org.newdawn.slick.geom.Circle;

public class GwrObject {
  //Object position
  private Circle circle;
  //specify where object is faced (angle)
  private float faceAllignment = 0;
  //basic speed
  private float speed = 1;
  //movement
  private int directionX = 0;
  private int directionY = 0;
  

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
  
  /*public void setDirectionY(int directionY) {
    this.directionY = directionY;
  }
  public void setDirectionX(int directionX) {
    this.directionX = directionX;
  }*/

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
