/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.gui;

import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.gui.GuiElement;
import ggj16.states.PlayState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class PaperStackGui extends GuiButton {
    
    private float[] regions; //Regions bounded for the paperwork for each image.
    private BufferedImage[] images;
    private PlayState ps;
    
    public PaperStackGui(int x, int y, int w, int h, PlayState ps) {
        super(x, y, w, h, null, null, null, null);
        this.ps = ps;
        images = new BufferedImage[] {
            ps.getAssetManager().getImage("paperstack1"),
            ps.getAssetManager().getImage("paperstack2"),
            ps.getAssetManager().getImage("paperstack3"),
            ps.getAssetManager().getImage("paperstack4"),
            ps.getAssetManager().getImage("paperstack5")
        };
        setGuiButtonAction(new GuiButtonAction() {
            @Override
            public void onButtonPress() {
                toggle();
            }
        });
    }
    
    private int paperWorkPercent(float percent) {
        return (int)(ps.PAPERWORK_PER_DAY_BASE_VALUE*percent);
    }

    private void toggle() {
        ps.toggleToDoListVisiblity();
    }
    
    @Override
    public void render(Object o) {
        Graphics g = (Graphics)o;
        BufferedImage img = null;
        if (ps.getPaperworkLeft() > paperWorkPercent(0.75f)) {
            img = images[0];
        } else if (ps.getPaperworkLeft() > paperWorkPercent(0.5f) && ps.getPaperworkLeft() <= paperWorkPercent(0.75f)) {
            img = images[1];
        } else if (ps.getPaperworkLeft() > paperWorkPercent(0.25f) && ps.getPaperworkLeft() <= paperWorkPercent(0.5f) ) {
            img = images[2];
        } else if (ps.getPaperworkLeft() > 0 && ps.getPaperworkLeft() <= paperWorkPercent(0.25f) ) {
            img = images[3];
        } else if (ps.getPaperworkLeft() == 0) {
            img = images[4];
        }
        g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
        
        int paperworkPercentDone = 100 - (int)((float)ps.getPaperworkLeft()/ps.PAPERWORK_PER_DAY_BASE_VALUE*(float)100);
        g.setColor(Color.WHITE);
        g.drawString("Paperwork Done: " + paperworkPercentDone + "%", getX() + 30, getY() + 285);
    }
    
}
