
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;
import ggj16.tasks.objs.FireBallObject;

/**
 *
 * @author Kevin
 */
public class HitImpTask extends Task {

    // Keep repeating 2 steps.
    // 1. Press "F" to shoot
    // 2. Wait for the firebolt to hit target.
    //    If it hits, complete tasks
    //    Otherwise, go back to step 1.
    
    int stepOn = 0;
    FireBallObject fireBallObject;
    
    public HitImpTask(GameState stateInside) {
        super(stateInside);
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        if (stepOn == 1) {
            // update fireball
            
            // if (fireball hit monster) then complete
        }
    }
    
    

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (stepOn == 0 && i == KeyCode.KEY_F) {
            stepOn = 1; // shoot fire!
        }
    }
}
