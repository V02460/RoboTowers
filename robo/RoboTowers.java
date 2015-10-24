package robo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import javax.vecmath.Point2d;

import robo.network.NetworkEnviroment;
import robo.network.NetworkEntity;

import robo.map.Map;
import robo.map.SimpleLayerMap;
import robo.graphics.EntityList;
import robo.graphics.EntityList;
import robo.network.Type;

public class RoboTowers extends BasicGame
{
	private Map map;
	EntityList entitylist;
	double x_off;
	double y_off;
	
	private NetworkEnviroment ne;
	
	public RoboTowers(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map = new SimpleLayerMap(40, 40);
		entitylist = new EntityList();
		entitylist.insertMap(map);

		// Camera Offset to be moved into Player
		x_off=0;
		y_off=0;
		// Size of the window. To be moved to initialisation.
		entitylist.SetWindowSize(800,640);
		
		// NETWORK STUFF
		ne = new NetworkEnviroment("localhost");
		NetworkEntity.setNetworkEnviroment(ne);

		new NetworkEntity("robo/res/gfx/projectile.png", new Point2d(10, 3), 3.2f, 5, Type.BULLET, null, true);

		// ne.setCreationCallback();
		// ne.setDeletionCallback();
		// ne.setUpdateCallback();
		// ne.setHitCallback();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException
	{
		// update all Entities
		// needs: entity array

		// ne.sendFrameUpdate();

		FrameCounter.frameNumber++;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//g.drawString("Howdy!", 10, 10);
		entitylist.drawEntities(x_off, y_off);
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
			appgc = new AppGameContainer(new RoboTowers("RoboTowers Epicness!!!1111einself"));

			appgc.setMinimumLogicUpdateInterval(20);
			appgc.setMaximumLogicUpdateInterval(20);
			appgc.setDisplayMode(1366, 786, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(RoboTowers.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}