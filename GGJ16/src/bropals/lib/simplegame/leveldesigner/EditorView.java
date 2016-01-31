/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame.leveldesigner;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JComponent;

/**
 * A component for editing an EditableLevel.
 *
 * @author Jonathon
 */
public class EditorView extends JComponent implements MouseListener, KeyListener, MouseMotionListener {

    private static final int BOUNDS_TOLERANCE = 7;
    private float snapDistance = 9;
    private EditableLevel<EditableEntity> editing;
    private int cameraX, cameraY;
    private EditableEntity selected = null;
    private int dragStartX, dragStartY;
    private DragMode dragMode = DragMode.NONE;
    private PropertyEditor propertyEditor;
    private boolean snapping;
    private float minimumHeight = 20, minimumWidth = 20;
    private String cursorText = "";
    private EditableEntity clipboard = null;

    public EditorView() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        snapping = true;
        setPreferredSize(new Dimension(800, 600));
    }

    public void setPropertyEditor(PropertyEditor propertyEditor) {
        this.propertyEditor = propertyEditor;
    }

    public void setLevel(EditableLevel level) {
        editing = level;
        cameraX = 0;
        cameraY = 0;
        selected = null;
        dragMode = DragMode.NONE;
        repaint();
    }

    private boolean inXRange(EditableEntity entity, int xLoc) {
        return xLoc > entity.getX() && xLoc < entity.getX() + entity.getWidth();
    }

    private boolean inYRange(EditableEntity entity, int yLoc) {
        return yLoc > entity.getY() && yLoc < entity.getY() + entity.getHeight();
    }

    private boolean canDrag(EditableEntity entity, int pointX, int pointY) {
        return pointX > entity.getX() && entity.getX() + entity.getWidth() > pointX
                && pointY > entity.getY() && pointY < entity.getY() + entity.getHeight();
    }

    private boolean canResizeTop(EditableEntity e, int mx, int my) {
        return inXRange(e, mx) && Math.abs(e.getY() - my) <= BOUNDS_TOLERANCE;
    }

    private boolean canResizeBottom(EditableEntity e, int mx, int my) {
        return inXRange(e, mx) && Math.abs(e.getY() + e.getHeight() - my) <= BOUNDS_TOLERANCE;
    }

    private boolean canResizeLeft(EditableEntity e, int mx, int my) {
        return inYRange(e, my) && Math.abs(e.getX() - mx) <= BOUNDS_TOLERANCE;
    }

    private boolean canResizeRight(EditableEntity e, int mx, int my) {
        return inYRange(e, my) && Math.abs(e.getX() + e.getWidth() - mx) <= BOUNDS_TOLERANCE;
    }

    /**
     * Sees if the left bounds of the selected entity can snap compared to the
     * other entity's right bounds.
     *
     * @param other the other entity.
     * @return the displacement to move to complete the snap, with the selected
     * entity being the initial and the other entity being the final, or 0 if it
     * cannot snap.
     */
    private float leftSnapDistance(EditableEntity other) {
        return abs(selected.getX() - (other.getX() + other.getWidth()));
    }

    /**
     * Sees if the right bounds of the selected entity can snap compared to the
     * other entity's left bounds.
     *
     * @param other the other entity.
     * @return the distance to move to complete the snap, , or 0 if it cannot
     * snap.
     */
    private float rightSnapDistance(EditableEntity other) {
        return abs(selected.getX() + selected.getWidth() - other.getX());
    }

    /**
     * Sees if the top bounds of the selected entity can snap compared to the
     * other entity's bottom bounds.
     *
     * @param other the other entity.
     * @return the distance to move to complete the snap.
     */
    private float topSnapDistance(EditableEntity other) {
        return abs(selected.getY() - (other.getY() + other.getHeight()));
    }

    /**
     * Sees if the bottom bounds of the selected entity can snap compared to the
     * other entity's top bounds.
     *
     * @param other the other entity.
     * @return the distance to move to complete the snap.
     */
    private float bottomSnapDistance(EditableEntity other) {
        return abs(selected.getY() + selected.getHeight() - other.getY());
    }

    /**
     * Tries to snap the selected entity to the other entity, only if it is able
     * to. This snap function is for when the entity is being dragged.
     *
     * @param other the other entity.
     * @return <code>true</code> if it does snap, <code>false</code> if it
     * doesn't.
     */
    private void trySnapDrag(EditableEntity other) {
        if (selected == null) {
            return;
        }
        /*
         delta = final - initial;
         final = delta + initial
         initial = final - delta
         */
        float dis;
        if ((dis = leftSnapDistance(other)) < snapDistance) {
            selected.setX(other.getX() + other.getWidth());
        } else if ((dis = rightSnapDistance(other)) < snapDistance) {
            selected.setX(other.getX() - selected.getWidth());
        }
        if ((dis = topSnapDistance(other)) < snapDistance) {
            selected.setY(other.getY() + other.getHeight());
        } else if ((dis = bottomSnapDistance(other)) < snapDistance) {
            selected.setY(other.getY() - selected.getHeight());
        }
    }

    public float getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(float minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public float getMinimumWidth() {
        return minimumWidth;
    }

    public void setMinimumWidth(float minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    public void setSnapDistance(float snapDistance) {
        this.snapDistance = snapDistance;
    }

    public float getSnapDistance() {
        return snapDistance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        if (editing != null) {
            for (EditableEntity entity : editing.getEditables()) {
                entity.renderForLevelEditor(g, cameraX, cameraY);
                if (entity == selected) {
                    g.setColor(Color.BLUE);
                    g.drawRect((int) (entity.getX() - cameraX), (int) (entity.getY() - cameraY),
                            (int) entity.getWidth(), (int) entity.getHeight());
                }
            }
        } else {
            g.setColor(Color.BLACK);
            g.drawString("No current level", 20, 20);
        }
        Point cursorPos = getMousePosition();
        if (cursorPos != null) {
            g.setColor(Color.BLACK);
            g.drawString(cursorText, cursorPos.x + 20, cursorPos.y + 5);
        }
    }

    /**
     * Gets if this editor view has object snapping enabled.
     *
     * @return if this editor view has object snapping enabled.
     */
    public boolean isSnapping() {
        return snapping;
    }

    /**
     * Sets if objects should snap in this editor view.
     *
     * @param snapping the new snap preference.
     */
    public void setSnapping(boolean snapping) {
        this.snapping = snapping;
    }

    public int getCameraX() {
        return cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

    public void deleteSelected() {
        if (selected != null) {
            editing.removeEditable(selected);
            selected = null;
            repaint();
        }
    }
    
    public void copySelected() {
        if (selected != null) {
            clipboard = selected.copy();
        }
    }
    
    public void pasteSelected() {
        if (clipboard != null) {
            EditableEntity pasted = clipboard.copy();
            Point mouse = getMousePosition();
            if (mouse == null) {
                pasted.setX(getCameraX()+(getWidth()/2)-(getWidth()/2));
                pasted.setY(getCameraY()+(getHeight()/2)-(getHeight()/2));
            } else {
                pasted.setX(mouse.x + cameraX);
                pasted.setY(mouse.y + cameraY);
            }
            editing.addEditable(pasted);
            repaint();
        }
    }
    
    public void cutSelected() {
        copySelected();
        deleteSelected();
    }
    
    private void doSnap() {
        if (isSnapping()) {
            for (EditableEntity ee : editing.getEditables()) {
                if (ee != selected) {
                    trySnapDrag(ee);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();
        if (editing != null) {
            List<EditableEntity> entities = editing.getEditables();
            for (EditableEntity entity : entities) {
                int mx = e.getX() + cameraX;
                int my = e.getY() + cameraY;
                if (canResizeTop(entity, mx, my)) {
                    selected = entity;
                    propertyEditor.setEditable(selected);
                    dragMode = DragMode.RESIZE_TOP;
                    propertyEditor.repaint();
                    repaint();
                    return;
                } else if (canResizeBottom(entity, mx, my)) {
                    selected = entity;
                    propertyEditor.setEditable(selected);
                    dragMode = DragMode.RESIZE_BOTTOM;
                    propertyEditor.repaint();
                    repaint();
                    return;
                } else if (canResizeLeft(entity, mx, my)) {
                    selected = entity;
                    propertyEditor.setEditable(selected);
                    dragMode = DragMode.RESIZE_LEFT;
                    propertyEditor.repaint();
                    repaint();
                    return;
                } else if (canResizeRight(entity, mx, my)) {
                    selected = entity;
                    propertyEditor.setEditable(selected);
                    dragMode = DragMode.RESIZE_RIGHT;
                    propertyEditor.repaint();
                    repaint();
                    return;
                } else if (canDrag(entity, mx, my)) {
                    selected = entity;
                    propertyEditor.setEditable(selected);
                    dragStartX = e.getX() + cameraX - (int) entity.getX();
                    dragStartY = e.getY() + cameraY - (int) entity.getY();
                    dragMode = DragMode.MOVE_DRAG;
                    propertyEditor.repaint();
                    repaint();
                    return;
                }
            }
            dragMode = DragMode.NONE;
            selected = null;
            propertyEditor.setEditable(null);
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (editing != null) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    cameraY -= 100;
                    repaint();
                    break;
                case KeyEvent.VK_S:
                    cameraY += 100;
                    repaint();
                    break;
                case KeyEvent.VK_D:
                    cameraX += 100;
                    repaint();
                    break;
                case KeyEvent.VK_A:
                    cameraX -= 100;
                    repaint();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selected != null) {
            float size;
            switch (dragMode) {
                case MOVE_DRAG:
                    selected.setX(e.getX() + cameraX - dragStartX);
                    selected.setY(e.getY() + cameraY - dragStartY);
                    propertyEditor.validatePropertyValues();
                    doSnap();
                    break;
                case RESIZE_TOP:
                    float bottom = selected.getY() + selected.getHeight();
                    size = bottom - (e.getY() + cameraY);
                    if (size >= getMinimumHeight()) {
                        selected.setY(e.getY() + cameraY);
                        selected.setHeight(size);
                        propertyEditor.validatePropertyValues();
                    }
                    break;
                case RESIZE_LEFT:
                    float right = selected.getX() + selected.getWidth();
                    size = right - (e.getX() + cameraX);
                    if (size >= getMinimumWidth()) {
                        selected.setX(e.getX() + cameraX);
                        selected.setWidth(size);
                        propertyEditor.validatePropertyValues();
                    }
                    break;
                case RESIZE_RIGHT:
                    size = e.getX() + cameraX - selected.getX();
                    if (size >= getMinimumWidth()) {
                        selected.setWidth(size);
                        propertyEditor.validatePropertyValues();
                    }
                    break;
                case RESIZE_BOTTOM:
                    size = e.getY() + cameraY - selected.getY();
                    if (size >= getMinimumHeight()) {
                        selected.setHeight(size);
                        propertyEditor.validatePropertyValues();
                    }
                    break;
            }
            repaint();
            propertyEditor.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (editing != null) {
            int mx = e.getX() + cameraX;
            int my = e.getY() + cameraY;
            boolean specialCursor = false;
            for (EditableEntity ee : editing.getEditables()) {
                if (canResizeRight(ee, mx, my)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    cursorText = "Resize " + ee.toString();
                    specialCursor = true;
                } else if (canResizeLeft(ee, mx, my)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    cursorText = "Resize " + ee.toString();
                    specialCursor = true;
                } else if (canResizeTop(ee, mx, my)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    cursorText = "Resize " + ee.toString();
                    specialCursor = true;
                } else if (canResizeBottom(ee, mx, my)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                    cursorText = "Resize " + ee.toString();
                    specialCursor = true;
                } else if (canDrag(ee, mx, my)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    cursorText = "Drag " + ee.toString();
                    specialCursor = true;
                }
            }
            if (!specialCursor) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                cursorText = "";
            }
            repaint();
        }
    }

    private float abs(float f) {
        if (f < 0) {
            return -f;
        } else {
            return f;
        }
    }
}
