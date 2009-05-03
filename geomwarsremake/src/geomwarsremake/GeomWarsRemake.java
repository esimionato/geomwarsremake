package geomwarsremake;

import geomwarsremake.states.*;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;
import org.newdawn.slick.util.Log;


public class GeomWarsRemake extends StateBasedGame{
	static Logger logger = Logger.getLogger(GeomWarsRemake.class);

	public static final int MENU_STATE = 0;
	public static final int INGAME_STATE = 1;

	//  private LoadingState loading;
	private IngameState ingame;
	private MenuState menu;
	//  private ExitState exit;
	//  private HelpState help;
	private Music music;

	private boolean showFPS = true;


	public GeomWarsRemake(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		container.setShowFPS(showFPS);

		container.setSoundOn(false);
		container.setMusicOn(false);


		/* //add loading state and enter it
    	loading = new LoadingState(this);
    	addState(loading);
    	loading.setStarted(true);*/

		//add menu state
		menu = new MenuState(this, MENU_STATE);
		addState(menu);
		//menu.enter(container, this);
		//menu.startState(container);
		enterState(MENU_STATE);

		//add ingame state
		ingame = new IngameState(this, INGAME_STATE);
		addState(ingame);
		//ingame.enter(container, this);
		//ingame.startState(container);

		/*//add help state
    	help = new HelpState(this);
    	addState(help);*/    
	}

	public void globalKeyPressed(int key, char c) {
		if (key == Input.KEY_F) {
			showFPS = !showFPS;
			getContainer().setShowFPS(showFPS);
		}
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
			container.setTargetFrameRate(60);
			//TODO: xml properties: display size
			container.setDisplayMode(1024, 768, true);
			//container.setDisplayMode(800, 600, false); //For reading console...
			try {
				container.start();
			} catch (SlickException e){
				logger.fatal("game start failed",e);
			}
		}
		catch (Exception e) {
			logger.fatal("game creation failed",e);
		}

	}

	public void enterState(int id, Transition t1, Transition t2) {
		GameState t = getState(id);
		if (t instanceof GwarState) {
			GwarState s = ((GwarState)t);
			if (!s.isStarted()) {
				s.setStarted(true);
				try {
					s.startState(getContainer());
				}
				catch (SlickException e) {
					Log.error(e);
				}
			}
		}
		super.enterState(id, t1, t2);
	}

}
