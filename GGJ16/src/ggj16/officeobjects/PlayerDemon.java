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
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class PlayerDemon extends OfficeObject implements KeyListener {

    private boolean right = false;
    private boolean left = false;
    private int speed = 6;
    private BufferedImage standingLeft;
    private BufferedImage standingRight;
    private BufferedImage standingImage;
    private Animation leftAnim;
    private Animation rightAnim;
    private Animation curAnim;
    
    public PlayerDemon(GameWorld par, float x, Animation left, Animation right, Camera camera) {
        super(par, x, (Animation)null, camera);
        this.leftAnim = left;
        this.rightAnim = right;
        leftAnim.update(0);
        rightAnim.update(0);
        standingRight = par.getState().getImage("demon2");
        par.getState().getAssetManager().createHorizontialFlipCopy(standingRight, "standingLeft");
        standingLeft = par.getState().getImage("demon2Left");
        standingImage = standingRight;
        setImage(standingImage);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        //Move the player. The tasks updating and whatnot will be done in PlayState
        if (right) {
            curAnim = rightAnim;
            translateX(speed);
            translateX(6);
            standingImage = standingRight;
        } else if (left) {
            curAnim = leftAnim;
            translateX(-speed);
            translateX(-6);
            standingImage = standingLeft;
        } else if (!right && !left) {
            setImage(standingImage);
            curAnim = null;
        }
        if (curAnim != null) {
            setAnimation(curAnim);
            curAnim.update(delta);
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
