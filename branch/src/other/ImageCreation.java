package other;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageCreation {
	
	public Image createGreenImage(){
		Image img = null;
		float s = 2f;
		int size = 30;

		try {
			System.out.println("Coucou");
			img = new Image((int)(size+s), (int)(size+s));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
		Graphics g;
		try {
			System.out.println("Hello");
			g = img.getGraphics();
			g.clear();

			g.setColor(Color.green);
			g.setLineWidth(2*s);
			//Top line
			g.drawLine(s, s, size, s);
			//Left line
			g.drawLine(s, s, s, size);
			//Right line
			g.drawLine(size, s, size , size);
			//Bottom line
			g.drawLine(s, size, size, size);
			//InternalSquare
			g.drawLine(s, (size+s)/2, (size+s)/2, s);
			g.drawLine((size+s)/2, s, size, (size+s)/2);
			g.drawLine(size, (size+s)/2, (size+s)/2, size);
			g.drawLine((size+s)/2, size, s, (size+s)/2);
			g.flush();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public Image createPinkImage(){
		Image img = null;
		float s = 2.5f;
		int size = 30;

		try {
			System.out.println("Coucou");
			img = new Image((int)(size+s), (int)(size+s));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
		Graphics g;
		try {
			System.out.println("Hello");
			g = img.getGraphics();
			g.clear();
			
			g.setColor(new Color(234, 83, 177));
			g.setLineWidth(2*s);
			//Top line
			//Top line
			g.drawLine(s, s, size, s);
			//Left line
			g.drawLine(s, s, s, size);
			//Right line
			g.drawLine(size, s, size , size);
			//Bottom line
			g.drawLine(s, size, size, size);
			//X in middle
			g.drawLine(s, s, size, size);
			g.drawLine(s, size, size, s);
			g.flush();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public Image createSpinningImage(){
		Image img = null;
		float s = 2;
		float size = 40;

		try {
			System.out.println("Coucou");
			img = new Image((int)(size+s), (int)(size+s));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
		Graphics g;
		try {
			System.out.println("Hello");
			g = img.getGraphics();
			g.clear();
			
			g.setColor(new Color(189, 112, 205));
			g.setLineWidth(5.5f);
			//The + in the middle
			g.drawLine((size+s)/2, s, (size+s)/2, size);
			g.drawLine(s, (size+s)/2, size, (size+s)/2);
			//The X
			g.drawLine((size+s)/4, (size+s)/4, (size+s)/4*3, (size+s)/4*3);
			g.drawLine((size+s)/4*3, (size+s)/4, (size+s)/4, (size+s)/4*3);
			//Top triangle
			g.drawLine((size+s)/2, s, (size+s)/4, (size+s)/4);
			//Bottom triangle
			g.drawLine((size+s)/2, size, (size+s)/4*3, (size+s)/4*3);
			//Right triangle
			g.drawLine((size+s)/4*3, (size+s)/4, size, (size+s)/2);
			//Left triangle
			g.drawLine(s, (size+s)/2, (size+s)/4, (size+s)/4*3);
			//The circle in the middle
			g.setColor(Color.white);
			float r = 2.5f;
			g.fillOval((size+s)/2 - r, (size+s)/2 - r, 2*r, 2*r);
			//Top circle
			r = 1.5f;
			g.fillOval((size+s)/2 - r, s-r, 2*r, 2*r);
			//Bottom circle
			g.fillOval((size+s)/2 - r, size - r, 2*r, 2*r);
			//Left circle
			g.fillOval(s-r, (size+s)/2 - r, 2*r, 2*r);
			//Right circle
			g.fillOval(size - r, (size+s)/2 - r, 2*r, 2*r);
			//4 small circle on top of triangle
			r = 1;
			g.fillOval((size+s)/4 - r, (size+s)/4 - r, 2*r, 2*r);
			g.fillOval((size+s)/4*3 - r, (size+s)/4 - r, 2*r, 2*r);
			g.fillOval((size+s)/4*3 - r, (size+s)/4*3 - r, 2*r, 2*r);
			g.fillOval((size+s)/4 - r, (size+s)/4*3 - r, 2*r, 2*r);
			g.flush();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return img;
	}

}
