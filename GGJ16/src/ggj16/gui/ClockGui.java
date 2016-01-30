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
    private int current;
    private BufferedImage[] images;
    
    public ClockGui(int x, int y, int w, int h, PlayState ps) {
        super(x, y, w, h);
        this.ps = ps;
        current = 0;
        images = new BufferedImage[] {
            ps.getImage("clock1"),
            ps.getImage("clock2"),
            ps.getImage("clock3"),
            ps.getImage("clock4"),
            ps.getImage("clock5"),
            ps.getImage("clock6"),
            ps.getImage("clock7"),
            ps.getImage("clock8"),
            ps.getImage("clock9"),
            ps.getImage("clock10"),
            ps.getImage("clock11"),
            ps.getImage("clock12")
        };
    }
    
    @Override
    public void render(Object o) {
        Graphics2D g = (Graphics2D)o;
        g.drawImage(images[current], getX(), getY(), getWidth(), getHeight(), null);
    }
    
    public void setClockRotation(int hour) {
        current = hour;
    }
}
