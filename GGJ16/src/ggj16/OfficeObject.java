
package ggj16;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public class OfficeObject extends BaseEntity {

    private float x;
    private BufferedImage image;
    private Animation animation;
    private Camera camera;
    
    /**
     * An office object
     * @param par the game world that this object is in
     * @param x the left edge of the object
     * @param image the still image to draw this object as
     * @param camera the camera to control where it is drawn
     */
    public OfficeObject(GameWorld par, float x, BufferedImage image, Camera camera) {
        super(par);
        this.x = x;
        this.image = image;
        this.camera = camera;
        animation = null;
    }
    
    /**
     * An office object
     * @param par the game world that this object is in
     * @param x the left edge of the object
     * @param animation the animation to draw this object as
     * @param camera the camera to control where it is drawn
     */
    public OfficeObject(GameWorld par, float x, Animation animation, Camera camera) {
        super(par);
        this.x = x;
        this.image = null;
        this.animation = animation;
        this.camera = camera;
    }

    @Override
    public void update(int delta) {
        if (animation != null) {
            animation.update(delta);
        }
    }

    public int getWidth() {
        if (animation != null) {
            return animation.getCurrentImage().getWidth();
        } else if (image != null) {
            return image.getWidth();
        } else {
            return 0;
        }
    }
    
    public int getHeight() {
        if (animation != null) {
            return animation.getCurrentImage().getHeight();
        } else if (image != null) {
            return image.getHeight();
        } else {
            return 0;
        }
    }
    
    @Override
    public void render(Object o) {
        //A CLIPPED graphics2d object for the office part of the screen
        Graphics2D g = (Graphics2D)o;
        if (animation != null) {
            g.drawImage(animation.getCurrentImage(), (int)(x-camera.getXLocation()),  300-animation.getCurrentImage().getHeight(), null);
        }
        if (image != null) {
            g.drawImage(image, (int)(x-camera.getXLocation()),  300-image.getHeight(), null);
        }
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        if (this.x < Camera.getCameraMin()) {
            this.x = Camera.getCameraMin();
        } else if (this.x+getWidth() > Camera.getCameraMax()) {
            this.x = Camera.getCameraMax()-getWidth();
        }
    }
    

    
    public void translateX(float x) {
        setX(this.x+=x);
    }
}
