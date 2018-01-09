package bpa.dev.linavity.entities;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.world.Level;

public abstract class Mob extends GameObject{
	
	// Dimension Variables
		//Yeh we usin this
		private float prevX, prevY;
		
		// The x and y coordinates of the mob in the world
		private float x, y;
		// The width and height of the mob
		private int width, height;
	
	// Collision Variables
		// Is the mob colliding with something
		private boolean collide;
		// On which side(s) is the mob colliding
		private boolean cu, cd, cl, cr;
	
	// Character Variables
		private Image mobImage = null;
		
	// Physics Variables
		// Momentum in the y direction
		private float ymo; 

		protected boolean isFalling;
		
	//Number of Possible Jumps: jumpNum + 1
		protected int jumpNum;
		
	//Mob Game Stats
		private double health, damage;
		private boolean isAlive;
	
	//Gravity object
	private Gravity gravity;
	
	private Rectangle boundingBox = new Rectangle();
	private Rectangle collisionCheck = new Rectangle();
	
	//Default constructor
	public Mob(Utils util) 
			throws SlickException{
		
		// Character Variables
		this.setMobImage(new Image("res/sprites/player/player_0.png")); //By default, our Mob has the player skin
		this.health = 100;
		
		// Dimension Variables
		this.width = this.getMobImage().getWidth();
		this.height = this.getMobImage().getHeight();
		
		this.gravity = new Gravity();
		
		// Collision variables
		this.setCollide(false);
		this.cu = false;
		this.cd = false;
		this.cl = false;
		this.cr = false;
		
		// Position
		this.x = 100;
		this.y = 100;
		
		// Physic's Variables
		this.ymo = 0;
		
		this.isFalling = true;
		
		this.jumpNum = 1;
		this.isAlive = true;
		
		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
	}
	
	//Non-Default constructor
	public Mob(String textureDirectory, int x, int y) 
			throws SlickException{
		// Character Variables
				this.setMobImage(new Image(textureDirectory)); //By default, our Mob has the player skin
		
		this.x = x;
		this.y = y;
				
		// Dimension Variables
		this.width = this.getMobImage().getWidth();
		this.height = this.getMobImage().getHeight();
		this.isFalling = true;
		this.jumpNum = 1;
		this.isAlive = true;
		
		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
	}

	/**
	 * Update our mob's position
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
	
	public boolean collidesWithTile(Tile tile, Camera cam) {
		
		collisionCheck.setBounds((int) (tile.getX() - cam.getX()), (int) (tile.getY() - cam.getY()), (int) tile.getWidth(), (int) tile.getHeight());
		return boundingBox.intersects(collisionCheck);
		
	}
	
	public void collidedWithTile(Level level, Camera cam) {
		
		Tile[][] screenTiles = level.getScreenTiles(cam);
		for(int r = 0; r < screenTiles.length; r++) {
			for(int c = 0; c < screenTiles[0].length; c++) {
				if(screenTiles[r][c] != null) {
					if(!screenTiles[r][c].isPassable()) {
						
						//Up Collision
						
						//NOTE: Collision when both up and down / left and right does not wrk
						
						float checkUpY =  screenTiles[r][c].getY() + screenTiles[r][c].getHeight();
						float checkUpX_Left = screenTiles[r][c].getX() - screenTiles[r][c].getWidth();
						float checkUpX_Right = screenTiles[r][c].getX() + screenTiles[r][c].getWidth();
						
						if(y == checkUpY  && (x > checkUpX_Left && x < checkUpX_Right)) {
							System.err.println("UP COLLISION");
							this.cu = true;
							this.y = y + 1;
							break;
						}
						
						//Down Collision
						
						float checkDownY =  (screenTiles[r][c].getY()) - screenTiles[r][c].getHeight();
						float checkDownX_Left = (screenTiles[r][c].getX()) - screenTiles[r][c].getWidth();
						float checkDownX_Right = (screenTiles[r][c].getX()) + screenTiles[r][c].getWidth();
						
						
						if(y == checkDownY  && (x > checkDownX_Left && x < checkDownX_Right)) {
							System.err.println("DOWN COLLISION");
							this.cd = true;
							this.y = y - 1;
							this.isFalling = false;
							break;
						}
						
						
						//Left Collision
						
						float checkLeftX =  screenTiles[r][c].getX() + screenTiles[r][c].getWidth();
						float checkLeftY_Up = screenTiles[r][c].getY() - screenTiles[r][c].getHeight();
						float checkLeftY_Down = screenTiles[r][c].getY() + screenTiles[r][c].getHeight();
						
						if(x == checkLeftX && (y > checkLeftY_Up && y < checkLeftY_Down)) {
							System.err.println("LEFT COLLISION");
							this.cl = true;
							if(cr)
								this.x = x + 0;
							else	
								this.x = x + 1;
							break;
						}
						
						
						//Right Collision
						
						float checkRightX =  screenTiles[r][c].getX() + screenTiles[r][c].getWidth();
						float checkRightY_Up = screenTiles[r][c].getY() - screenTiles[r][c].getHeight();
						float checkRightY_Down = screenTiles[r][c].getY() + screenTiles[r][c].getHeight();
						
						if(x == checkRightX && (y > checkRightY_Up && y < checkRightY_Down)) {
							this.cr = true;
							System.err.println("RIGHT COLLISION");
							if(cl)
								this.x = x - 0;
							else	
								this.x = x - 1;
							break;
						}
						
						else {
							this.cu = false;
							this.cd = false;
							this.cl = false;
							this.cr = false;
							this.collide = false;
						}
					}
				}
			}
		}
	}
	
	/* GETTERS */
	
	/**
	 * @return the cu
	 */
	public boolean isCu() {
		return cu;
	}

	/**
	 * @return the cd
	 */
	public boolean isCd() {
		return cd;
	}

	/**
	 * @return the cl
	 */
	public boolean isCl() {
		return cl;
	}

	/**
	 * @return the cr
	 */
	public boolean isCr() {
		return cr;
	}

	public boolean isCollide() {
		return collide;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	public float getPrevX(){
		return prevX;
	}
	
	public float getPrevY(){
		return prevY;
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getDamage() {
		return damage;
	}

	/**
	 * @return the mobImage
	 */
	public Image getMobImage() {
		return mobImage;
	}
	
	public float getYmo() {
		return ymo;
	}
	
	public boolean isFalling(){
		return isFalling;
	}
	
	public int getJumpNum(){
		return jumpNum;
	}
	
	public Gravity getGravity() {
		return gravity;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	/* SETTERS */

	public void setCollide(boolean collide) {
		this.collide = collide;
	}
	
	/**
	 * @param cu the cu to set
	 */
	public void setCu(boolean cu) {
		this.cu = cu;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(boolean cd) {
		this.cd = cd;
	}

	/**
	 * @param cl the cl to set
	 */
	public void setCl(boolean cl) {
		this.cl = cl;
	}

	/**
	 * @param cr the cr to set
	 */
	public void setCr(boolean cr) {
		this.cr = cr;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setPrevX(float prevX){
		this.prevX = prevX;
	}
	
	public void setPrevY(float prevY){
		this.prevY = prevY;
	}
	
	/**
	 * @param x the x to set
	 */
	public  void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public  void setY(float y) {
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
	
	public void setIsFalling(boolean falling){
		this.isFalling = falling;
	}
	
	public void setJumpNum(int jumpNum){
		this.jumpNum = jumpNum;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setIsAlive(boolean isAlive){
		this.isAlive = isAlive;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
	
}//end of class
