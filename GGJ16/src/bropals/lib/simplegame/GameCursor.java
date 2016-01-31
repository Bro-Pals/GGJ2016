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
package bropals.lib.simplegame;

import java.awt.image.BufferedImage;

/**
 * Represents a cursor drawn with an image.
 * @author Jonathon
 */
public class GameCursor {
    
    private BufferedImage image;
    private int offsetX;
    private int offsetY;
    
    /**
     * Creates a new GameCursor.
     * @param image
     * @param offsetX
     * @param offsetY 
     */
    public GameCursor(BufferedImage image, int offsetX, int offsetY) {
        this.image=image;
        this.offsetX=offsetX;
        this.offsetY=offsetY;
    }

    /**
     * Gets the image for this GameCursor.
     * @return the image for this GameCursor
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets the image for this GameCursor
     * @param image the image for this GameCursor
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Gets the X offset at which the image's top left corner will be
     * from the cursor's hotspot.
     * @return the x offset
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * Sets the X offset at which the image's top left corner will be
     * from the cursor's hotspot.
     * @param offsetX the x offset
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Gets the Y offset at which the image's top left corner will be
     * from the cursor's hotspot.
     * @return the y offset
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * Sets the Y offset at which the image's top left corner will be
     * from the cursor's hotspot.
     * @param offsetY the y offset
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    
}
