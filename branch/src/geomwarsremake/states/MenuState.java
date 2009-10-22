package geomwarsremake.states;

import java.util.ArrayList;

import geomwarsremake.GeomWarsRemake;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;


public class MenuState extends GwarState implements ComponentListener {
  
	private SpriteSheet buttonsSS;
	private ArrayList <GwarButton> buttonList = new ArrayList<GwarButton>(); 

	public MenuState(GeomWarsRemake ctx, int stateID) {
		super(ctx, stateID);
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
	}

	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if (key == Input.KEY_ESCAPE)
		{
			System.exit(0);
		}
	}

	@Override
	public void componentActivated(AbstractComponent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void updateState(GameContainer c, int delta) {
		Input input = c.getInput();
		float mx = input.getMouseX();
		float my = input.getMouseY();	
		
		for(GwarButton b : buttonList){
			b.collide(mx, my);
			b.updateScale(delta);
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				String bact = b.getAction();
				if(!bact.equals("none")){
					if (bact.equals("exit")) {
						System.exit(0);
					} else if (bact.equals("newGame")) {
						getContext().enterState(GeomWarsRemake.INGAME_STATE,
								this.getExitTransition(),
								this.getEnterTransition()); 
					} else if (bact.equals("options")) {
						//Nothing for the moment
					}
					logger.debug("main menu MRight. bact:"+bact);
				}
			}
		}

	}

	@Override
	public void initState(GameContainer c) throws SlickException {
		//load buttons
		buttonsSS = new SpriteSheet(new Image("/resources/menu_temp.png"), 150,64);
		GwarButton newGameButton = new GwarButton(buttonsSS.getSprite(0, 0),"newGame");
		newGameButton.posX = 200;
		newGameButton.posY = 200;
		buttonList.add(newGameButton);
		GwarButton optionsButton = new GwarButton(buttonsSS.getSprite(0, 2),"options");
		optionsButton.posX = 200;
		optionsButton.posY = 300;
		buttonList.add(optionsButton);
		GwarButton exitButton = new GwarButton(buttonsSS.getSprite(0, 1),"exit");
		exitButton.posX = 200;
		exitButton.posY = 400;
		buttonList.add(exitButton);

	}


	@Override
	public void renderState(GameContainer c, Graphics g) {

		//draw grid
		g.setColor(Color.green);
		for (int i=0; i<2000;i+=100) {
			g.drawLine(0, i, 2000, i);
			g.drawLine(i, 0, i, 2000);
		}

		//draw buttons
		for(GwarButton b : buttonList) {
			//g.drawImage(b.image, b.posX, b.posY);
			b.image.draw(b.posX, b.posY, b.scale);
		}

	}

}
