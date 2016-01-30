
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public class WakeSleeperTask extends Task {

    // just press "F" to wake them up
    
    private BufferedImage wakePrompt;
    
    public WakeSleeperTask(GameState stateInside, String desc) {
        super(stateInside, desc);
        wakePrompt = stateInside.getAssetManager().getImage("scarePrompt");
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("Wake worker task here", 40, 150);
        
        // draw prompt;
        g2.drawImage(wakePrompt, 80, 20, null);
    }

    @Override
    public void key(int i, boolean bln) {
        super.key(i, bln); 
        if (!bln) {
            return;
        }
        if (i == KeyCode.KEY_F) {
            setComplete(true);
        }
    }

    public void resetForDay() {
        setComplete(false);
    }

}
