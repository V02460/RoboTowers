import java.awt.geom.Point2D;

/**
 * Created by Matthias on 24.10.2015.
 *
 * General super class for all active units in the game.
 * Be they the players, mobile npcs or static towers.
 *
 */
public abstract class Units {

    private boolean alive;
    protected Point2D position;

    // direction angle in radians
    protected double direction;
    protected double strength;
    protected double speed;
    protected double health;

    protected Enum[] sockets;


    public void display(){
        //TODO: this
    }

    public void move(int timeSlice, float speedPercentage){
        double moveDist = this.speed * speedPercentage * timeSlice/1000.0;
        double newX = position.getX() + Math.cos(direction) * moveDist;
        double newY = position.getY() + Math.sin(direction) * moveDist;
        position.setLocation(newX, newY);
    }

    public void shoot(){
        //TODO: this
    }

    public void aim(Point2D targetPos){
        double x = targetPos.getX() - this.position.getX();
        double y = targetPos.getY() - this.position.getY();

        this.direction = Math.atan2(y, x);

    }

    public void loseHealth(float damage){
        if (alive) {
            health -= damage;

            if (health <= 0) {
                alive = false;
            }
        }
    }

    public void gainHealth(float heal){
        if (alive) {
            health += heal;
        }
    }

}
