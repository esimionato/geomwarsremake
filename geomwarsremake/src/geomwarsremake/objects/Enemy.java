package geomwarsremake.objects;

public class Enemy extends GwrObject{
  private float weight = 1;
  
  //set active when we add object to map (e.g. we could create it and set coordinates beforehand)
  private boolean active = false;
  // indicate that object is dead and will be removed soon
  private boolean dead = false;
  //enemy type (snake, hole, pacman, etc)
  private String type = "";

	@Override
	public void updatePosition(int deltaTime) {
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * AI logic here, should be iimplemented for each spesific enemy type separately.
	 * @param level - ingame level
	 */
	public void actAI(Level level){
	  
	}
  
	/**
	 * called when enemy is hitted by shot
	 * maybe we should give coordinate of hit, so object will recognize where it was hitted
	 */
	public void hited(){
	  
	}
	
	// when enemy is died (display animation, remove object)
	public void died() {
	  
	}
	

}
