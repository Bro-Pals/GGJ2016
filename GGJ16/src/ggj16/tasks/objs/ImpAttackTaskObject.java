
package ggj16.tasks.objs;

import bropals.lib.simplegame.entity.GameWorld;
import ggj16.Camera;
import ggj16.Task;
import ggj16.officeobjects.OfficeTaskObject;
import java.awt.image.BufferedImage;

/**
 * The object that is set down 
 * @author Kevin
 */
public class ImpAttackTaskObject extends OfficeTaskObject {

    public ImpAttackTaskObject(GameWorld par, float x, BufferedImage image, Camera camera, Task associatedTask) {
        super(par, x, image, camera, associatedTask);
    }
    
   
}
