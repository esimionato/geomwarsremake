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
    
    
    //draw enemies
    
    //draw shots
    
      //draw player
    g.setColor(Color.green);
    g.fillOval(level.pship.getX(), level.pship.getY(), 10, 10);
    //draw direction
    g.setColor(Color.red);
    Double angle = (double)level.pship.getFaceAllignment();
    float dirX1 = level.pship.getX()+5;
    float dirY1 = level.pship.getY()+5;
    float dirX2 = level.pship.getX()+5+(float)(10*Math.cos(Math.toRadians(angle)));
    float dirY2 = level.pship.getY()+5+(float)(10*Math.sin(Math.toRadians(angle)));
    g.drawLine(dirX1, dirY1, dirX2, dirY2);
    
    
    //draw effects
    
    //draw GUI
    g.setColor(Color.green);
    g.drawString("face "+level.pship.getFaceAllignment(), 20, 20);
    //g.drawString("mouseX "+mouseX, 300, 20);
    //g.drawString("mouseY "+mouseY, 300, 40);
    g.setColor(Color.black);
  }
  
  public void mouseMoved(int oldx, int oldy, int newx, int newy) {
    if (newx == 0)
      newx = 1;
    if (newy == 0)
      newy = 1;
    mouseX = newx-level.pship.getX();
    
    mouseY = newy-level.pship.getY();
    level.pship.setFaceAllignment((float)(Math.toDegrees(Math.atan(mouseY/mouseX))));
    if (mouseX<0)
      level.pship.setFaceAllignment(level.pship.getFaceAllignment()+180);
  }

  public void updateState(GameContainer container, int delta){
    
    //check mouse controls
    
    
    //move player
    level.pship.setX(level.pship.getX()+level.pship.getSpeed()*level.pship.getDirectionX());
    level.pship.setY(level.pship.getY()+level.pship.getSpeed()*level.pship.getDirectionY());
    //move enemies
    
    //move shots
    
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
  
  //whan key released
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
    
  }


}
