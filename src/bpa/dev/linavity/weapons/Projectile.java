package bpa.dev.linavity.weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.utils.Utils;

public class Projectile {

	//Player object needed
	Utils util;
	
	//Picture for projectile
	private Image projectileImage;
	//Amount of damage dealt by the projectile
	private int damage;
	//How fast the projectile can be thrown
	private int speed;
	
	private float x, y;
	
	//Starting projectile
	public Projectile(Utils util) throws SlickException {
		this.util = util;
		this.projectileImage = new Image("res/projectile.png");
		this.damage = 10;
		this.speed = 10;
		this.x = util.getPlayer().getX();
		this.y = util.getPlayer().getY();
	}
	
	//Non-Default Projectile
	public Projectile(Utils util, String imageDirectory, int damage, int speed) throws SlickException {
		this.util = util;
		this.projectileImage = new Image(imageDirectory);
		this.damage = damage;
		this.speed = speed;
		this.x = util.getPlayer().getX();
		this.y = util.getPlayer().getY();
	}
	
	//Move Projectile Across the Screen
	public void updatePos() {
		this.x = this.x + this.speed;
	}
	
	/* GETTERS */
	
	public Image getProjectileImage() {
		return projectileImage;
	}

	public int getDamage() {
		return damage;
	}
	
	public int getSpeed() {
		return damage;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	/* SETTERS */
	
	public void setProjectileImage(Image projectileImage) {
		this.projectileImage = projectileImage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}//end of class
