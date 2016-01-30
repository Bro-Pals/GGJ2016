
package ggj16;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public class OfficeObject extends BaseEntity {

    private float x;
    private BufferedImage image;
    private Animation animation;
    
    /**
     * An office object
     * @param par the game world that this object is in
     * @param x the left edge of the object
     */
    public OfficeObject(GameWorld par, float x) {
        super(par);
        this.x = x;
    }

    @Override
    public void update(int i) {
        
    }

    @Override
    public void render(Object o) {
        //A CLIPPED graphics2d object for the office part of the screen
        
    }

    /**
     * Sets the object to draw as a still image. This replaces the current
     * image or animation.
     * @param image the still image to draw it on.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        animation = null;
    }
    
    /**
     * Sets the object to draw as an animation. This replaces the current
     * image or animation.
     * @param animation the animation to set to.
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
        image = null;
    }
    
}
