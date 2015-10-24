package graphics;

import javax.vecmath.Point2d;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Entity {
	private Point2d position;
	private float rotation;
	private Image image;
	private int layer;
	
	public Entity(String path, Point2d p, float r, int l) throws SlickException {
		setPosition(p);
		setRotation(r);
		setLayer(l);
		image = new Image(path); // Should take care to open each image only once for all tiles
	}
	
	public Point2d getPosition() {
		return position;
	}

	public void setPosition(Point2d p) {
		position=p;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int l) {
		layer=l;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float r) {
		rotation=r;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(String path) throws SlickException {
		image = new Image(path);
	}
	
	public boolean equals(Object o){
		System.out.println(((Entity) o).getLayer() + " " + getLayer());
		if(o instanceof Entity) {
			return ((Entity) o).getLayer()==getLayer();
		}
		return false;
	}
	
}
