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

import bropals.lib.simplegame.state.GameState;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Represents the drawing window. Can be implemented by multiple types of
 * drawing.
 * @author Jonathon
 */
public interface GameWindow {
    /**
     * Sets the image icon of this GameWindow.
     * @param icon the icon to set it to.
     */
    public void setIcon(BufferedImage icon);
    /**
     * Checks to see if the full screen resolution is supported. If the size
     * does not work for fullscreen, it will still work for a window.
     * @param screenWidth the screen width to check
     * @param screenHeight the screen height to check
     * @return if the resolution is supported
     */
    public boolean supportsResolution(int screenWidth, int screenHeight);
    /**
     * Checks to see if the full screen resolution is supported. If the size
     * does not work for fullscreen, it will still work for a window.
     *
     * @param resolution the screen resolution to check
     * @return if the resolution is supported
     */
    public boolean supportsResolution(ScreenResolution resolution);
    /**
     * Makes a list of ScreenResolutions that the given graphics device
     * supports.
     *
     * @param device the graphics device
     * @return the list of supported screen resolutions
     */
    public ScreenResolution[] getSupportedScreenResolutionList(GraphicsDevice device);
    /**
     * Checks to see if the AWTGameWindow is requesting to close.
     *
     * @return the request to close state.
     */
    public boolean isRequestingToClose();
    /**
     * Checks to see if the AWTGameWindow is in fullscreen.
     *
     * @return the fullscreen state of the window.
     */
    public boolean isFullscreen();
    /**
     * Checks to see if the AWTGameWindow is in windowed mode.
     *
     * @return the windowed state of the window.
     */
    public boolean isWindowed();
    /**
     * Sets the fullscreen state of the AWTGameWindow. Call
     * <code>applyGraphicsConfiguration()</code> to update the fullscreen state.
     *
     * @param fullscreen the new fullscreen state.
     */
    public void setFullscreen(boolean fullscreen);
    /**
     * Sets the windowed state of the AWTGameWindow. Call
     * <code>applyGraphicsConfiguration()</code> to update the windowed state.
     *
     * @param windowed the new windowed state.
     */
    public void setWindowed(boolean windowed);
    /**
     * Sets the screen resolution of the AWTGameWindow. Call
     * <code>applyGraphicsConfiguration()</code> to update the fullscreen state.
     * If the screen resolution is not supported, this method returns
     * <code>false</code>.
     * 
     * @param screenResolution the new screen resolution.
     * @return if the screen change was successful.
     */
    public boolean setScreenResolution(ScreenResolution screenResolution);
    /**
     * Gets the current game cursor. If <code>null</code> then the default
     * system cursor is used.
     * @return the game cursor
     */
    public GameCursor getGameCursor();
    /**
     * Sets the game cursor. If <code>null</code> then the default
     * system cursor is used.
     * @param gameCursor the game cursor
     */
    public void setGameCursor(GameCursor gameCursor);
    /**
     * Destroys the window and all of its used resources.
     */
    public void destroy();
    /**
     * Gets the screen width in pixels.
     *
     * @return the screen width in pixels.
     */
    public int getScreenWidth();
    /**
     * Gets the screen height in pixels.
     *
     * @return the screen height in pixels.
     */
    public int getScreenHeight();
    /**
     * Request to close the game window.
     */
    public void requestToClose();
    /**
     * Gets the screen resolution.
     *
     * @return the screen resolution.
     */
    public ScreenResolution getScreenResolution();
    /**
     * Process all input that has occurred since the last frame.
     */
    public void flushInput();
    /**
     * For internal use only
     * @param runner 
     */
    public void giveGameStateRunner(GameStateRunner runner);
    /**
     * Renders the given game state.
     * @param state the state to render.
     */
    public void renderState(GameState state);
    /**
     * Gets the mouse position in the screen's coordinate space.
     * Returns (-1, -1) if the mouse
     * position is not on the screen.
     * @return the mouse position.
     */
    public Point getMousePosition();
    /**
     * Registers a quit handler
     * @param handler the quit handler
     */
    public void registerQuitHandler(QuitHandler handler);
    /**
     * Unregisters a quit handler
     * @param handler the quit handler
     */
    public void unregisterQuitHandler(QuitHandler handler);
}
