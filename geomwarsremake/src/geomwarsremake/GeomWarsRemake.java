package geomwarsremake;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import geomwarsremake.states.*;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;


public class GeomWarsRemake extends StateBasedGame{
  static Logger logger = Logger.getLogger(GeomWarsRemake.class);
  
//  private LoadingState loading;
//  private StandardIngameState std_ingame;
  private MenuState menu;
//  private ExitState exit;
//  private HelpState help;
  private Music music;
  

  public GeomWarsRemake(String name) {
    super(name);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void initStatesList(GameContainer arg0) throws SlickException {
    // TODO Auto-generated method stub

  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    //boolean debug = (args.length>=1 && args[0].equals("-d"));
    boolean debug =true; //only during development
    DOMConfigurator.configure("log4j.xml");
    logger.debug("Here is some DEBUG");
    logger.info("Here is some INFO");
    logger.warn("Here is some WARN");
    logger.error("Here is some ERROR");
    logger.fatal("Here is some FATAL");

    
    try {
      //An applet would use the AppletGameContainer.
      GeomWarsRemake g = new GeomWarsRemake("GeomWarsRemake");
      AppGameContainer container = new AppGameContainer(g);
      //TODO: xml properties: display size
      container.setDisplayMode(800, 600, true);
      try {
      container.start();
      } catch (SlickException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

}
