/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.gui;

import bropals.lib.simplegame.gui.GuiElement;
import ggj16.states.PlayState;
import ggj16.Task;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class ToDoListElement extends GuiElement {

    private PlayState ps;
    private BufferedImage listImage;
    
    public ToDoListElement(int x, int y, int w, int h, PlayState ps) {
        super(x, y, w, h);
        this.ps = ps;
        listImage = ps.getAssetManager().getImage("list");
    }

    // xPos, yPos, xOffest, yOffset
    private int[] linePos = {
        185, 134, 146, 3,  // item 0
        183, 169, 198, 3,  // item 1
        181, 200, 166, 4,  // item 2
        179, 237, 196, -6, // item 3
        182, 266, 177, -2, // item 4
        183, 303, 147, 4   // item 5
    };
    
    @Override
    public void render(Object o) {

        Graphics2D g2 = (Graphics2D) o;
        
        if (isEnabled()) {
            // just draw a list of all of the tasks for now
            /// have different colors for completed and not completed tasks
            //g2.setColor(Color.WHITE);
           
            //g2.fillRect(100, 100, 600, 400);
            int posX = 100;
            int posY = 100;
            g2.drawImage(listImage, posX, posY, null);
            
            ArrayList<Task> toDoList = ps.getToDoList();
            for (int i=0; i<toDoList.size(); i++) {
                int index = i * 4;
                if (toDoList.get(i).isComplete()) {
                    g2.setStroke(new BasicStroke(4));
                    g2.setColor(new Color(126, 26, 18));
                    g2.drawLine(posX + linePos[index], posY + linePos[index + 1], 
                           posX + linePos[index] + linePos[index + 2], 
                           posY + linePos[index + 1] + linePos[index + 3]);
                }
            }
        
        }
    }

    
}
