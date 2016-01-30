
package ggj16;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.animation.Track;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.state.GameState;
import ggj16.officeobjects.PlayerDemon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Where all the rituals happen.
 * @author Kevin
 */
public class PlayState extends GameState {
       
    private Camera camera;
    // game world values
    private GameWorld<OfficeObject> officeWorld;
    private PlayerDemon demonPlayer; // special reference of the demon
    
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
        //Input does not need to be updated
        
        //Update the camera
        //The camera sets its position itself
        camera.setXLocation((int)(demonPlayer.getX()+(demonPlayer.getWidth()/2)-400));
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        //Clear the background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 800, 600);
        // 1. clip office work to draw.
        // 800x300 Office world from (0, 0)
        //    draw background(s)
        //    draw all of the office furniture/people
        //    draw the demon
        //    If one is viewing the tasks window, render the tasks.
        g2.clipRect(0, 0, 800, 300); // reset the graphics 
        
        for (int i=0; i<officeWorld.getEntities().size(); i++) {
            officeWorld.getEntities().get(i).render(g2);
        }
        // 2. clip task world to draw.
        // 400x300 task world from (200, 300)
        //    If there is an active task, draw it
        //    Otherwise, draw a placeholder(?)
        g2.clipRect(200, 300, 400, 300); // ( think?)
       /// for (int j=0; j<toDoList.size(); j++) {
       ///     toDoList.get(j).render(g2); // pass the clipped graphics in
       /// }
        if (activeTask != null) {
            activeTask.render(g2);
        } else {
            //Whatever is there with no task
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
        officeWorld = new GameWorld<>(this);
        camera = new Camera();
        toDoList = new ArrayList<>();
        
        Animation demonAnimation = new Animation();
        Track t = new Track(new BufferedImage[]{getImage("demonPlaceholder")});
        demonAnimation.addTrack(t);
        demonAnimation.setTrack(0);
        demonPlayer = new PlayerDemon(officeWorld, 0, demonAnimation, camera);
        
        officeWorld.addEntity(demonPlayer);
        
    }

    @Override
    public void onExit() {
    }

    @Override
    public void key(int keycode, boolean pressed) {
        demonPlayer.key(keycode, pressed);
    }    
    
    /** 
     * Alter the amount of paperwork
     * @param amount How much paperwork left will change. (neg or pos)
     */
    public void changePaperwork(float amount) {
        paperworkLeft += amount;
    }
}
