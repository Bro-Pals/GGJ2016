
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
    //   Read 12 applications (paper folding and stuff)
    // 2. Press "N" for accepting applicant
    //    Stamp an applications
    // 3. Tap "S" 10 times to interview them
    //     Press "S" to speak
   
    
    int stepOn = 0;
    // codes to do in order
    int timesToTapLeft = 12; // for the first stage;
    
    int millisToWait = 5000; // 5 seconds.
    int currWaitTime = 0;
    boolean down;
    
    private BufferedImage rPrompt, nPrompt, sPrompt, pg1, pg2, pga, bg, interv;
    
    public InterviewTask(GameState stateInside) {
        super(stateInside, "Hire worker");
        rPrompt = stateInside.getAssetManager().getImage("rKeyPrompt");
        nPrompt = stateInside.getAssetManager().getImage("nKeyPrompt");
        sPrompt = stateInside.getAssetManager().getImage("sKeyPrompt");
        bg = stateInside.getAssetManager().getImage("paperTaskBackground");
        pg1 = stateInside.getAssetManager().getImage("applicationPaper1");
        pg2 = stateInside.getAssetManager().getImage("applicationPaper2");
        pga = stateInside.getAssetManager().getImage("applicationPaperAccept");
        interv = stateInside.getAssetManager().getImage("workerInterview");
        down = false;
    }

    @Override
    public void update(int ms) {
        super.update(ms);
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 300);
        
        /*
        g2.setColor(Color.BLACK);
        g2.drawString("Interview task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);
        g2.drawString("timesToTapLeft: " + timesToTapLeft, 40, 160);
         */
        
        if (stepOn == 0) {
            g2.drawImage(bg, 0, 0, null);
            if (down) {
                g2.drawImage(pg2, 0, 0, null);
            } else {
                g2.drawImage(pg1, 0, 0, null);
            }
            g2.drawImage(rPrompt, 240, 40, null);
        } else if (stepOn == 1) {
            g2.drawImage(bg, 0, 0, null);
            g2.drawImage(pga, 0, 0, null);
            g2.drawImage(nPrompt, 240, 40, null);
        } else if (stepOn == 2) {
            g2.drawImage(interv, 0, 0, null);
            g2.drawImage(sPrompt, 70, 40, null);
        }
        
    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        down = bln;
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
            SoundPlayer.getSoundPlayer().playStamp();
            timesToTapLeft = 10; // setup for the next step
        } else if (stepOn == 2 && i == KeyCode.KEY_S) {
            timesToTapLeft--;
            SoundPlayer.getSoundPlayer().playDemonSpeaking();
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
    
    @Override
    public void getAfflictedByObstruction() {
        
    }
}
