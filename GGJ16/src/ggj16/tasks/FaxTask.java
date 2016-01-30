
package ggj16.tasks;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import ggj16.SoundPlayer;
import ggj16.Task;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public class FaxTask extends Task {

    int stepOn = 0;
    int currWaitTime = 0; // wait time counter
    int waitTimeDoneFaxing = 4000; // 4 seconds to fax.
    
    private BufferedImage[] images;
    
    public FaxTask(GameState stateInside) {
        super(stateInside, "send fax");
        images = new BufferedImage[] {
            stateInside.getAssetManager().getImage("dKeyPrompt"),
            stateInside.getAssetManager().getImage("fKeyPrompt"),
            stateInside.getAssetManager().getImage("faxMachineTask"), // 2
            stateInside.getAssetManager().getImage("faxMachineDialed"), // 3
            stateInside.getAssetManager().getImage("taskCompletePrompt"), // 4
            stateInside.getAssetManager().getImage("faxingPrompt") // 5
            
        };
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
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 300);
        
        /*
        debug stuff
        g2.setColor(Color.BLACK);
        g2.drawString("fax task", 40, 60);
        g2.drawString("stepOn: " + stepOn, 40, 160);
        */
        if (stepOn == 0 || stepOn == 1) {
            g2.drawImage(images[2 + stepOn], 0, 0, null); // fax machine
            g2.drawImage(images[stepOn], 100 + (70 * stepOn), 60, null); // prompt
        } else{
            g2.drawImage(images[3], 0, 0, null); // fax machine
            if (!isComplete()) {
                g2.drawImage(images[5], 120, 60, null); // prompt
            } else {
                g2.drawImage(images[4], 0 , 0, null); // prompt
            }
        }
        
        
        
        
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
            SoundPlayer.getSoundPlayer().playFaxMachine();
        }
    }
    
    @Override
    public void resetForDay() {
        stepOn = 0;
        currWaitTime = 0;
        waitTimeDoneFaxing = 4000;
        setComplete(false);
    }
}
