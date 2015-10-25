package robo.graphics;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import javax.vecmath.Point2d;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import robo.map.TileType;



public class EntityList {
	private static Map<Integer, List<Entity>> entities = new HashMap<>();
	private static Images images = new Images();
	
	
	public static void addEntity(Entity e) {
		if (entities.containsKey(e.getLayer())) {
			entities.get(e.getLayer()).add(e);
		} else {
			List<Entity> l = new LinkedList<Entity>();
			l.add(e);
			entities.put(e.getLayer(), l);
		}
	}

	public static void deleteEntity(Entity e) {
		if(entities.containsKey(e.getLayer())) {
			entities.get(e.getLayer()).remove(e);
		}		
	}

	public static void drawEntities(Camera camera) throws SlickException {
		for (List<Entity> l : entities.values()) {
			for (Entity e : l) {
				// Move By Offset an Center in Window
				double x=e.getPosition().x-camera.getX()+camera.getWidth()/2;
				double y=e.getPosition().y-camera.getY()+camera.getHeight()/2;
				Image image=images.getImage(e.getImageString());
				// Check whether Image is inside Window			
				if(x+image.getWidth()>=0 && x-image.getWidth()<=camera.getWidth()){
					if(y+image.getHeight()>=0 && y-image.getHeight()<=camera.getHeight()){
						// Draw Entity					
						image.setRotation((float) (e.getRotation()*360/(2.0*Math.PI)));
						image.drawCentered((float) ((int) x),(float) ((int) y));
					}
				}
			}
		}
	}


	// Transform map into entities
	public static void insertMap(robo.map.Map map) throws SlickException {
		Random random=new Random();	
		for ( int x = 0; x < map.getWidth(); x++ ) {
			for ( int y = 0; y < map.getHeight(); y++ ) {
				String image_path;
				Entity e;
				
				// chose Image
				if(map.get(x,y)==TileType.Wall){
					image_path="wall" + (random.nextInt(3)+1) + ".png";
				} else if(map.get(x,y)==TileType.PlayerSpawn){
					image_path="player.png";
				} else {
					image_path="floor" + (random.nextInt(3)+1) + ".png";
				}
				// Define Image and Position and push to entity-list
				// Image size assumed to be 50x50
				e=new Entity(image_path, new Point2d((2*x+1)*25, (2*y+1)*25), 0, 0);
			}
		}
	}
}


