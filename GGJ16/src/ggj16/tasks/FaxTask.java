
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
public class FaxTask extends Task {

    int stepOn = 0;
    int currWaitTime = 0; // wait time counter
    int waitTimeDoneFaxing = 4000; // 4 seconds to fax.
    
    public FaxTask(GameState stateInside) {
        super(stateInside, "send fax");
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        
        if (stepOn == 2) {
            currWaitTime += ms;
            if (currWaitTime > waitTimeDoneFaxing) {
                setComplete(true);
            }
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("fax task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);

    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (!bln) {
            return;
        }
        
        if (stepOn == 0 && i == KeyCode.KEY_D) {
            stepOn = 1;
        } else if (stepOn == 1 && i == KeyCode.KEY_F) {
            stepOn = 2;
        }
    }
}
