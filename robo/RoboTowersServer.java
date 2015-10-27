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

public class RoboTowersServer extends BasicGame
{
	private NetworkEnviroment ne;

	public RoboTowersServer(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException
	{
		// start server

		System.out.println("Server init");
		ne = new NetworkEnviroment(null, 1234);

		// ne.setCreationCallback();
		// ne.setDeletionCallback();
		// ne.setUpdateCallback();
		// ne.setHitCallback();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException
	{
		ne.receive();
		
		FrameCounter.frameNumber++;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.drawString("Howdy!", 200, 10);
	}

	public static void main(String[] args)
	{
		System.out.println("I'm the SERVER.");

		try
		{
			AppGameContainer gc;
			gc = new AppGameContainer(new RoboTowersServer("Simple Slick Game"));

			gc.setMinimumLogicUpdateInterval(20);
			gc.setMaximumLogicUpdateInterval(20);
			gc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(RoboTowersServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}