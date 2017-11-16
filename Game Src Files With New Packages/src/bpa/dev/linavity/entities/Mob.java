package bpa.dev.linavity.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.physics.Gravity;

public class Mob{

	private int x, y;
	private Image mobImage = null;
	
	public Mob() 
			throws SlickException{
		setMobImage(new Image("data/alien.png"));
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
		this.y = y + Gravity.gravity;
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
	
}
