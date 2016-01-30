
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
public class ScareHouseTask extends Task {
    
    
    private boolean right, left;
    private float littleDemonPosX;
    private int[] housesPosX;
    private boolean[] housesScared;
    private BufferedImage completePrompt, houseNotScared, houseIsScared, demonTinyTask;
    private BufferedImage scarePrompt, aPrompt, dPrompt, roadImage;
    
    public ScareHouseTask(GameState stateInside) {
        super(stateInside, "Scare house");
        littleDemonPosX = 30;
        housesPosX = new int[]{120, 190, 250};
        housesScared = new boolean[]{false, false, false};
        completePrompt = stateInside.getAssetManager().getImage("taskCompletePrompt");
        houseNotScared = stateInside.getAssetManager().getImage("houseNotScared");
        houseIsScared = stateInside.getAssetManager().getImage("houseIsScared");
        scarePrompt = stateInside.getAssetManager().getImage("scarePrompt");
        aPrompt = stateInside.getAssetManager().getImage("aKeyPrompt");
        dPrompt = stateInside.getAssetManager().getImage("dKeyPrompt");
        demonTinyTask = stateInside.getAssetManager().getImage("demonTinyTask");
        roadImage = stateInside.getAssetManager().getImage("roadLunchTask");
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        float demonSpeed = 2.3f;
        if (right) {
            littleDemonPosX += demonSpeed;
            if (littleDemonPosX > 400) {
                littleDemonPosX = 400;
            }
        } else if (left) {
            littleDemonPosX -= demonSpeed;
            if (littleDemonPosX < 0) {
                littleDemonPosX = 0;
            }
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 300);
        
        /*
        Debug values
        g2.setColor(Color.BLACK);
        g2.drawString("Scare house task", 40, 60);
        */
        
        // road 
        g2.drawImage(roadImage, 50, 210, null);
        
        for (int j=0; j<housesPosX.length; j++) {
            if (housesScared[j]) {
                g2.drawImage(houseIsScared, housesPosX[j] - (houseIsScared.getWidth()/2), 160, null);
            } else {
                g2.drawImage(houseNotScared, housesPosX[j] - (houseNotScared.getWidth()/2), 160, null);
            }
        }
        
        // draw the little demon
        g2.drawImage(demonTinyTask, (int)littleDemonPosX, 200, null);
        
        g2.drawImage(aPrompt, 70, 40, null);
        g2.drawImage(dPrompt, 140, 40, null);
        g2.drawImage(scarePrompt, 230, 40, null);
        
        // draw complete on finishing the task
        if (isComplete()) {
            g2.drawImage(completePrompt, 0, 0, null);
        }

    }

     @Override
    public void key(int i, boolean bln) {
        super.key(i, bln);
        if (i == KeyCode.KEY_D) {
            right = bln;
        }
        if (i == KeyCode.KEY_A) {
            left = bln;
        }

        // scare the house the demon is nearby
        if (i == KeyCode.KEY_F) {
            boolean allScared = true;
            for (int j=0; j<housesPosX.length; j++) {
                if (Math.abs(littleDemonPosX - housesPosX[j] + (houseNotScared.getWidth()/2)) < 15) {
                    housesScared[j] = true;
                }
                if (!housesScared[j]) {
                    allScared = false;
                }
            }
            if (allScared) {
                // complete task when all the houses are scared
                setComplete(true);
                left = false;
                right = false;
            }
        }
        
    }
    
    @Override
    public void resetForDay() {
        littleDemonPosX = 30;
        for (int i=0; i<housesScared.length; i++) {
            housesScared[i] = false;
        }
        left = false;
        right = false;
        setComplete(false);
    }
}
