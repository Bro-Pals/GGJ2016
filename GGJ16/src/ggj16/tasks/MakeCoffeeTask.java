
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
public class MakeCoffeeTask extends Task {

    // 3 steps: F, P, O
    int stepOn = 0;
    // codes to do in order
    int[] codes = new int[]{
        KeyCode.KEY_F, 
        KeyCode.KEY_P, 
        KeyCode.KEY_O
    };
    
    int currWaitTime = 0;
    int waitForCoffeeTime = 5000; // 5 seconds

    private BufferedImage[] images;
    
    public MakeCoffeeTask(GameState stateInside) {
        super(stateInside, "Make coffee");
        images = new BufferedImage[] {
            stateInside.getAssetManager().getImage("coffeepot1"),
            stateInside.getAssetManager().getImage("coffeepot2"),
            stateInside.getAssetManager().getImage("coffeepot3"),
            stateInside.getAssetManager().getImage("coffeepot4")
        };
    }
    
    @Override
    public void update(int ms) {
        super.update(ms); 
        if (stepOn == codes.length) {
            currWaitTime += ms;
            System.out.println("BREWING");
            if (currWaitTime > waitForCoffeeTime) {
                setComplete(true);
            }
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 300);
        
        BufferedImage img = null;
        if (waitForCoffeeTime-currWaitTime > (waitForCoffeeTime/4)*3) {
            img = images[3];
        } else if (waitForCoffeeTime-currWaitTime > waitForCoffeeTime/2) {
            img = images[2];
        } else if (waitForCoffeeTime-currWaitTime > waitForCoffeeTime/4) {
            img = images[1];
        } else {
            img = images[0];
        }
        g2.drawImage(img, 100, 50, null);
    }


    @Override
    public void key(int i, boolean bln) {
        super.key(i, bln); 
        if (stepOn != codes.length && i == codes[stepOn]) {
            stepOn++;
            if (stepOn == codes.length) { 
                setComplete(true);
            }
        }
    }

    
 

}
