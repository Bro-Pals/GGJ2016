
package ggj16;

import bropals.lib.simplegame.KeyListener;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;
import java.awt.Graphics2D;

/**
 * A task to be completed!
 * 
 * @author Kevin
 */
public abstract class Task implements KeyListener {

    GameWorld<TexturedBlock> world; // all of the objects for this task
    
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
    
}
