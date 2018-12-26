package War;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Airplane {
	
	private ImageIcon myimage;
	private boolean seeable = false;
	private int myX;
	private int myY;
	private int width;
	private int height;
	
	// constructor
	public Airplane() {
		myX = 200;
		myY = 200;
		width = 20;
		height = 20;
	}
	
	public Airplane(int x, int y, int w, int h, ImageIcon im) {
		myX = x;
		myY = y;
		width = w;
		height = h;
		myimage = im;
	}
	
	// setters
	public void setX(int x) {
		myX = x;
	}
	
	public void setY(int y) {
		myY = y;
	}
	
	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public void setImage(ImageIcon image) {
		myimage = image;
	}
	
	// getters
	public int getX() {
		return myX;
	}
	
	public int getY() {
		return myY;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public ImageIcon getImage() {
		return myimage;
	}
	
	public boolean getSeeable() {
		return seeable;
	}
	
	public void setSeeable(boolean s) {
		seeable = s;
	}
	
	public void draw(Graphics myBuffer) {
		if(!seeable)
			myBuffer.drawImage(myimage.getImage(), getX(), getY(), getWidth(), getHeight(), null);
		else 
			setX(8000);
	}
}
