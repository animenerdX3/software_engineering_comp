package bpa.dev.linavity.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.world.Level;

public class Mob{

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
		
	//Default constructor
	public Mob() 
			throws SlickException{
		
		// Character Variables
		this.setMobImage(new Image("res/sprites/player/player_0.png")); //By default, our Mob has the player skin
		this.health = 100;
		
		// Dimension Variables
		this.width = this.getMobImage().getWidth() - 2;
		this.height = this.getMobImage().getHeight() - 2;
		
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
	
	
	// Check to see if the player is colliding, and on which sides
	public void checkCollisions(Level level, Camera cam) {
		
		// Get a 2d array of tile objects that are contained within our camera's view
		Tile[][] screenTiles = level.getScreenTiles(cam);
		
		boolean setCollideTo = false;
		boolean setCu = false;
		boolean setCd = false;
		boolean setCl = false;
		boolean setCr = false;
		
		
		// Run through collision detection for the tiles that belong on the screen.
		for(int i = 0; i < screenTiles.length; i++) {
			for(int j = 0; j < screenTiles[i].length; j++) {
				
				if(screenTiles[i][j] != null) { // If the tile exists
					if(!screenTiles[i][j].isPassable()){ // Can be collided with
						if(checkBothSides(screenTiles[i][j])){ // And is colliding then...

							//Check For Reverse Gravity
							if(gravity.getFlipDirection()) {
								// Check Up
								if(checkUpCollision(screenTiles[i][j])){
									setCu = true;
									setCollideTo = true;
									break;
								}
								
								// Check Left
								if(checkLeftCollision(screenTiles[i][j])){
									setCl = true;
									setCollideTo = true; //Object is colliding
									break;
								}	
								
								// Check Down
								if(checkDownCollision(screenTiles[i][j])){
									setCd = true;
									setCollideTo = true; //Object is colliding
									break;
								}
								
								// Check Right
								if(checkRightCollision(screenTiles[i][j])){
									setCr = true;
									setCollideTo = true; //Object is colliding
									break;
								}
									
								

							}
							//Check For Regular Gravity
							else {
								// Check Down
								if(checkDownCollision(screenTiles[i][j])){
									setCd = true;
									setCollideTo = true; //Object is colliding
									break;
								}
								
								// Check Right
								if(checkRightCollision(screenTiles[i][j])){
									setCr = true;
									setCollideTo = true; //Object is colliding
									break;
								}
									
								// Check Up
								if(checkUpCollision(screenTiles[i][j])){
									setCu = true;
									setCollideTo = true;
									break;
								}
								
								// Check Left
								if(checkLeftCollision(screenTiles[i][j])){
									setCl = true;
									setCollideTo = true; //Object is colliding
									break;
								}
							
							}

						}
					}
				}
				
			}
		}
		// End of tile collision detection	
		this.setCollide(setCollideTo);
		this.setCu(setCu);
		this.setCd(setCd);
		this.setCl(setCl);
		this.setCr(setCr);
				
		
	}
	
	public boolean checkLeftCollision(Tile tile) {
		if(this.x <= tile.getX()  && getPrevX() <= x){
			System.err.println("Left Collision");
			this.x = (float) (tile.getX() - mobImage.getWidth() - 4);
			return true;
		}	
		return false;
	
	}//end of checkLeftCollision
	
	public boolean checkRightCollision(Tile tile) {
		if(this.x >= tile.getX() &&  getPrevX() >= x){
			System.err.println("Right Collision");
			this.x = (float) (tile.getX() + mobImage.getWidth() + 4);
			return true;
		}
		return false;
		
	}//end of checkRightCollision
	
	public boolean checkUpCollision(Tile tile) {
		if(this.y >= tile.getY()  && getPrevY() >= y){
			System.err.println("Up Collision");
			this.y = (float) (tile.getY() + mobImage.getHeight());
			return true;
		}
		return false;
	
	}//end of checkUpCollision
	
	public boolean checkDownCollision(Tile tile) {
		if(this.y <= tile.getY() && getPrevY() <= y){
			System.err.println("Down Collision"); 
			this.y = (float) (tile.getY() - mobImage.getHeight());
			return true;
		}
		return false;
		
	}//end of checkDownCollision
	
	// We need to make mob and tile extend a common game object so that we can perform collision detection with anything
	private boolean checkBothSides(Tile tile){
		return checkSide(this.x, this.x + this.width, tile.getX(), tile.getX() + tile.getWidth()) && 
				checkSide(this.y, this.y + this.height, tile.getY(), tile.getY() + tile.getHeight());
	}
	
	private boolean checkSide(float objNum1, float objNum2, float f, float g){
		// Want to make sure that the min and the max of the two coordinates are oriented properly.
		float objMin = Math.min(objNum1, objNum2);
		float objMax = Math.max(objNum1, objNum2);
		
		// Want to make sure that the min and the max of the two coordinates are oriented properly.
		float tileMin = Math.min(f, g);
		float tileMax = Math.max(f, g);
		
		if(objMax >= tileMin && objMin <= tileMax) // If the two sides overlap
			return true; 
		else
			return false;
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
	
}
