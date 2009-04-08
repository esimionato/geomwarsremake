package geomwarsremake.states;

import geomwarsremake.GeomWarsRemake;

import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;



public abstract class GwarState extends BasicGameState {
  static Logger logger = Logger.getLogger(GwarState.class);
  protected GeomWarsRemake context;
  private boolean started=false;
  private Color bg = Color.black;
  public static final int id = 0;
  
  public GwarState(GeomWarsRemake ctx) {
    this.context = ctx;
  }
  
  public boolean isStarted() {
    return started;
  }
  
  public void setStarted(boolean t) {
    started = t;
  }
  
  public void setBackground(Color c) {
    bg = c;
  }
  
  public final void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    renderState(container, g);
  }
  
  public final void init(GameContainer container, StateBasedGame game) throws SlickException {
    initState(container);
  }
  
  public final void update(GameContainer container, StateBasedGame game, int delta) {
    updateState(container, delta);
  }
  
  public abstract void initState(GameContainer c) throws SlickException;
  public abstract void renderState(GameContainer c, Graphics graphics);
  public abstract void updateState(GameContainer c, int delta);
  
  public Color getBackground() {
    return bg;
  }
  
  public void keyPressed(int key, char c) {
    getContext().globalKeyPressed(key, c);
  }
  
  
  public boolean isKeyDown(Input in, int[] controls) {
    for (int i : controls) {
      if (in.isKeyDown(i))
        return true;  
    }
    return false;
  }
  
  public Transition getExitTransition() {
    return new FadeOutTransition(Color.white);
  }
  
  public Transition getEnterTransition() {
    return new FadeInTransition(Color.white);
  }
  
  public void startState(GameContainer container) throws SlickException {
  }
  
  public GeomWarsRemake getContext() {
    return context;
  }
  
  public int getID() {
    return id;
  }

}
