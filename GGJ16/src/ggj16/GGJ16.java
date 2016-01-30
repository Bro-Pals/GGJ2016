/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16;

import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.io.AssetManager;
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
        AssetManager assetManager = new AssetManager(new File("").getAbsoluteFile(), false);
        GameWindow window = new AWTGameWindow("Our game", 800, 600);
        
        GameStateRunner runner = new GameStateRunner(window, assetManager);
        runner.setState(new PlayState());
        runner.loop();
    }
    
}
