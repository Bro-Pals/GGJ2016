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
public class WinState extends GameState {
        
    @Override
    public void update(int i) {
    }

    @Override
    public void render(Object o) {
    }

    @Override
    public void onEnter() {
        SoundPlayer.getSoundPlayer().playVictoryMusic();
    }

    @Override
    public void onExit() {
    }
    
}
