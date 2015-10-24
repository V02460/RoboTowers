package graphics;

import java.util.TreeSet;
import java.util.Iterator;
import javax.vecmath.Point2d;

import org.newdawn.slick.SlickException;

import map.TileType;

public class EntityList {
	private static TreeSet<Entity> entities=new TreeSet<Entity>(new EntityComparator());
	
	public void addEntity(Entity e) {
		entities.add(e);
		
	}

	public void deleteEntity(Entity e) {
		entities.remove(e);
	}
	
	
	public void drawEntities() {
		Iterator<Entity> i; 		
		i=entities.iterator();
		while(i.hasNext()){
		    Entity e=i.next();
			e.getImage().drawCentered((float) e.getPosition().x,(float) e.getPosition().y);
		}
	}
	
	public void insertMap(map.Map map) throws SlickException {
		for ( int x = 0; x < map.getWidth(); x++ ) {
			for ( int y = 0; y < map.getHeight(); y++ ) {
				String image_path;
				Entity e;
				if(map.get(x,y)==TileType.Wall){
					image_path="res/gfx/wall.png";
				} else {
					image_path="res/gfx/floor.png";
				}
				e=new Entity(image_path, new Point2d(x*32+16, y*32+16), 0, x+y*20);
				addEntity(e);
				//System.out.println(entities.size()+ " " +e.getLayer() + " " + (int) e.getPosition().x);
			
			}
		}
	}
	
	@Override
	public String toString() {
		String out = "";
		Iterator<Entity> i; 		
		i=entities.iterator();
		while(i.hasNext()){
		    Entity e=i.next();
		    out +=(int) e.getPosition().x + " ";
		}
		out+="\n";
		return out;
	}
	
}


