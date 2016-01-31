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

/**
 * A simpler version of display mode. Holds only the screen width and height.
 *
 * @author Jonathon
 */
public class ScreenResolution {

    private final int screenWidth;
    private final int screenHeight;

    public ScreenResolution(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Gets the width of the screen for this ScreenResolution.
     * @return the width of the screen for this ScreenResolution
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Gets the height of the screen for this ScreenResolution.
     * @return the height of the screen for this ScreenResolution
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    public boolean equals(Object object) {
        ScreenResolution res = (ScreenResolution)object;
        return getScreenWidth()==res.getScreenWidth() && 
                getScreenHeight()==res.getScreenHeight();
    }
}
