package bpa.dev.linavity.entities;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;

public class GravityPack{

	private float depletionRate;
	private float gravpower;
	private boolean canFlip;
	
	public GravityPack() throws SlickException {
		this.depletionRate = 0.3f;
		this.gravpower = 0;
		this.canFlip = false;
	}
	
	public GravityPack(float gravpower) throws SlickException {
		this.depletionRate = 0.3f;
		this.gravpower = gravpower;
		this.canFlip = true;
	}
	
	/**
	 * Drain gravity power from the player's gravity pack based on our depletion rate
	 */
	public void depletingGravPack(){
		Main.util.getPlayer().getGravPack().setGravpower(Main.util.getPlayer().getGravPack().getGravpower() - depletionRate);
	}//end of depletingGravPack

	/**
	 * going to check for the constraints on the the gravity power 
	 * and also set the flip boolean when needed
	 * 
	 * canFlip will always be set to true unless gravpower is 0
	 */
	public void gravPowerCheck(){
		this.setCanFlip(true);
		if( this.gravpower > 100)
			this.setGravpower(100);
		else if (gravpower <= 0){ //when i set gravpower to 0 or anything less set flip to false
			this.setGravpower(0);
			this.setCanFlip(false);
		}
	}

	/* GETTERS */
	
	/**
	 * 
	 * @return our gravity pack's depletion rate
	 */
	public float getDepletionRate() {
		return depletionRate;
	}

	/**
	 * 
	 * @return the gravity power in our gravity pack
	 */
	public float getGravpower() {
		return gravpower;
	}

	/**
	 * 
	 * @return if true, the user can flip the gravity pack. if false, the user cannot flip
	 */
	public boolean isCanFlip() {
		return canFlip;
	}

	/* SETTERS */
	
	/**
	 * changes the depletion rate
	 * @param depletionRate
	 */
	public void setDepletionRate(float depletionRate) {
		this.depletionRate = depletionRate;
	}

	/**
	 * changes the gravity power
	 * @param gravpower
	 */
	public void setGravpower(float gravpower) {
		this.gravpower = gravpower;
	}

	/**
	 * changes the flipping boolean
	 * @param canFlip
	 */
	public void setCanFlip(boolean canFlip) {
		this.canFlip = canFlip;
	}
	
	/**
	 * Overwrites the class's toString method for saving purposes
	 */
	public String toString() {
		return ""+this.gravpower;
	}
	
}//end of class
