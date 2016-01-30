/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.gui;

import bropals.lib.simplegame.gui.GuiElement;
import ggj16.PlayState;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class ClockGui extends GuiElement {
    
    private PlayState ps;
    private BufferedImage clockHand;
    private BufferedImage clock;
    private AffineTransform trans;
    private int offsetX;
    private int offsetY;
    
    public ClockGui(int x, int y, int w, int h, PlayState ps) {
        super(x, y, w, h);
        this.ps = ps;
        clockHand = ps.getImage("clockhand");
        clock = ps.getImage("clockface");
        trans = new AffineTransform();
        setClockRotation(0);
    }
    
    @Override
    public void render(Object o) {
        Graphics2D g = (Graphics2D)o;
        g.drawImage(clock, getX(), getY(), getWidth(), getHeight(), null);
        AffineTransform old = g.getTransform();
        g.setTransform(trans);
        g.drawImage(clockHand, getX()+97, getY()+144, null);
        g.setTransform(old);
    }
    
    public void setClockRotation(int hour) {
        double rot = Math.PI/2 + ((float)(hour)/6 * Math.PI);
        offsetX = (int)(Math.cos(rot)*clockHand.getWidth()/2);
        offsetY = (int)(Math.sin(rot)*clockHand.getHeight()/2);
        trans.setTransform(
                Math.cos(rot), Math.sin(rot),
                -Math.sin(rot), Math.cos(rot), 
                -clockHand.getWidth()/2, -clockHand.getHeight()/2
        );
    }
}
