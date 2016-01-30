
package ggj16;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.animation.Track;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiElement;
import bropals.lib.simplegame.gui.GuiGroup;
import bropals.lib.simplegame.state.GameState;
import ggj16.gui.PaperStackGui;
import ggj16.gui.ToDoListElement;
import ggj16.officeobjects.OfficeTaskObject;
import ggj16.officeobjects.PlayerDemon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Where all the rituals happen.
 * @author Kevin
 */
public class PlayState extends GameState {
       
    private Camera camera;
    private Gui gui;
    private ToDoListElement todoListGuiElement;
    
    // game world values
    private GameWorld<OfficeObject> officeWorld;
    private PlayerDemon demonPlayer; // special reference of the demon
    
    // task values
    private ArrayList<Task> toDoList; // every task
    private Task activeTask; // special reference to a task taking in input, pointed from "tasks"
    
    // game values.
    private int dayOn; // Count what day you're on.
    private float paperworkLeft = 100; // how much paperwork is left for that day.
    private float initialPaperworkValue = 100;
    private boolean viewingTasks; // if they're viewing tasks (render tasks?)
    
    @Override
    public void update(int delta) {
        // 1. update office world
        officeWorld.updateEntities(delta);
        // 2. update tasks world
        for (int j=0; j<toDoList.size(); j++) {
            toDoList.get(j).update(delta);
        }
        // 3. ?? update the input ??
        //Input does not need to be updated
        
        //Update the camera
        //The camera sets its position itself
        camera.setXLocation((int)(demonPlayer.getX()+(demonPlayer.getWidth()/2)-400));
        OfficeTaskObject intersects = null;
        for (int i=0; i<officeWorld.getEntities().size(); i++) {
            OfficeObject obj = officeWorld.getEntities().get(i);
            if (obj instanceof OfficeTaskObject) {
                OfficeTaskObject task = (OfficeTaskObject)obj;
                if (task.collidesWith(demonPlayer)) {
                    intersects = task;
                }
            }
        }
        if (intersects != null) {
            activeTask = intersects.getAssociatedTask();
        } else {
            activeTask = null;
        }
        gui.update(-1, -1);
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
        g2.setClip(0, 0, 800, 300); // reset the graphics 
        
        for (int i=0; i<officeWorld.getEntities().size(); i++) {
            officeWorld.getEntities().get(i).render(g2);
        }
        // 2. clip task world to draw.
        // 400x300 task world from (200, 300)
        //    If there is an active task, draw it
        //    Otherwise, draw a placeholder(?)
        g2.setClip(200, 300, 400, 300); // ( think?)
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
        g2.setClip(0, 0, 800, 600); // reset the graphics 
        gui.render(o);
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
        
        
        
        
        ///Gui init
        gui = new Gui();
        GuiGroup main = new GuiGroup();
        main.addElement(todoListGuiElement = new ToDoListElement(200, 200, 600, 400, this));
        main.addElement(new PaperStackGui(0, 300, 200, 300, this));
        gui.addGroup("main", main);
        gui.setEnabled("main", true);
    }

    ////Huuuurrrrrrr
    public PlayerDemon getDemonPlayer() {
        return demonPlayer;
    }

    public void setActiveTask(Task activeTask) {
        this.activeTask = activeTask;
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

    public float getPaperworkLeft() {
        return paperworkLeft;
    }

    public float getInitialPaperworkValue() {
        return initialPaperworkValue;
    }
    
    public void toggleToDoListVisiblity() {
        todoListGuiElement.setEnabled(!todoListGuiElement.isEnabled());
    }
}
