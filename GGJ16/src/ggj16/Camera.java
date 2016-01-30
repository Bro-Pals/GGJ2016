/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16;

/**
 *
 * @author Jonathon
 */
public class Camera {

    private static final int CAMERA_MIN = 0; //The left edge
    private static final int CAMERA_MAX = 3000; //The right edge

    private float x;

    public float getXLocation() {
        return x;
    }

    public void setXLocation(float x) {
        if (x >= CAMERA_MIN && x <= CAMERA_MAX-800) {
            this.x = x;
        } else if (x < CAMERA_MIN) {
            this.x = CAMERA_MIN;
        } else if (x > CAMERA_MAX-800) {
            this.x = CAMERA_MAX-800;
        }
    }
    
    public static int getCameraMax() {
        return CAMERA_MAX;
    }
    
    public static int getCameraMin() {
        return CAMERA_MIN;
    }
}
