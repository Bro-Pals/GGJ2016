
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.states.PlayState;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;

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
        super(playState, "Do paperwork");
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

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("paperwork task", 40, 60);
    }

    
    @Override
    public void resetForDay() {
        ready = false;
        setComplete(false);
    }
}
