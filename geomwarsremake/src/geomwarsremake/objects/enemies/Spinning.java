package geomwarsremake.objects.enemies;

import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class Spinning extends Enemy {

	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof Spinning;
	}

	@Override
	public void updatePosition(int deltaTime, Level level) {
		// TODO Auto-generated method stub
		
	}

}
