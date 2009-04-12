package geomwarsremake.objects;

import java.util.ArrayList;

/**
 *  Main datastructure for game. Contains all other objects.
 */
public class Level {
  public PlayerShip pship;
  public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  public ArrayList<Shot> shots = new ArrayList<Shot>();
  
  
  public void load() {
    pship = new PlayerShip();
    
  }
  
  /**
   * Enemies generator.
   * 
   */

}
