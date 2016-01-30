/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.gui;

import bropals.lib.simplegame.gui.GuiElement;
import ggj16.states.PlayState;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

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

        Graphics2D g2 = (Graphics2D) o;
        
        if (isEnabled()) {
            // just draw a list of all of the tasks for now
            /// have different colors for completed and not completed tasks
            g2.setColor(Color.WHITE);
           
            g2.fillRect(100, 100, 600, 400);
            ArrayList<Task> toDoList = ps.getToDoList();
            for (int i=0; i<toDoList.size(); i++) {
                if (toDoList.get(i).isComplete()) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.BLACK);
                }
                g2.drawString(toDoList.get(i).getDescription(), 120, 100 + (20 * (i + 1)));
            }
        
        }
    }

    
}
