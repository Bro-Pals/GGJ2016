
package ggj16.states;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.animation.Track;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiGroup;
import bropals.lib.simplegame.state.GameState;
import ggj16.Camera;
import ggj16.Employee;
import ggj16.OfficeObject;
import ggj16.Task;
import ggj16.gui.ClockGui;
import ggj16.gui.PaperStackGui;
import ggj16.gui.ToDoListElement;
import ggj16.officeobjects.OfficeTaskObject;
import ggj16.officeobjects.PlayerDemon;
import ggj16.sound.SoundPlayer;
import ggj16.tasks.EmailTask;
import ggj16.tasks.FaxTask;
import ggj16.tasks.HitImpTask;
import ggj16.tasks.InterviewTask;
import ggj16.tasks.MakeCoffeeTask;
import ggj16.tasks.MeetingTask;
import ggj16.tasks.PaperworkTask;
import ggj16.tasks.ScareHouseTask;
import ggj16.tasks.TakeLunchTask;
import ggj16.tasks.objs.ChangeEmployeeStateTaskObject;
import ggj16.tasks.objs.ImpAttackTaskObject;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Where all the rituals happen.
 * @author Kevin
 */
public class PlayState extends GameState {
    
    // Important constant values values
    private final int IMP_WAIT_TIME_STAGE_1 = 9000; // 10 seconds between each chance for an imp to spawn.
    private final int IMP_WAIT_TIME_STAGE_2 = 6500; // 9 seconds between each chance for an imp to spawn.
    private final int IMP_WAIT_TIME_STAGE_3 = 4500; // 7.5 seconds between each chance for an imp to spawn.
    
    public final float PAPERWORK_PER_DAY_BASE_VALUE = 170;
    private final float EMPLOYEE_WORK_RATE = 0.009f; // the rate that each worker reduces paperwork.
    private final float MINUTES_STAGE_1 = 4; // days 1 and 2
    private final float MINUTES_STAGE_2 = 3; // days 3 and 4
    private final float MINUTES_STAGE_3 = 2; // days 5 and 6
    private final int MILLIS_PER_MINUTE = 40000; // how many milliseconds pass every minute
    
    private final int MILLIS_PER_HOUR_INITIAL = (int)(MILLIS_PER_MINUTE * MINUTES_STAGE_1/12); // how many milliseconds pass for each hour.
    private final int MILLIS_PER_HOUR_STAGE_2 = (int)(MILLIS_PER_MINUTE * MINUTES_STAGE_2/12); // milliseconds per "hour" days 2 and 3
    private final int MILLIS_PER_HOUR_STAGE_3 = (int)(MILLIS_PER_MINUTE * MINUTES_STAGE_3/12); // milliseconds per "hour" days 4 and 5
    
    private Camera camera;
    private Gui gui;
    private ClockGui clockGui;
    private ToDoListElement todoListGuiElement;
    
    // game world values
    private GameWorld<OfficeObject> officeWorld;
    private PlayerDemon demonPlayer; // special reference of the demon
    boolean debugDemonRun = false;
    
    private boolean viewingCompletedDay;
    private int viewingDayTime;
    private BufferedImage completeDay;
    
    // task values
    private ArrayList<Task> toDoList; // every task
    private Task activeTask; // special reference to a task taking in input, pointed from "tasks"
    private int nextTaskIndex = 0; // the index of the next task that needs to be done.
    
    // worker values
    private Employee[] workers;
    private BufferedImage[] workerHeadsIcon;
    /*
    private int currWorkerSleepTime;
    private final int WORKER_SLEEP_WAIT_TIME = 12000; // 12 seconds
    */
    // imp attack values
    private ArrayList<ImpAttackTaskObject> impTaskObjects; // list of all imp attack tasks objects
    private int currImpAttackTime;
   
    private int impWaitTimeCurrent; // wait time between each imp spawn chance.
    
    // game values.
    private int dayOn; // Count what day you're on.
    private int obstructionValue = 0; //Level of obstruction
    private int hoursLeft = 12;
    private int ticksPerHour;
    // ticksPerHour * 12 /1000 = seconds per day ( 360 secs )
    private int tickProgress;
    private float paperworkLeft = PAPERWORK_PER_DAY_BASE_VALUE; // how much paperwork is left for that day.
    private boolean viewingTasks; // if they're viewing tasks (render tasks?)
    
