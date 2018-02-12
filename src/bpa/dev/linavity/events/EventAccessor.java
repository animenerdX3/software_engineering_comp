package bpa.dev.linavity.events;

import java.util.ArrayList;

public class EventAccessor {

	private ArrayList<EventData> events;
	
	public EventAccessor() {
		this.events = new ArrayList<EventData>();
	}
	
	public void addLevelEvent(int x, int y, String event, Object data) {
		events.add(new EventData(x, y, event, data));
	}
	
	public void removeEvent(int x, int y, String event, Object data) {
		events.remove(new EventData(x, y, event, data));
	}

	public void changeEvent(int row, int col, Object data) {
		for(int i = 0; i < this.events.size(); i++) {
			if(events.get(i).getX() == row)
				if(events.get(i).getY() == col)
				events.get(i).setData(data);
		}
	}
	
	/* GETTERS */
	
	/**
	 * 
	 * @return the level's events
	 */
	public ArrayList<EventData> getEvents() {
		return events;
	}

	/* SETTERS */
	
	/**
	 * changes the level events
	 * @param events
	 */
	public void setEvents(ArrayList<EventData> events) {
		this.events = events;
	}
	
	
}//end of class
