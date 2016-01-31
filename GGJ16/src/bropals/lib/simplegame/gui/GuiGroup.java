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
package bropals.lib.simplegame.gui;

import java.util.ArrayList;

/**
 * Makes it easier to group GuiElements together.
 * @author Jonathon
 */
public class GuiGroup {
    
    private ArrayList<GuiElement> group = new ArrayList<GuiElement>();
    private boolean enabled = true;
    
    /**
     * Adds an element to this GuiGroup
     * @param element the element to add
     */
    public void addElement(GuiElement element) {
        group.add(element);
    }
    
    /**
     * Removes an element from this GuiGroup
     * @param element the element to remove
     */
    public void removeElement(GuiElement element) {
        group.remove(element);
    }

    /**
     * Checks to see if this GuiGroup is enabled.
     * @return the enabled state of this GuiGroup.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled state of this GuiGroup. When this GuiGroup is not
     * enabled, then it will not draw, update, or react to input even when
     * its functions are called.
     * @param enabled the new enabled state.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
        
    /**
     * Updates all elements in this GuiGroup
     * @param mouseX the current X mouse position
     * @param mouseY the current Y mouse position
     */
    public void update(int mouseX, int mouseY) {
        if (!isEnabled())
            return;
        for (GuiElement element : group) {
            element.update(mouseX, mouseY);
        }
    }
    
    /**
     * Draws all elements in this GuiGroup
     * @param graphicsObject the graphics object to draw this GuiGroup with
     */
    public void render(Object graphicsObject) {
        if (!isEnabled())
            return;
        for (GuiElement element : group) {
            element.render(graphicsObject);
        }
    }
    
    /**
     * Sends mouse pressed input to all elements in this GuiGroup
     * @param mouseX the X mouse position
     * @param mouseY the Y mouse position
     */
    public void mouseInput(int mouseX, int mouseY) {
        if (!isEnabled())
            return;
        for (GuiElement element : group) {
            element.mouseInput(mouseX, mouseY);
        }
    }
}
