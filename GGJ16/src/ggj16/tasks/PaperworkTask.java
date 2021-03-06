
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.sound.SoundPlayer;
import ggj16.states.PlayState;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public class PaperworkTask extends Task {

    // tap F to reduce paperwork
    // this task can't be completed - it's just done.
    
    private boolean ready;
    private PlayState plateStateRef;
    private BufferedImage fPrompt, bg, pg1, pg2;
    
    public PaperworkTask(PlayState playState) {
        super(playState, "Do paperwork");
        this.plateStateRef = playState;
        fPrompt = playState.getAssetManager().getImage("fKeyPrompt");
        bg = playState.getAssetManager().getImage("paperTaskBackground");
        pg1 = playState.getAssetManager().getImage("paperTask1");
        pg2 = playState.getAssetManager().getImage("paperTask2");
    }

    @Override
    public void key(int i, boolean bln) {
        super.key(i, bln); 
        if (ready && i == KeyCode.KEY_F && bln) {
            // reduce paperwork
            ready = false;
            plateStateRef.changePaperwork(-0.8f);
            SoundPlayer.getSoundPlayer().playPaperWriting();
        }
        if (!bln) {
            ready = true;
        }
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 300);
        
        g2.drawImage(bg, 0, 0, null);
        if (ready) {
            g2.drawImage(pg1, 0, 0, null);
        } else {
            g2.drawImage(pg2, 0, 0, null);
        }
        
        // draw control prompts
        g2.drawImage(fPrompt, 120, 60, null);
    }

    
    @Override
    public void resetForDay() {
        ready = false;
        setComplete(false);
    }
    
    @Override
    public void getAfflictedByObstruction() {
        
    }
}
