package robo.graphics;

import org.newdawn.slick.SlickException;

import robo.collision.CollisionTest;
import robo.network.NetworkEntity;
import robo.network.Type;

import javax.vecmath.Point2d;

/**
 * Created by Matthias on 24.10.2015.
 *
 * A single projectile fired by a robot
 *
 */
public class Projectile extends NetworkEntity {
    private static float speed = 10;

    private float power;

    public Projectile(Point2d spawnPosition, float spawnDirection, float spawnPower) throws SlickException{
        super("projectile.png", spawnPosition, spawnDirection, 101, Type.BULLET, new byte[0], true);

        CollisionTest.addBullet(this);
        this.power = spawnPower;
    }

    public void update() {

        float direction = this.getRotation();
        double newX = this.getPosition().x + Math.cos(direction) * this.speed;
        double newY = this.getPosition().y + Math.sin(direction) * this.speed;
        this.setPosition(new Point2d(newX, newY));
    }

    public float getPower() {
        return this.power;
    }

    @Override
    public void delete() {
    	super.delete();
    	CollisionTest.deleteBullet(this);
    }
}
