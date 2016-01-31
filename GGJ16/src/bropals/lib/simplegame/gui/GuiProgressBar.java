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
package bropals.lib.simplegame.gui;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Implements a simple progress bar.
 * @author Jonathon
 */
public class GuiProgressBar extends GuiElement {

    private int value;
    private int maxValue;
    private Color progressBarColor;
    private Color backgroundColor;
    
    /**
     * Makes a GuiProgress bar for visually displaying progress on something.
     * @param x the x position of the element
     * @param y the y position of the element
     * @param w the width of the element
     * @param h the height of the element
     * @param value the initial progress value
     * @param maxValue the maximum progress value
     */
    public GuiProgressBar(int x, int y, int w, int h, int value, int maxValue) {
        super(x, y, w, h);
        this.value=value;
        this.maxValue= 0;
    }
    
    @Override
    public void render(Object graphicsObject) {
        Graphics g = (Graphics)graphicsObject;
        int progDraw = (int)((double)value/(double)maxValue * (double)getWidth());
        g.setColor(getProgressBarColor());
        g.fillRect(getX(), getY(), progDraw, getHeight());
        g.setColor(getBackgroundColor());
        g.fillRect(getX() + progDraw, getY(), getWidth()-progDraw, getHeight());
    }

    /**
     * Gets the current progress value
     * @return the current progress value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the current progress value
     * @param value the current progress value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the maximum value
     * @return the maximum value
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the maximum value
     * @param maxValue the maximum value
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Gets the color the bar is drawn in
     * @return the color of the bar
     */
    public Color getProgressBarColor() {
        return progressBarColor;
    }

    /**
     * Sets the color the bar is drawn in
     * @param progressBarColor the color the bar is drawn in
     */
    public void setProgressBarColor(Color progressBarColor) {
        this.progressBarColor = progressBarColor;
    }

    /**
     * Gets the color of the background
     * @return the background color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background color
     * @param backgroundColor the background color
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    /**
     * Sees if the current progress value has reached the max value.
     * @return the completion state
     */
    public boolean isComplete() {
        return value >= maxValue;
    }
    
    /**
     * Increase the progress. If the progress is over the max value, then
     * it is clamped down to match the max value.
     * @param progress the amount to increase the progress value by.
     */
    public void increaseProgress(int progress) {
        setValue(getValue()+progress);
        if (getValue() > getMaxValue()) {
            setValue(getMaxValue());
        }
    }
    
    /**
     * Sets the progress back to zero
     */
    public void resetProgress() {
        setValue(0);
    }
}
