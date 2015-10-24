package graphics;

import javax.vecmath.Point2d;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Entity {
	private Point2d position;
	private float rotation;
	private Image image;
	private int layer;
	
	public Entity(String path, Point2d p, float r) throws SlickException {
		setPosition(p);
		setRotation(r);
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
}
