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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **/
package bropals.lib.simplegame.entity;

import bropals.lib.simplegame.state.GameState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A generic GameWorld that holds objects of type {@link bropals.lib.simplegame.entity.BaseEntity}.
 * The GameWorld will update and render all the entities.
 * 
 * @author Kevin Prehn
 * @param <T> the type of Entity that this GameWorld uses as its base entity type.
 */
public class GameWorld<T extends BaseEntity> {
    
    private final List<T> entities;
    private GameState stateInside;
    
    /**
     * Create a GameWorld that holds entities. Initially starts with no entities.
     * @param stateIn The state containing this game world.
     */
    public GameWorld(GameState stateIn) {
        stateInside = stateIn;
        entities = new ArrayList<>();
    }
    
    /**
     * Updates all the entities in this GameWorld's list of entities. It
     * will also remove any entity whose parent is not this GameWorld or null.
     */
    public void updateEntities(int mills) {
        for (int i=0; i<entities.size(); i++) {
            // remove the entity from this game world if it doesn't belong
            // here or if it's been removed
            if (entities.get(i).getParent() != this) {
                entities.remove(i);
                continue;
            }
            entities.get(i).update(mills);
        }
    }
    
    /**
     * Returns a list of the entities in this GameWorld.
     * @return A list of the entities in this GameWorld.
     */
    public List<T> getEntities() {
        return entities;
    }
    
    /**
     * Adds an entity to the list of entities if it's not already added.
     * @param entity The entity being added.
     */
    public void addEntity(T entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
            entity.setParent(this);
        }
    }
    
    /**
     * Get the game state containing this game world.
     * @return The game state containing this game world.
     */
    public GameState getState() {
        return stateInside;
    }

    /**
     * Set the game state containing this game world.
     * @param stateInside The game state containing this game world.
     */
    public void setStateInside(GameState stateInside) {
        this.stateInside = stateInside;
    }
    
    
}
