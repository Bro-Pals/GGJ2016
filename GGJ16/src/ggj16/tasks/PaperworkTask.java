
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.PlayState;
import ggj16.Task;

/**
 *
 * @author Kevin
 */
public class PaperworkTask extends Task {

    // tap F to reduce paperwork
    // this task can't be completed - it's just done.
    
    private PlayState plateStateRef;
    
    public PaperworkTask(PlayState playState) {
        super(playState);
        this.plateStateRef = playState;
    }

    @Override
    public void key(int i, boolean bln) {
        super.key(i, bln); 
        if (i == KeyCode.KEY_F) {
            // reduce paperwork
            plateStateRef.changePaperwork(-1);
        }
    }

    
}
