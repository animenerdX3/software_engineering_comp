package bpa.dev.linavity.weapons;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;

public class Projectile {

	//Picture for projectile
	private Image projectileImage;
	//Amount of damage dealt by the projectile
	private int damage;
	//How fast the projectile can be thrown
	private int speed;
	
	private float x, y;
	private int width, height;
	
	private boolean shotLeft, shotRight;
	
	private Rectangle collisionBox = new Rectangle();
	
	//Starting projectile
	public Projectile(boolean left, boolean right) throws SlickException {
		this.projectileImage = new Image("res/projectile.png");
		this.damage = 10;
		this.speed = 20;
		this.x = Main.util.getPlayer().getX();
		this.y = Main.util.getPlayer().getY() + 12;
		this.width = this.projectileImage.getWidth();
		this.height = this.projectileImage.getHeight();
		this.shotLeft = left;
		this.shotRight = right;
		this.collisionBox = new Rectangle((int)x, (int)y, width, height);
	}
	
	//Non-Default Projectile
	public Projectile(String imageDirectory, int damage, int speed, boolean left, boolean right) throws SlickException {
		this.projectileImage = new Image(imageDirectory);
		this.damage = damage;
		this.speed = speed;
		this.x = Main.util.getPlayer().getX();
		this.y = Main.util.getPlayer().getY();
		this.shotLeft = left;
		this.shotRight = right;
	}
	
	//Move Projectile Across the Screen
	public void updatePos() {
		if(this.shotLeft)
			this.x = this.x - this.speed;
		
		else if(this.shotRight)
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
		return speed;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isShotLeft() {
		return shotLeft;
	}
	
	public boolean isShotRight() {
		return shotRight;
	}
	
	public Rectangle getCollisionBox() {
		return collisionBox;
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
	
	public void setShotLeft(boolean shotLeft) {
		this.shotLeft = shotLeft;
	}
	
	public void setShotRight(boolean shotRight) {
		this.shotRight = shotRight;
	}

	public void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}
}//end of class
