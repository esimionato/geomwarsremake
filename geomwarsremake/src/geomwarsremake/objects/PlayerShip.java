package geomwarsremake.objects;

import org.newdawn.slick.geom.Circle;

public class PlayerShip extends GwrObject{

  //temp weapon
  private float weaponInterval = 1000;// 1000 = one second
  
  public PlayerShip(){
    setCircle(new Circle(400, 400, 5));
    setSpeed(0.3f);
  }
  
  
  


}
