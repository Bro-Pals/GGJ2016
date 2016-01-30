/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.officeobjects;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.entity.GameWorld;
import ggj16.Camera;
import ggj16.OfficeObject;
import ggj16.Task;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class OfficeTaskObject extends OfficeObject {

    private Task associatedTask;
    
    public OfficeTaskObject(GameWorld par, float x, BufferedImage image, Camera camera, Task associatedTask) {
        super(par, x, image, camera);
        this.associatedTask = associatedTask;
    }

    public OfficeTaskObject(GameWorld par, float x, Animation animation, Camera camera, Task associatedTask) {
        super(par, x, animation, camera);
        this.associatedTask = associatedTask;
    }

    public Task getAssociatedTask() {
        return associatedTask;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }
    
    public boolean collidesWith(PlayerDemon player) {
        float half = player.getX() + (player.getWidth()/2);
        return half > getX() && half < getX() + getWidth();
    }
}
