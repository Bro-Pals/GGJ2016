
package ggj16.tasks.objs;

import bropals.lib.simplegame.entity.GameWorld;
import ggj16.Camera;
import ggj16.Employee;
import ggj16.Task;
import ggj16.officeobjects.OfficeTaskObject;
import java.awt.image.BufferedImage;

/**
 * The task that is going to be for the interview task and the wake up employee task objects.
 * @author Kevin
 */
public class ChangeEmployeeStateTaskObject extends OfficeTaskObject {
    private Employee targetEmployee;
    private int stateToChangeTo;

    public ChangeEmployeeStateTaskObject(GameWorld par, float x, 
            BufferedImage image, Camera camera, Task associatedTask, Employee tarEmp, int stateTo) {
        super(par, x, image, camera, associatedTask);
        this.targetEmployee = tarEmp;
        this.stateToChangeTo = stateTo;
    }    

    @Override
    public void update(int i) {
        super.update(i); 
        // when the task is done, update the employee state
        if (getAssociatedTask().isComplete()) {
            targetEmployee.setState(stateToChangeTo);
        }
    }
    
    

}
