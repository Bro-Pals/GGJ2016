/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Jonathon Prehn and Kevin Prehn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package bropals.lib.simplegame.entity.block;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.entity.GameWorld;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The basic textured block in the game. You either give it an animation or 
 * an image to draw. Whichever one is set will be one that it uses to draw. 
 * If there is both an image and an animation, it will draw with the animation. 
 * If there is no image nor animation, it will draw a block with a black border
 * and dark gray center.
 * 
 * Setting tileImage to true makes it repeat the given image for the width 
 * and height of the entity. It doesn't cut off the final row and column of 
 * the image to make it fit exactly.
 * @author Kevin Prehn
 */
public class TexturedBlock extends BlockEntity {

    private Animation animation;
    private BufferedImage image;
    private boolean tileImage;
    
    /**
     * Create a TexturedBlock object, a subclass of BlockEntity. The 
     * block has no animation or image.
     * @param parent The parent of the block
     * @param x The block's x position
     * @param y The block's y position
     * @param width The block's width
     * @param height The block's height
     */
    public TexturedBlock(GameWorld parent, float x, float y, float width, 
            float height) {
        super(parent, x, y, width, height, true);
        animation = null;
        image = null;
        tileImage = false;
    }
    
    /**
     * Set the animation used by this textured block.
     * @param a The animation to be used.
     */
    public void setAnimation(Animation a) {
        this.animation = a;
    }
    
    /**
     * Set the image for this block.
     * @param bi The image that will become the texture of this image.
     */
    public void setImage(BufferedImage bi) {
        this.image = bi;
        this.tileImage = false;
    }
    
    /**
     * Set the image for this block and set if this image will be tiled.
     * @param tile Whether or not the image should repeat itself for the size
     * of the entity when being drawn
     */
    public void setTileImage(boolean tile) {
        this.tileImage = tile;
    }
    
    @Override
    public void update(int mills) {
        super.update(mills);
        
        if (animation != null)
            animation.update(animation.getTrackOn().getMillisBetweenImages());
    }
    
    @Override
    public void render(Object graphicsObj) {
        Graphics2D g2 = (Graphics2D) graphicsObj;
        if (animation != null) {
            g2.drawImage(animation.getCurrentImage(), (int)getX(), (int)getY(), null);
        } else if (image != null) {
            if (tileImage) {
                for (int x=0; x<getWidth(); x+=image.getWidth()) {
                    for (int y=0; y<getHeight(); y+=image.getHeight()) {
                        g2.drawImage(image, (int)(getX() + x), 
                                (int)(getY() + y), null);
                    }
                }
            } else {
                g2.drawImage(image, (int)getX(), (int)getY(), null);
            }
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect((int)getX() + 2, (int)getY() + 2, (int)getWidth() - 4,
                    (int)getHeight() - 4);
        }
    }

}
