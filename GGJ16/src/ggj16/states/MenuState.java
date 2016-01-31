/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.states;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.sound.SoundPlayer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class MenuState extends GameState {
    
    int screenOn;
    
    private BufferedImage[] images;
    
    
    @Override
    public void update(int i) {
    }

    @Override
    public void render(Object o) {
        Graphics g = (Graphics)o;
        if (images.length > screenOn) {
             g.drawImage(images[screenOn], 0, 0, null);
        }
       
    }

    @Override
    public void onEnter() {
        images = new BufferedImage[]{ 
            getImage("menuscreen"),
            getImage("instructions")
        };        
        
        screenOn = 0;
        SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.MAIN_SONG);
    }

    @Override
    public void onExit() {
    }

    @Override
    public void key(int keycode, boolean pressed) {
        if (keycode == KeyCode.KEY_SPACE && !pressed) {
            screenOn++;
            if (screenOn >= images.length) {
                // go to the next state when they're done.
                getGameStateRunner().setState(new PlayState());
            }
            
        }
    }
    
    
}
