package geomwarsremake.objects.enemies;

import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class Pacman extends Enemy {

	
	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof Pacman;
	}

	@Override
	public void updatePosition(int deltaTime) {
		// TODO Auto-generated method stub
		
	}

}
