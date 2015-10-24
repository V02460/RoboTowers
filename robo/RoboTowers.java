package robo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import robo.map.Map;
import robo.graphics.EntityList;

public class RoboTowers extends BasicGame
{
	private Map map;
	EntityList entitylist;
	double x_off;
	double y_off;
	
	
	public RoboTowers(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map=new robo.map.SimpleLayerMap(100, 100);
		entitylist=new robo.graphics.EntityList();
		entitylist.insertMap(map);

		// Camera Offset to be moved into Player
		x_off=0;
		y_off=0;
		// Size of the window. To be moved to initialisation.
		entitylist.SetWindowSize(1366,786);
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{		
		// Camera Auto-Moving
		// x_off+=1.0;
		// y_off+=1.0;
		
		// Draw Stuff
		entitylist.drawEntities(x_off, y_off);
		
	}

	public static void main(String[] args)
	{
		System.out.println("Hello RoboTowers");

		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new RoboTowers("Simple Slick Game"));
			appgc.setDisplayMode(1366, 786, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(RoboTowers.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}