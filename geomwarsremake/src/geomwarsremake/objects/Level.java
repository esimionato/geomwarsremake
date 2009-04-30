package geomwarsremake.objects;

import geomwarsremake.objects.enemies.AttractionHole;
import geomwarsremake.objects.enemies.BlueLozenge;

import java.util.ArrayList;

/**
 *  Main datastructure for game. Contains all other objects.
 */
public class Level {
  public PlayerShip pship;
  public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  public ArrayList<Shot> shots = new ArrayList<Shot>();
  //Attraction hole are also contains in the Enemy list. This list is to help
  //with the attraction and repulsion of the hole over every other objects in the game
  public ArrayList<AttractionHole> holes = new ArrayList<AttractionHole>();
  
  public void load() {
    pship = new PlayerShip();
    enemies.add(new BlueLozenge(400,0));
    AttractionHole hole = new AttractionHole(100, 300);
    enemies.add(hole);
    holes.add(hole);
  }
  
  
  
  /**
   * Enemies generator.
   * 
   */

}
