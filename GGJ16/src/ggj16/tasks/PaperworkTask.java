
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
    
    private boolean ready;
    private PlayState plateStateRef;
    
    public PaperworkTask(PlayState playState) {
        super(playState);
        this.plateStateRef = playState;
    }

    @Override
    public void key(int i, boolean bln) {
        super.key(i, bln); 
        if (ready && i == KeyCode.KEY_F && bln) {
            // reduce paperwork
            ready = false;
            plateStateRef.changePaperwork(-1);
        }
        if (!bln) {
            ready = true;
        }
    }

    
}
