package geomwarsremake.states;

import geomwarsremake.GeomWarsRemake;

import org.newdawn.slick.state.BasicGameState;


public abstract class GwarState extends BasicGameState {
  protected GeomWarsRemake context;
  private boolean started=false;
  
  public GwarState(GeomWarsRemake ctx) {
    this.context = ctx;
  }

}
