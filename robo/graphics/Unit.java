package robo.graphics;

import org.newdawn.slick.SlickException;

import robo.collision.CollisionTest;
import robo.network.NetworkEntity;
import robo.network.Type;

import javax.vecmath.Point2d;

/**
 * Created by Matthias on 24.10.2015.
 *
 * General super class for all active units in the game.
 * Be they the players, mobile npcs or static towers.
 *
 *
 */
public class Unit extends NetworkEntity {

    private boolean alive;
    private boolean doShoot;

    // direction angle in radians
    private double aimDirection;
    private double strength;
    private double speed;
    private double maxSpeed;
    private double health;
    
    private int changeSpeed;
    private int changeDirection;

    private Materials[] sockets;

    private Entity armourBase;
    private Entity armourTower;
    private Entity weapon;


    public Unit(Point2d spawnPos, float spawnDirection, Materials[] sockets) throws SlickException{
        super("foundation.png", spawnPos, spawnDirection, 201, Type.PLAYER, new byte[0], true);

        this.alive = true;
        this.doShoot = false;

        CollisionTest.addUnit(this);

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
                case WHEELS:
                    if (this.maxSpeed == 0)
                        this.maxSpeed = 1;
                    this.maxSpeed += 1;
                    break;
                case ARMOUR:
                    this.health *= 2;
                    ++healthNum;
                    break;
                case WEAPON:
                    this.strength *= 2;
                    ++strengthNum;
                    break;
                default:
                    break;
            }
        }

        if (maxSpeed > 0) {
            this.setImageString("wheels.png");
        }



        // Choose gfx for armourBase and -Tower
        String armourBaseImg = "armourBase" + healthNum + ".png";
        String armourTowerImg = "armourTower" + healthNum + ".png";

        armourBase = new Entity(armourBaseImg, spawnPos, spawnDirection, 202);
        armourTower = new Entity(armourTowerImg, spawnPos, spawnDirection, 203);

        // Choose gfx for weapon
        String weaponImg = "weapon" + strengthNum + ".png";

        weapon = new Entity(weaponImg, spawnPos, spawnDirection, 204);

    }

    public void update() throws SlickException{
        double acceleration;
        if (changeSpeed == -1) {
            acceleration = -Math.pow(this.speed, 2)/10;
            //this.speed -= 0.1*this.maxSpeed;
        }
        else if (changeSpeed == 1) {
            acceleration  = Math.pow(this.maxSpeed - this.speed, 2)/10;
            //this.speed += 0.1*this.maxSpeed;
        }
        else {
            acceleration = -Math.pow(this.speed, 2)/100;
            //this.speed -= 0.01*this.maxSpeed;
        }


        float newDirection = this.getRotation();
        if (changeDirection == -1) {
            acceleration -= this.speed/20;
            //this.speed -= 0.1 * this.maxSpeed;
            newDirection -= Math.PI * 0.0125;
        }
        else if (changeDirection == 1) {
            acceleration -= this.speed/20;
            //this.speed -= 0.1 * this.maxSpeed;
            newDirection += Math.PI * 0.0125;
        }
        this.speed += acceleration;
        this.setRotation(newDirection);

        if (this.speed < 0 || this.speed < 0.2 && acceleration < 0) {
            this.speed = 0;
        }
        else if (this.speed > this.maxSpeed) {
            this.speed = this.maxSpeed;
        }

        double newX = this.getPosition().getX() + Math.cos(newDirection) * this.speed;
        double newY = this.getPosition().getY() + Math.sin(newDirection) * this.speed;
        this.setPosition(new Point2d(newX, newY));

        if (doShoot) {
            Projectile p = new Projectile(this.getPosition(), (float)this.aimDirection, (float) this.strength);
            p.update();
            doShoot = false;
        }
    }

    @Override
    public void setPosition(Point2d position){
        super.setPosition(position);
        armourBase.setPosition(position);
        armourTower.setPosition(position);
        weapon.setPosition(position);
    }

    @Override
    public void setRotation(float direction) {
        super.setRotation(direction);
        armourBase.setRotation(direction);
        armourTower.setRotation((float)this.aimDirection);
        weapon.setRotation((float)this.aimDirection);
    }


    public void aim(int diffX, int diffY){

        this.aimDirection = Math.atan2(diffY, diffX);

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

    public void giveShootOrder() {
        this.doShoot = true;
    }

    public void setChangeSpeed(int cs) {
    	changeSpeed = cs;
    }

    public void setChangeDirection(int cd) {
    	changeDirection = cd;
    }

    public void instantStop() {
    	speed = 0;
    	changeSpeed = 0;
    }

    @Override
    public void delete() {
    	super.delete();
    	CollisionTest.deleteUnit(this);
    }
}