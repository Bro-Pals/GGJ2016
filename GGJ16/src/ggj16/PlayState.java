
package ggj16;

import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;
import bropals.lib.simplegame.state.GameState;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class PlayState extends GameState {

    GameWorld<TexturedBlock> officeWorld;
    ArrayList<Task> toDoList; // every task
    Task activeTask; // task taking in input, pointed from "tasks"
    float paperworkLeft;
    boolean viewingTasks; // if they're viewing tasks
    
    @Override
    public void update(int i) {
        
        // 1. update office world
        // 2. update tasks world
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
        // 2. clip task world to draw.
        // 400x300 task world from (200, 300)
        //    If there is an active task, draw it
        //    Otherwise, draw a placeholder(?)
        // 3. Draw the GUIs
        // Guis are in 2 parts:
        // 200x300 at (0, 300)
        //    This area will show the remaining amount of work.
        // 200x300 at (600, 300)
        //    This area will show the TO-DO list
        
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }

}
