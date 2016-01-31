/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Jonathon Prehn and Kevin Prehn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package bropals.lib.simplegame.entity.component;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import java.util.ArrayList;

/**
 * An entity that uses components to define its behavior. Rendering is
 * done by extending and overriding <code>render()</code> like BaseEntity.
 * @author Jonathon
 */
public abstract class BaseComponentEntity extends BaseEntity {

    private final ArrayList<EntityComponent> components = new ArrayList<>();
    
    /**
     * Creates a new component entity with the given parent. The parent will
     * add this entity to its list of entities.
     * @param par this component entity's parent.
     */
    public BaseComponentEntity(GameWorld par) {
        super(par);
        
    }

    @Override
    public void update(int mills) {
        for (EntityComponent ec : components) {
            ec.update(mills);
        }
    }
    
    /**
     * Adds a component to this entity, extending its behavior.
     * @param component the component to add
     */
    public void addComponent(EntityComponent component) {
        components.add(component);
    }
    
    /**
     * Removes a component from this entity, shrinking its behavior.
     * @param component the component to remove
     */
    public void removeComponent(EntityComponent component) {
        components.remove(component);
    }
    
    /**
     * Gets the component of the specified type in this entity.
     * @param <T> the type of the component that is being obtained
     * @param type the class of the component that is being obtained
     * @return the component of that type, or <code>null</code> if this
     * entity does not have a component of that type.
     */
    public <T extends EntityComponent> T getComponent(Class<T> type) {
        for (EntityComponent ec : components) {
            if (ec.getClass() == type) {
                return (T) ec;
            }
        }
        return null;
    }
}
