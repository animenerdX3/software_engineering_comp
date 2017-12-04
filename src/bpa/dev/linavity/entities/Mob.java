package bpa.dev.linavity.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.assets.Collision;
import bpa.dev.linavity.physics.Gravity;

public class Mob{

	private Gravity gravity = new Gravity();
	private int x, y;
	private Image mobImage = null;
	private Collision collide;
	private float ymo; // Momentum in the y direction
	
	



	public Mob() 
			throws SlickException{
		setMobImage(new Image("data/player_0.png"));
		ymo = 0;
		this.collide = new Collision();
	}

	
	public void updatePos() {
		//Gravity affecting the player
		this.setY((int) (this.getY() + gravity.getGravity() - ymo));
		if(ymo >= 0)
			ymo -= .7;
	}
	
	/**
	 * @return the x
	 */
	public  int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public  void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public  int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public  void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the mobImage
	 */
	public  Image getMobImage() {
		return mobImage;
	}

	/**
	 * @param mobImage the mobImage to set
	 */
	public void setMobImage(Image mobImage) {
		this.mobImage = mobImage;
	}
	
	public float getYmo() {
		return ymo;
	}


	public void setYmo(float ymo) {
		this.ymo = ymo;
	}
	
}