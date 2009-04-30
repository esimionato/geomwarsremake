package geomwarsremake.states;

import geomwarsremake.GeomWarsRemake;
import geomwarsremake.objects.*;

import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import util.GeomWarUtil;


public class IngameState extends GwarState {
	static Logger logger = Logger.getLogger(IngameState.class);
	private Level level;
	public static final int id = 3;
	//float mouseX,mouseY;
	/** GameContainer width */
	float width;
	/** GameContainer height */
	float height;
	private boolean soundEnabled = false;
	private Music music;

	public IngameState(GeomWarsRemake ctx) {
		super(ctx);
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
	}

	public int getID() {
		return id;
	}
	
	@Override
	public void initState(GameContainer c) throws SlickException {
		setBackground(new Color(87, 146, 186));  
		c.setMaximumLogicUpdateInterval(50);
		width = c.getWidth();
		height = c.getHeight();
		music = new Music("resources/sounds/drum.ogg");
	}

	@Override
	public void renderState(GameContainer c, Graphics g) {
		//draw background
		g.setColor(Color.black);
		//g.clear();
		


		//Position the map
		g.translate(getTranslateX(), getTranslateY());
		
		
		//Draw map area
		g.setColor(Color.white);
		g.drawRect(0, 0, 800, 600);
		

		//draw enemies
		//Temporary
		g.setColor(Color.magenta);
		for(Enemy enemy : level.enemies){
			g.draw(enemy.getCircle());
		}

		//draw shots
		g.setColor(Color.blue);
		for(Shot shot : level.shots){
			g.draw(shot.getCircle());
			g.drawString(""+shot.timeRemain, shot.getCircle().getMinX(), shot.getCircle().getMinY());
		}

		//draw player
		g.setColor(Color.green);
		g.fill(level.pship.getCircle());
		
		//draw direction
		g.setColor(Color.red);
		Double angle = (double)level.pship.getFaceAllignment();
		float dirX1 = level.pship.getCircle().getCenterX();
		float dirY1 = level.pship.getCircle().getCenterY();
		float dirX2 = dirX1+(float)(20*Math.cos(angle));
		float dirY2 = dirY1+(float)(20*Math.sin(angle));
		g.drawLine(dirX1, dirY1, dirX2, dirY2);


		//draw effects

		//draw GUI
		//set translation back
		g.translate(-getTranslateX(), -getTranslateY());
		g.setColor(Color.green);
		g.drawString("face "+level.pship.getFaceAllignment(), 20, 20);
		g.drawString("Score: "+level.pship.getScores(), 900, 700);
		//g.drawString("mouseX "+mouseX, 300, 20);
		//g.drawString("mouseY "+mouseY, 300, 40);
		g.setColor(Color.black);
	}

	public void updateState(GameContainer container, int delta){
		Input input = container.getInput();
		//check mouse controls
		

		//move player
		level.pship.updatePosition(delta, level);
		
		//Update player direction
		float x = input.getMouseX() - width/2;
		float y = input.getMouseY() - height/2;
		level.pship.setFaceAllignment((float)GeomWarUtil.findAngle(x, y));
		
		/*//Create shots
		if(level.shots.size() > 20){ 
			level.shots.clear(); //Too much lag currently. The shots never disappear.
		}*/
		
		level.pship.updateShotTime(delta);
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			for(Shot shot : level.pship.createShot()){
				level.shots.add(shot);
			}
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			level.pship.wantUseBomb();
		}
		
		//move enemies
		for(Enemy enemy : level.enemies){
			enemy.updatePosition(delta, level);
			enemy.checkForCollision(level);
		}

		//move shots
		for(Shot shot : level.shots){
			shot.updatePosition(delta, level);
			if(shot.reduceLiveTime(0.02f)<0){
			  shot.setDestroyed();
			}
		}
		
		//remove destroyed shots
		for (int i = 0; i<level.shots.size();i++){
		  if (level.shots.get(i).getDestroyed()){
		    level.shots.remove(i);
		    i--;
		  }
		}

		//calculate hits

		//kill enemies
		
		//remove emenies from level
		for(int i = 0; i<level.enemies.size();i++) {
		  if (level.enemies.get(i).isDead()) {
		    level.enemies.remove(i);
		    i--;
		  }
		}
		

		//other

	}

	public void startState(GameContainer container) throws SlickException {

		level = new Level();
		level.load();

		ConfigurableEmitter.setRelativePath("data");

	}
	
	public void toggleSound() {
	  soundEnabled = !soundEnabled;
	  if (soundEnabled){
	    music.loop();
	  } else {
	    music.stop();
	  }
	}

	//when key pressed
	@Override
	public void keyPressed(int key, char c)
	{
		super.keyPressed(key, c);
		if (key == Input.KEY_R)
		{
			level = new Level();
		}
		if(key == Input.KEY_W)
		{
			level.pship.wantDirectionUP(true);
		}
		if(key == Input.KEY_A)
		{
			level.pship.wantDirectionLEFT(true);
		}
		if(key == Input.KEY_D)
		{
			level.pship.wantDirectionRIGHT(true);
		}
		if(key == Input.KEY_S)
		{
			level.pship.wantDirectionDOWN(true);
		}
		if (key == Input.KEY_ESCAPE)
		{
			System.exit(0);
		}
	}

	//when key released
	@Override
	public void keyReleased(int key, char c)
	{
		super.keyReleased(key, c);
		if(key == Input.KEY_W)
		{
			level.pship.wantDirectionUP(false);
		}
		if(key == Input.KEY_A)
		{
			level.pship.wantDirectionLEFT(false);
		}
		if(key == Input.KEY_D)
		{
			level.pship.wantDirectionRIGHT(false);
		}
		if(key == Input.KEY_S)
		{
			level.pship.wantDirectionDOWN(false);
		}
		if(key == Input.KEY_M)
    {
      toggleSound();
    }
		if (key == Input.KEY_ESCAPE)
		{
			System.exit(0);
		}   
	}
	
	/*//Still useful? Why we have mouseX and mouseY.
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseX = newx-level.pship.getCircle().getCenterX();
		mouseY = newy-level.pship.getCircle().getCenterY();
	}*/

	
	
	/**
	 * The translation that we operate on the graphics object on the x axis
	 * @return The x value of the translation
	 */
	private float getTranslateX(){
		return -level.pship.getCircle().getCenterX() + width/2;
	}

	private float getTranslateY(){
		return -level.pship.getCircle().getCenterY() + height/2;
	}

}
