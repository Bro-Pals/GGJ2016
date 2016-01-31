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
package bropals.lib.simplegame.state;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Implements Entity rendering and updating. Need to call <code>super</code>
 * in all methods.
 * @author Jonathon
 */
public class EntityState extends GameState {

    private final GameWorld<BaseEntity> gameWorld = new GameWorld(this);

    /**
     * Gets this EntityState's GameWorld.
     * @return this EntityState's GameWorld
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }
    
    /**
     * Adds an Entity to GameWorld.
     * @param be the entity to add
     */
    public void addEntity(BaseEntity be) {
        gameWorld.addEntity(be);
    }

    @Override
    public void update(int mills) {
        gameWorld.updateEntities(mills);
    }

    @Override
    public void render(Object graphicsObj) {
        for (BaseEntity be : gameWorld.getEntities()) {
            be.render(graphicsObj);
        }
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }

    @Override
    public void key(int keycode, boolean pressed) {
        super.key(keycode, pressed);
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        super.mouse(mousebutton, x, y, pressed);
    }
}
