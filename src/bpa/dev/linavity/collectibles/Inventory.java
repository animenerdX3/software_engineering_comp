package bpa.dev.linavity.collectibles;

import java.util.ArrayList;

public class Inventory {
	
	private ArrayList<Item>items;
	
	public Inventory() {
		
	}
	
	public Inventory(ArrayList<Item>items) {
		this.items = items;
	}
	
	public void addToInventory(Item item) {
		this.items.add(item);
	}
	
	public void removeFromInventory(Item item) {
		this.items.remove(item);
	}	
	
}//end of class
