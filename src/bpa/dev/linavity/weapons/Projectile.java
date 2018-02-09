package bpa.dev.linavity.weapons;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.utils.ErrorLog;

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
	private boolean isCollide;
	private int collisionRadius;
	
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
		this.isCollide = false;
		this.collisionRadius = 10;
	}
	
	//Non-Default Projectile
	public Projectile(String imageDirectory, int damage, int speed, boolean left, boolean right) throws SlickException {
		this.projectileImage = new Image(imageDirectory);
		this.damage = damage;
		this.speed = speed;
		this.x = Main.util.getPlayer().getX();
		this.y = Main.util.getPlayer().getY();
		this.shotLeft = left;
		this.isCollide = false;
		this.collisionRadius = 10;
	}
	
	//Move Projectile Across the Screen
	public void updatePos() {
		if(this.shotLeft)
			this.x = this.x - this.speed;
		
		else if(this.shotRight)
			this.x = this.x + this.speed;
		try {
			onCollision(Main.util.getLevel().getScreenTiles(new Camera(this.x, this.y, this.collisionRadius), Main.util.getLevel().getMap()), new Camera(this.x, this.y, this.collisionRadius));
		} catch (SlickException e) {
			ErrorLog.displayError(e);
		}
		
	}
	
	/**
	 * check if the projectile collides with an object
	 * @param level
	 * @param cam
	 * @throws SlickException 
	 */
	private void onCollision(Tile[][] screenTiles, Camera cam) throws SlickException {
		//Check for through our level map
		for(int r = 0; r < screenTiles.length; r++) { // Run through each row
			for(int c = 0; c < screenTiles[0].length; c++) // Run through each column
				if(screenTiles[r][c] != null)  // If the tile exists
						checkTileCollision(screenTiles[r][c]); // Is the mob colliding with this tile?
		}

	}//end of onCollision
	
	private void checkTileCollision(Tile tile) {
		if(!tile.isPassable()) {
			if(this.x + this.speed >= tile.getX() && this.x + this.speed <= tile.getX() + tile.getWidth()) {
				if(this.y >= tile.getY() && this.y <= tile.getY() + tile.getHeight()) {
					this.isCollide = true;
				}
			}
		}
	}//end of checkTileCollision

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
	
	public boolean isCollide() {
		return isCollide;
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

	public void setCollide(boolean isCollide) {
		this.isCollide = isCollide;
	}
	
}//end of class
