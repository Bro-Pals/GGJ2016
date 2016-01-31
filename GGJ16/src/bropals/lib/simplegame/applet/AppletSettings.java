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
package bropals.lib.simplegame.applet;

/**
 * Contains information about how a GameApplet should be.
 * @author Jonathon
 */
public class AppletSettings {
    
    private final int width;
    private final int height;
    private final int framesPerSecond;

    /**
     * Creates a new AppletSettings object.
     * @param width the width of the applet
     * @param height the height of the applet
     * @param framesPerSecond the frames per second of the game in the applet
     */
    public AppletSettings(int width, int height, int framesPerSecond) {
        this.width = width;
        this.height = height;
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * Gets the width of the applet
     * @return the width of the applet
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the applet
     * @return the height of the applet
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the frames per second to run the applet at
     * @return the frames per second to run the applet at.
     */
    public int getFramesPerSecond() {
        return framesPerSecond;
    }
    
    
}
