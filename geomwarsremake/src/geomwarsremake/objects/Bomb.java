package geomwarsremake.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class Bomb {
	
	private final int MAX_OUTER_RADIUS = 800;

	private final float INCREASE_RADIUS = 0.5f;
	private final float DIF_RADIUS = 300;
	
	private float outerRadius;
	private float innerRadius;
	private float posX;
	private float posY;
	
	private boolean dead = false;
	
	public Bomb(Level level){
		posX = level.pship.getCircle().getCenterX();
		posY = level.pship.getCircle().getCenterY();
		System.out.println("HAHAHA3");
		outerRadius = level.pship.getCircle().getRadius();
		System.out.println("Bonjour2");
		innerRadius = 0;
	}
	
	public boolean isDead(){
		return dead;
	}
	public float getX(){
		return posX;
	}
	public float getY(){
		return posY;
	}
	public float getOuterRadius(){
		return outerRadius;
	}
	public float getInnerRadius(){
		return innerRadius;
	}
	
	public void update(int deltaTime){
		if(outerRadius < MAX_OUTER_RADIUS){
			outerRadius += deltaTime*INCREASE_RADIUS;
			if(outerRadius-innerRadius > DIF_RADIUS){
				innerRadius = outerRadius - DIF_RADIUS;
			}
		}else{
			dead = true;
		}
	}
	
	
	
	public void draw(Graphics g){
		g.setColor(Color.white);
		g.setLineWidth(5);
		g.draw(new Circle(posX, posY, outerRadius));
		g.setLineWidth(1);
	}

}
