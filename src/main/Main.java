package main;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame{

	public Main(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Main("Coolest SciFi Platformer Alien Megatastic Palooza of Escape Guy Does Escape Things 100"));
			appgc.setDisplayMode(800, 800, false);
			appgc.setAlwaysRender(true); // Constant Rendering 
			appgc.setTargetFrameRate(59);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		addState(new BasicGame());
	}

}
