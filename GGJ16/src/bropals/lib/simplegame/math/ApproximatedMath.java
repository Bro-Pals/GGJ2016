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
 * Approximations of different equations that, while not perfectly accurate, 
 * will be a lot faster than using the {@link java.lang.Math Math} class.
 * @author Jonathon
 */
public class ApproximatedMath {
    /**
     * {@link java.lang.Math#PI Math.PI} casted to a float.
     */
    public static final float PI_F = (float)Math.PI;
    
    /**
     * {@link java.lang.Math#PI Math.PI}*2 casted to a float.
     */
    public static final float TWO_PI_F = (float)(Math.PI*2);
    
    /**
     * Gets the cosine of the angle.
     * @param angle the angle, in radians.
     * @return the cosine of the angle.
     */
    public static float cos(float angle) {
        angle = angle%TWO_PI_F;
        if (angle > PI_F) {
            angle -= TWO_PI_F;
        }
        return 1 - (angle*angle/2) +  //2nd power
                (angle*angle*angle*angle/24) - //4th power
                (angle*angle*angle*angle*angle*angle/720); //6th power
    }
    
    /**
     * Gets the sin of the angle.
     * @param angle the angle, in radians.
     * @return the sin of the angle.
     */
    public static float sin(float angle) {
        angle = angle%TWO_PI_F;
        if (angle > PI_F) {
            angle -= TWO_PI_F;
        }
        return angle - (angle*angle*angle/6) + //3rd power
                (angle*angle*angle*angle*angle/120) - //5th power
                (angle*angle*angle*angle*angle*angle*angle/5040); //7th power
    }
}
