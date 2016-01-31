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
package bropals.lib.simplegame;

import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.state.GameState;

/**
 * Continuously runs the update and render loop of a GameState object.
 * @author Kevin Prehn
 */
public class GameStateRunner {
    
    private AssetManager assetManager;
    private GameState currentState;
    private GameWindow currentWindow;
    private long startTime, diff;
    private int millisBetweenFrames;
    
    /**
     * Creates a GameStateRunner with a AWTGameWindow.
     * @param window The window that will be drawn to for this GameStateRunner
     * @param assetManager the asset manager to use for this GameStateRunner
     */
    public GameStateRunner(GameWindow window, AssetManager assetManager) {
        currentState = null;
        startTime = 0;
        millisBetweenFrames = 40;
        currentWindow = window;
        currentWindow.giveGameStateRunner(this);
        this.assetManager = assetManager;
    }
    
    /**
     * Creates a GameStateRunner with an initial GameState.
     * @param window The window that will be draw to for this GameStateRunner.
     * @param assetManager the asset manager to use for this GameStateRunner
     * @param initialState The initial state in this GameStateRunner.
     */
    public GameStateRunner(GameWindow window, AssetManager assetManager, GameState initialState) {
        this(window, assetManager);
        setState(initialState);
    }
    
    /**
     * Set how  many frames per second this GameStateRunner runs at.
     * @param fps How many frames per second this GameStateRunner will run at.
     */
    public void setFps(int fps) {
        millisBetweenFrames = (int)(1000.0/(double)fps);
    }
    
    /**
     * Sets the given GameState tot be the current state. The state
     * that was there before is removed after onExit() is called in it.
     * onEnter is called in the new GameState.
     * @param state The new GameState
     */
    public void setState(GameState state) {
        if (currentState != null)
            currentState.onExit();
        
        currentState = state;
        state.setWindow(currentWindow);
        state.setAssetManager(assetManager);
        state.setGameStateRunner(this);
        state.onEnter();
    }
    
    /**
     * So we can extend and change how we render in other GameStates.
     */
    protected void renderState(GameState state) {
        currentWindow.renderState(state);
    }
    
    /**
     * Endlessly loops the current GameState until something goes wrong.
     * 
     */
    public void loop() {
        boolean running = true;
        while(running) {
            if (currentState == null || currentWindow == null || 
                    currentWindow.isRequestingToClose()) {
                running = false;
                continue;
            }
            
            startTime = System.currentTimeMillis();
            GameState runState = currentState; // in case the state is changed
                                              // in the middle of the loop
            currentWindow.flushInput();
            runState.update(millisBetweenFrames);
            renderState(runState);
            diff = System.currentTimeMillis() - startTime;
            if (diff < millisBetweenFrames) {
                try {
                    Thread.sleep(millisBetweenFrames - diff);
                } catch(Exception e) {}
            }
        }
        currentWindow.destroy();
    }

    
    // applying a level of indirection with the event methods...

    public void keyPressed(int keycode) {
        if (currentState != null)
            currentState.key(keycode, true);
    }

    public void keyReleased(int keycode) {
         if (currentState != null)
            currentState.key(keycode, false);
    }
    
    public void mousePressed(int mousebutton, int x, int y) {
        if (currentState != null)
            currentState.mouse(mousebutton, x, y, true);
    }

    public void mouseReleased(int mousebutton, int x, int y) {
        if (currentState != null)
            currentState.mouse(mousebutton, x, y, false);
    }
    
    /**
     * Get the asset manager used by this game state runner.
     * @return The asset manager used by this game state runner.
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Get the game state currently being looped in this game state runner.
     * @return The game state currently being looped.
     */
    protected GameState getCurrentState() {
        return currentState;
    }

    /**
     * Set the game state to become the newly ran game state. The states will 
     * switch on the next update-render cycle. onExit() will be called on the
     * old game state if it isn't null, and onEnter() will be called on the given game state.
     * @param currentState The game state to switch to.
     */
    protected void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    /**
     * Get the window the game state runner is rendering the current game state to.
     * @return The window the game state runner is drawing the current game state to.
     */
    protected GameWindow getCurrentWindow() {
        return currentWindow;
    }

    /**
     * Set the window the game state runner uses.
     * @param currentWindow Set the window the game state runner uses.
     */
    protected void setCurrentWindow(GameWindow currentWindow) {
        this.currentWindow = currentWindow;
    }

    /**
     * Get how many milliseconds approximately pass between frames.
     * @return How many milliseconds approximately pass between frames.
     */
    protected int getMillisBetweenFrames() {
        return millisBetweenFrames;
    }

    /**
     * Set how many milliseconds approximately pass between frames.
     * @param millisBetweenFrames How many milliseconds approximately pass between frames.
     */
    protected void setMillisBetweenFrames(int millisBetweenFrames) {
        this.millisBetweenFrames = millisBetweenFrames;
    }
    
    
}
