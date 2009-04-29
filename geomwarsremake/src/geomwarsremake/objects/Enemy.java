package geomwarsremake.objects;

public abstract class Enemy extends GwrObject{
  private float weight = 1;
  
  //set active when we add object to map (e.g. we could create it and set coordinates beforehand)
  protected boolean active = false;
  // indicate that object is dead and will be removed soon
  protected boolean dead = false;
  //enemy type (snake, hole, pacman, etc)
  protected String type = "";

	@Override
	public abstract void updatePosition(int deltaTime, Level level);
	
	@Override
	public abstract void checkForCollision(Level level);
	

	/**
	 * AI logic here, should be iimplemented for each spesific enemy type separately.
	 * @param level - ingame level
	 */
	public void actAI(Level level){
	  //I will put it in updatePosition
	}
  
	/**
	 * called when enemy is hitted by shot
	 * maybe we should give coordinate of hit, so object will recognize where it was hitted
	 */
	public void hited(){
	  //set as not active, remove from play field
	}
	
	// when enemy is died (display animation, remove object)
	public void died() {
	  
	}
	
	public boolean isDead() {
	  return dead;
	  
	}

}
