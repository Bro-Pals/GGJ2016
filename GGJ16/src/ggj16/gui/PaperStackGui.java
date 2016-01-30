/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.gui;

import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.gui.GuiElement;
import ggj16.PlayState;
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
        regions = new float[] {
            ps.getInitialPaperworkValue()*0.8f,
            ps.getInitialPaperworkValue()*0.6f,
            ps.getInitialPaperworkValue()*0.4f,
            ps.getInitialPaperworkValue()*0.2f
        };
        setGuiButtonAction(new GuiButtonAction() {
            @Override
            public void onButtonPress() {
                toggle();
            }
        });
    }

    private void toggle() {
        ps.toggleToDoListVisiblity();
    }
    
    @Override
    public void render(Object o) {
        Graphics g = (Graphics)o;
        BufferedImage img = null;
        if (ps.getPaperworkLeft() > regions[0]) {
            img = images[0];
        } else if (ps.getPaperworkLeft() > regions[1]) {
            img = images[1];
        } else if (ps.getPaperworkLeft() > regions[2]) {
            img = images[2];
        } else if (ps.getPaperworkLeft() > regions[3]) {
            img = images[3];
        } else if (ps.getPaperworkLeft() == 0) {
            img = images[4];
        }
        g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
