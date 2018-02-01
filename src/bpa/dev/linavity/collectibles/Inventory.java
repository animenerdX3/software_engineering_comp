package bpa.dev.linavity.collectibles;

import java.util.ArrayList;

public class Inventory {
	
	private ArrayList<Item>items;
	
	public Inventory() {
		this.items = new ArrayList<Item>();
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

	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
}//end of class
