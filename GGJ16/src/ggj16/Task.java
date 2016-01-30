
package ggj16;

import ggj16.tasks.objs.TaskObject;
import bropals.lib.simplegame.KeyListener;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;
import bropals.lib.simplegame.state.GameState;
import java.awt.Graphics2D;

/**
 * A task to be completed!
 * 
 * @author Kevin
 */
public abstract class Task implements KeyListener {

    GameWorld<TaskObject> world; // all of the objects for this task
    boolean isComplete;
    
    public Task(GameState stateInside) {
        isComplete = false;
        world = new GameWorld<TaskObject>(stateInside);
    }
    
    /**
     * Update the task
     * @param ms  milliseconds that frame
     */
    public void update(int ms) {
        
    }
    
    /**
     * Render this graphics object
     * @param g2 A CLIPPED graphics object to draw to.
     */
    public void render(Graphics2D g2) {
        
    }
    
    @Override
    public void key(int i, boolean bln) {
    }

    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Set if a task is completed or not
     * @param isComplete Is this task completed?
     */
    protected void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    
}
