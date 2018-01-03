package bpa.dev.linavity.events;

import java.util.ArrayList;

public class MessageHandler {
	
	// An array list (queue) of messages to be handled by the game
	private ArrayList<Message> messageQueue = new ArrayList<Message>();
	
	// Default Constructor
	public MessageHandler(){
		
	}
	
	// Method that allows other objects to add a message to the message queue
	public void addEvent(Message message){
		messageQueue.add(message);
	}
	
	// Runs through the array list of events and dispatches them to the proper recipients
	public void dispatchEvents(){
		
		for(int i = 0; i < messageQueue.size(); i++){
			
			if(!messageQueue.get(i).equals(null)){
				messageQueue.get(i).getTo().onMessage();
			}
			
			
		}
		
	}
	
}