    //Background (unorganized for now :<)
    private BufferedImage officeBackgroundRepeated;
    private BufferedImage coolParallaxScrolling;
    private int parallaxOffset;
    private BufferedImage border;
    private BufferedImage border2;
    // other prompts
    private BufferedImage canNotDoYetPrompt;
    
    // alert image
    private BufferedImage impAlertImage;
    
    //Imp flying across the screen
    private int impXLoc = 0;
    private boolean showImp = false;
    private BufferedImage impImage;
    
    
    @Override
    public void update(int delta) {
        if (viewingCompletedDay) {
            viewingDayTime += delta;
            if (viewingDayTime>=4000) {
                advanceDay();
            } 
            return;
        }
        tickProgress += delta;
        if (tickProgress >= ticksPerHour) {
            advanceHour();
            tickProgress = 0;
        }
        // 1. update office world
        officeWorld.updateEntities(delta);       
        // update paperwork
        paperworkLeft -= (getNumWorkerAlive() * EMPLOYEE_WORK_RATE);
        if (paperworkLeft < 0) {
            paperworkLeft = 0;
        }
       
        
        // imp spawning
        currImpAttackTime += delta;
        if (currImpAttackTime > impWaitTimeCurrent&&
                getNumWorkerAlive() != 0) { // there must be workers alive to spawn an imp on one
            // make an imp randomly attack
            // make a random worker fall asleep
            if (Math.random() > 0.5) { // 50% chance to spawn at time limit
                // randomly chose a non-dead worker
                int whichOne = 0;
                int iterations = 0; // prevent infinite loop
                do {
                    whichOne = (int)(Math.random() * workers.length);
                    iterations++;
                } while(iterations >= 50 && // safe lock to stop the loop after 100 times.
                        workers[whichOne].getState() != Employee.WORKING &&
                        !workers[whichOne].isBeingAttackedByImp());
                if (!workers[whichOne].isBeingAttackedByImp() && 
                        workers[whichOne].getState() == Employee.WORKING &&
                        iterations < 50) { // only spawn an imp if iterations did not go out too far
                    // define the task and add it to the list of tasks to be updated
                    HitImpTask impTask = new HitImpTask(this, workers[whichOne]);
                    ImpAttackTaskObject impTaskObject = new ImpAttackTaskObject(officeWorld, workers[whichOne].getX(),
                            getAssetManager().getImage("emptyImage"), camera, impTask);
                    impTaskObjects.add(impTaskObject); // add to the list to keep track of it
                    // spawn a "wake up" task object near them
                    officeWorld.addEntity(impTaskObject);
                    SoundPlayer.getSoundPlayer().playImpApproaching();
                   // System.out.println("An imp started to attack an employee: whichOne: " + whichOne
                   //     + ", worker being attacked already: " + workers[whichOne].isBeingAttackedByImp());
                    workers[whichOne].setBeingAttackedByImp(true);
                }
            }
            currImpAttackTime = 0;
        }
        
        
        if (nextTaskIndex < toDoList.size() && toDoList.get(nextTaskIndex).isComplete()) {
            nextTaskIndex++;
        }
        for (int j=0; j<toDoList.size(); j++) {
            toDoList.get(j).update(delta);
        }
        // 3. ?? update the input ??
        //Input does not need to be updated
        
        //Update the camera
        //The camera sets its position itself
        camera.setXLocation((int)(demonPlayer.getX()+(demonPlayer.getWidth()/2)-400));
        //Cool parallax scrolling
        parallaxOffset = (int)( (float)(demonPlayer.getX()/((float)(Camera.getCameraMax()-demonPlayer.getWidth())-Camera.getCameraMin())) * 800 );
        
        //Update flying imp
        if (showImp) {
            impXLoc += 30;
            if (impXLoc>850) {
                showImp = false;
            }
        }
        
        //startShowingImp();
        
        OfficeTaskObject intersects = null;
        for (int i=0; i<officeWorld.getEntities().size(); i++) {
            OfficeObject obj = officeWorld.getEntities().get(i);
            if (obj instanceof OfficeTaskObject) {
                OfficeTaskObject task = (OfficeTaskObject)obj;
                if (task instanceof ChangeEmployeeStateTaskObject ||
                       task instanceof ImpAttackTaskObject) {
                    // if a wake employee, interview, or kill imp task is completed, then its deleted on completion.
                    if (task.getAssociatedTask().isComplete()) {
                        // remove object
                        if (activeTask == task.getAssociatedTask()) {
                            activeTask = null;
                        }
                        obj.setParent(null);
                        officeWorld.getEntities().remove(obj);
                        // remove the imp task from the list of imp tasks.
                        if (task instanceof ImpAttackTaskObject) {
                            ImpAttackTaskObject iat = (ImpAttackTaskObject)task;
                            if (impTaskObjects.contains(iat)) {
                                impTaskObjects.remove(iat);
                            }
                        }
                        continue; // go to the next iteration.
                    }
                }
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
        Graphics2D officeRender = (Graphics2D)g2.create(0, 0, 800, 300); // reset the graphics 
        
        //Draw the background
        officeRender.drawImage(coolParallaxScrolling, -parallaxOffset, 0, null);
        officeRender.drawImage(coolParallaxScrolling, 800-parallaxOffset, 0, null);
        officeRender.drawImage(coolParallaxScrolling, -parallaxOffset, 100, null);
        officeRender.drawImage(coolParallaxScrolling, 800-parallaxOffset, 100, null);
        
        int offset = ((int)camera.getXLocation())%officeBackgroundRepeated.getWidth();
        for (int i=0; i<((officeBackgroundRepeated.getWidth()/800)+1); i++) {
            officeRender.drawImage(officeBackgroundRepeated, (int)(-offset+(i*officeBackgroundRepeated.getWidth())), 100, null );
        }
        
        for (int i=0; i<officeWorld.getEntities().size(); i++) {
            officeWorld.getEntities().get(i).render(officeRender);
        }
        officeRender.dispose();
        // 2. clip task world to draw.
        // 400x300 task world from (200, 300)
        //    If there is an active task, draw it
        //    Otherwise, draw a placeholder(?)
        Graphics2D taskRender = (Graphics2D)g2.create(200, 300, 400, 300);
       /// for (int j=0; j<toDoList.size(); j++) {
       ///     toDoList.get(j).render(g2); // pass the clipped graphics in
       /// }
        if (activeTask != null) {
            // find what task needs to be done next
            activeTask.render(taskRender);
            if (!isCompletedWithTasks() &&
                    activeTask != toDoList.get(nextTaskIndex) && 
                    !activeTask.isComplete() &&
                    !(activeTask instanceof HitImpTask) &&
                    !(activeTask instanceof InterviewTask) &&
                    !(activeTask instanceof PaperworkTask)) {
                // prompt that the task is visited too  early
                taskRender.drawImage(canNotDoYetPrompt, 0, 0, null);
            }
        } else {
            //Whatever is there with no task
        }
        taskRender.dispose();
        
        if (isFinishedWithDailyThings() && !viewingCompletedDay) {
            //SoundPlayer.getSoundPlayer().playVictoryMusic();
            SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.NONE);
            SoundPlayer.getSoundPlayer().playVictoryMusic();
            viewingCompletedDay = true;
        }

        // draw the to-do list on top of everything
        if (viewingTasks) {
            // View all of the tasks.
        }
        gui.render(o);        
        //Draw borders
        
        g2.drawImage(border, 0, 300, null);
        g2.drawImage(border2, 196, 306, null);
        g2.drawImage(border2, 598, 306, null);
        
        
        
        // render employee status gui according to what state they're in.
        for (int i=0; i<workers.length; i++) {
            g2.drawImage(workerHeadsIcon[(workers[i].getState())], 495 + (50 * i), 10, null);
        }
        
        BufferedImage dayImg = getAssetManager().getImage("day" + (dayOn + 1));
        if (dayImg != null) {
            g2.drawImage(dayImg, 10, 10, null);
        }
        
        // draw the alert GUI warning about an imp attack
        for (int i=0; i<impTaskObjects.size(); i++) {
            int yPos = 30 + (i * 25);
            int xPos = (int)(impTaskObjects.get(i).getX() - camera.getXLocation());
            // contain it to the screen so one can see what direction to go.
            if (xPos < 10) {
                xPos = 10;
            } else if (xPos > 730) {
                xPos = 770;
            }
            g2.drawImage(impAlertImage, xPos, yPos, null);
        }
        
        
        //Draw an imp that is flying across the screen
        if (showImp) {
            g2.drawImage(
                    impImage,
                    impXLoc-(impImage.getWidth()/2),
                    (int)((((float)((impXLoc-400)*(impXLoc-400)))/1000)+150)-(impImage.getHeight()/2),
                    null
            );
        }
        
        if (viewingCompletedDay) {
            //g2.drawImage(completeDay, 200, 150, null);
        }
        
        // draw the todolist last to go on top
        todoListGuiElement.render(o);
        
        /*
        Debugging things
        */
        /*
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 150, 50);
        g2.setColor(Color.BLACK);
        g2.drawString("activeTask: " + activeTask, 10, 20);
        g2.drawString("paperworkLeft: " + paperworkLeft, 10, 30);
        */
    }

    @Override
    public void onEnter() {
        //currWorkerSleepTime = 0;
        officeWorld = new GameWorld<>(this);
        camera = new Camera();
        toDoList = new ArrayList<>();
        impTaskObjects = new ArrayList<>();
        ticksPerHour = MILLIS_PER_HOUR_INITIAL;
        impWaitTimeCurrent = IMP_WAIT_TIME_STAGE_1;
        // task and todo list init
        
        MakeCoffeeTask coffeeTask = new MakeCoffeeTask(this);
        MeetingTask meetingTask = new MeetingTask(this);
        TakeLunchTask lunchTask = new TakeLunchTask(this);
        EmailTask emailTask = new EmailTask(this);
        ScareHouseTask scareTask = new ScareHouseTask(this);
        FaxTask faxTask = new FaxTask(this);
        PaperworkTask paperworkTask = new PaperworkTask(this);
        toDoList.add(coffeeTask);
        toDoList.add(meetingTask);
        toDoList.add(lunchTask);
        toDoList.add(emailTask);
        toDoList.add(scareTask);
        toDoList.add(faxTask);
       
        workers = new Employee[6];
        BufferedImage workerWorkingImg = getAssetManager().getImage("worker");
        
        impAlertImage =  getAssetManager().getImage("impAlert");
        completeDay = getAssetManager().getImage("dayComplete");
        
        int workerImageAdjust = 44;
        
        // add everything to the world
        // desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 100, getAssetManager().getImage("workerDesk"), camera));
        workers[0] = new Employee(officeWorld, 100 + workerImageAdjust, workerWorkingImg, camera);
        officeWorld.addEntity(workers[0]);
        // meeting door
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 400, getAssetManager().getImage("meetingDoor"), camera, meetingTask));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 550, getAssetManager().getImage("workerDesk"), camera));
        workers[1] = new Employee(officeWorld, 550 + workerImageAdjust, workerWorkingImg, camera);
        officeWorld.addEntity(workers[1]);
        // coffee maker
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 850, getAssetManager().getImage("coffeemaker"), camera, coffeeTask));
        // fax machine
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 1000, getAssetManager().getImage("faxMachine"), camera, faxTask));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 1200, getAssetManager().getImage("workerDesk"), camera));
        workers[2] = new Employee(officeWorld, 1200 + workerImageAdjust, workerWorkingImg, camera);
        officeWorld.addEntity(workers[2]);
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 1450, getAssetManager().getImage("workerDesk"), camera));
        workers[3] = new Employee(officeWorld, 1450 + workerImageAdjust, workerWorkingImg, camera);
        officeWorld.addEntity(workers[3]);
        // phone to order lunch
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 1650, getAssetManager().getImage("phoneOffice"), camera, lunchTask));
        // demon desk
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 1850, getAssetManager().getImage("paperdesk"), camera, paperworkTask));
        // computer to answer emails
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 2000, getAssetManager().getImage("computerdesk"), camera, emailTask));
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 2250, getAssetManager().getImage("workerDesk"), camera));
        workers[4] = new Employee(officeWorld, 2250 + workerImageAdjust, workerWorkingImg, camera);
        officeWorld.addEntity(workers[4]);
        //  desk
        officeWorld.addEntity(new OfficeObject(officeWorld, 2500, getAssetManager().getImage("workerDesk"), camera));
        workers[5] = new Employee(officeWorld, 2500 + workerImageAdjust, workerWorkingImg, camera);
        officeWorld.addEntity(workers[5]);
        // portal to scare the family
        officeWorld.addEntity(new OfficeTaskObject(officeWorld, 2800, getAssetManager().getImage("portalOffice"), camera, scareTask));
        
        
        // init player last (so they're drawn on top)
        Track right = new Track(new BufferedImage[]{getImage("demon1"), getImage("demon2"), getImage("demon3")}, 200);
        getAssetManager().createHorizontialFlipCopy(getImage("demon1"), "demon1Left");
        getAssetManager().createHorizontialFlipCopy(getImage("demon2"), "demon2Left");
        getAssetManager().createHorizontialFlipCopy(getImage("demon3"), "demon3Left");
        Track left = new Track(new BufferedImage[]{
            getImage("demon1Left"), 
            getImage("demon2Left"), 
            getImage("demon3Left")
        }, 200);
        Animation rightAnim = new Animation();
        rightAnim.setTrack(rightAnim.addTrack(right));
        Animation leftAnim = new Animation();
        leftAnim.setTrack(leftAnim.addTrack(left));
        demonPlayer = new PlayerDemon(officeWorld, 0, leftAnim, rightAnim, camera);
        officeWorld.addEntity(demonPlayer);
        
        // init flip imp images
        getAssetManager().createHorizontialFlipCopy(getAssetManager().getImage("troublemaker1"), "troublemaker1flip");
        getAssetManager().createHorizontialFlipCopy(getAssetManager().getImage("troublemaker2"), "troublemaker2flip");
        
        impImage = getImage("troublemaker1flip");
        
        // employee heads init
        workerHeadsIcon = new BufferedImage[]{
            getAssetManager().getImage("workerHeadAwake"),
          // getAssetManager().getImage("workerHeadSleeping"),
            getAssetManager().getImage("workerHeadDead")
         };
        
        //Background init
        officeBackgroundRepeated = getAssetManager().getImage("foreground");
        coolParallaxScrolling = getAssetManager().getImage("background");
        border = getAssetManager().getImage("border");
        border2 = getAssetManager().getImage("border2");
        
        // other images init
        canNotDoYetPrompt = getAssetManager().getImage("canNotDoYet");
        
        ///Gui init
        gui = new Gui();
        GuiGroup main = new GuiGroup();
        
        main.addElement(new PaperStackGui(0, 300, 200, 300, this));
        main.addElement(clockGui = new ClockGui(600, 300, 200, 300, this));
        // draw last to put it on top
        main.addElement(todoListGuiElement = new ToDoListElement(200, 200, 600, 400, this));
        gui.addGroup("main", main);
        gui.setEnabled("main", true);
        todoListGuiElement.setEnabled(false);
        dayOn = 0;
        SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.MAIN_SONG);
        
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
        // handle input for actice task (only on valid active task)

        if (!isCompletedWithTasks() && activeTask != null && activeTask == toDoList.get(nextTaskIndex)) {
            activeTask.key(keycode, pressed);
        } else {
            // if it's not the right one on the todo list then see if
            // it's one of the other tasks.
            if ((activeTask instanceof HitImpTask) ||
                (activeTask instanceof InterviewTask) ||
                (activeTask instanceof PaperworkTask)    ) {
                activeTask.key(keycode, pressed);
            }
        }
        if (keycode == KeyCode.KEY_T && pressed) {
            //System.out.println("Toggle");
            toggleToDoListVisiblity();
        }
        if (keycode == KeyCode.KEY_Z) {
            if (pressed) {
                demonPlayer.setSpeed(18);
            } else {
                demonPlayer.setSpeed(6);
            }
        }
        
        // to test days there will be a cheat button
        if (keycode == KeyCode.KEY_K && pressed) {
            paperworkLeft -= 90;
            //System.out.println("Complete all the things");
            for (int i=0; i<toDoList.size(); i++) {
                toDoList.get(i).setComplete(true);
            }
            nextTaskIndex = toDoList.size();
        }
    }    
    
    /** 
     * Alter the amount of paperwork
     * @param amount How much paperwork left will change. (neg or pos)
     */
    public void changePaperwork(float amount) {
        paperworkLeft += amount;
        if (paperworkLeft < 0) {
            paperworkLeft = 0;
        }
    }

    public float getPaperworkLeft() {
        return paperworkLeft;
    }
    
    public void toggleToDoListVisiblity() {
        todoListGuiElement.setEnabled(!todoListGuiElement.isEnabled());
    }
    
    public int getHoursLeft() {
        return hoursLeft;
    }
    
    public void advanceHour() {
        //System.out.println("Hour decreaseed");
        hoursLeft--;
        if (hoursLeft>0) {
            clockGui.setClockRotation(12-hoursLeft);
        } else {
            hoursLeft = 12;
            advanceDay();
        }
    }

    
    /// adsfasdfasdjfldsjls
    public ArrayList<Task> getToDoList() {
        return toDoList;
    }

    public int getObstructionValue() {
        return obstructionValue;
    }
        
    public void resetForNewDay() {
        hoursLeft = 12;
        paperworkLeft = PAPERWORK_PER_DAY_BASE_VALUE;
        nextTaskIndex = 0;
        tickProgress = 0;
        ticksPerHour = MILLIS_PER_HOUR_INITIAL;
        currImpAttackTime = 0; // reset imp spawn time;
        for (int i=0; i<toDoList.size(); i++) {
            toDoList.get(i).resetForDay();
        }
        // remove all imp task blocks.
        for (int i=0; i<impTaskObjects.size(); i++) {
            if (impTaskObjects.get(i).getAssociatedTask() == activeTask) {
                activeTask = null;
            }
            officeWorld.getEntities().remove(impTaskObjects.get(i));
            impTaskObjects.remove(i);
        }
        SoundPlayer.getSoundPlayer().setMusicTo(SoundPlayer.MAIN_SONG);
    }
    
    public boolean isCompletedWithTasks() {
        return nextTaskIndex >= toDoList.size();
    }
    
    public int getNumWorkerAlive() {
        int count = 0;
        for (int i=0; i<workers.length; i++) {
            if (workers[i].getState() != Employee.DEAD) {
                count++;
            }
        }
        return count;
    }
    
    public void startShowingImp() {
        if (!showImp) {
            showImp = true;
            impXLoc = 0;
        }
    }
    
    public void advanceDay() {
        dayOn++;
        viewingCompletedDay = false;
        viewingDayTime = 0;
        if (isFinishedWithDailyThings()) { // did player finish all paperwork and daily ritual?
            //Completed all tasks
            //Go to the next day
            resetForNewDay();
            demonPlayer.setX(30);
            if (dayOn == 1) {
                obstructionValue = 1;
            } else if (dayOn == 2) {
                ticksPerHour = MILLIS_PER_HOUR_STAGE_2; 
                impWaitTimeCurrent = IMP_WAIT_TIME_STAGE_2;
            } else if (dayOn == 3) {
                obstructionValue = 4;
            } else if (dayOn == 4) {
                ticksPerHour = MILLIS_PER_HOUR_STAGE_3;
                impWaitTimeCurrent = IMP_WAIT_TIME_STAGE_3;
                obstructionValue = 7;
            } else if (dayOn == 5) {
                //Lets make this the win state
                getGameStateRunner().setState(new WinState());
            }
            
           // System.out.println("The day is now: " + dayOn);
           // System.out.println("Ticks per hour is now: " + ticksPerHour);
        } else {
            //Did not complete all tasks
            //System.out.println("YOU ARE FIRED");
            getGameStateRunner().setState(new FiredState());
        }
    }
    
    
    /// huuururrurrrrr
    public void onImpKillEmployee(HitImpTask hit) {
        Employee targetEmployee = hit.getEmployeeTargeted();
        if (activeTask == hit) {
            activeTask = null;
        }
        // spawn a task block for interviewing
        // define the task and add it to the list of tasks to be updated
        InterviewTask interviewTask = new InterviewTask(this);
        ChangeEmployeeStateTaskObject cesto = new ChangeEmployeeStateTaskObject(officeWorld, 
                targetEmployee.getX(), getAssetManager().getImage("emptyImage"), camera, interviewTask,
                targetEmployee, Employee.WORKING);
        // spawn a "wake up" task object near them
        officeWorld.addEntity(cesto);
                    
        // remove the imp task block from showing
        for (int i=0; i<impTaskObjects.size(); i++) {
            if (impTaskObjects.get(i).getAssociatedTask() == hit) {
                impTaskObjects.remove(i);
                break;
            }
        }
    }

    /**
     * See if the player is finished with everything to do in the day, that is
     * do all their paperwork and finish all tasks.
     * @return If the player is done with everything or not.
     */
    private boolean isFinishedWithDailyThings() {
        return paperworkLeft <= 0 && isCompletedWithTasks();
    }
}
