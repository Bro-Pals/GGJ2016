
package ggj16;

import bropals.lib.simplegame.controls.Controller;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;
import bropals.lib.simplegame.entity.controls.EntityController;
import bropals.lib.simplegame.state.GameState;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Where all the rituals happen.
 * @author Kevin
 */
public class PlayState extends GameState {
    
    // game world values
    private GameWorld<OfficeObject> officeWorld;
    private TexturedBlock demonPlayer; // special reference of the demon
    
    // task values
    private ArrayList<Task> toDoList; // every task
    private Task activeTask; // special reference to a task taking in input, pointed from "tasks"
    
    // game values.
    private int dayOn; // Count what day you're on.
    private float paperworkLeft; // how much paperwork is left for that day.
    private boolean viewingTasks; // if they're viewing tasks (render tasks?)
    
    @Override
    public void update(int i) {
        
        // 1. update office world
        officeWorld.updateEntities(i);
        // 2. update tasks world
        for (int j=0; j<toDoList.size(); j++) {
            toDoList.get(j).update(i);
        }
        // 3. ?? update the input ??
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        
        // 1. clip office work to draw.
        // 800x300 Office world from (0, 0)
        //    draw background(s)
        //    draw all of the office furniture/people
        //    draw the demon
        //    If one is viewing the tasks window, render the tasks.
        g2.clipRect(0, 0, 800, 300); // reset the graphics 
        
        // 2. clip task world to draw.
        // 400x300 task world from (200, 300)
        //    If there is an active task, draw it
        //    Otherwise, draw a placeholder(?)
        g2.clipRect(200, 300, 400, 300); // ( think?)
        for (int j=0; j<toDoList.size(); j++) {
            toDoList.get(j).render(g2); // pass the clipped graphics in
        }
        
        
        // 3. Draw the GUIs
        // Guis are in 2 parts:
        // 200x300 at (0, 300)
        //    This area will show the remaining amount of work.
        // 200x300 at (600, 300)
        //    This area will show the TO-DO list
        
        
        // draw the to-do list on top of everything
        if (viewingTasks) {
            // View all of the tasks.
        }
    }

    @Override
    public void onEnter() {
        
        
    }

    @Override
    public void onExit() {
    }

    @Override
    public void key(int keycode, boolean pressed) {
        
    }    
}
