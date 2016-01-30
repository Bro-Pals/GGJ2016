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
        images = new BufferedImage[]{
            stateInside.getAssetManager().getImage("coffeepot1"),
            stateInside.getAssetManager().getImage("coffeepot2"),
            stateInside.getAssetManager().getImage("coffeepot3"),
            stateInside.getAssetManager().getImage("coffeepot4"),
            stateInside.getAssetManager().getImage("taskCompletePrompt"),
            stateInside.getAssetManager().getImage("brewingPrompt"), // 5
            stateInside.getAssetManager().getImage("fKeyPrompt"),
            stateInside.getAssetManager().getImage("pKeyPrompt"),
            stateInside.getAssetManager().getImage("oKeyPrompt"),};
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        if (!isComplete()) {
            if (stepOn == codes.length) {
                currWaitTime += ms;
                if (currWaitTime > waitForCoffeeTime) {
                    setComplete(true);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 300);

        BufferedImage img = null;
        if (currWaitTime < waitForCoffeeTime / 3) {
            img = images[0];
        } else if (currWaitTime < (waitForCoffeeTime / 3) * 2) {
            img = images[1];
        } else if (currWaitTime < waitForCoffeeTime) {
            img = images[2];
        } else {
            img = images[3];
        }
        g2.drawImage(img, 100, 50, null);
        if (stepOn < 3) {
            g2.drawImage(images[6 + stepOn], 100 + (30 * stepOn), 60, null);
        }
        if (stepOn == codes.length && !isComplete()) {
            // show brewing prompt when coffee is being made
            g2.drawImage(images[5], 120, 60, null);
        }

        // draw completed prompt on completion
        if (isComplete()) {
            g2.drawImage(images[4], 0, 0, null);
        }
    }

    @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (!bln) {
            return;
        }
        if (stepOn != codes.length && i == codes[stepOn]) {
            stepOn++;
        }
    }

    @Override
    public void resetForDay() {
        stepOn = 0;
        currWaitTime = 0;
        setComplete(false);
    }

}
