/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.gui;

import bropals.lib.simplegame.gui.GuiElement;
import ggj16.PlayState;

/**
 *
 * @author Jonathon
 */
public class ToDoListElement extends GuiElement {

    private PlayState ps;
    
    public ToDoListElement(int x, int y, int w, int h, PlayState ps) {
        super(x, y, w, h);
        this.ps = ps;
    }

    @Override
    public void render(Object o) {
        
    }

    
}
