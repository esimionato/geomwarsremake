package other;

import org.newdawn.slick.Graphics;

public class FireworkLine {
	
	final int TOTAL_TIME = 1200;
	final double LENGTH = 30;
	
	double initialSpeed;
	double angle;
	int t = 0;
	
	public FireworkLine(double speed, double angle){
		initialSpeed = speed;
		this.angle = angle;
	}
	
	private double getDistance(){
		return initialSpeed * t - initialSpeed/TOTAL_TIME * t*t/2;
	}
	
	private double getLength(){
		return LENGTH * (TOTAL_TIME-t)/TOTAL_TIME;
	}
	
	public void render(Graphics g, int x, int y){
		int fx = (int) (x + getDistance()*Math.cos(angle));
		int fy = (int) (y + getDistance()*Math.sin(angle));
		int closeX = (int) (x + (getDistance()-getLength())*Math.cos(angle));
		int closeY = (int) (y + (getDistance()-getLength())*Math.sin(angle));
		g.drawLine(fx, fy, closeX, closeY);
	}
	
	public void update(int deltaTime){
		t += deltaTime;
	}

}
