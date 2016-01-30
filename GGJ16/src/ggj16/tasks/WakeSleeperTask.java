
package ggj16.tasks;

import bropals.lib.simplegame.state.GameState;
import ggj16.Task;

/**
 *
 * @author Kevin
 */
public class WakeSleeperTask extends Task {

    public WakeSleeperTask(GameState stateInside, String desc) {
        super(stateInside, desc);
    }

    @Override
    public void resetForDay() {
        setComplete(false);
    }
}
