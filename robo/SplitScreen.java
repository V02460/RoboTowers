package robo;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Point2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import robo.collision.CollisionTest;
import robo.graphics.Camera;
import robo.graphics.EntityList;
import robo.graphics.EntityUpdateList;
import robo.graphics.Materials;
import robo.graphics.Unit;
import robo.map.Map;
import robo.map.SimpleLayerMap;
import robo.network.NetworkEntity;
import robo.network.NetworkEnviroment;
import robo.sounds.Sounds;

public class SplitScreen extends BasicGame {
	public static final int TILE_SIZE = 50;

	private Map map;
	private Unit player1;
	private Unit player2;
	Sounds soundlist;
	Camera camera1;
	Camera camera2;

	private NetworkEnviroment ne;
	private Input in;
	
	public SplitScreen(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ne = new NetworkEnviroment("localhost");
		NetworkEntity.setNetworkEnviroment(ne); // must be at the beginning of init
		
		map = new SimpleLayerMap(30, 30);
		Materials[] ms = createRandomLoadOut();
		player1 = new Unit(new Point2d((map.getPlayer1X()+0.5)*TILE_SIZE, (map.getPlayer1Y()+0.5)*TILE_SIZE), (float) 0.85, ms);
		player2 = new Unit(new Point2d((map.getPlayer2X()+0.5)*TILE_SIZE, (map.getPlayer2Y()+0.5)*TILE_SIZE), (float) 3.95, ms);
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
	private Materials[] createRandomLoadOut() {
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

		// ne.sendFrameUpdate();
		CollisionTest.checkColisions();
		EntityUpdateList.updateEntities();
		camera1.setPosition(player1.getPosition().x, player1.getPosition().y);
		camera2.setPosition(player2.getPosition().x, player2.getPosition().y);
		FrameCounter.frameNumber++;

		int diffX = (int) player2.getPosition().x- (int) player1.getPosition().x;
		int diffY = (int) player2.getPosition().y- (int) player1.getPosition().y;

		player1.aim((int)diffX, (int)diffY);

	
	}


	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//g.drawString("Howdy!", 10, 10);
		// Camera Auto-Moving
		//camera.setPosition(camera.getX()+1.0, camera.getY()+1.0);
		EntityList.drawEntities(camera1,1);
		EntityList.drawEntities(camera2,2);

		g.setColor(new Color(100,150,100,220));
		g.fillRoundRect(6, 40, 90, 90, 6);
		g.setColor(Color.white);

		DecimalFormat df0 = new DecimalFormat( "##0" );
		DecimalFormat df1 = new DecimalFormat("##0.0");

		String hp = "HP: " + df0.format(player1.getHealth());
		g.drawString(hp, 10, 50);
		String dmg = "DMG: " + df0.format(player1.getStrength());
		g.drawString(dmg, 10, 70);
		String speed = "SPD: " + df1.format(player1.getSpeed());
		g.drawString(speed, 10, 90);

		g.setColor(new Color(100,100,150,220));
		g.fillRoundRect(6, 120, 90, 90, 6);
		g.setColor(Color.white);

		String hp2 = "HP: " + df0.format(player2.getHealth());
		g.drawString(hp2, 10, 130);
		String dmg2 = "DMG: " + df0.format(player2.getStrength());
		g.drawString(dmg2, 10, 150);
		String speed2 = "SPD: " + df1.format(player2.getSpeed());
		g.drawString(speed2, 10, 170);
	}

	// TODO few next for player 2
	@Override
	public void mousePressed(int button, int x, int y) {
		player2.giveShootOrder();
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		int diffX = newx - camera1.getWidth()/2-camera1.getWidth();
		int diffY = newy - camera1.getHeight()/2;

		player2.aim(diffX, diffY);
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
			case Input.KEY_W:
				player1.setChangeSpeed(1);
				break;
			case Input.KEY_A:
				player1.setChangeDirection(-1);
				break;
			case Input.KEY_S:
				player1.setChangeSpeed(-1);
				break;
			case Input.KEY_D:
				player1.setChangeDirection(1);
				break;
			case Input.KEY_UP:
				player2.setChangeSpeed(1);
				break;
			case Input.KEY_LEFT:
				player2.setChangeDirection(-1);
				break;
			case Input.KEY_DOWN:
				player2.setChangeSpeed(-1);
				break;
			case Input.KEY_RIGHT:
				player2.setChangeDirection(1);
				break;
			case Input.KEY_SPACE:
				player1.giveShootOrder();
				break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		switch (key) {
			case Input.KEY_W:
				if (in.isKeyDown(Input.KEY_S)) {
					player1.setChangeSpeed(-1);
				} else {
					player1.setChangeSpeed(0);
				}
				break;
			case Input.KEY_A:
				if (in.isKeyDown(Input.KEY_D)) {
					player1.setChangeDirection(1);
				} else {
					player1.setChangeDirection(0);
				}
				break;
			case Input.KEY_S:
				if (in.isKeyDown(Input.KEY_W)) {
					player1.setChangeSpeed(1);
				} else {
					player1.setChangeSpeed(0);
				}
				break;
			case Input.KEY_D:
				if (in.isKeyDown(Input.KEY_A)) {
					player1.setChangeDirection(-1);
				} else {
					player1.setChangeDirection(0);
				}
				break;			
		
			case Input.KEY_UP:
				if (in.isKeyDown(Input.KEY_DOWN)) {
					player2.setChangeSpeed(-1);
				} else {
					player2.setChangeSpeed(0);
			}
				break;
			case Input.KEY_LEFT:
				if (in.isKeyDown(Input.KEY_RIGHT)) {
					player2.setChangeDirection(1);
				} else {
					player2.setChangeDirection(0);
				}
				break;
			case Input.KEY_DOWN:
				if (in.isKeyDown(Input.KEY_UP)) {
					player2.setChangeSpeed(1);
				} else {
					player2.setChangeSpeed(0);
				}
				break;
			case Input.KEY_RIGHT:
				if (in.isKeyDown(Input.KEY_LEFT)) {
					player2.setChangeDirection(-1);
				} else {
					player2.setChangeDirection(0);
				}
				break;
				
		}
		
	}

	public static void main(String[] args) {
		SplitScreen rt=new SplitScreen("Last Minute Hacks FTW");
		System.out.println("Hello RoboTowers");
		rt.camera1 = new Camera();
		rt.camera2 = new Camera();
		rt.camera1.setPosition(0,0);
		rt.camera2.setPosition(0,0);
		rt.camera1.setWindow(12*TILE_SIZE,12*TILE_SIZE);
		rt.camera2.setWindow(12*TILE_SIZE,12*TILE_SIZE);

		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(rt);

			appgc.setMinimumLogicUpdateInterval(20);
			appgc.setMaximumLogicUpdateInterval(20);

			appgc.setDisplayMode(rt.camera1.getWidth()*2, rt.camera1.getHeight(), false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(RoboTowers.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}