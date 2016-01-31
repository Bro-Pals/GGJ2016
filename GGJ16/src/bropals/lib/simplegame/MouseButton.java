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
package bropals.lib.simplegame;

import bropals.lib.simplegame.logger.ErrorLogger;
import static java.awt.event.MouseEvent.*;
//import static org.lwjgl.glfw.GLFW.*;

/**
 * The library's mouse button codes to use.
 * @author Jonathon
 */
public class MouseButton {
    
    public static final int BUTTON_LEFT=0, BUTTON_RIGHT=1, BUTTON_MIDDLE=2;
    
    /**
     * Convert the mouse button code used by LWJGL to the BroPals game library format.
     * @param lwjglMouseButton A mouse button code by LWJGL
     * @return A mouse button code used by BroPals game library
     */
    /*
    public static int convertLWJGLCodeToBroPalsCode(int lwjglMouseButton) {
        switch(lwjglMouseButton) {
            case GLFW_MOUSE_BUTTON_LEFT:
                return BUTTON_LEFT;
            case GLFW_MOUSE_BUTTON_RIGHT:
                return BUTTON_RIGHT;
            case GLFW_MOUSE_BUTTON_MIDDLE:
                return BUTTON_MIDDLE;
        }
        ErrorLogger.println("Could not recognize LWJGL mouse button: " + lwjglMouseButton);
        return -1;
    }
    */
    
    /**
     * Convert the mouse button code used by java.awt to the BroPals game library format.
     * @param awtMouseButton A mouse button code by java.awt
     * @return A mouse button code used by BroPals game library
     */
    public static int convertAWTCodeToBroPalsCode(int awtMouseButton) {
        switch(awtMouseButton) {
            case BUTTON1: return BUTTON_LEFT;
            case BUTTON2: return BUTTON_MIDDLE;
            case BUTTON3: return BUTTON_RIGHT;
        }
        ErrorLogger.println("Could not recognize AWT mouse button: " + awtMouseButton);
        return -1;
    }
}
