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
    g.fillOval(level.pship.getX()+200, level.pship.getY()+200, 10, 10);
    
    
    //draw effects
    
    //draw GUI
    g.setColor(Color.black);
  }

  public void updateState(GameContainer container, int delta){
    
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
      level.pship.setDirectionY(-1);
    }
    if(key == Input.KEY_S)
    {
      level.pship.setDirectionY(1);
    }
    if(key == Input.KEY_D)
    {
      level.pship.setDirectionX(1);
    }
    if(key == Input.KEY_A)
    {
      level.pship.setDirectionX(-1);
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
      level.pship.setDirectionY(0);
    }
    if(key == Input.KEY_A)
    {
      level.pship.setDirectionX(0);
    }
    if(key == Input.KEY_D)
    {
      level.pship.setDirectionX(0);
    }
    if(key == Input.KEY_S)
      {
        level.pship.setDirectionY(0);
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
