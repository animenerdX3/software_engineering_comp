package bpa.dev.linavity.events;

import bpa.dev.linavity.GameObject;

public class Message {

	// List of possible events and their ID's
	public final static int gravPadRecharge = 0;
	public final static int doorToggle = 1;
	public final static int endLevel = 2;

	// The contents of our event messages
	private GameObject to;
	private GameObject from;
	private int type;
	private Object data;
	
	// Default Constructor
	public Message(GameObject to, GameObject from, int type, Object data){
		this.to = to;
		this.from = from;
		this.type = type;
		this.data = data;
	}
	
	
	// Getters
	
	/**
	 * @return the to
	 */
	public GameObject getTo() {
		return to;
	}
	
	/**
	 * @return the from
	 */
	public GameObject getFrom() {
		return from;
	}
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	
	// Setters
	
	/**
	 * @param to the to to set
	 */
	public void setTo(GameObject to) {
		this.to = to;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(GameObject from) {
		this.from = from;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
}
