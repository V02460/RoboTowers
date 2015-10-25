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
import robo.network.Type;
import robo.graphics.Camera;

public class RoboTowers extends BasicGame
{
	private Map map;
	EntityList entitylist;
	Camera camera;
	

	private NetworkEnviroment ne;
	
	public RoboTowers(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map = new SimpleLayerMap(150, 150);
		entitylist = new EntityList();
		entitylist.insertMap(map);
		
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
		// Camera Auto-Moving
		//camera.setPosition(camera.getX()+1.0, camera.getY()+1.0);
		entitylist.drawEntities(camera);
		
		
		
	}

	public static void main(String[] args)
	{
		RoboTowers rt=new RoboTowers("RoboTowers Epicness!!!1111einself");
		System.out.println("Hello RoboTowers");
		rt.camera=new Camera();
		rt.camera.setPosition(0,0);
		rt.camera.setWindow(800,600);

		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(rt);

			appgc.setMinimumLogicUpdateInterval(20);
			appgc.setMaximumLogicUpdateInterval(20);
			
			appgc.setDisplayMode(rt.camera.getWidth(), rt.camera.getHeight(), false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(RoboTowers.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}