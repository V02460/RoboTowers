package graphics;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import javax.vecmath.Point2d;

import org.newdawn.slick.SlickException;

import map.TileType;

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
	
	
	public void drawEntities() {
		for (List<Entity> l : entities.values()) {
			for (Entity e : l) {
				e.getImage().drawCentered((float) e.getPosition().x,(float) e.getPosition().y);
			}
		}
	}
	
	public void insertMap(map.Map map) throws SlickException {
		for ( int x = 0; x < map.getWidth(); x++ ) {
			for ( int y = 0; y < map.getHeight(); y++ ) {
				String image_path;
				Entity e;
				if(map.get(x,y)==TileType.Wall){
					image_path="res/gfx/wall.png";
				} else if(map.get(x,y)==TileType.PlayerSpawn){
					image_path="res/gfx/player.png";
				} else {
					image_path="res/gfx/floor.png";
				}
				e=new Entity(image_path, new Point2d(x*32+16, y*32+16), 0, 0);
				addEntity(e);
				//System.out.println(entities.size()+ " " +e.getLayer() + " " + (int) e.getPosition().x);
			
			}
		}
	}
}


