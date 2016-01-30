
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
public class EmailTask extends Task {

    int stepOn = 0;
    int[] steps = new int[]{KeyCode.KEY_R, KeyCode.KEY_D};
    int timesLeft = 8;
    
    public EmailTask(GameState stateInside) {
        super(stateInside, "Respond to emails");
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("Email task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);

    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (!bln) {
            return;
        }
        if (steps[stepOn] == i) {
            stepOn++;
            if (stepOn >= steps.length) {
                stepOn = 0;
                timesLeft--;
                if (timesLeft <= 0) {
                    setComplete(true);
                }
            }
        }
    }
}
