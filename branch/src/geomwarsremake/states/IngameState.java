package geomwarsremake.states;

import java.util.ArrayList;

import geomwarsremake.GeomWarsRemake;
import geomwarsremake.objects.*;

import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import other.Firework;
import other.ImageCreation;

import util.GeomWarUtil;


public class IngameState extends GwarState {
	static Logger logger = Logger.getLogger(IngameState.class);
	private Level level;
	/** GameContainer width */
	float width;
	/** GameContainer height */
	float height;
	//Sound variable
	private boolean soundEnabled = false;
	private Music music;
	private Sound sHit;
	//Image
	public Image shipImage;
	public Image greenImage;
	public Image shot;
	public Image pinkImage;
	public Image spinningImage;
	
	public ArrayList<Firework> fireworks = new ArrayList<Firework>();

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
		
		//Load image
		//Load image
		ImageCreation ic = new ImageCreation();
		pinkImage = ic.createPinkImage();
		greenImage = ic.createGreenImage();
		spinningImage = ic.createSpinningImage();
		
		shipImage = new Image("resources/images/PlayerShip.png");
		shot = new Image("resources/images/Shot.png");
		
		music = new Music("resources/sounds/drum.ogg");
		sHit = new Sound("resources/sounds/pop2.ogg");
	}
	
	public void playHit() {
	  if (soundEnabled){
	    sHit.play();
	  }
	}

	@Override
	public void renderState(GameContainer c, Graphics g) {
		//draw background
		g.setColor(Color.black);
		
		//Position the map
		g.translate(getTranslateX(), getTranslateY());
		
		//Draw map area
		g.setColor(Color.white);
		float w = g.getLineWidth();
		int size = 10;
		g.setLineWidth(size);
		g.drawRect(-size/2, -size/2, level.mapWidth+size, level.mapHeight+size);
		g.setLineWidth(w);
		drawGrid(g);

		//Draw bombs
		for(Bomb bomb : level.bombs){
			bomb.draw(g);
		}
		
		//draw enemies
		for(Enemy enemy : level.enemies){
			enemy.draw(g);
		}

		//draw shots
		g.setColor(Color.blue);
		for(Shot shot : level.shots){
			shot.draw(g);
		}

		//draw player
		g.setColor(Color.green);
		level.pship.draw(g);
		
		//draw direction
		g.setColor(Color.red);
		Double angle = (double)level.pship.getFaceAllignment();
		float dirX1 = level.pship.getCircle().getCenterX();
		float dirY1 = level.pship.getCircle().getCenterY();
		float dirX2 = dirX1+(float)(20*Math.cos(angle));
		float dirY2 = dirY1+(float)(20*Math.sin(angle));
		g.drawLine(dirX1, dirY1, dirX2, dirY2);

		//draw effects
		for(int i=0; i<fireworks.size(); i++){
			fireworks.get(i).render(g);
		}

		//set translation back
		g.translate(-getTranslateX(), -getTranslateY());
		
		//draw GUI
		g.setColor(Color.green);
		g.drawString("face "+level.pship.getFaceAllignment(), 20, 20);
		g.drawString("life "+level.pship.getNumberOfLife(), 200, 20);
		g.drawString("bomb "+level.pship.getNumberOfBomb(), 300, 20);
		g.drawString("Score: "+level.pship.getScores(), 900, 700);
		g.drawString("Time : " + level.totalTime/1000, 400, 20);
		g.setColor(Color.black);
	}
	
	private void drawGrid(Graphics g){
		float w = g.getLineWidth();
		float width = level.mapWidth;
		float height = level.mapHeight;
		g.setColor(Color.blue);
		g.setLineWidth(1);
		for(int i=0; i<width/40; i++){
			g.drawLine(i*40, 0, i*40, height);
		}
		for(int j=0; j<height/40; j++){
			g.drawLine(0, j*40, width, j*40);
		}
		g.setLineWidth(w);
	}
	
	private void drawStar(Graphics g){
		//How should I do that???
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
		level.pship.updateDrawingDirection(delta);
		

		//move player
		level.pship.updatePosition(delta);
		level.pship.collisionMapArea();
		
		//Update shooting direction
		float x = input.getMouseX() - width/2;
		float y = input.getMouseY() - height/2;
		level.pship.setFaceAllignment((float)GeomWarUtil.findAngle(x, y));
		
		//Create shot
		level.pship.updateShotTime(delta);
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			for(Shot shot : level.pship.createShot()){
				level.addObject(shot);
			}
		}
		
		//Use bomb
		level.pship.updateBombTime(delta);
		if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			level.pship.wantUseBomb();
		}
		//Update bomb
		for(Bomb bomb : level.bombs){
			bomb.update(delta);
		}
		
		//Create enemies
		level.updateGenerator(delta);
		
		//move enemies and check for collision
		for(Enemy enemy : level.enemies){
			enemy.update(delta);
		}

		//move shots and check for collision
		for(Shot shot : level.shots){
			shot.updatePosition(delta);
			shot.collisionMapArea();
			shot.checkForCollision();
		}
		
		//Remove dead objects
		level.removeDeadObjects();
		level.addEnemies();
		
		//other
		for(int i=0; i<fireworks.size(); i++){
			fireworks.get(i).update(delta);
			if(fireworks.get(i).getTime() > fireworks.get(i).TOTAL_TIME){
				fireworks.remove(i);
				i--;
			}
		}

	}

	public void startState(GameContainer container) throws SlickException {

		level = new Level(this);
		level.load();

		ConfigurableEmitter.setRelativePath("data");

	}
	
	/**
	 * 
	 * @param disableSound - if false, always disable sound
	 */
	public void toggleSound(boolean disableSound) {
	  soundEnabled = !soundEnabled;
	  if (disableSound == false) {
	    soundEnabled = false;
	  }
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
			level = new Level(this);
			level.load();
		}
		if (key == Input.KEY_ESCAPE)
		{
			toggleSound(false);
			setStarted(false);
			//try {
				//greenImage.destroy();
				//Graphics g = pinkImage.getGraphics();
				//g.destroy();
			//} catch (SlickException e) {
				//e.printStackTrace();
			//}
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
			toggleSound(true);
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
