package bpa.dev.linavity.assets;

public class Collision {

	//Instance variables
	private boolean collide;
	
	//Default constructor
	public Collision() {
		this.collide = true;
	}
	
	//Non-Default Constructor
	public Collision(boolean collide) {
		this.collide = collide;
	}
	
	/* GETTERS */
	public boolean getCollide() {
		return collide;
	}
	
	/* SETTERS */
	public void setCollide(boolean collide) {
		this.collide = collide;
	}
	
	//Overrides toString method
	public String toString() {
		return "Collide: "+this.collide;
	}
	
}//end of class
