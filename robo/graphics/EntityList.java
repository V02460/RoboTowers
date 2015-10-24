package robo.graphics;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import javax.vecmath.Point2d;

import org.newdawn.slick.SlickException;
import robo.map.TileType;



public class EntityList {
	private static Map<Integer, List<Entity>> entities = new HashMap<>();
	
	
	public void addEntity(Entity e) {
		if (entities.containsKey(e.getLayer())) {
			entities.get(e.getLayer()).add(e);
		} else {
			List<Entity> l = new LinkedList<Entity>();
			l.add(e);
			entities.put(e.getLayer(), l);
		}
	}

	public void deleteEntity(Entity e) {
		if(entities.containsKey(e.getLayer())) {
			entities.get(e.getLayer()).remove(e);
		}		
	}
	
	
	public void drawEntities(Camera camera) {
		for (List<Entity> l : entities.values()) {
			for (Entity e : l) {
				// Move By Offset an Center in Window
				double x=e.getPosition().x-camera.getX()+camera.getWidth()/2;
				double y=e.getPosition().y-camera.getY()+camera.getHeight()/2;				
				// Check whether Image is inside Window			
				if(x+e.getImage().getWidth()>=0 && x-e.getImage().getWidth()<=camera.getWidth()){
					if(y+e.getImage().getHeight()>=0 && y-e.getImage().getHeight()<=camera.getHeight()){
						// Draw Entity
						e.getImage().drawCentered((float) x,(float) y);
					}
				}
			}
		}
	}
	
	
	// Transform map into entities
	public void insertMap(robo.map.Map map) throws SlickException {
		for ( int x = 0; x < map.getWidth(); x++ ) {
			for ( int y = 0; y < map.getHeight(); y++ ) {
				String image_path;
				Entity e;
				
				// chose Image
				if(map.get(x,y)==TileType.Wall){
					image_path="robo/res/gfx/wall.png";
				} else if(map.get(x,y)==TileType.PlayerSpawn){
					image_path="robo/res/gfx/player.png";
				} else {
					image_path="robo/res/gfx/floor.png";
				}
				// Define Image and Position and push to entity-list
				// Image size assumed to be 32x32
				e=new Entity(image_path, new Point2d(x*32+16, y*32+16), 0, 0);
				addEntity(e);
			
			}
		}
	}
}


