package geomwarsremake.objects;

public class PlayerShip {
  public float x = 0;
  public float y = 0;
  private int directionX = 0;
  private int directionY = 0;
  private int speed = 1;
  private  int aimDirection;
  
  //moovment control
  public int getDirectionX() {
    return directionX;
  }
  public int getDirectionY() {
    return directionY;
  }
  public void setDirectionY(int i) {
    directionY = i;
  }
  public void setDirectionX(int i) {
    directionX = i;
  }
  public int getSpeed() {
    return speed;
  }

}
