
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
    BufferedImage[] images;

    
    int millisToWait = 5000; // 5 seconds.
    int currWaitTime = 0;
    
    public TakeLunchTask(GameState stateInside) {
        super(stateInside, "Order lunch");
        images = new BufferedImage[]{
            stateInside.getAssetManager().getImage("6KeyPrompt"),
            stateInside.getAssetManager().getImage("1KeyPrompt"),
            stateInside.getAssetManager().getImage("4KeyPrompt"),
            stateInside.getAssetManager().getImage("9KeyPrompt"),
        };
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        if (stepOn >= codes.length) {
            currWaitTime += ms;
            if (currWaitTime > millisToWait) {
                setComplete(true); // done waiting!
            }
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("take lunch task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);
        g2.drawString("currWaitTime: " + currWaitTime, 40, 210);
        
        if (stepOn < codes.length) {
            g2.drawImage(images[stepOn], 0, 0, null);
        }
    }


     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (!bln) {
            return;
        }
        // when you complete the codes... wait for the food
        if (stepOn != codes.length) { // if you aren't yet completed with all of the steps...
            if (i == codes[stepOn]) { // if the keycode matches the current step...
                stepOn++; // go to the next step
                
            }
            
        } 
    }
    
}
