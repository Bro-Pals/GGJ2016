/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.states;

import bropals.lib.simplegame.state.GameState;
import ggj16.sound.SoundPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Where you go after you are fired.
 * @author Jonathon
 */
public class FiredState extends GameState {

    int screenOn;
    BufferedImage[] images;
    
    @Override
    public void update(int i) {
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        Graphics g = (Graphics)o;
        if (images.length > screenOn) {
             g.drawImage(images[screenOn], 0, 0, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(0, 0, 800, 600);
        }
    }

    @Override
    public void onEnter() {
        SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.LOSER_SONG);
        images = new BufferedImage[]{ // nothing
            
        };
        screenOn = 0;
    }

    @Override
    public void onExit() {
    }
    
}
