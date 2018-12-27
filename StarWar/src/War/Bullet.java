package War;

import javax.swing.ImageIcon;

public class Bullet extends Airplane {
	
	private int dy = 1;
	private int bulletFly = 0;

	public Bullet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bullet(int x, int y, int w, int h, ImageIcon im) {
		super(x, y, w, h, im);
		super.setSeeable(true);
		// TODO Auto-generated constructor stub
	}
	
	public void setDY(int dy) {
		this.dy = dy;
	}
	
	public void setBulletFly(int bb) {
		bulletFly = bb;
	}
	
	public int getDY() {
		return dy;
	}
	
	public int getBulletFly() {
		return bulletFly;
	}
	
	public void move() {
		super.setY(super.getY() - getDY());
		if(getY() <= - getWidth()) {
			setX(-3000);
			setY(-3000);
		} else if(getY() >= Panel.FRAME) {
			setX(3000);
			setY(3000);
		}
	}

}
