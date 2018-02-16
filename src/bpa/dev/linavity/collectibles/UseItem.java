package bpa.dev.linavity.collectibles;

import bpa.dev.linavity.Main;

public class UseItem {

	private String item;
	
	public UseItem(String item){
		this.item = item;
		itemEffect(this.item);
	}
	
	private void itemEffect(String item){
		if(this.item.equalsIgnoreCase("health")) {
			if(Main.util.getPlayer().getHealth() + 10 > 100)
				Main.util.getPlayer().setHealth(100);
			else
				Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() + 10);
				Main.util.getSFX(9).play(1f, Main.util.getSoundManager().getVolume());
		}
		if(this.item.equalsIgnoreCase("gravcapsule")) {
			if(Main.util.getPlayer().getGravPack().getGravpower() + 40 > 100)
				Main.util.getPlayer().getGravPack().setGravpower(100);
			else
				Main.util.getPlayer().getGravPack().setGravpower(Main.util.getPlayer().getGravPack().getGravpower() + 40);
			Main.util.getSFX(10).play(1f, Main.util.getSoundManager().getVolume());
		}
	}//end of itemEffect
	
}//end of class
