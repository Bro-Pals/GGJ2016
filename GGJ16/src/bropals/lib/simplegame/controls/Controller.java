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
package bropals.lib.simplegame.controls;

import bropals.lib.simplegame.KeyListener;
import bropals.lib.simplegame.MouseListener;
import bropals.lib.simplegame.state.GameState;

/**
 * Skeleton class for separating Player input code from behavior code.
 * @author Jonathon
 */
public abstract class Controller implements KeyListener, MouseListener {
    
    /**
     * The game state containing this controller.
     */
    private GameState state;

    /**
     * Gets the game state that this controller is associated with.
     * @return the game state that this controller is associated with.
     */
    public GameState getState() {
        return state;
    }

    /**
     * Gets the game state that this controller is associated with.
     * @param state the game state that this controller is associated with.
     */
    public void setState(GameState state) {
        this.state = state;
    }
}
