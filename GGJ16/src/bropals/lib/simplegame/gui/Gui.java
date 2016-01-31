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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Groups together GuiGroups. Each GuiGroup will have a name that it is
 * referenced by, given to it when the GuiGroup is added to this Gui.
 * @author Jonathon
 */
public class Gui {
    
    private Map<String, GuiGroup> groups = new HashMap<>();
    private List<GuiGroup> groupSet = new ArrayList<>();
    
    /**
     * Adds a GuiGroup to this Gui.
     * @param key the key to give to the group
     * @param group the group to add to the Gui
     */
    public void addGroup(String key, GuiGroup group) {
        groups.put(key, group);
        groupSet.add(group);
    }
    
    /**
     * Removes the GuiGroup with the specified key.
     * @param key the key of the group to remove
     */
    public void removeGroup(String key) {
        groupSet.remove(groups.get(key));
        groups.remove(key);
    }
    
    /**
     * Sets the enabled state of the specified GuiGroup
     * @param key the key of the group to set the enabled state of
     * @param enabled the enabled state of the group
     */
    public void setEnabled(String key, boolean enabled) {
        groups.get(key).setEnabled(enabled);
    }
    
    /**
     * Enables the GuiGroup with the specified key.
     * @param key the key of the specified GuiGroup
     */
    public void enable(String key) {
        setEnabled(key, true);
    }
    
    /**
     * Disables the GuiGroup with the specified key.
     * @param key the key of the specified GuiGroup
     */
    public void disable(String key) {
        setEnabled(key, false);
    }
    
    /**
     * Disables all the GuiGroups in this Gui except for the specified ones.
     * @param dontDisable the GuiGroups that shouldn't be disabled.
     */
    public void disableAllBut(String ... dontDisable) {
        Set<String> keys = groups.keySet();
        for (String key : keys) {
            boolean ok = true;
            for (String str : dontDisable) {
                if (key.equals(str)) {
                    ok = false;
                }
            }
            if (ok) {
                disable(key);
            }
        }
    }
    
    /**
     * Disables all the GuiGroups, and then enables the ones in the 
     * <code>enable</code> list.
     * @param enable the list of GuiGroups to enable
     */
    public void enableOnly(String ... enable) {
        Set<String> keys = groups.keySet();
        for (String key : keys) {
            boolean ok = false;
            for (String str : enable) {
                if (key.equals(str)) {
                    ok = true;
                }
            }
            if (ok) {
                enable(key);
            }
        }
    }
    
    /**
     * Enables one GuiGroup while disabling another.
     * @param on the key of the GuiGroup to enable.
     * @param off the key of the GuiGroup to disable.
     */
    public void swap(String on, String off) {
        enable(on);
        disable(off);
    }
    
    /**
     * Updates all enabled GuiGroups in this Gui
     * @param mouseX the current X mouse position
     * @param mouseY the current Y mouse position
     */
    public void update(int mouseX, int mouseY) {
        for (GuiGroup group : groupSet) {
            group.update(mouseX, mouseY);
        }
    }
    
    /**
     * Draws all enabled GuiGroups in this Gui
     * @param graphicsObject the graphics object to draw the GuiGroups with
     */
    public void render(Object graphicsObject) {
        for (GuiGroup group : groupSet) {
            group.render(graphicsObject);
        }
    }
    
    /**
     * Sends mouse pressed input to all enabled GuiGroups in this Gui
     * @param mouseX the X mouse position
     * @param mouseY the Y mouse position
     */
    public void mouseInput(int mouseX, int mouseY) {
        for (GuiGroup group : groupSet) {
            group.mouseInput(mouseX, mouseY);
        }
    }
}
