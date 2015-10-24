import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class RoboTowers extends BasicGame
{
	private map.Map map;
	graphics.EntityList entitylist;
	double x_off;
	double y_off;
	
	
	public RoboTowers(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map=new map.SimpleLayerMap(25,20);
		entitylist=new graphics.EntityList();
		entitylist.insertMap(map);

		// Camera Offset to be moved into Player
		x_off=0;
		y_off=0;
		// Size of the window. To be moved to initialisation.
		entitylist.SetWindowSize(800,640);
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//g.drawString("Howdy!", 10, 10);
		entitylist.drawEntities();
		/*
		// Camera Auto-Moving
		// x_off+=1.0;
		// y_off+=1.0;
		
		// Draw Stuff
		entitylist.drawEntities(x_off, y_off);*/
		
	}

	public static void main(String[] args)
	{
		System.out.println("Hello RoboTowers");

		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new RoboTowers("Simple Slick Game"));
			appgc.setDisplayMode(800, 640, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(RoboTowers.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}