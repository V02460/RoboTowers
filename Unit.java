import javax.vecmath.Vector2d;

/**
 * Created by Matthias on 24.10.2015.
 *
 * General super class for all active units in the game.
 * Be they the players, mobile npcs or static towers.
 *
 */
public abstract class Unit extends NetworkEntity {

    private boolean alive;

    // direction angle in radians
    private double aimDirection;
    private double strength;
    private double speed;
    private double maxSpeed;
    private double health;

    private Materials[] sockets;

    private Entity armourBase;
    private Entity armourTower;
    private Entity weapon;


    public Unit(Vector2d spawnPos, float spawnDirection, Materials[] sockets) {

        this.speed = 0;
        this.maxSpeed = 0;

        this.strength = 1;
        this.health = 10;

        int healthNum = 0;
        int strengthNum = 0;
        // set values for maxSpeed, strength, health
        // values NOT final
        for (int i = 0; i < sockets.length; i++) {
            switch(sockets[i]) {
                case Wheels:
                    this.maxSpeed += 1;
                    break;
                case Armour:
                    this.health *= 2;
                    ++healthNum;
                    break;
                case Weapon:
                    this.strength *= 2;
                    ++strengthNum;
                    break;
                default:
                    break;
            }
        }

        // Choose gfx for base
        String baseImg;
        if (maxSpeed > 0) {
            baseImg = "wheels.png";
        }
        else {
            baseImg = "foundation.png";
        }

        super(baseImg, spawnPos, spawnDirection);

        // Choose gfx for armourBase and -Tower
        String armourBaseImg = "armourBase" + healthNum + ".png";
        String armourTowerImg = "armourTower" + healthNum + ".png";

        armourBase = new Entity(armourBaseImg, spawnPos, spawnDirection);
        armourTower = new Entity(armourTowerImg, spawnPos, spawnDirection);

        // Choose gfx for weapon
        String weaponImg = "weapon" + strengthNum + ".png";

        weapon = new Entity(weaponImg, spawnPos, spawnDirection);

}


    public void update() {
        this.update(0, 0);
    }

    public void update(int changeSpeed) {
        this.update(changeSpeed, 0);
    }

    public void update(int changeSpeed, int changeDirection){
        if (changeSpeed == -1) {
            this.speed -= 0.1*this.maxSpeed;
        }
        else if (changeSpeed == 1) {
            this.speed += 0.1*this.maxSpeed;
        }
        else {
            this.speed -= 0.01*this.maxSpeed;
        }

        if (this.speed < 0) {
            this.speed = 0;
        }
        else if (this.speed > this.maxSpeed) {
            this.speed = this.maxSpeed;
        }

        float newDirection = this.getDirection();
        if (changeDirection == -1) {
            // by pi/40
            this.speed -= 0.02 * this.maxSpeed;
            newDirection -= Math.PI * 0.025;
        }
        else if (changeDirection == 1) {
            this.speed -= 0.02 * this.maxSpeed;
            newDirection += Math.PI * 0.025;
        }
        this.setDirection(newDirection);

        double newX = this.getPosition().getX() + Math.cos(newDirection) * this.speed;
        double newY = this.getPosition().getX() + Math.sin(newDirection) * this.speed;
        this.setPosition(new Vector2d(newX, newY));
    }

    @Override
    public void setPosition(Vector2d position){
        super.setPosition(position);
        armourBase.setPosition(position);
        armourTower.setPosition(position);
        weapon.setPosition(position);
    }

    public void shoot(){
        Projectile bullet = new Projectile(this.getPosition(), this.getDirection());
         //TODO: register projectiles for Collision Detection
    }

    public void aim(Vector2d targetPos){
        double x = targetPos.getX() - this.getPosition().getX();
        double y = targetPos.getY() - this.getPosition().getY();

        this.aimDirection = Math.atan2(y, x);

    }

    public void loseHealth(float damage){
        if (alive) {
            health -= damage;

            if (health <= 0) {
                alive = false;
                //TODO: play Death-Animation
            } else {
                //TODO: play Damage-Animation
            }
        }
    }

    public void gainHealth(float heal){
        if (alive) {
            health += heal;
        }
    }

}
