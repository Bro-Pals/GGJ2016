
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.sound.SoundPlayer;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public class InterviewTask extends Task {
    // 3 steps:
    // 1. Tap "R" 12 times
    // 2. Press "N" for accepting applicant
    // 3. Tap "S" 10 times to interview them
   
    
    int stepOn = 0;
    // codes to do in order
    int timesToTapLeft = 12; // for the first stage;
    
    int millisToWait = 5000; // 5 seconds.
    int currWaitTime = 0;
    
    private BufferedImage rPrompt, nPrompt, sPrompt;
    
    public InterviewTask(GameState stateInside) {
        super(stateInside, "Hire worker");
        rPrompt = stateInside.getAssetManager().getImage("rKeyPrompt");
        nPrompt = stateInside.getAssetManager().getImage("nKeyPrompt");
        sPrompt = stateInside.getAssetManager().getImage("sKeyPrompt");
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
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.setColor(Color.BLACK);
        g2.drawString("Interview task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);
        g2.drawString("timesToTapLeft: " + timesToTapLeft, 40, 160);

        if (stepOn == 0) {
            g2.drawImage(rPrompt, 120, 40, null);
        } else if (stepOn == 1) {
            g2.drawImage(nPrompt, 120, 40, null);
        } else if (stepOn == 2) {
            g2.drawImage(sPrompt, 120, 40, null);
        }
        
    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (!bln) {
            return;
        }
        // when you complete the codes... wait for the food
        if (stepOn == 0 && i == KeyCode.KEY_R) {
            timesToTapLeft--;
            SoundPlayer.getSoundPlayer().playPageTurn();
            if (timesToTapLeft <= 0) { // reduce steps left.
                stepOn++; // next step
            }
        } else if (stepOn == 1 && i == KeyCode.KEY_N) {
            stepOn++;
            timesToTapLeft = 10; // setup for the next step
        } else if (stepOn == 2 && i == KeyCode.KEY_S) {
            if (timesToTapLeft <= 0) { // reduce steps left.
                setComplete(true); // finish task after tapping S
            }
        }
    }
    
    @Override
    public void resetForDay() {
        stepOn = 0;
        timesToTapLeft = 12;
        currWaitTime = 0;
        setComplete(false);
    }
}
