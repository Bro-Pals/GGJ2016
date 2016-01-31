/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame.leveldesigner;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Implements some of the common methods of EditableEntity.
 * 
 * @author Jonathon
 */
public abstract class AbstractEditableEntity implements EditableEntity {

    private float x, y, width, height;
    private int layer;
    
    public AbstractEditableEntity() {
        x=0; y=0; width=0; height=0; layer=0;
    }

    public AbstractEditableEntity(float x, float y, float width, float height, int layer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.layer = layer;
    }
        
    @Override
    public void setRenderLayer(int layer) {
        this.layer=layer;
    }

    @Override
    public void setX(float x) {
        this.x=x;
    }

    @Override
    public void setY(float y) {
        this.y=y;
    }

    @Override
    public void setWidth(float width) {
        this.width=width;
    }

    @Override
    public void setHeight(float height) {
        this.height=height;
    }

    @Override
    public int getRenderLayer() {
        return layer;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void renderForLevelEditor(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        g.setColor(Color.BLACK);
        g.fillRect((int)(getX()-cameraOffsetX), (int)(getX()-cameraOffsetY), 
                (int)getWidth(), (int)getHeight());
    }
}
