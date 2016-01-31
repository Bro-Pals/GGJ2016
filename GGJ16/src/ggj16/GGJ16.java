/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16;

import ggj16.sound.SoundPlayer;
import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.io.AssetManager;
import ggj16.states.FiredState;
import ggj16.states.MenuState;
import ggj16.states.WinState;
import java.io.File;

/**
 *
 * @author Pants
 */
public class GGJ16 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AssetManager assetManager = new AssetManager(new File("assets"), true);
        GameWindow window = new AWTGameWindow("Our game", 800, 600);
        
        assetManager.loadImagesInDirectories("images", true);
        assetManager.loadSoundEffectsInDirectories("sounds", true);
        assetManager.loadSoundEffectsInDirectories("music", true);
        SoundPlayer.getSoundPlayer().init(assetManager);
        
        GameStateRunner runner = new GameStateRunner(window, assetManager);
        runner.setState(new WinState());
        runner.loop();
    }
    
}
