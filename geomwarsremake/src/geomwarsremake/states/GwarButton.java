package geomwarsremake.states;

import org.newdawn.slick.Image;

public class GwarButton {
  public Image image;
  private String action = "none";
  float posX, posY = 0;
  
  public GwarButton(Image img, String action){
    if (img==null) {
      return;
    }
    this.image = img;
    this.action = action;
  }
  
  
  
  public String getAction(){
    return action;
  }
  
  /**
   * 
   * @param mx - mouse x coord
   * @param my - mouse y coord
   * @return butonAction if mouse inside button
   */
  public String collide(float mx, float my) {
    //if mouse click inside button
    if ((mx<posX)||(mx>posX+image.getWidth())||(my<posY)||(my>posY+image.getHeight())){
      return "none";
    } else{
      return action;
    }
    
  }

}
