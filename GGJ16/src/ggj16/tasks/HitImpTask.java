
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;
import ggj16.tasks.objs.MoveTaskObject;
import ggj16.Employee;
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
    
    public HitImpTask(GameState stateInside, Employee threatenedEmployee) {
        super(stateInside, "kill imp");
        
        float impSpawnX = 200;
        float impSpawnY = 50;
        float impVelocity = 1f; // speed of the imp
        // imp initiall moves to the right
        impThing = new MoveTaskObject(getWorld(), impSpawnX, impSpawnY, 0, impVelocity);
        getWorld().addEntity(impThing);
        timeLeftForEmployeeDeath = 20000;
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        
        timeLeftForEmployeeDeath -= ms; // death timer goes down.
        if (timeLeftForEmployeeDeath < 0) {
            // kill employee
            employeeTargeted.setState(Employee.DEAD);
            setComplete(true); // set complete to get rid of it.
        }
        
        // update the imp movement here
        
        
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
                   } 
                }
            }
            
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("Imp killing task right here", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);
        g2.drawString("The current aim of the fireball: " + currentAim, 40, 260);
        
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
        if (stepOn == 0 && i == KeyCode.KEY_F) {
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
    
    
}
