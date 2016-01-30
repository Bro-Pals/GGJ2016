
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Kevin
 */
public class MeetingTask extends Task {

    // a row of people in a meeting
    // you're tapping "M" to get stuff done
    // when somebody falls asleep, you can't press M until your arrow goes over 
    // them and you press "F", then you can keep on getting stuff done with pressing M
    
    // what step you're on
    int stepOn = 0;
    int arrowOver = 0; // who you have your arrow over, changed with A and D keys
    int sleeper = -1; // when -1 nobody is sleeping, otherwise it's equal to who is sleeping
    int numPeople = 4; // how many people there are (sleeper can't be greater than numPeople-1)
    int workLeft = 25; // how much work is left to do. it wants to be 0
    
    // stepOn == 0 and M makes stuff productive
    // stepOn == 1 and arrowOver == sleeper and F will make stepOn = 0
    
    int timeBetweenRandomSleeper = 2000;
    int timeUntilSleeper = 0;
    
    public MeetingTask(GameState stateInside) {
        super(stateInside, "Conduct meeting");
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        
        if (stepOn == 0) {
            // randomly have a sleeper appear and make stepOn == 1
            
            // after enough time passes
            timeUntilSleeper += ms;
            if (timeUntilSleeper > timeBetweenRandomSleeper) {
                if (Math.random() < 0.5) { // 50% chance
                    // make somebody be the sleeper
                    sleeper = (int)(Math.random() * numPeople);
                    stepOn = 1;
                    timeUntilSleeper = 0; // reset timer
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
        g2.drawString("meeting task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);
        g2.drawString("arrow over: " + arrowOver, 40, 260);
        g2.drawString("sleeper is (-1 means nobody): " + sleeper, 40, 360);


    }


     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (stepOn == 0 && i == KeyCode.KEY_M) {
            workLeft--;
            if (workLeft <= 0) {
                setComplete(true);
            }
        } else if (stepOn == 1 && arrowOver == sleeper && i == KeyCode.KEY_F) {
            // wake him up
            stepOn = 0;
            sleeper = -1;
        }
        
        if (i == KeyCode.KEY_A) {
            arrowOver--;
            if (arrowOver < 0) {
                arrowOver = 0;
            }
        } else if (i == KeyCode.KEY_D) {
            arrowOver++;
            if (arrowOver >= numPeople) {
                arrowOver = numPeople - 1;
            }
        }
    }
    
}
