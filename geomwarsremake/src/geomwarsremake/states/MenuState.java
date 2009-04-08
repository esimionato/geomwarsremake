package geomwarsremake.states;

import geomwarsremake.GeomWarsRemake;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;


public class MenuState extends GwarState implements ComponentListener {
  
  public static final int id = 1;

  public MenuState(GeomWarsRemake ctx) {
    super(ctx);
  }

  public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    super.enter(container, game);
  }
  
  @Override
  public int getID() {
    // TODO Auto-generated method stub
    return 0;
  }
  
  public void keyPressed(int key, char c) {
    super.keyPressed(key, c);
  }

  @Override
  public void componentActivated(AbstractComponent arg0) {
    // TODO Auto-generated method stub
    
  }


  @Override
  public void updateState(GameContainer c, int delta) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void initState(GameContainer c) throws SlickException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void renderState(GameContainer c, Graphics g) {
    g.setColor(Color.red);
    g.fillRect(100, 100, 100, 100);
    
  }

}
