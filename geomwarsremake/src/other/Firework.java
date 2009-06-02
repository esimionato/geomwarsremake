package other;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Firework {
	
	static final Color [] colors = {Color.blue, Color.pink, Color.green};
	
	public static final int TOTAL_TIME = 1200;
	final int NUMBER_OF_LINE = 30;
	final double MAX_SPEED = 0.7;
	
	int timeElapsed = 0;
	int posX;
	int posY;
	
	Color color;
	
	ArrayList<FireworkLine> lines = new ArrayList<FireworkLine>();
	
	public Firework(int x, int y){
		color = colors[(int)(Math.random()*3)];
		posX = x;
		posY = y;
		for(int i=0; i<NUMBER_OF_LINE; i++){
			double angle = Math.random() * (2*Math.PI);
			double speed = ((Math.random()+0.5)/1.5) * MAX_SPEED;
			lines.add(new FireworkLine(speed, angle));
		}
	}
	
	public void render(Graphics g){
		g.setColor(color);
		float w = g.getLineWidth();
		g.setLineWidth(2.5f);
		for(int i=0; i<lines.size(); i++){
			lines.get(i).render(g, posX, posY);
		}
		g.setLineWidth(w);
	}
	
	public void update(int deltaTime){
		timeElapsed += deltaTime;
		for(int i=0; i<lines.size(); i++){
			lines.get(i).update(deltaTime);
		}
	}
	
	public int getTime(){
		return timeElapsed;
	}

}
