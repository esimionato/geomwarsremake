package geomwarsremake.objects;

public class PlayerShip extends GwrObject{
  private int directionX = 0;
  private int directionY = 0;
  
  public PlayerShip(){
    setX(0);
    setY(0);
    setSpeed(0.3f);
  }
  
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

}
