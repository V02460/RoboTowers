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
	private graphics.Map_Graphics display;
	
	public RoboTowers(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map=new map.SimpleLayerMap(20,15);
		display=new graphics.Map_Graphics();
		display.InitMapGraphics();
		
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//g.drawString("Howdy!", 10, 10);
		display.DrawMap(map);

	}

	public static void main(String[] args)
	{
		System.out.println("Hello RoboTowers");

		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new RoboTowers("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(RoboTowers.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}