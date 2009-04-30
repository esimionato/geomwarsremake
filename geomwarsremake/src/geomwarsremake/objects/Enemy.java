package geomwarsremake.objects;

public abstract class Enemy extends GwrObject{
  
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
     * Move this object outside of the object it just collide with.
     * 
     * @param distX The distance in x between this object and the one it collide with.
     * @param distY The distance in y between this object and the one it collide with.
     * @param distance The distance between this object and the one it collide with.
     * @param desiredDistance The distance this object is suppose to be from the object 
     * it collide with.
     */
    protected void collisionUpdate(float distX, float distY, float distance, float desiredDistance){
        float x = circle.getX();
        float y = circle.getY();
        if(distance != 0){
            x += (distX/distance) * (desiredDistance-distance + 1);
            y += (distY/distance) * (desiredDistance-distance + 1);
        }else{
            x += desiredDistance + 1;
        }
        circle.setLocation(x, y);
    }
	

	/**
	 * AI logic here, should be implemented for each specific enemy type separately.
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
