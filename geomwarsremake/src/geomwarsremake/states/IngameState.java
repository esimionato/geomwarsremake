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
	/** GameContainer width */
	float width;
	/** GameContainer height */
	float height;
	private boolean soundEnabled = false;
	private Music music;

	public IngameState(GeomWarsRemake ctx, int stateID) {
		super(ctx, stateID);
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
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
		g.drawRect(0, 0, level.mapWidth, level.mapHeight);
		

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
			//g.drawString(""+shot.timeRemain, shot.getCircle().getMaxX(), shot.getCircle().getMinY());
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

		//set translation back
		g.translate(-getTranslateX(), -getTranslateY());
		
		//draw GUI
		g.setColor(Color.green);
		g.drawString("face "+level.pship.getFaceAllignment(), 20, 20);
		g.drawString("life "+level.pship.getNumberOfLife(), 200, 20);
		g.drawString("bomb "+level.pship.getNumberOfBomb(), 300, 20);
		g.drawString("Score: "+level.pship.getScores(), 900, 700);
		//g.drawString("mouseX "+mouseX, 300, 20);
		//g.drawString("mouseY "+mouseY, 300, 40);
		g.setColor(Color.black);
	}

	public void updateState(GameContainer container, int delta){
		//Check to see if the ship is alive
		if(!level.pship.isAlive()){
			if(level.pship.getCanRevive()){
				level.resetEnemies(); //The enemies list will only have elements to clear 1 time
				level.pship.updateRespawnTime(delta);
				return;
			}else{
				//GameOver
			}
		}
		
		Input input = container.getInput();
		//check mouse controls
		
		//Update ship direction movement
		level.pship.setDirection(input);
		

		//move player
		level.pship.updatePosition(delta, level);
		level.pship.collisionMapArea(level);
		
		//Update shooting direction
		float x = input.getMouseX() - width/2;
		float y = input.getMouseY() - height/2;
		level.pship.setFaceAllignment((float)GeomWarUtil.findAngle(x, y));
		
		//Create shot
		level.pship.updateShotTime(delta);
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			for(Shot shot : level.pship.createShot()){
				level.shots.add(shot);
			}
		}
		
		//Use bomb
		if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			level.pship.wantUseBomb();
		}
		
		//move enemies and check for collision
		for(Enemy enemy : level.enemies){
			enemy.updatePosition(delta, level);
			enemy.collisionMapArea(level);
			enemy.checkForCollision(level);
		}

		//move shots and check for collision
		for(Shot shot : level.shots){
			shot.updatePosition(delta, level);
			shot.collisionMapArea(level);
			shot.checkForCollision(level);
		}
		
		//Remove dead objects
		level.removeDeadObjects();
		
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
			level.load();
		}
		if (key == Input.KEY_ESCAPE)
		{
			setStarted(false);
			getContext().enterState(GeomWarsRemake.MENU_STATE);
		}
	}

	//when key released
	@Override
	public void keyReleased(int key, char c)
	{
		super.keyReleased(key, c);
		if(key == Input.KEY_M)
		{
			toggleSound();
		}
	}
	
	
	/**
	 * The translation that we operate on the graphics object on the x axis
	 * @return The x value of the translation
	 */
	private float getTranslateX(){
		float shipX = level.pship.getCircle().getCenterX();
		return -shipX + width/2;
	}

	private float getTranslateY(){
		float shipY = level.pship.getCircle().getCenterY();
		return -shipY + height/2;
	}

}
