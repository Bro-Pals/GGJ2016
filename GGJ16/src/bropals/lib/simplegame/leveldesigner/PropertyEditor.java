/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame.leveldesigner;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * Component for editing the properties of an EditableEntity object.
 * 
 * @author Jonathon
 */
public class PropertyEditor extends JComponent implements KeyListener, MouseListener, Scrollable {
    
    private final int ROW_HEIGHT = 20;
    
    private EditableEntity editing;
    private String[] propertyNames;
    private String[] propertyValues;
    private int selectedProperty = -1;
    private EditorView view;
    
    public PropertyEditor(EditorView view) {
        this.view = view;
        addMouseListener(this);
        addKeyListener(this);
        setPreferredSize(new Dimension(175, 300));
    }
    
    /**
     * Sets the editing object that this property editor is editing.
     * @param editable the editing property.
     */
    public void setEditable(EditableEntity editable) {
        this.editing = editable;
        if (this.editing != null) {
            propertyNames = editable.getEditableFields();
            propertyValues = new String[propertyNames.length];
            validatePropertyValues();
        } else {
            propertyNames = null;
            propertyValues = null;
        }
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        FontMetrics fm = g.getFontMetrics();
        if (active()) {
            for (int i=0; i<propertyNames.length; i++) {
                validateColor(g, i);
                drawRow(g, i, propertyNames[i], propertyValues[i], fm);
            }
        } else {
            g.drawString("No selected Entity to edit", 10, 10);
        }
    }
    
    public void validatePropertyValues() {
        for (int i=0; i<propertyNames.length; i++) {
            propertyValues[i] = this.editing.getFieldValue(propertyNames[i]);
        }
    }
    
    private void validateColor(Graphics g, int drawingIndex) {
        if (drawingIndex==selectedProperty) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.BLACK);
        }
    }

    private void drawRow(Graphics g, int rowNum, String propertyName, String propertyValue, FontMetrics fm) {
            g.drawRect(0, rowNum*ROW_HEIGHT, getWidth(), ROW_HEIGHT);
            g.drawLine(getWidth()/2, rowNum*ROW_HEIGHT, getWidth()/2, (rowNum+1)*ROW_HEIGHT);
            g.drawString(propertyName, 3, (rowNum*ROW_HEIGHT)+fm.getHeight()+3);
            g.drawString(propertyValue, 3 + (getWidth()/2), (rowNum*ROW_HEIGHT)+fm.getHeight()+3);
    }
    
    private void enterCurrentValue() {
        String response = editing.setFieldValue(propertyNames[selectedProperty], propertyValues[selectedProperty]);
        if (response != null) {
            propertyValues[selectedProperty] = response;
        }
        repaint();
        view.repaint();
    }
    
    private boolean validPropertySelection() {
        return selectedProperty>=0 && selectedProperty<propertyNames.length;
    }
    
    public boolean isInRow(int mouseY, int row) {
        return mouseY > (row*ROW_HEIGHT) && mouseY < ((row+1)*ROW_HEIGHT);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (active() && validPropertySelection()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                enterCurrentValue();
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (propertyValues[selectedProperty].length() > 0) {
                    propertyValues[selectedProperty] = propertyValues[selectedProperty].substring(0, propertyValues[selectedProperty].length()-1);
                    repaint();
                }
            } else if (Character.isDefined(e.getKeyChar())) {
                propertyValues[selectedProperty] += e.getKeyChar();
                repaint();
            }
        }
    }

    public boolean active() {
        return propertyValues != null && propertyNames != null;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (active()) {
            requestFocusInWindow();
            for (int i=0; i<propertyNames.length; i++) {
                if (isInRow(e.getY(), i)) {
                    if (i == selectedProperty) {
                        //Clear the field after double clicking
                        propertyValues[i] = "";
                    }
                    selectedProperty = i;
                    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                    repaint();
                    return;
                }
            }
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            selectedProperty = -1;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.VERTICAL) {
            return ROW_HEIGHT;
        } else {
            return 0;
        }
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.VERTICAL) {
            return ROW_HEIGHT*5;
        } else {
            return 0;
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
    
}
