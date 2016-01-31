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
package bropals.lib.simplegame.applet;

import bropals.lib.simplegame.GameCursor;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.MouseButton;
import bropals.lib.simplegame.ScreenResolution;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.InfoLogger;
import bropals.lib.simplegame.state.GameState;
import bropals.lib.simplegame.util.Queue;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JApplet;

/**
 * Used for when making Applets with BroPalsGameLibrary. Should be extended and
 * override the <code>loadResources</code>, <code>getInitialState()</code> and
 * <code>getAppletSettings()</code> methods.
 *
 * @author Jonathon
 */
public abstract class GameApplet extends JApplet implements GameWindow {

    private boolean requestingToClose = false;
    private GameStateRunner runner;
    private Canvas canvas;
    private Thread gameThread;
    private final Queue<MouseEvent> mouseEventDownQueue = new Queue<>();
    private final Queue<KeyEvent> keyEventUpQueue = new Queue<>();
    private final Queue<MouseEvent> mouseEventUpQueue = new Queue<>();
    private final Queue<KeyEvent> keyEventDownQueue = new Queue<>();

    @Override
    public void setIcon(BufferedImage icon) {
        InfoLogger.println("Can't change the icon of an applet");
    }

    @Override
    public boolean supportsResolution(int screenWidth, int screenHeight) {
        return true; //This is a web browser!
    }

    @Override
    public boolean supportsResolution(ScreenResolution resolution) {
        return true;
    }

    @Override
    public ScreenResolution[] getSupportedScreenResolutionList(GraphicsDevice device) {
        return null; //This is an applet
    }

    @Override
    public boolean isRequestingToClose() {
        return requestingToClose;
    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public boolean isWindowed() {
        return true;
    }

    @Override
    public void setFullscreen(boolean fullscreen) {
        throw new UnsupportedOperationException("This is an applet"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWindowed(boolean windowed) {
        //Already in a window
    }

    @Override
    public boolean setScreenResolution(ScreenResolution screenResolution) {
        resize(screenResolution.getScreenWidth(), screenResolution.getScreenHeight());
        return true;
    }

    @Override
    public GameCursor getGameCursor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGameCursor(GameCursor gameCursor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getScreenWidth() {
        return getWidth();
    }

    @Override
    public int getScreenHeight() {
        return getHeight();
    }

    @Override
    public void requestToClose() {
        requestingToClose = true;
    }

    @Override
    public ScreenResolution getScreenResolution() {
        return new ScreenResolution(getScreenWidth(), getScreenHeight());
    }

    @Override
    public void flushInput() {
        KeyEvent ke;
        while ((ke = keyEventDownQueue.next()) != null) {
            runner.keyPressed(KeyCode.convertAWTCodeToBroPalsCode(ke.getKeyCode()));
        }
        while ((ke = keyEventUpQueue.next()) != null) {
            runner.keyReleased(KeyCode.convertAWTCodeToBroPalsCode(ke.getKeyCode()));
        }
        MouseEvent me;
        while ((me = mouseEventDownQueue.next()) != null) {
            runner.mousePressed(MouseButton.convertAWTCodeToBroPalsCode(me.getButton()), me.getX(), me.getY());
        }
        while ((me = mouseEventUpQueue.next()) != null) {
            runner.mouseReleased(MouseButton.convertAWTCodeToBroPalsCode(me.getButton()), me.getX(), me.getY());
        }
    }

    @Override
    public void destroy() {
        canvas.getBufferStrategy().dispose();
    }

    @Override
    public void stop() {
        gameThread.interrupt();
    }

    @Override
    public void start() {
        gameThread.start();
    }

    @Override
    public void init() {
        this.setIgnoreRepaint(true);
        runner = new GameStateRunner(this, new AssetManager(getCodeBase(), true));
        AppletSettings settings = getAppletSettings();
        canvas = new Canvas();
        canvas.addKeyListener(new KeyHandler());
        canvas.addMouseListener(new MouseHandler());
        this.add(canvas);
        resize(settings.getWidth(), settings.getHeight());
        runner.setFps(settings.getFramesPerSecond());
        loadResources(runner.getAssetManager());
        runner.setState(getInitialState());
        //Start a new thread
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.loop();
            }
        });
    }

    /**
     * Download the resources needed for this Applet game by using the URL
     * AssetLoader given.
     * <p>
     * The asset manager's root directory is the directory of the Applet. Use
     * the normal load functions unless it is a more specific resource, then use
     * the URL load functions.
     *
     * @param assetManager the asset manager to load resources with
     */
    public abstract void loadResources(AssetManager assetManager);

    /**
     * Gets the state that this GameApplet is in initially.
     *
     * @return the state to be in initially
     */
    public abstract GameState getInitialState();

    /**
     * Get the Applet settings that this GameApplet should have
     *
     * @return
     */
    public abstract AppletSettings getAppletSettings();

    @Override
    public void giveGameStateRunner(GameStateRunner runner) {
        this.runner = runner;
    }

    @Override
    public void renderState(GameState state) {
        if (canvas.getBufferStrategy() == null && this.isDisplayable()) {
            canvas.createBufferStrategy(2);
        }
        Graphics g = canvas.getBufferStrategy().getDrawGraphics();
        state.render(g);
        g.dispose();
        canvas.getBufferStrategy().show();
    }

    /**
     * The internal key handler for GameApplet.
     */
    class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyEventDownQueue.addToQueue(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyEventUpQueue.addToQueue(e);
        }

    }

    /**
     * The internal mouse handler for GameApplet.
     */
    class MouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseEventDownQueue.addToQueue(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseEventUpQueue.addToQueue(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
