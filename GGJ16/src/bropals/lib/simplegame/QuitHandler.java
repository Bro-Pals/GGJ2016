/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame;

/**
 * Has something happen when the user request the window to close.
 * @author Jonathon
 */
public interface QuitHandler {
    /**
     * A method that is called when the GameWindow is closing.
     */
    public void onQuit();
}
