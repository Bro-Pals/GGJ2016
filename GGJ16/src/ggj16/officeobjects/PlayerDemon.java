/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.officeobjects;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.KeyListener;
import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.entity.GameWorld;
import ggj16.Camera;
import ggj16.OfficeObject;

/**
 *
 * @author Jonathon
 */
public class PlayerDemon extends OfficeObject implements KeyListener {

    private boolean right = false;
    private boolean left = false;
    private int speed = 6;
    
    public PlayerDemon(GameWorld par, float x, Animation playerAnimation, Camera camera) {
        super(par, x, playerAnimation, camera);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        //Move the player. The tasks updating and whatnot will be done in PlayState
        if (right) {
            translateX(speed);
        }
        if (left) {
            translateX(-speed);
        }
    }
    
    @Override
    public void key(int key, boolean pressed) {
        if (key == KeyCode.KEY_RIGHT) {
            right = pressed;
        } else if (key == KeyCode.KEY_LEFT) {
            left = pressed;
        }
    } 

    public void setSpeed(int speed) {
        this.speed = speed;
    }
   
}
