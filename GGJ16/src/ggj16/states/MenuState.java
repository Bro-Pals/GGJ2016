/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.states;

import bropals.lib.simplegame.state.GameState;
import ggj16.sound.SoundPlayer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class MenuState extends GameState {
    
    private BufferedImage bg;
    
    @Override
    public void update(int i) {
    }

    @Override
    public void render(Object o) {
        Graphics g = (Graphics)o;
        g.drawImage(bg, 0, 0, null);
    }

    @Override
    public void onEnter() {
        bg = getImage("menuscreen");
        SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.MAIN_SONG);
    }

    @Override
    public void onExit() {
    }

    @Override
    public void key(int keycode, boolean pressed) {
        if (!pressed) {
            getGameStateRunner().setState(new PlayState());
        }
    }
    
    
}
