package geomwarsremake.objects.enemies;

import geomwarsremake.objects.Enemy;
import geomwarsremake.objects.Level;

public class PinkSquareChildren extends Enemy {

	
	public boolean isInstanceOf(Enemy enemy){
		return enemy instanceof PinkSquareChildren;
	}

	@Override
	public void updatePosition(int deltaTime, Level level) {
		// TODO Auto-generated method stub
		
	}

}
