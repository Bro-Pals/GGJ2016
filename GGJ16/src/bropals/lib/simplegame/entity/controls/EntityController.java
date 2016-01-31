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
package bropals.lib.simplegame.entity.controls;

import bropals.lib.simplegame.controls.Controller;
import bropals.lib.simplegame.entity.block.BlockEntity;

/**
 * A skeleton for implementing how the Player controls an entity.
 * @author Jonathon
 */
public abstract class EntityController extends Controller {
    
    private BlockEntity controlling;
    
    /**
     * The constructor for EntityController.
     * @param controlling the entity that is to be controlled by this controller.
     */
    public EntityController(BlockEntity controlling) {
        this.controlling = controlling;
    }
    
/**
     * Gets the entity that is being controlled.
     * @return the entity that is being controlled.
     */
    public BlockEntity getControlling() {
        return controlling;
    }

    /**
     * Sets the entity that is being controlled.
     * @param controlling the entity that is to be controlled.
     */
    public void setControlling(BlockEntity controlling) {
        this.controlling = controlling;
    }}
