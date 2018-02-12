package bpa.dev.linavity.events;

public class EventData {

	private int x;
	private int y;
	private String event;
	private Object data;
	
	public EventData(int x, int y, String event, Object data) {
		this.x = x;
		this.y = y;
		this.event = event;
		this.data = data;
	}

	/* GETTERS */
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getEvent() {
		return event;
	}

	public Object getData() {
		return data;
	}

	/* SETTERS */
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String toString() {
		return this.x+","+this.y+","+this.event+","+this.data;
	}
	
}//end of class
