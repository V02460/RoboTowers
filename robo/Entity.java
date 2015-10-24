package robo;

import javax.vecmath.Point2d;

class Entity
{
	private Point2d position;
	private float rotation;

	public Entity(String imagePath, Point2d position, float rotation)
	{
		this.position = position;
		this.rotation = rotation;
	}

	public Point2d getPosition()
	{
		return position;
	}

	public void setPosition(Point2d position)
	{
		this.position = position;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}
}