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

import bropals.lib.simplegame.logger.InfoLogger;

/**
 * Represents a 3x3 matrix.
 * @author Jonathon
 */
public class Matrix3D {
    
    protected float m00, m01, m02,
                  m10, m11, m12,
                  m20, m21, m22 ;
    
    /**
     * Creates a 2x3 matrix of the form
     * <p>
     * [ 1 0 0 ]<br>
     * [ 0 1 0 ]<br>
     * [ 0 0 1 ]
     */
    public Matrix3D() {
        set(1, 0, 0,
            0, 1, 0,
            0, 0, 1);
    }
    
    public Matrix3D(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        set(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }
    
    /**
     * Sets the values of the matrix. It will be in the form
     * <p>
     * [ m00 m01 m02 ]<br>
     * [ m10 m11 m12 ]<br>
     * [ m20 m21 m22 ]
     * @param m00 row 1 column 1
     * @param m01 row 1 column 2
     * @param m02 row 1 column 3
     * @param m10 row 2 column 1
     * @param m11 row 2 column 2
     * @param m12 row 2 column 3
     * @param m20 row 3 column 1
     * @param m21 row 3 column 2
     * @param m22 row 3 column 3
     */
    public void set(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }
    
    /**
     * Creates a new matrix that is this matrix scaled by the given factor.
     * @param scalar the number to scale the matrix by
     * @return the scaled matrix
     */
    public Matrix3D multiply(float scalar) {
        return new Matrix3D(
            m00*scalar, m01*scalar, m02*scalar,
            m10*scalar, m11*scalar, m12*scalar,
            m20*scalar, m21*scalar, m22*scalar
        );
    }
    
    /**
     * Scales this matrix by the given factor.
     * @param scalar the number to scale the matrix by
     */
    public void multiplyLocal(float scalar) {
        set(
            m00*scalar, m01*scalar, m02*scalar,
            m10*scalar, m11*scalar, m12*scalar,
            m20*scalar, m21*scalar, m22*scalar
        );
    }
        
    /**
     * Creates a new Matrix3D that is the concatenation of this matrix to the other.
     * (this * other)
     * @param o the other matrix.
     * @return the concatenation of these two matrices.
     */
    public Matrix3D concatenate(Matrix3D o) {
        return new Matrix3D(
            (m00*o.m00) + (m01*o.m10) + (m02*o.m20), (m00*o.m01) + (m01*o.m11) + (m02*o.m21), (m00*o.m02) + (m01*o.m12) + (m02*o.m22),
            (m10*o.m00) + (m11*o.m10) + (m12*o.m20), (m10*o.m01) + (m11*o.m11) + (m12*o.m21), (m10*o.m02) + (m11*o.m12) + (m12*o.m22),
            (m20*o.m00) + (m21*o.m10) + (m22*o.m20), (m20*o.m01) + (m21*o.m11) + (m22*o.m21), (m20*o.m02) + (m21*o.m12) + (m22*o.m22)
        );
    }
    
    /**
     * Transforms the given vector by this matrix, return the new vector.
     * No Z component is needed for this transformation.
     * @param v the vector to transform by this matrix.
     * @return the transformation.
     */
    public Vector2D transform(Vector2D v) {
        return new Vector2D(
            (m00*v.getX()) + (m01*v.getY()) + m02,
            (m10*v.getX()) + (m11*v.getY()) + m12
        );
    }
    
    /**
     * Transforms the given vector by this matrix, setting it to the transformed
     * version.
     * No Z component is needed for this transformation.
     * @param v the vector to transform by this matrix, and set to the
     * result.
     */
    public void transformLocal(Vector2D v) {
        v.setValues(
            (m00*v.getX()) + (m01*v.getY()) + m02,
            (m10*v.getX()) + (m11*v.getY()) + m12
        );
    }
    
    /**
     * A faster way to cause a translation to occur on this matrix. Use this
     * if possible.
     * @param x the x translation
     * @param y the y translation
     */
    public void translate(float x, float y) {
        this.m02 += x;
        this.m12 += y;
    }
    
    /**
     * A faster way to cause a rotation to occur on this matrix. Use this
     * if possible.
     * @param angle the angle to rotate this matrix by
     */
    public void rotate(float angle) {
        float cos = ApproximatedMath.cos(angle);
        float sin = ApproximatedMath.sin(angle);
        float newM00 = (m00*cos) + (m01*sin);
        m01 = -(m00*sin) + (m01*cos);
        m00 = newM00;
        float newM10 = (m10*cos) + (m11*sin);
        m11 = -(m10*sin) + (m11*sin);
        m10 = newM10;
    }
    
    /**
     * Calculates and returns the determinant of this matrix.
     * @return the determinant of this matrix.
     */
    public float determinant() {
        return m00*(m11*m22-m12*m21) - m01*(m10*m22-m12*m20) + m02*(m10*m21-m11*m20);     
    }
    
    /**
     * Determines if this matrix has an inverse.
     * @return if the matrix has an inverse
     */
    public boolean hasInverse() {
        return determinant()!=0;
    }
    
    /**
     * Calculates and returns the inverse of this matrix, if it has one. If this
     * matrix does not have an inverse, then it returns <code>null</code>
     * @return the inverse of this matrix.
     */
    public Matrix3D inverse() {
        float det = determinant();
        if (det != 0) {
            Matrix3D inverse = new Matrix3D(
                m22*m11-m21*m12, -m22*m01+m21*m02, m12*m01-m11*m02,
                -m22*m10+m20*m12, m22*m00-m20*m02, -m12*m00+m10*m02,
                m21*m10-m20*m11, -m21*m00+m20*m01, m11*m00-m10*m01
            );
            inverse.multiplyLocal(1/det);
            return inverse;
        } else {
            return null;
        }
    }
    
    /**
     * Creates a translation matrix.
     * @param x the x translation.
     * @param y the y translation.
     * @return the matrix that represents a translation.
     */
    public static Matrix3D createTranslationMatrix(float x, float y) {
        return new Matrix3D(1, 0, x,
                           0, 1, y, 
                           0, 0, 1);
    }
    
    /**
     * Creates a rotation matrix to rotate about the z axis.
     * @param angle the angle to rotate
     * @return the matrix that represents a rotation.
     */
    public static Matrix3D createRotationMatrix(float angle) {
        float cos = ApproximatedMath.cos(angle);
        float sin = ApproximatedMath.sin(angle);
        return new Matrix3D(cos, -sin, 0,
                           sin, cos,  0, 
                           0,   0,    1);
    }
    
    /**
     * Creates a scaling matrix to scale space.
     * @param x the x factor to scale space by
     * @param y the y factor to scale space by.
     * @return the matrix that represents a scaling of space.
     */
    public static Matrix3D createScalingMatrix(float x, float y) {
        return new Matrix3D(x, 0, 0,
                           0, y, 0, 
                           0, 0, 1);
    }

    /**
     * Gets the float array version of these values.
     * @return the float array version.
     */
    public float[] toFloatArray() {
        return new float[] { 
            m00, m01, m02,
            m10, m11, m12,
            m20, m21, m22
        };
    }
    
    /**
     * Prints this matrix to the InfoLogger.
     */
    public void printMatrix() {
        InfoLogger.println("[ " + m00 + " " + m01 + " " + m02 + " ]");
        InfoLogger.println("[ " + m10 + " " + m11 + " " + m12 + " ]");
        InfoLogger.println("[ " + m20 + " " + m21 + " " + m22 + " ]");
    }
}
