package geomwarsremake.objects.enemies;

import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class Snake extends Enemy {

	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof Snake;
	}

	@Override
	public void updatePosition(int deltaTime, Level level) {
		// TODO Auto-generated method stub
		
	}

}
