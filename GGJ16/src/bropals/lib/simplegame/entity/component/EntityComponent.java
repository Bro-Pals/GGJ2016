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

/**
 * The super class for entity components. To be used in conjunction with
 * {@link bropals.lib.simplegame.entity.component.BaseComponentEntity}.
 * @author Jonathon
 */
public abstract class EntityComponent {
    
    /**
     * The component entity that this EntityComponent is attached to.
     */
    private BaseComponentEntity parent = null;

    /**
     * Gets the BaseComponentEntity that this EntityComponent is attached to.
     * @return the BaseComponentEntity that this EntityComponent is attached to
     */
    public BaseComponentEntity getParent() {
        return parent;
    }

    /**
     * Sets the BaseComponentEntity that this EntityComponent is attached to.
     * @param parent the BaseComponentEntity that this EntityComponent is attached to
     */
    public void setParent(BaseComponentEntity parent) {
        this.parent = parent;
    }
    
    /**
     * Updates this EntityComponent. Implement its behavior here.
     */
    public abstract void update(int mills);
}
