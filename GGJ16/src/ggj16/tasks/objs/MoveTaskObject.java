
package ggj16.tasks.objs;

import bropals.lib.simplegame.entity.GameWorld;

/**
 * uuugh
 * @author Kevin
 */
public class MoveTaskObject extends TaskObject {

    // uuuuugh
    float x, y, direction, velocity;
    
    public MoveTaskObject(GameWorld par) {
        super(par);
        x = 0;
        y = 0;
        direction = 0;
        velocity = 0;
    }
    
    public MoveTaskObject(GameWorld par, float x, float y, float direction, float velocity) {
        super(par);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.velocity = velocity;
    }

    @Override
    public void update(int i) {
        super.update(i);
        
        // uuuughghghhgh
        x += (float)(Math.cos(direction) * velocity);
        y += (float)(Math.sin(direction) * velocity);
    }
    
    /**
     * See if this object is close enough to the other object
     * @param other The other object
     * @param distance The distance to check.
     * @return Return true if the other object is at least as close as the distance
     *         variable passed in.
     */
    public boolean closeTo(MoveTaskObject other, int distance) {
        float diffX = x - other.getX();
        float diffY = y - other.getY();
        return ((diffX * diffX) + (diffY * diffY)) < (distance * distance);
    }
    
    //// UUUUUGHGHGHGHG

    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getDirection() {
        return direction;
    }

    public float getVelocity() {
        return velocity;
    }

    
}
