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
package bropals.lib.simplegame.math;

/**
 * A Vector2D that is similar to a normal Vector2D only has maximum values.
 * These maximum values apply for both the positive and negative directions.
 * @author Kevin Prehn
 */
public class Vector2DCapped extends Vector2D {
    
    private float xLimit, yLimit;
    
    /**
     * Creates a Vector2DCapped object, with the given maximum values.
     * @param x The initial x value
     * @param y The initial y value
     * @param xLimit The limit on the x value
     * @param yLimit The limit on the y value
     */
    public Vector2DCapped(float x, float y, float xLimit, float yLimit) {
        super(x, y);
        this.xLimit = xLimit;
        this.yLimit = yLimit;
        // call them to make the values capped if needed
        setX(x);
        setY(y);
    }

    /**
     * Sets the vector's x component. If the given value is out 
     * of the limit, the x value is set to the limit instead.
     * @param x the vector's x component
     */
    @Override
    public void setX(double x) {
        if (x > xLimit)
            super.setX(xLimit);
        else if (x < -xLimit)
            super.setX(-xLimit);
        else
            super.setX(x);
    }

    /**
     * Sets the vector's y component. If the given value is out 
     * of the limit, the y value is set to the limit instead.
     * @param y the vector's y component
     */
    @Override
    public void setY(double y) {
        if (y > yLimit)
            super.setY(yLimit);
        else if (y < -yLimit)
            super.setY(-yLimit);
        else
            super.setY(y);
    }

}
