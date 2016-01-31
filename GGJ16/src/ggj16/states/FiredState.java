/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.states;

import bropals.lib.simplegame.KeyCode;
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
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 800, 600);
        switch (screenOn) {
            case 0:
                g2.drawImage(images[0], 397, 72, 275, 367, null);
                break;
            case 1:
                
                break;
            case 2:
                g2.drawImage(images[0], 0, 0, 800, 1067, null);
                break;
            case 3:
                
                break;
        }
    }

    @Override
    public void onEnter() {
        SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.LOSER_SONG);
        images = new BufferedImage[]{
            getAssetManager().getImage("boss")
        };
        screenOn = 0;
    }

    @Override
    public void key(int keycode, boolean pressed) {
        super.key(keycode, pressed);
        if (pressed && keycode == KeyCode.KEY_SPACE) {
            screenOn++;
            if (screenOn > 3) {
                
            }
        }
    }

    @Override
    public void onExit() {
    }
    
}
