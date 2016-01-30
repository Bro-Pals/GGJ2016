
package ggj16;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;

/**
 *
 * @author Kevin
 */
public class OfficeObject extends BaseEntity {

    float x;
    
    public OfficeObject(GameWorld par, float x) {
        super(par);
        this.x = x;
    }

    @Override
    public void update(int i) {
    }

    @Override
    public void render(Object o) {
    }

}
