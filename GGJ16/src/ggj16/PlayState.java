
package ggj16;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.animation.Track;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiGroup;
import bropals.lib.simplegame.state.GameState;
import ggj16.gui.ClockGui;
import ggj16.gui.PaperStackGui;
import ggj16.gui.ToDoListElement;
import ggj16.officeobjects.OfficeTaskObject;
import ggj16.officeobjects.PlayerDemon;
import ggj16.tasks.EmailTask;
import ggj16.tasks.FaxTask;
import ggj16.tasks.MakeCoffeeTask;
import ggj16.tasks.MeetingTask;
import ggj16.tasks.ScareHouseTask;
import ggj16.tasks.TakeLunchTask;
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
    private Gui gui;
    private ClockGui clockGui;
    private ToDoListElement todoListGuiElement;
    
    // game world values
    private GameWorld<OfficeObject> officeWorld;
    private PlayerDemon demonPlayer; // special reference of the demon
    
    // task values
    private ArrayList<Task> toDoList; // every task
    private Task activeTask; // special reference to a task taking in input, pointed from "tasks"
    
    // game values.
    private int dayOn; // Count what day you're on.
    private int hoursLeft = 4;
    private float paperworkLeft = 100; // how much paperwork is left for that day.
    private float initialPaperworkValue = 100;
    private boolean viewingTasks; // if they're viewing tasks (render tasks?)
    
    //Background (unorganized for now :<)
    private BufferedImage officeBackgroundRepeated;
    private BufferedImage coolParallaxScrolling;
    private int parallaxOffset;
    
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
        //Cool parallax scrolling
        parallaxOffset = (int)( (float)(demonPlayer.getX()/((float)(Camera.getCameraMax()-demonPlayer.getWidth())-Camera.getCameraMin())) * 1600 );
        
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
        
        //Draw the background
        g2.drawImage(coolParallaxScrolling, -parallaxOffset, 0, null);
        g2.drawImage(coolParallaxScrolling, 800-parallaxOffset, 0, null);
        
        int offset = ((int)camera.getXLocation())%officeBackgroundRepeated.getWidth();
        for (int i=0; i<((officeBackgroundRepeated.getWidth()/800)+1); i++) {
            g2.drawImage(officeBackgroundRepeated, (int)(-offset+(i*officeBackgroundRepeated.getWidth())), 100, null );
        }
        
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
        
        
        
        
        /*
        Debugging things
        */
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 150, 50);
        g2.setColor(Color.BLACK);
        g2.drawString("activeTask: " + activeTask, 10, 20);
        
    }

    @Override
    public void onEnter() {
        officeWorld = new GameWorld<>(this);
        camera = new Camera();
        toDoList = new ArrayList<>();
        
        
        Animation demonAnimation = new Animation();
        Track t = new Track(new BufferedImage[]{getImage("demonrightview")});
        demonAnimation.addTrack(t);
        demonAnimation.setTrack(0);
        demonPlayer = new PlayerDemon(officeWorld, 0, demonAnimation, camera);
        
        officeWorld.addEntity(demonPlayer);
        
        // task and todo list init
        MakeCoffeeTask coffeeTask = new MakeCoffeeTask(this);
        MeetingTask meetingTask = new MeetingTask(this);
        TakeLunchTask lunchTask = new TakeLunchTask(this);
        EmailTask emailTask = new EmailTask(this);
        ScareHouseTask scareTask = new ScareHouseTask(this);
        FaxTask faxTask = new FaxTask(this);
        toDoList.add(coffeeTask);
        toDoList.add(meetingTask);
        toDoList.add(lunchTask);
        toDoList.add(emailTask);
        toDoList.add(scareTask);
        toDoList.add(faxTask);
        
        // add everything to the world
        // desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 100, getAssetManager().getImage("workerDesk"), camera));
        // meeting door
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 400, getAssetManager().getImage("meetingDoor"), camera, meetingTask));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 550, getAssetManager().getImage("workerDesk"), camera));
        // coffee maker
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 850, getAssetManager().getImage("coffeeMakerOffice"), camera, coffeeTask));
        // fax machine
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 1000, getAssetManager().getImage("faxMachine"), camera, faxTask));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 1200, getAssetManager().getImage("workerDesk"), camera));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 1450, getAssetManager().getImage("workerDesk"), camera));
        // phone to order lunch
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 1650, getAssetManager().getImage("phoneOffice"), camera, lunchTask));
        // demon desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 1750, getAssetManager().getImage("demonDesk"), camera));
        // computer to answer emails
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 2000, getAssetManager().getImage("demonComputer"), camera, emailTask));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 2250, getAssetManager().getImage("workerDesk"), camera));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 2500, getAssetManager().getImage("workerDesk"), camera));
        // portal to scare the family
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 2800, getAssetManager().getImage("portalOffice"), camera, scareTask));
        
        //Background init
        officeBackgroundRepeated = getAssetManager().getImage("foreground");
        coolParallaxScrolling = getAssetManager().getImage("background");
        
        ///Gui init
        gui = new Gui();
        GuiGroup main = new GuiGroup();
        main.addElement(todoListGuiElement = new ToDoListElement(200, 200, 600, 400, this));
        main.addElement(new PaperStackGui(0, 300, 200, 300, this));
        main.addElement(clockGui = new ClockGui(600, 300, 200, 300, this));
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
    
    public int getHoursLeft() {
        return hoursLeft;
    }
    
    public void advanceHour() {
        hoursLeft--;
        clockGui.setClockRotation(12-hoursLeft);
    }

    
    /// adsfasdfasdjfldsjls
    public ArrayList<Task> getToDoList() {
        return toDoList;
    }
    
    
    
}
