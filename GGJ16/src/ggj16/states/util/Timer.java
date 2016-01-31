/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.states.util;

/**
 *
 * @author Jonathon
 */
public class Timer {
    
    private TimerAction action;
    private int targetNumber;
    private int progress;
    private boolean stopped;
    
    public Timer(int targetNumber) {
        progress = 0;
        this.targetNumber = targetNumber;
        action = null;
        stopped = false;
    }
    
    public void setAction(TimerAction action) {
        this.action = action;
    }
    
    public TimerAction getAction() {
        return action;
    }
    
    public int getTargetNumber() {
        return targetNumber;
    }
    
    public void setTargetNumber(int targetNumber) {
        this.targetNumber=targetNumber;
    }
    
    public int getProgress() {
        return progress;
    }
    
    public void resetManually() {
        progress = 0;
    }
    
    public void updateTimer(int delta) {
        if (!stopped) {
            progress += delta;
            if (progress >= targetNumber) {
                if (action != null) {
                    action.intervalComplete();
                    progress = 0;
                }
            }
        }
    }
    
    public boolean isStopped() {
        return stopped;
    }
    
    public boolean isRunning() {
        return !stopped;
    }
    
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
