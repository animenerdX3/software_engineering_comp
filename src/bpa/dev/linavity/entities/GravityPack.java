package bpa.dev.linavity.entities;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.utils.Utils;

public class GravityPack{

	
	private float depletionRate;
	
	public GravityPack() throws SlickException {
		this.depletionRate = 0.5f;
	}

	public void depletingGravPack(Utils util){
			System.out.println("Depleting Gravity");
			util.getPlayer().setGravityPack(util.getPlayer().getGravityPack() - depletionRate);
	}

	public float getDepletionRate() {
		return depletionRate;
	}

	public void setDepletionRate(float depletionRate) {
		this.depletionRate = depletionRate;
	}
	
}//end of class
