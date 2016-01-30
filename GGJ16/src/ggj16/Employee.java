
package ggj16;

import bropals.lib.simplegame.entity.GameWorld;
import java.awt.image.BufferedImage;

/**
 * just like in coding class
 * @author Kevin
 */
public class Employee extends OfficeObject {

    public final static int WORKING = 0, DEAD = 1;
    int state;
    int positionX;
    boolean beingAttackedByImp;
            
    public Employee(GameWorld par, float x, BufferedImage image, Camera camera) {
        super(par, x, image, camera);
        state = WORKING;
        beingAttackedByImp = false;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        switch(state) {
            case WORKING:
                setImage(getParent().getState().getImage("worker"));
                break;
            case DEAD:
                setImage(null);
                break;
        }
    }

    public void setState(int state) {
        this.state = state;
    }
    
    public int getState() {
        return state;
    }

    public boolean isBeingAttackedByImp() {
        return beingAttackedByImp;
    }

    public void setBeingAttackedByImp(boolean beingAttackedByImp) {
        this.beingAttackedByImp = beingAttackedByImp;
    }
    
    
}
