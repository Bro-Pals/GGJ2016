
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;
import java.awt.Graphics2D;

/**
 *
 * @author Kevin
 */
public class MakeCoffeeTask extends Task {

    // 3 steps: F, P, O
    int stepOn = 0;
    // codes to do in order
    int[] codes = new int[]{
        KeyCode.KEY_F, 
        KeyCode.KEY_P, 
        KeyCode.KEY_O
    };
    
    int currWaitTime = 0;
    int waitForCoffeeTime = 5000; // 5 seconds

    public MakeCoffeeTask(GameState stateInside) {
        super(stateInside);
    }
    
    @Override
    public void update(int ms) {
        super.update(ms); 
        if (stepOn == codes.length) {
            currWaitTime += ms;
            if (currWaitTime > waitForCoffeeTime) {
                setComplete(true);
            }
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2); 
    }

    @Override
    public void key(int i, boolean bln) {
        super.key(i, bln); 
        if (stepOn != codes.length && i == codes[stepOn]) {
            stepOn++;
            if (stepOn == codes.length) { 
                setComplete(true);
            }
        }
    }

    
 

}
