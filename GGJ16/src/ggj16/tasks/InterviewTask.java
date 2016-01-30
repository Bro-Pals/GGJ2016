
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;

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
        super(stateInside);
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
    public void key(int i, boolean bln) {
        super.key(i, bln);
        // when you complete the codes... wait for the food
        if (stepOn == 0 && i == KeyCode.KEY_R) {
            timesToTapLeft--;
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
    
}