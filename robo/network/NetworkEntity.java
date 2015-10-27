package robo.network;

import org.newdawn.slick.SlickException;
import javax.vecmath.Point2d;

import robo.graphics.Entity;
import robo.FrameCounter;
import robo.graphics.EntityUpdateList;

public class NetworkEntity extends Entity
{
	private static NetworkEnviroment ne;

	private long dirtyFrame = 0;
	private boolean doUpdates;

	public static void setNetworkEnviroment(NetworkEnviroment networkEnviroment)
	{
		ne = networkEnviroment;
	}

	public NetworkEntity(String imagePath, Point2d position, float rotation,
			int layer, Type type, byte[] params, boolean doUpdates) 
				throws SlickException
	{
		super(imagePath, position, rotation, layer);

		this.doUpdates = doUpdates;

//		if (doUpdates) {
//			EntityUpdateList.addEntity(this);
//		}
		ne.createEntity(type, params, this, null);

	}

	@Override
	public void setPosition(Point2d position)
	{
		super.setPosition(position);

		if (!doUpdates) return;

		dirtyFrame = FrameCounter.getFrameCount();
	}

	@Override
	public void setRotation(float rotation)
	{
		super.setRotation(rotation);

		if (!doUpdates) return;

		dirtyFrame = FrameCounter.getFrameCount();
	}

	void setPositionNoUpdate(Point2d position)
	{
		super.setPosition(position);
	}

	void setRotationNoUpdate(float rotation)
	{
		super.setRotation(rotation);
	}

	public void update() throws SlickException{}

	// 1. Send UDP pos, rotation
	// 2. Send TCP creation, shots, hits
	// 2. smooth movement / extrapolate by velocity or interpolation function with params (user input)
	// delayed hit display
}