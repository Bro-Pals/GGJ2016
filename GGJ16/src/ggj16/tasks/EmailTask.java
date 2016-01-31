
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
public class EmailTask extends Task {

    int stepOn = 0;
    int[] steps = new int[]{KeyCode.KEY_R, KeyCode.KEY_D};
    int timesLeft = 8;
    private BufferedImage read;
    private BufferedImage respond; //, finish;
    private BufferedImage canNotDoYetPrompt, rPrompt, dPrompt;
    private BufferedImage bg, completePrompt;
    
    public EmailTask(GameState stateInside) {
        super(stateInside, "Respond to emails");
        read = stateInside.getAssetManager().getImage("readScreen");
        respond = stateInside.getAssetManager().getImage("respondScreen");
        //finish = stateInside.getAssetManager().getImage("emailFinish");
        bg = stateInside.getAssetManager().getImage("emailBackground");
        completePrompt = stateInside.getAssetManager().getImage("taskCompletePrompt");
        canNotDoYetPrompt = stateInside.getAssetManager().getImage("canNotDoYet");
        rPrompt = stateInside.getAssetManager().getImage("rKeyPrompt");
        dPrompt = stateInside.getAssetManager().getImage("dKeyPrompt");        
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
        
        g2.drawImage(bg, 0, 0, null);
        if (isComplete()) {
            g2.drawImage(respond, 0, 0, null);
        } else if (stepOn == 0) {
            //g2.drawImage(respond, 78, 58, null);
            g2.drawImage(respond, 0, 0, null);
            g2.drawImage(rPrompt, 120, 50, null);
        } else if (stepOn == 1) {
            //g2.drawImage(read, 78, 58, null);
            g2.drawImage(read, 0, 0, null);
            g2.drawImage(dPrompt, 120, 50, null);
        }
        
        if (isComplete()) {
            g2.drawImage(completePrompt, 0, 0, null);
        }
    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (!bln) {
            return;
        }
        if (steps[stepOn] == i) {
            stepOn++;
            SoundPlayer.getSoundPlayer().playMouseClick();
            if (stepOn >= steps.length) {
                stepOn = 0;
                timesLeft--;
                if (timesLeft <= 0) {
                    setComplete(true);
                }
            }
        }
    }

    @Override
    public void resetForDay() {
        stepOn = 0;
        timesLeft = 8;
        setComplete(false);
    }
    
    @Override
    public void getAfflictedByObstruction() {
        timesLeft = 8;
        stepOn = 0;
    }
}
