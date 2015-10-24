package graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import map.TileType;

public class Map_Graphics {
	private Image img_floor;
	private Image img_wall; 
	
	public void InitMapGraphics() throws SlickException {
		img_floor = new Image("res/floor.png");
		img_wall = new Image("res/wall.png");
	}
	
	public void DrawMap(map.Map map) {
		for ( int x = 0; x < map.get_width(); x++ ) {
			for ( int y = 0; y < map.get_height(); y++ ) {
				if(map.get(x, y)==TileType.Wall) {
					img_wall.drawCentered(x*32,y*32 );
				} else {
					img_floor.drawCentered(x*32,y*32 );					
				}
			}
		}
	}
}
