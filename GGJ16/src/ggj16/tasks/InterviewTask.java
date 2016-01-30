
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.SoundPlayer;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Kevin
 */
public class InterviewTask extends Task {
    // 3 steps:
    // 1. Tap "R" 12 times
    // 2. Press "N" for accepting applicant
    // 3. Tap "S" 10 times to interview them
   
    
    int stepOn = 0;
    // codes to do in order
    int timesToTapLeft = 12; // for the first stage;
   
    
    int millisToWait = 5000; // 5 seconds.
    int currWaitTime = 0;
    
    public InterviewTask(GameState stateInside) {
        super(stateInside, "Hire worker");
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        currWaitTime += ms;
        if (currWaitTime > millisToWait) {
            setComplete(true); // done waiting!
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("Interview task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);
        g2.drawString("timesToTapLeft: " + timesToTapLeft, 40, 160);

    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (!bln) {
            return;
        }
        // when you complete the codes... wait for the food
        if (stepOn == 0 && i == KeyCode.KEY_R) {
            timesToTapLeft--;
            SoundPlayer.getSoundPlayer().playPageTurn();
            if (timesToTapLeft <= 0) { // reduce steps left.
                stepOn++; // next step
            }
        } else if (stepOn == 1 && i == KeyCode.KEY_N) {
            stepOn++;
            timesToTapLeft = 10; // setup for the next step
        } else if (stepOn == 2 && i == KeyCode.KEY_S) {
            if (timesToTapLeft <= 0) { // reduce steps left.
                setComplete(true); // finish task after tapping S
            }
        }
    }
    
    @Override
    public void resetForDay() {
        stepOn = 0;
        timesToTapLeft = 12;
        currWaitTime = 0;
        setComplete(false);
    }
}
