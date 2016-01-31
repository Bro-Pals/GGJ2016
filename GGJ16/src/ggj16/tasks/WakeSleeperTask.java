/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.tasks;

import bropals.lib.simplegame.state.GameState;
import ggj16.Task;

/**
 *
 * @author Jonathon
 */
public class WakeSleeperTask extends Task {

    public WakeSleeperTask(GameState stateInside, String desc) {
        super(stateInside, desc);
    }

    @Override
    public void resetForDay() {
    }
    
    @Override
    public void getAfflictedByObstruction() {
        
    }
}
