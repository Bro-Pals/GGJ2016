
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;

/**
 *
 * @author Kevin
 */
public class TakeLunchTask extends Task {

    // 4 steps
    int stepOn = 0;
    // codes to do in order
    // dialing the number will be "6149" (for now)
    int[] codes = new int[]{
        KeyCode.KEY_6, 
        KeyCode.KEY_1, 
        KeyCode.KEY_4,
        KeyCode.KEY_9
    };
    
    int millisToWait = 5000; // 5 seconds.
    int currWaitTime = 0;
    
    public TakeLunchTask(GameState stateInside) {
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
        if (stepOn != codes.length) { // if you aren't yet completed with all of the steps...
            if (i == codes[stepOn]) { // if the keycode matches the current step...
                stepOn++; // go to the next step
                
            }
            
        } 
    }
    
}
