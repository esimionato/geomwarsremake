package geomwarsremake.states;

import geomwarsremake.GeomWarsRemake;
import geomwarsremake.objects.*;

import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;


public class IngameState extends GwarState {
	static Logger logger = Logger.getLogger(IngameState.class);
	private Level level;
	public static final int id = 3;
	float mouseX,mouseY;
	/** GameContainer width */
	float width;
	/** GameContainer height */
	float height;

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

		//draw shots
		g.setColor(Color.blue);
		for(Shot shot : level.shots){
			g.fill(shot.getCircle());
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
		g.setColor(Color.green);
		g.drawString("face "+level.pship.getFaceAllignment(), 20, 20);
		//g.drawString("mouseX "+mouseX, 300, 20);
		//g.drawString("mouseY "+mouseY, 300, 40);
		g.setColor(Color.black);
	}

	//Still useful? Why we have mouseX and mouseY.
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseX = newx-level.pship.getCircle().getCenterX();
		mouseY = newy-level.pship.getCircle().getCenterY();
	}

	public void updateState(GameContainer container, int delta){
		Input input = container.getInput();
		//check mouse controls
		

		//move player
		level.pship.updatePosition(delta);
		
		//Update player direction
		float x = input.getMouseX() - width/2;
		float y = input.getMouseY() - height/2;
		level.pship.setFaceAllignment((float)findAngle(x, y));
		
		//Create shots
		if(level.shots.size() > 20){ 
			level.shots.clear(); //Too much lag currently. The shots never disappear.
		}
		level.pship.updateShotTime(delta);
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			for(Shot shot : level.pship.createShot()){
				level.shots.add(shot);
			}
		}
		
		//move enemies

		//move shots
		for(Shot shot : level.shots){
			shot.updatePosition(delta);
		}

		//calculate hits

		//kill enemies

		//other

	}

	public void startState(GameContainer container) throws SlickException {

		level = new Level();
		level.load();

		ConfigurableEmitter.setRelativePath("data");

	}

	//when key pressed
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
		if (key == Input.KEY_ESCAPE)
		{
			System.exit(0);
		}   
	}

	@Override
	public void initState(GameContainer c) throws SlickException {
		setBackground(new Color(87, 146, 186));  
		c.setMaximumLogicUpdateInterval(50);
		width = c.getWidth();
		height = c.getHeight();
	}

	/**
	 * The angle of the vector (x, y). The angle is between 0 and 2*PI.
	 * @param x The x value of the vector.
	 * @param y The y value of the vector.
	 * @return The angle of the vector (x, y).
	 */
	private double findAngle(double x, double y){
		double theta = 0;
		if(x == 0){
			if(y>0){
				theta = Math.PI/2;
			}else if(y < 0){
				theta = Math.PI*3/2;
			}
		}
		if(x > 0){
			theta = Math.atan(y/Math.abs(x));
		}
		if(x < 0){
			theta = Math.PI - Math.atan(y/Math.abs(x));
		}

		if(theta < 0){
			theta += Math.PI*2;
		}
		return theta;
	}
	
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
