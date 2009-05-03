package geomwarsremake.states;

import org.newdawn.slick.Image;

public class GwarButton {
	
	private final float SCALE_STEP = 0.001f;
	private final float SCALE_MAX = 1.1f;
	
	public Image image;
	private String action = "none";
	float posX, posY = 0;
	float scale = 1;
	/** Indicate if the mouse if over this button */
	private boolean isOver;
	
	public GwarButton(Image img, String action){
		if (img==null) {
			return;
		}
		this.image = img;
		this.action = action;
	}

	public String getAction(){
		if(isOver){
			return action;
		}else{
			return "none";
		}
	}
	
	public void updateScale(int deltaTime){
		if(isOver){
			if(scale < SCALE_MAX){
				scale += SCALE_STEP*deltaTime;
			}
		}else{
			if(scale > 1){
				scale -= SCALE_STEP*deltaTime;
			}
		}
	}

	/**
	 * 
	 * @param mx - mouse x coord
	 * @param my - mouse y coord
	 * @return butonAction if mouse inside button
	 */
	public void collide(float mx, float my) {
		//if mouse click inside button
		if ((mx<posX)||(mx>posX+image.getWidth())||(my<posY)||(my>posY+image.getHeight())){
			isOver = false;
		} else{
			isOver = true;
		}

	}

}
