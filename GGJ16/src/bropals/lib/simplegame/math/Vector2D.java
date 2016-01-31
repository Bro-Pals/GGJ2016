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
package bropals.lib.simplegame.math;

import java.awt.Graphics;

/**
 * A Vector with 2 components to it.
 * 
 * All the components are stored as floats, despite the 
 * getter and setter methods taking doubles.
 * @author Kevin Prehn
 */
public class Vector2D {
    
    private float x, y;
    
    /**
     * Make a new [0, 0] Vector.
     */
    public Vector2D() {
        x = 0;
        y = 0;
    }
    
    /**
     * Make a new Vector with the given initial values.
     * @param x The initial x component.
     * @param y The initial y component.
     */
    public Vector2D(double x, double y) {
        this.x = (float)x;
        this.y = (float)y;
    }

    /**
     * Returns the dot product between this vector and another vector.
     * @param other The other vector
     * @return The dot product between the two vectors.
     */
    public float dot(Vector2D other) {
        return (x * other.getX()) + (y * other.getY());
    }
    
    /**
     * Return a new vector whose magnitude is 1 and direction
     * is the same.
     * @return A new normalized vector
     */
    public Vector2D normalize() {
        float magn = magnitude();
        return new Vector2D(x / magn, y / magn);
    }
    
    /**
     * Normalize this vector, making it's magnitude 1 and the 
     * direction unchanged.
     */
    public void normalizeLocal() {
        float magn = magnitude();
        setX(x / magn);
        setY(y / magn);
    }
    
    /**
     * Make a new vector that is a copy of this vector but scaled.
     * @param factor The factor the new vector will be scaled by.
     * @return A new vector that is a copy of this vector but scaled 
     * by the given factor
     */
    public Vector2D scale(double factor) {
        return new Vector2D(x * factor, y * factor);
    }
    
    /**
     * Scale this vector by the given factor.
     * @param factor The factor this vector will be scaled
     */
    public void scaleLocal(double factor) {
        setX(x * factor);
        setY(y * factor);
    }
    
    /**
     * Make a new vector that is the sum of this and a given vector.
     * @param other The other vector
     * @return A new vector that is the sum of this and the given vector.
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.getX(), y + other.getY());
    }
    
    /**
     * Add the given vector to this vector.
     * @param other The vector being added.
     */
    public void addLocal(Vector2D other) {
        setX(x + other.getX());
        setY(y + other.getY());
    }
    
    /**
     * Returns the magnitude (length) of the vector.
     * @return The magnitude of the vector.
     */
    public float magnitude() {
        return (float)Math.sqrt((x * x) + (y * y));
    }
    
    /**
     * Returns the magnitude squared of the vector. This
     * is so the computer doesn't have to do a square root.
     * @return The magnitude of the vector squared
     */
    public float magnitudeSquared() {
        return (x * x) + (y * y);
    }
    
    /**
     * Set both values of the Vector.
     * @param x The new x component
     * @param y The new y component
     */
    public void setValues(double x, double y) {
        setX((float)x);
        setY((float)y);
    }
    
    /**
     * Returns the vector's x component
     * @return the vector's x component
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the vector's x component
     * @param x the vector's x component
     */
    public void setX(double x) {
        this.x = (float)x;
    }

    /**
     * Returns the vector's y component
     * @return the vector's y component
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the vector's y component
     * @param y the vector's y component
     */
    public void setY(double y) {
        this.y = (float)y;
    }
    
    /**
     * Creates a vector that has its direction rotated by the given angle.
     * @param angle the angle to offset from this vector, in radians.
     * @return the vector with the angle offset given.
     */
    public Vector2D vectorFromAngleOffset(float angle) {
        float cos = (float)ApproximatedMath.cos(angle);
        return new Vector2D(getX()*cos, getY()*cos);
    }
    
    /**
     * Sets this vector to a vector that has its direction rotated by the given
     * angle.
     * @param angle the angle to offset from this vector, in radians.
     */
    public void vectorFromAngleOffsetLocal(float angle) {
        float cos = (float)ApproximatedMath.cos(angle);
        setValues(getX()*cos, getY()*cos);
    }
    
    /**
     * Draws this Vector2D for debugging purposes.
     * @param g the graphics used to draw this vector
     * @param offsetX the X offset of the origin
     * @param offsetY the y offset of the origin
     */
    public void drawVector(Graphics g, float offsetX, float offsetY) {
        g.drawLine((int)offsetX, (int)offsetY, (int)(offsetX+getX()), (int)(offsetY+getY()));
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector2D) {
            return getX() == ((Vector2D)other).getX() && 
                   getY() == ((Vector2D)other).getY();
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "[ " + getX() + ", " + getY() + " ]";
    }
}
