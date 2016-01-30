
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;
import ggj16.tasks.objs.MoveTaskObject;
import ggj16.Employee;
import ggj16.states.PlayState;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Hit imp task
 * @author Kevin
 */
public class HitImpTask extends Task {

    // Keep repeating 2 steps.
    // 1. Press "F" to shoot
    // 2. Wait for the firebolt to hit target.
    //    If it hits, complete tasks
    //    Otherwise, go back to step 1.
    
    int stepOn = 0;
    float currentAim = (float)Math.PI/2; // starting pointing forward
    float aimVel = 0.1f;
    MoveTaskObject fireBallObject;
    MoveTaskObject impThing;
    int timeLeftForEmployeeDeath;
    Employee employeeTargeted;
    final float impSpawnX = 200;
    final float impSpawnY = 50;
    final float impVelocity = 1f; // speed of the imp

    PlayState ps;
    
    public HitImpTask(GameState stateInside, Employee threatenedEmployee) {
        super(stateInside, "kill imp");
        
        // imp initiall moves to the right
        impThing = new MoveTaskObject(getWorld(), impSpawnX, impSpawnY, 0, impVelocity);
        getWorld().addEntity(impThing);
        timeLeftForEmployeeDeath = 20000;
        this.employeeTargeted = threatenedEmployee;
        ps = (PlayState)stateInside;
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        
        if (isComplete()) {
            return;
        }
        timeLeftForEmployeeDeath -= ms; // death timer goes down.
        if (employeeTargeted != null && timeLeftForEmployeeDeath < 0) {
            // kill employee
            employeeTargeted.setState(Employee.DEAD);
            setComplete(true); // set complete to get rid of it.
            ps.onImpKillEmployee(this);
            employeeTargeted.setBeingAttackedByImp(false);
        }
        
        // update the imp movement here
        if (impThing != null) {
            if (impThing.getX() < 20) {
                impThing.setDirection(0); /// move right
            } else if (impThing.getX() > 390) {
                impThing.setDirection((float)Math.PI); // move left
            }
        }
        
        if (stepOn == 0) {
            // make the aim move back and form in a range.
            currentAim += aimVel; // move it by some amount;
            if (aimVel > 0 && currentAim > 3*Math.PI/4) {
                aimVel *= -1f; // switch direction velocity
            } else if (aimVel < 0 && currentAim < Math.PI/4) {
                aimVel *= -1f; // switch direction velocity
            }
        } else if (stepOn == 1) {
           
            // if (fireball hit monster) then complete
            
            
            // update fireball
            if (fireBallObject != null) {

                // if the fireball is off screen
                if (fireBallObject.getY() < 10) {
                    stepOn = 0; // go back to before the fire shot
                } else {
                   if (impThing.closeTo(fireBallObject, 30)) {
                       // we have killed dat imp boi
                       setComplete(true);
                       employeeTargeted.setBeingAttackedByImp(false);
                       employeeTargeted = null; //don't reference it
                   } 
                }
            }
            
        }
        
        if (impThing != null) {
            impThing.update(ms);
        }
        if (fireBallObject != null) {
            fireBallObject.update(ms);
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 300);
        if (isComplete()) { // outta sight outta mind..?
            return;
        }
        
        
        g2.setColor(Color.BLACK);
        g2.drawString("Imp killing task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 110);
        g2.drawString("The current aim of the fireball: " + currentAim, 40, 210);
        
        g2.setColor(Color.YELLOW);
        g2.drawLine((int)(200), (int)(270), 
                (int)(200 + (40 * Math.cos(currentAim))), (int)(270 - (40 * Math.sin(currentAim))));
        
        if (fireBallObject != null) {
            // placeholder fireball object
            g2.setColor(Color.ORANGE);
            g2.fillOval((int)fireBallObject.getX() - 10, (int)fireBallObject.getY() - 10, 20, 20);
        }
        
        // draw the placeholder imp
        g2.setColor(Color.RED);
        g2.fillOval((int)impThing.getX() - 20, (int)impThing.getY() - 20, 40, 40);
    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (bln && stepOn == 0 && i == KeyCode.KEY_F) {
            stepOn = 1; // shoot fire!
            // spawn the fire ball thing in the task
            float ballVel = 3f;
            float ballSpawnX = 200;
            float ballSpawnY = 270;
            fireBallObject = new MoveTaskObject(getWorld(), ballSpawnX, ballSpawnY, currentAim, ballVel);
            getWorld().addEntity(fireBallObject);
        }
    }

    /**
     * Returns how much longer until the employee referenced by this task dies.
     * @return How much longer until the employee referenced by this task dies. 
     */
    public int getTimeLeftForEmployeeDeath() {
        return timeLeftForEmployeeDeath;
    }
    
    @Override
    public void resetForDay() {
        stepOn = 0;
        currentAim = (float)Math.PI/2; // starting pointing forward
        aimVel = 0.1f;
        timeLeftForEmployeeDeath = 20000;
        impThing.setX(impSpawnX);
        impThing.setY(impSpawnY);
        impThing.setDirection(0);
        impThing.setVelocity(impVelocity);
        setComplete(false);
    }
}
