package geomwarsremake.objects;

public class GwrObject {
  //coordinates
  private float x ,y;
  //specify where object is faced
  private float direction;
  //basic speed
  private float speed = 1;
  
  
  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getDirection() {
    return direction;
  }

  public void setDirection(float direction) {
    this.direction = direction;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }
  
  public float getSpeed() {
    return speed;
  }

}
