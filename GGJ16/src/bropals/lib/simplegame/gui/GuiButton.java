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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **/
package bropals.lib.simplegame.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Represents a GUI button.
 * @author Jonathon
 */
public class GuiButton extends GuiElement {

    private BufferedImage down, up, hover, current = null;
    private GuiButtonAction guiButtonAction;
    private int downCounter = 0, downTime = 15;
    
    /**
     * Creates a new GuiButton by specifying its position, size, image data,
     * and press response.
     * @param x the x position.
     * @param y the y position.
     * @param w the width.
     * @param h the height.
     * @param down the image to show when the button is pressed.
     * @param up the image to show when the button is not being hovered over.
     * @param hover the image to show when the button is being hovered over.
     * @param guiButtonAction the method to call when this button is pressed.
     */
    public GuiButton(int x, int y, int w, int h,
            BufferedImage down, BufferedImage up, BufferedImage hover,
            GuiButtonAction guiButtonAction
    ) {
        super(x, y, w, h);
        this.down=down;
        this.up=up;
        this.hover=hover;
        this.guiButtonAction=guiButtonAction;
    }

    /**
     * Sets the number of frames the button is shown in the down state after
     * being pressed.
     * @param downTime the number of the frames the button is down. 
     */
    public void setDownTime(int downTime) {
        this.downTime=downTime;
    }
    
    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        if (downCounter > 0) {
            downCounter--;
        } else {
            if (contains(mouseX, mouseY)) {
                current = hover;
            } else {
                current = up;
            }
        }
    }

    @Override
    public void onMousePress() {
        current = down;
        downCounter = downTime;
        guiButtonAction.onButtonPress();
    }
    
    @Override
    public void render(Object graphicsObject) {
        ((Graphics)graphicsObject).drawImage(current, getX(), getY(), getWidth(), getHeight(), null);
    }

    public void setDown(BufferedImage down) {
        this.down = down;
    }

    public void setUp(BufferedImage up) {
        this.up = up;
    }

    public void setHover(BufferedImage hover) {
        this.hover = hover;
    }

    public void setGuiButtonAction(GuiButtonAction guiButtonAction) {
        this.guiButtonAction = guiButtonAction;
    }
    
    
}
