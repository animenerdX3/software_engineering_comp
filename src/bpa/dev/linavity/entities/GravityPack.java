package bpa.dev.linavity.entities;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.utils.Utils;

public class GravityPack{

	
	private float depletionRate;
	private float gravpower;
	private boolean canFlip;
	
	public GravityPack() throws SlickException {
		this.depletionRate = 0.5f;
		this.gravpower = 0;
		this.canFlip = false;
	}
	
	public GravityPack(float gravpower) throws SlickException {
		this.depletionRate = 0.3f;
		this.setGravpower(gravpower);
		this.canFlip = true;
	}
	

	public void depletingGravPack(){
			System.out.println("Depleting Gravity");
			System.out.println("Depletion Rate: "+this.depletionRate);
			System.out.println("Power: "+ Main.util.getPlayer().getGravPack().getGravpower());
			Main.util.getPlayer().getGravPack().setGravpower(Main.util.getPlayer().getGravPack().getGravpower() - depletionRate);
	}

	public float getDepletionRate() {
		return depletionRate;
	}

	public void setDepletionRate(float depletionRate) {
		this.depletionRate = depletionRate;
	}

	public float getGravpower() {
		return gravpower;
	}

	public void setGravpower(float gravpower) {
		this.gravpower = gravpower;
	}
	
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
		System.out.println("CAN FLIP: "+this.canFlip);
	}

	public boolean isCanFlip() {
		return canFlip;
	}

	public void setCanFlip(boolean canFlip) {
		this.canFlip = canFlip;
	}
	
	
	
}//end of class
