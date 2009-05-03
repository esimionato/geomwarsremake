package geomwarsremake.objects;

import geomwarsremake.objects.enemies.AttractionHole;
import geomwarsremake.objects.enemies.BlueLozenge;
import geomwarsremake.objects.enemies.GreenSquare;
import geomwarsremake.objects.enemies.PinkSquare;

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

	public int mapWidth = 1000;
	public int mapHeight = 800;
	
	public void load() {
		pship = new PlayerShip();
		addEnemy(new BlueLozenge(400,0));
		addEnemy(new GreenSquare(0, 400));
		addEnemy(new PinkSquare(800, 400));
		addEnemy(new AttractionHole(100, 300));
		addEnemy(new BlueLozenge(400,0));
		addEnemy(new GreenSquare(0, 400));
		addEnemy(new PinkSquare(800, 400));
		//addEnemy(new AttractionHole(100, 300));
		addEnemy(new BlueLozenge(400,0));
		addEnemy(new GreenSquare(0, 400));
		addEnemy(new PinkSquare(800, 400));
		//addEnemy(new AttractionHole(100, 300));
		addEnemy(new BlueLozenge(400,0));
		addEnemy(new GreenSquare(0, 400));
		addEnemy(new PinkSquare(800, 400));
		//addEnemy(new AttractionHole(100, 300));
	}
	
	
	public void resetEnemies(){
		enemies.clear();
		holes.clear();
	}
	
	private void addEnemy(Enemy enemy){
		if(enemy instanceof AttractionHole){
			holes.add((AttractionHole) enemy);
		}
		enemies.add(enemy);
	}
	
	private void removeEnemy(Enemy enemy, int index){
		if(enemy instanceof AttractionHole){
			holes.remove(enemy);
		}
		enemies.remove(index);
	}
	
	public void removeDeadObjects(){
		for(int i=0; i<enemies.size(); i++){
			if(enemies.get(i).isDead()){
				removeEnemy(enemies.get(i), i);
				i--;
			}
		}
		for(int i=0; i<shots.size(); i++){
			if(!shots.get(i).getCanHit()){
				shots.remove(i);
				i--;
			}
		}
	}

	



	/**
	 * Enemies generator.
	 * 
	 */

}
