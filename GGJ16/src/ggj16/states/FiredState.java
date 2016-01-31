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
    int lastScreen = 5;
    int paperPos = 0;
    BufferedImage[] images;
    
    @Override
    public void update(int i) {
        if (screenOn == 4) {
            paperPos += 4;
        }
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
                g2.drawImage(images[2], 0, 0, null);
                g2.drawImage(images[3], 0, 0, null);
                break;
            case 2:
                g2.drawImage(images[0], 0, 0, 800, 1067, null);
                break;
            case 3:
                g2.drawImage(images[2], 0, 0, 1302, 977, null);
                break;
            case 4:
                g2.drawImage(images[4], 0, 0, null);  // demon dies
                g2.drawImage(images[3], 0, paperPos, null);
                break;
            case 5:
                g2.drawImage(images[1], 0, 0, null);
                break;
        }
        g2.setColor(Color.WHITE);
        if (screenOn < lastScreen) {
            g2.drawString("Press Spacebar to continue...", 15, 580);
        } else  {
            g2.drawString("Press Spacebar to return to the main menu...", 50, 560);
        }
    }

    @Override
    public void onEnter() {
        SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.LOSER_SONG);
        images = new BufferedImage[]{
            getAssetManager().getImage("boss"),
            getAssetManager().getImage("fireScreenFinal"),
            getAssetManager().getImage("cutscreenDemon"),
            getAssetManager().getImage("cutscreenPaper"),
            getAssetManager().getImage("cutscreenFireDemon") // 4
        };
        screenOn = 0;
    }

    @Override
    public void key(int keycode, boolean pressed) {
        super.key(keycode, pressed);
        if (!pressed && keycode == KeyCode.KEY_SPACE) {
            screenOn++;
            if (screenOn == 4) {
                SoundPlayer.getSoundPlayer().playFireball();
            }
            if (screenOn > lastScreen) {
                getGameStateRunner().setState(new MenuState());
            }
        }
    }

    @Override
    public void onExit() {
    }
    
}
