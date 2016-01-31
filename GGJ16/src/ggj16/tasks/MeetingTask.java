
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
public class MeetingTask extends Task {

    // a row of people in a meeting
    // you're tapping "M" to get stuff done
    // when somebody falls asleep, you can't press M until your arrow goes over 
    // them and you press "F", then you can keep on getting stuff done with pressing M
    
    // what step you're on
    int stepOn = 0;
    int arrowOver = 0; // who you have your arrow over, changed with A and D keys
    int sleeper = -1; // when -1 nobody is sleeping, otherwise it's equal to who is sleeping
    int numPeople = 4; // how many people there are (sleeper can't be greater than numPeople-1)
    int workLeft = 25; // how much work is left to do. it wants to be 0
    
    // stepOn == 0 and M makes stuff productive
    // stepOn == 1 and arrowOver == sleeper and F will make stepOn = 0
    
    int timeBetweenRandomSleeper = 2000;
    int timeUntilSleeper = 0;
    int booTimeLeft;
    int speechTimeLeft;
    
    private BufferedImage bg;
    private BufferedImage awake;
    private BufferedImage asleep;
    private BufferedImage speech;
    private BufferedImage boo;
    private BufferedImage marker;
    private BufferedImage aPrompt, dPrompt, mPrompt, completePrompt;
    private BufferedImage fTowakePrompt;
    private int[][] workerPositions;
    private int[] speechPosition;
    
    public MeetingTask(GameState stateInside) {
        super(stateInside, "Conduct meeting");
        bg = stateInside.getAssetManager().getImage("meetingBackground");
        awake = stateInside.getAssetManager().getImage("workerAwake");
        asleep = stateInside.getAssetManager().getImage("workerAsleep");
        speech = stateInside.getAssetManager().getImage("speech");
        boo = stateInside.getAssetManager().getImage("boo");
        marker = stateInside.getAssetManager().getImage("marker");
        aPrompt = stateInside.getAssetManager().getImage("aKeyPrompt");
        dPrompt = stateInside.getAssetManager().getImage("dKeyPrompt");
        mPrompt = stateInside.getAssetManager().getImage("mKeyPrompt");
        fTowakePrompt = stateInside.getAssetManager().getImage("wakeUpPrompt");
        completePrompt = stateInside.getAssetManager().getImage("taskCompletePrompt");

        workerPositions = new int[][] {
            new int[] { 34, 83 },
            new int[] { 111, 83 },
            new int[] { 182, 83 },
            new int[] { 250, 83 }
        };
        speechPosition = new int[] { 311, 24 };
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        if (booTimeLeft>0) {
            booTimeLeft -= ms;
        }
        if (speechTimeLeft>0) {
            speechTimeLeft -= ms;
        }
        if (stepOn == 0) {
            // randomly have a sleeper appear and make stepOn == 1
            
            // after enough time passes
            timeUntilSleeper += ms;
            if (timeUntilSleeper > timeBetweenRandomSleeper) {
                if (Math.random() < 0.5) { // 50% chance
                    // make somebody be the sleeper
                    sleeper = (int)(Math.random() * numPeople);
                    stepOn = 1;
                    timeUntilSleeper = 0; // reset timer
                }
            }
        }
    }
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 400, 300);
        
        g2.drawImage(bg, 0, 0, null);
        for (int i=0; i<4; i++) {
            if (i == sleeper) {
                g2.drawImage(asleep, workerPositions[i][0], workerPositions[i][1], null);
            } else {
                g2.drawImage(awake, workerPositions[i][0], workerPositions[i][1], null);
            }
        }
        g2.drawImage(marker, workerPositions[arrowOver][0]+7, workerPositions[arrowOver][1]-50, null );
        if (speechTimeLeft>0) {
            g2.drawImage(speech, speechPosition[0], speechPosition[1], null);
        }
        if (booTimeLeft>0) {
            g2.drawImage(boo, speechPosition[0], speechPosition[1], null);
        }
        
        // control prompts
        if (stepOn == 0) {
            g2.drawImage(mPrompt, 120, 240, null);
        } else if (stepOn == 1) {
            g2.drawImage(aPrompt, 60, 240, null);
            g2.drawImage(dPrompt, 130, 240, null);
            g2.drawImage(fTowakePrompt, 240, 240, null);
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
        if (stepOn == 0 && i == KeyCode.KEY_M) {
            workLeft--;
            speechTimeLeft = 150;
            if (workLeft <= 0) {
                setComplete(true);
            }
        } else if (stepOn == 1 && arrowOver == sleeper && i == KeyCode.KEY_F) {
            // wake him up
            stepOn = 0;
            sleeper = -1;
            booTimeLeft = 250;
        }
        
        if (i == KeyCode.KEY_A) {
            arrowOver--;
            if (arrowOver < 0) {
                arrowOver = 0;
            }
        } else if (i == KeyCode.KEY_D) {
            arrowOver++;
            if (arrowOver >= numPeople) {
                arrowOver = numPeople - 1;
            }
        }
    }
    
    @Override
    public void resetForDay() {
        stepOn = 0;
        arrowOver = 0;
        sleeper = -1;
        numPeople = 4;
        workLeft = 25;
        speechTimeLeft = 0;
        booTimeLeft = 0;
        timeUntilSleeper = 0;
        setComplete(false);
    }
    
    @Override
    public void getAfflictedByObstruction() {
        workLeft = 25;
    }
}
