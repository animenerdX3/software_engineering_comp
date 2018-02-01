package bpa.dev.linavity.collectibles;

import bpa.dev.linavity.Main;

public class UseItem {

	private String item;
	
	public UseItem(String item){
		this.item = item;
		itemEffect(this.item);
	}
	
	private void itemEffect(String item){
		if(this.item.equalsIgnoreCase("health"))
			Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() + 10);
	}//end of itemEffect
	
}//end of class
