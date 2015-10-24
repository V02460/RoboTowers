import javax.vecmath.Vector2d;

/**
 * Created by Matthias on 24.10.2015.
 *
 * A single projectile fired by a robot
 */
public class Projectile extends NetworkEntity {
    private static float speed = 10;

    private float power;

    public Projectile(Vector2d spawnPosition, float spawnDirection, float spawnPower) {
        super(spawnPosition, spawnDirection);

        this.power = spawnPower;
    }

    public void update() {

        float direction = this.getDirection();
        double newX = this.getPosition().getX() + Math.cos(direction) * this.speed;
        double newY = this.getPosition().getX() + Math.sin(direction) * this.speed;
        this.setPosition(new Vector2d(newX, newY));
    }

    public float getPower() {
        return this.power;
    }
}
