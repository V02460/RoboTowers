package robo.graphics;

import javax.vecmath.Point2d;



public class Entity {
	private Point2d position;
	private float rotation;				// Saved in Radians
	private String image;
	private int layer;
	
	public Entity(String path, Point2d p, float r, int l) {
		this.position = p;
		this.rotation = r;
		this.layer = l;
		image = "robo/res/gfx/" + path;
		EntityList.addEntity(this);

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
	
	public String getImageString() {
		return image;
	}

	public void setImageString(String path){
		image = "robo/res/gfx/" + path;
	}

	public void delete() {
		EntityList.deleteEntity(this);
	}
}