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

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.KeyListener;
import bropals.lib.simplegame.entity.block.BlockEntity;

/**
 * Associate with an Entity to have them be controlled in a top-down fashion.
 * Moving up, down, left, and right causes the entity to get velocity in 
 * the direction that is being held.
 * @author Jonathon
 */
public class TopDownEntityController extends EntityController {
    
    private int keyUp;
    private int keyDown;
    private int keyLeft;
    private int keyRight;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private float speed;

    /**
     * Creates a controller that controls an entity with WASD.
     * @param controlling the entity to control.
     * @param speed the movement speed of the controlled entity.
     */
    public TopDownEntityController(BlockEntity controlling, float speed) {
        super(controlling);
        keyUp = KeyCode.KEY_W;
        keyDown = KeyCode.KEY_S;
        keyLeft = KeyCode.KEY_A;
        keyRight = KeyCode.KEY_D;
        this.speed = speed;
    }

    /**
     * Creates a controller that controls an entity with the specified keys.
     * @param keyUp the "move up" key.
     * @param keyDown the "move down" key.
     * @param keyLeft the "move left" key.
     * @param keyRight the "move right" key.
     * @param controlling the entity to control.
     * @param speed the movement speed of the controlled entity.
     */
    public TopDownEntityController(int keyUp, int keyDown, int keyLeft, int keyRight, BlockEntity controlling, float speed) {
        super(controlling);
        this.keyUp = keyUp;
        this.keyDown = keyDown;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.speed = speed;
    }    

    /**
     * Gets the "move up" key.
     * @return the "move up" key.
     */
    public int getKeyUp() {
        return keyUp;
    }

    /**
     * Sets the "move up" key.
     * @param keyUp the "move up" key.
     */
    public void setKeyUp(int keyUp) {
        this.keyUp = keyUp;
    }

    /**
     * Gets the "move down" key.
     * @return the "move down" key.
     */
    public int getKeyDown() {
        return keyDown;
    }

    /**
     * Sets the "move down" key.
     * @param keyDown the "move down" key.
     */
    public void setKeyDown(int keyDown) {
        this.keyDown = keyDown;
    }

    /**
     * Gets the "move left" key.
     * @return the "move left" key.
     */
    public int getKeyLeft() {
        return keyLeft;
    }

    /**
     * Sets the "move left" key.
     * @param keyLeft the "move left" key.
     */
    public void setKeyLeft(int keyLeft) {
        this.keyLeft = keyLeft;
    }

    /**
     * Gets the "move right" key.
     * @return the "move right" key.
     */
    public int getKeyRight() {
        return keyRight;
    }

    /**
     * Sets the "move right" key.
     * @param keyRight the "move right" key.
     */
    public void setKeyRight(int keyRight) {
        this.keyRight = keyRight;
    }
    
    /**
     * Gets the movement speed of the controlled entity.
     * @return the movement speed of the controlled entity.
     */
    public float getSpeed() {
        return speed;
    }
    
    /**
     * Sets the movement speed of the controlled entity.
     * @param speed the movement speed of the controlled entity.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    /**
     * Updates the logic of the control.
     */
    public void updateControls() {
        if (movingUp) {
            getControlling().getVelocity().setY(-speed);
        } else if (movingDown) {
            getControlling().getVelocity().setY(speed);
        } else {
            getControlling().getVelocity().setY(0);
        }
        if (movingRight) {
            getControlling().getVelocity().setX(speed);
        } else if (movingLeft) {
            getControlling().getVelocity().setX(-speed);
        } else {
            getControlling().getVelocity().setX(0);
        }
    }

    @Override
    public void key(int keycode, boolean pressed) {
        if (keycode == keyUp) {
            movingUp = pressed;
        } else if (keycode == keyDown) {
            movingDown = pressed;
        } else if (keycode == keyLeft) {
            movingLeft = pressed;
        } else if (keycode == keyRight) {
            movingRight = pressed;
        }
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
    }
    
}
