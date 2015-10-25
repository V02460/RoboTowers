package robo;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import javax.vecmath.Point2d;

import robo.network.NetworkEnviroment;
import robo.network.NetworkEntity;
import robo.map.Map;
import robo.map.SimpleLayerMap;
import robo.graphics.EntityList;
import robo.graphics.Materials;
import robo.graphics.Unit;
import robo.network.Type;
import robo.sounds.Sounds;
import robo.graphics.Camera;

public class RoboTowers extends BasicGame
{
	private Map map;
	private Unit player;
	EntityList entitylist;
	Sounds soundlist; 
	Camera camera;
	

	private NetworkEnviroment ne;
	private Input in;
	
	public RoboTowers(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ne = new NetworkEnviroment("localhost");
		NetworkEntity.setNetworkEnviroment(ne); // must be at the beginning of init
		
		
		
		
		map = new SimpleLayerMap(150, 150);
		Materials[] ms = new Materials[1];
		ms[0] = Materials.WHEELS;
		player = new Unit(new Point2d(100, 100), 0, ms);
		entitylist = new EntityList();
		entitylist.insertMap(map);
		entitylist.addEntity(player);
		soundlist=new Sounds();
		soundlist.playSound("RoboTowers.mp3");

		in = new Input(gc.getHeight());

		new NetworkEntity("projectile.png", new Point2d(10, 3), 3.2f, 5, Type.BULLET, new byte[0], true);

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
		player.update();
		camera.setPosition(player.getPosition().x, player.getPosition().y);
		FrameCounter.frameNumber++;
	}
/*
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		
	}
*/

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_W:
			player.setChangeSpeed(1);		
			break;
		case Input.KEY_A:
			player.setChangeDirection(-1);
			break;
		case Input.KEY_S:
			player.setChangeSpeed(-1);
			break;
		case Input.KEY_D:
			player.setChangeDirection(1);
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_W:
			if (in.isKeyDown(Input.KEY_S)) {
				player.setChangeSpeed(-1);
			} else {
				player.setChangeSpeed(0);
			}
			break;
		case Input.KEY_A:
			if (in.isKeyDown(Input.KEY_D)) {
				player.setChangeDirection(1);
			} else {
				player.setChangeDirection(0);
			}
			break;
		case Input.KEY_S:
			if (in.isKeyDown(Input.KEY_W)) {
				player.setChangeSpeed(1);
			} else {
				player.setChangeSpeed(0);
			}
			break;
		case Input.KEY_D:
			if (in.isKeyDown(Input.KEY_A)) {
				player.setChangeDirection(-1);
			} else {
				player.setChangeDirection(0);
			}
			break;
		}
		
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