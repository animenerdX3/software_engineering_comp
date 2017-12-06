package bpa.dev.linavity.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.assets.Collision;
import bpa.dev.linavity.utils.Utils;

public class Mob{

	private int x, y;
	private Image mobImage = null;
	private Collision collide;
	private float ymo; // Momentum in the y direction

	//Default constructor
	public Mob() 
			throws SlickException{
		setMobImage(new Image("data/player_0.png"));//By default, our Mob has the player skin
		ymo = 0;
		this.collide = new Collision();
	}
	
	//Non-Default constructor
	public Mob(String textureDirectory) 
			throws SlickException{
		setMobImage(new Image(textureDirectory));//Give our mob a skin that we pre-determined
		ymo = 0;
		this.collide = new Collision();
	}

	/**
	 * Update our gravity based on our y momentum
	 */
	public void updatePos(int gravPower) {
		//Gravity affecting the player
		
		if((gravPower / -1) > 0){ // If the gravity is reversed, flip the decrementation to incrementation
			this.setY((int) (this.getY() + gravPower - ymo));
			if(ymo < 0) 
				ymo += .7;
		}else{ 
			this.setY((int) (this.getY() + gravPower - ymo));
			if(ymo > 0)
				ymo -= .7;
		}
		
	}
	
	/* GETTERS */
	
	/**
	 * @return the x
	 */
	public  int getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public  int getY() {
		return y;
	}

	/**
	 * @return the mobImage
	 */
	public  Image getMobImage() {
		return mobImage;
	}
	
	public float getYmo() {
		return ymo;
	}
	
	/* SETTERS */
	
	/**
	 * @param x the x to set
	 */
	public  void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public  void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @param mobImage the mobImage to set
	 */
	public void setMobImage(Image mobImage) {
		this.mobImage = mobImage;
	}


	public void setYmo(float ymo) {
		this.ymo = ymo;
	}
	
}
