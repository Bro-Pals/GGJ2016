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
import bropals.lib.simplegame.util.Queue;
import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents the drawing area. This is the AWT implementation of GameWindow.
 *
 * @author Kevin Prehn
 */
public class AWTGameWindow implements GameWindow {

    private Frame frame;
    private boolean fullscreen;
    private String title;
    private ScreenResolution screenResolution;
    private ScreenResolution nativeResolution;
    private GameStateRunner runner = null;
    private ScreenResolution[] resolutions;
    private int bitDepth;
    private int refreshRate;
    private GraphicsDevice device;
    private boolean requestToClose = false;
    private GameCursor gameCursor = null;
    private Cursor invisibleCursor = null; //For implementing GameCursor
    private final Queue<MouseEvent> mouseEventDownQueue = new Queue<>();
    private final Queue<KeyEvent> keyEventUpQueue = new Queue<>();
    private final Queue<MouseEvent> mouseEventUpQueue = new Queue<>();
    private final Queue<KeyEvent> keyEventDownQueue = new Queue<>();
    private ArrayList<QuitHandler> quitHandlers = new ArrayList<>();
    
    /**
     * Creates a visible GameWindow with the supplied parameters.
     *
     * @param title the title of the window.
     * @param screenWidth the screen resolution width.
     * @param screenHeight the screen resolution height.
     * @param fullscreen the fullscreen state of the GameWindow.
     * @param device the GraphicsDevice to use for this GameWindow.
     */
    public AWTGameWindow(String title, int screenWidth, int screenHeight, 
            boolean fullscreen, GraphicsDevice device) {
        this.title = title;
        this.fullscreen = fullscreen;
        this.device = device;
        initNativeScreenResolution(device);
        this.resolutions = getSupportedScreenResolutionList(device);
        setScreenResolution(new ScreenResolution(screenWidth, screenHeight));
        applyGraphicsConfiguration();
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                requestToClose = true;
            }
            
            
        });
    }
    
    /**
     * Creates a visible GameWindow with the supplied parameters. Uses the
     * default screen device.
     *
     * @param title the title of the window.
     * @param screenWidth the screen resolution width.
     * @param screenHeight the screen resolution height.
     * @param fullscreen the fullscreen state of the GameWindow.
     */
    public AWTGameWindow(String title, int screenWidth, int screenHeight, 
            boolean fullscreen) {
        this(title, screenWidth, screenHeight, fullscreen, 
                getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }

    /**
     * @inherit
     */
    @Override
    public void setIcon(BufferedImage icon) {
        frame.setIconImage(icon);
    }
    
    /**
     * Creates a visible GameWindow with the supplied parameters. Uses the
     * default screen device; starts in windowed mode.
     *
     * @param title the title of the window.
     * @param screenWidth the screen resolution width.
     * @param screenHeight the screen resolution height.
     */
    public AWTGameWindow(String title, int screenWidth, int screenHeight) {
        this(title, screenWidth, screenHeight, false, 
                getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }
    
    /**
     * @inherit
     */
    @Override
    public boolean supportsResolution(int width, int height) {
        return supportsResolution(new ScreenResolution(width, height));
    }

    /**
     * @inherit
     */
    @Override
    public boolean supportsResolution(ScreenResolution resolution) {
        for (ScreenResolution s : resolutions) {
            if (s.equals(resolution)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @inherit
     */ 
    @Override
    public ScreenResolution[] getSupportedScreenResolutionList(GraphicsDevice device) {
        this.device = device;
        DisplayMode current = this.device.getDisplayMode();
        this.bitDepth = current.getBitDepth();
        this.refreshRate = current.getRefreshRate();
        DisplayMode[] list = this.device.getDisplayModes();
        ArrayList<ScreenResolution> collected = new ArrayList<>();
        ScreenResolution res;
        for (DisplayMode dm : list) {
            res = new ScreenResolution(dm.getWidth(), dm.getHeight());
            if (!collected.contains(res)) {
                collected.add(res);
            }
        }
        return (ScreenResolution[]) collected.toArray(new ScreenResolution[0]);
    }

    /**
     * Sets this AWTGameWindow's native screen resolution with the native
     * resolution of the given device.
     *
     * @param device the graphics device that will have its screen resolution be
     * called the native resolution.
     */
    private void initNativeScreenResolution(GraphicsDevice device) {
        nativeResolution = new ScreenResolution(
                device.getDisplayMode().getWidth(),
                device.getDisplayMode().getHeight()
        );
    }

    /**
     * @inherit 
     */
    @Override
    public boolean isRequestingToClose() {
        return requestToClose;
    }

    /**
     * Gets the left insets of the window.
     *
     * @return the left insets of the window.
     */
    private int getInsetsLeft() {
        if (!fullscreen) {
            return frame.getInsets().left;
        } else {
            return 0;
        }
    }

    /**
     * Gets the top insets of the window.
     *
     * @return the top insets of the window.
     */
    private int getInsetsTop() {
        if (!fullscreen) {
            return frame.getInsets().top;
        } else {
            return 0;
        }
    }

    /**
     * Gets the bottom insets of the window.
     *
     * @return the bottom insets of the window.
     */
    private int getInsetsBottom() {
        if (!fullscreen) {
            return frame.getInsets().bottom;
        } else {
            return 0;
        }
    }

    /**
     * Gets the right insets of the window.
     *
     * @return the right insets of the window.
     */
    private int getInsetsRight() {
        if (!fullscreen) {
            return frame.getInsets().right;
        } else {
            return 0;
        }
    }

    /**
     * @inherit 
     */
    @Override
    public boolean isFullscreen() {
        return fullscreen;
    }
    
    /**
     * @inherit 
     */
    @Override
    public boolean isWindowed() {
        return !fullscreen;
    }

    /**
     * @inherit 
     */
    @Override
    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
    
    /**
     * @inherit 
     */
    @Override
    public void setWindowed(boolean windowed) {
        this.fullscreen = !windowed;
    }

    /**
     * @inherit
     */
    @Override
    public boolean setScreenResolution(ScreenResolution screenResolution) {
        if (isFullscreen()) {
            if (!supportsResolution(screenResolution)) {
                return false;
            }
        }
        this.screenResolution = screenResolution;
        return true;
    }

    /**
     * Has the AWTGameWindow update its window to match the configuration it was
 given. The Graphics object obtained from <code>getDrawGraphics()</code>
     * is now invalid, and need to have <code>dispose()</code> called it, 
     * replacing it with a new Graphics object.
     */
    private void applyGraphicsConfiguration() {
        if (frame!=null) {
            frame.dispose();
            frame = null;
        }
        frame = new Frame(title);
        if (fullscreen) {
            frame.setUndecorated(true);
        }
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.setVisible(true);
        setupInput();
        applyScreenResolution();
        createBuffers();
    }

    /**
     * Converts a screen resolution into a display mode.
     * @param screenResolution the screen resolution to convert.
     * @return the display mode that is the converted screen resolution.
     */
    private DisplayMode convertScreenResolution(ScreenResolution screenResolution) {
        return new DisplayMode(
                screenResolution.getScreenWidth(),
                screenResolution.getScreenHeight(),
                bitDepth, refreshRate
        );
    }

    /**
     * @inherit
     */
    @Override
    public GameCursor getGameCursor() {
        return gameCursor;
    }

    /**
     * @inherit
     */
    @Override
    public void setGameCursor(GameCursor gameCursor) {
        this.gameCursor = gameCursor;
        if (this.gameCursor != null) {
            if (invisibleCursor==null) {
                BufferedImage invisible = new BufferedImage(1, 1, 
                        BufferedImage.TYPE_4BYTE_ABGR);
                invisible.getRaster().setPixel(0, 0, new int[]{0, 0, 0, 0});
                this.invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                        invisible, new Point(
                                gameCursor.getOffsetX(), gameCursor.getOffsetY()), 
                        "invisible");
            }
            frame.setCursor(invisibleCursor);
        } else {
            frame.setCursor(Cursor.getDefaultCursor());
        }
    }  
    
    /**
     * Sets the size of the window when in windowed mode, and sets the
     * display mode of the current graphics device when in fullscreen mode.
     */
    private void applyScreenResolution() {
        if (isWindowed()) {
            if (device.getFullScreenWindow()!=null && 
                    !screenResolution.equals(nativeResolution)) {
                device.setDisplayMode(convertScreenResolution(nativeResolution));
            }
            device.setFullScreenWindow(null);
            int w = getScreenWidth() + getInsetsLeft() + getInsetsRight();
            int h = getScreenHeight() + getInsetsTop() + getInsetsBottom();
            frame.setBounds(
                    (nativeResolution.getScreenWidth() / 2) - (w / 2),
                    (nativeResolution.getScreenHeight() / 2) - (h / 2),
                    w, h
            );
        } else {
            device.setFullScreenWindow(frame);
            device.setDisplayMode(convertScreenResolution(screenResolution));
        }
    }
    
    /**
     * Sets up the input handling for the window.
     */
    private void setupInput() {
        frame.addKeyListener(new KeyHandler());
        frame.addMouseListener(new MouseHandler());
    }
    
    /**
     * Creates the buffers for double buffered drawing.
     */
    private void createBuffers() {
        if (frame.getBufferStrategy()!=null) {
            frame.getBufferStrategy().dispose();
        }
        frame.createBufferStrategy(2);
    }

    /**
     * Gets the Graphics object to draw to the screen.
     *
     * @return the graphics object to draw on the screen
     */
    public Graphics getDrawGraphics() {
        if (frame.getBufferStrategy() == null) {
            frame.createBufferStrategy(2);
        }
        Graphics g = frame.getBufferStrategy().getDrawGraphics();
        g.translate(getInsetsLeft(), getInsetsTop());
        return g;
    }

    /**
     * Swaps the buffers. Need to pass the graphics object that was just
     * used to draw to AWTGameWindow.
     *
     * @param g the graphics that was used to draw to the AWTGameWindow
     */
    public void swapBuffers(Graphics g) {
        if (gameCursor!=null) {
            Point mp = getMousePosition();
            if (mp.x > 0 && mp.y > 0) {
                g.drawImage(
                    gameCursor.getImage(),
                    mp.x+gameCursor.getOffsetX(),
                    mp.y+gameCursor.getOffsetY(),
                    null
                );
            }
        }
        g.dispose();
        frame.getBufferStrategy().show();
    }

    /**
     * @inherit
     */
    @Override
    public void destroy() {
        notifyQuitHandlers();
        if (frame != null) {
            if (isFullscreen()) {
                device.setDisplayMode(convertScreenResolution(nativeResolution));
                device.setFullScreenWindow(null);
            }
            frame.dispose();
        }
    }

    /**
     * @inherit
     */
    @Override
    public int getScreenWidth() {
        return screenResolution.getScreenWidth();
    }

    /**
     * @inherit
     */
    @Override
    public int getScreenHeight() {
        return screenResolution.getScreenHeight();
    }

    /**
     * @inherit
     */
    @Override
    public void requestToClose() {
        this.requestToClose = true;
    }

    /**
     * @inherit
     */
    @Override
    public ScreenResolution getScreenResolution() {
        return screenResolution;
    }

    /**
     * @inherit
     */
    @Override
    public Point getMousePosition() {
        try {
            Point p = frame.getMousePosition();
            p.x -= getInsetsLeft();
            p.y -= getInsetsTop();
            return p;
        } catch(NullPointerException e) {
            return new Point(-1, -1);
        }
    }
    
    /**
     * @inherit
     */
    @Override
    public void giveGameStateRunner(GameStateRunner runner) {
        this.runner = runner;
    }
    
        
    /**
     * @inherit
     */
    @Override
    public void flushInput() {
        KeyEvent ke;
        while ( (ke = keyEventDownQueue.next()) != null ) {
            runner.keyPressed(KeyCode.convertAWTCodeToBroPalsCode(ke.getKeyCode()));
        }
        while ( (ke = keyEventUpQueue.next()) != null ) {
            runner.keyReleased(KeyCode.convertAWTCodeToBroPalsCode(ke.getKeyCode()));
        }
        MouseEvent me;
        while ( (me = mouseEventDownQueue.next()) != null ) {
            runner.mousePressed(MouseButton.convertAWTCodeToBroPalsCode(me.getButton()), me.getX(), me.getY());
        }
        while ( (me = mouseEventUpQueue.next()) != null ) {
            runner.mouseReleased(MouseButton.convertAWTCodeToBroPalsCode(me.getButton()), me.getX(), me.getY());
        }
    }
    
    /**
     * @inherit
     */
    @Override
    public void renderState(GameState state) {
        Graphics g = getDrawGraphics();
        if (state != null) {
            state.render(g);
        }
        swapBuffers(g);
    }
    
    /**
     * Get the raw frame object.
     * @return The raw frame.
     */
    public Frame getRawFrame() {
        return frame;
    }

    private void notifyQuitHandlers() {
        for (QuitHandler handler : quitHandlers) {
            handler.onQuit();
        }
    }
    
    @Override
    public void registerQuitHandler(QuitHandler handler) {
        quitHandlers.add(handler);
    }

    @Override
    public void unregisterQuitHandler(QuitHandler handler) {
        quitHandlers.remove(handler);
    }

    /**
     * The internal key handler for AWTGameWindow.
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
     * The internal mouse handler for AWTGameWindow.
     */
    class MouseHandler implements MouseListener {

        /**
         * Translates the coordinates to the correct space.
         * @param e the mouse event to transform
         */
        private void translateMouseEvent(MouseEvent e) {
            e.translatePoint(-getInsetsLeft(), -getInsetsTop());
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            translateMouseEvent(e);
            mouseEventDownQueue.addToQueue(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            translateMouseEvent(e);
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
