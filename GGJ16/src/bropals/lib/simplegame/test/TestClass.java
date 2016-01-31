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
package bropals.lib.simplegame.test;

import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.gui.GuiElement;
import bropals.lib.simplegame.gui.GuiGroup;
import bropals.lib.simplegame.logger.InfoLogger;
import bropals.lib.simplegame.state.GameState;
import java.awt.Point;

/**
 *
 * @author Jonathon
 */
public class TestClass extends GameState {

    private GuiButton guiButton;

    @Override
    public void update(int mills) {
        //Get the mouse position
        Point mousePosition = getWindow().getMousePosition();
        //If the mouse is not in the window, then
        //getMousePosition returns null.
        if (mousePosition != null) {
            guiButton.update(mousePosition.x, mousePosition.y);
        }
    }

    @Override
    public void render(Object graphicsObj) {        
        guiButton.render(graphicsObj);
    }

    @Override
    public void onEnter() {
        guiButton = new GuiButton(
                50, //Top left X
                50, //Top left Y
                100, //Width in pixels
                100, //Height in pixels
                //Down state image. This is when the button
                //has been pressed
                getImage("buttonDown.png"),
                //Up state image. This is when the button
                //is not having the mouse hover over it.
                getImage("buttonUp.png"), 
                //Hover state image. This is when the mouse
                //is hovering over the button, but not
                //pressing it.
                getImage("buttonHover.png"),
                //The action that happens when
                //the button is pressed.
                new CoolGuiButtonAction()
        );
                
    }

    @Override
    public void onExit() {
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        if (pressed) {
            guiButton.mouseInput(x, y);
        }
    }

    class CoolGuiButtonAction implements GuiButtonAction {

        @Override
        public void onButtonPress() {
            InfoLogger.println("Pressed the button");
        }

    }
    
    /*
        This GuiElement turns red when you hover
        your mouse over it.
    */
    class CoolGuiElement extends GuiElement {

        private boolean hovering;
        
        public CoolGuiElement(int x, int y, int w, int h) {
            super(x, y, w, h);
        }

        @Override
        public void update(int mouseX, int mouseY) {
            hovering = contains(mouseX, mouseY);
        }        
        
        @Override
        public void render(Object graphicsObject) {
        }
        
    }
}
