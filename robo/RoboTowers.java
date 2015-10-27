package robo;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import javax.vecmath.Point2d;

import robo.network.NetworkEnviroment;
import robo.network.NetworkEntity;
import robo.map.Map;
import robo.map.SimpleLayerMap;
import robo.collision.CollisionTest;
import robo.graphics.EntityList;
import robo.graphics.EntityUpdateList;
import robo.graphics.Materials;
import robo.graphics.Unit;
import robo.network.Type;
import robo.sounds.Sounds;
import robo.graphics.Camera;

public class RoboTowers extends BasicGame {
	public static final int TILE_SIZE = 50;

	private Map map;
	private Unit player;
	Sounds soundlist;
	Camera camera;

	private NetworkEnviroment ne;
	private Input in;

	 private static int port;

	public RoboTowers(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ne = new NetworkEnviroment("localhost", port);
		NetworkEntity.setNetworkEnviroment(ne); // must be at the beginning of init
		
		map = new SimpleLayerMap(150, 150);
		Materials[] ms = createRandomLoadOut();
		player = new Unit(new Point2d((map.getPlayer1X()+0.5)*TILE_SIZE, (map.getPlayer1Y()+0.5)*TILE_SIZE), (float) 0, ms);
		EntityList.insertMap(map);
		CollisionTest.setMap(map);
		soundlist=new Sounds();
		soundlist.playSound("RoboTowers.mp3");

		in = new Input(gc.getHeight());

		// ne.setCreationCallback();
		// ne.setDeletionCallback();
		// ne.setUpdateCallback();
		// ne.setHitCallback();
	}


	// FIXME: This should be only temporary
	public static Materials[] createRandomLoadOut() {
		Materials[] mats = new Materials[6];
		mats[0] = Materials.WHEELS;

		for (int i = 1; i < mats.length; i++) {
			mats[i] = Materials.randomMaterial();
		}

		return mats;
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException
	{
		// update all Entities
		// needs: entity array

		ne.receive();

		// ne.sendFrameUpdate();
		CollisionTest.checkColisions();
		EntityUpdateList.updateEntities();
		camera.setPosition(player.getPosition().x, player.getPosition().y);

		FrameCounter.frameNumber++;
	}


	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//g.drawString("Howdy!", 10, 10);
		// Camera Auto-Moving
		//camera.setPosition(camera.getX()+1.0, camera.getY()+1.0);
		EntityList.drawEntities(camera);

		g.setColor(new Color(100,150,100,220));
		g.fillRoundRect(6, 40, 90, 90, 6);
		g.setColor(Color.white);

		DecimalFormat df0 = new DecimalFormat( "##0" );
		DecimalFormat df1 = new DecimalFormat("##0.0");

		String hp = "HP: " + df0.format(player.getHealth());
		g.drawString(hp, 10, 50);
		String dmg = "DMG: " + df0.format(player.getStrength());
		g.drawString(dmg, 10, 70);
		String speed = "SPD: " + df1.format(player.getSpeed());
		g.drawString(speed, 10, 90);
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		player.giveShootOrder();
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		int diffX = newx - camera.getWidth()/2;
		int diffY = newy - camera.getHeight()/2;

		player.aim(diffX, diffY);
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
			case Input.KEY_W:
			case Input.KEY_UP:
				player.setChangeSpeed(1);
				break;
			case Input.KEY_A:
			case Input.KEY_LEFT:
				player.setChangeDirection(-1);
				break;
			case Input.KEY_S:
			case Input.KEY_DOWN:
				player.setChangeSpeed(-1);
				break;
			case Input.KEY_D:
			case Input.KEY_RIGHT:
				player.setChangeDirection(1);
				break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		switch (key) {
			case Input.KEY_W:
			case Input.KEY_UP:
				if (in.isKeyDown(Input.KEY_S) || in.isKeyDown(Input.KEY_DOWN)) {
					player.setChangeSpeed(-1);
				} else {
					player.setChangeSpeed(0);
				}
				break;
			case Input.KEY_A:
			case Input.KEY_LEFT:
				if (in.isKeyDown(Input.KEY_D) || in.isKeyDown(Input.KEY_RIGHT)) {
					player.setChangeDirection(1);
				} else {
					player.setChangeDirection(0);
				}
				break;
			case Input.KEY_S:
			case Input.KEY_DOWN:
				if (in.isKeyDown(Input.KEY_W) || in.isKeyDown(Input.KEY_UP)) {
					player.setChangeSpeed(1);
				} else {
					player.setChangeSpeed(0);
				}
				break;
			case Input.KEY_D:
			case Input.KEY_RIGHT:
				if (in.isKeyDown(Input.KEY_A) || in.isKeyDown(Input.KEY_LEFT)) {
					player.setChangeDirection(-1);
				} else {
					player.setChangeDirection(0);
				}
				break;
		}
		
	}

	public static void main(String[] args) {
		port = Integer.parseInt(args[0]);
		RoboTowers rt=new RoboTowers("RoboTowers Epicness!!!1111einself");
		System.out.println("Hello RoboTowers");
		rt.camera=new Camera();
		rt.camera.setPosition(0,0);
		rt.camera.setWindow(800,600);

		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(rt);

			appgc.setMinimumLogicUpdateInterval(20);
			appgc.setMaximumLogicUpdateInterval(20);
			
			appgc.setDisplayMode(rt.camera.getWidth(), rt.camera.getHeight(), false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(RoboTowers.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}