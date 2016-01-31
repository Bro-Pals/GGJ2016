/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame.leveldesigner;

import java.awt.Graphics;

/**
 * Interface for making something editable in the level editor.
 * 
 * @author Jonathon
 */
public interface EditableEntity {
    
    public void setRenderLayer(int layer);
    public void setX(float x);
    public void setY(float y);
    public void setWidth(float width);
    public void setHeight(float height);
    /**
     * Gets the render layer that this EditableEntity belongs in. Objects
     * that are farther away from the camera, and are drawn last, should
     * have a higher render layer value.
     * @return the render layer this EditableEntity belongs in.
     */
    public int getRenderLayer();
    /**
     * Gets the x position of the editable entity in world coordinates.
     * @return the y position in world coordinates.
     */
    public float getX();
    /**
     * Gets the y position of the editable entity in world coordinates.
     * @return the y position in world coordinates.
     */
    public float getY();
    /**
     * Gets the width of the editable entity in world coordinates.
     * @return the width in world coordinates.
     */
    public float getWidth();
    /**
     * Gets the height of the editable entity in world coordinates.
     * @return the height in world coordinates.
     */
    public float getHeight();
    /**
     * Get the list of property names that should be editable by the property
     * editor in the level editor.
     * @return the list of property names for this editable object.
     */
    public String[] getEditableFields();
    /**
     * Gets the text for the field based on the property. This exists because
     * all property fields may not be equals.
     * @param propertyName the name of the property that is requesting a field
     * text.
     * @return the field text for that property.
     */
    public String getFieldValue(String propertyName);
    /**
     * Sets the field value for the property based on the field text. If there
     * was an error with the field, then this function returns the field
     * that would fix the field.
     * @param propertyName the name of the property to set
     * @param valueName the field text to set the property value.
     * @return <code>null</code> if the value was correctly set, otherwise, the
     * field text that indicates an error in the field text.
     */
    public String setFieldValue(String propertyName, String valueName);
    /**
     * Renders this entity for the level editor.
     * @param g the graphics context to draw
     * @param cameraOffsetX the camera offset x
     * @param cameraOffsetY the camera offset y
     */
    public void renderForLevelEditor(Graphics g, int cameraOffsetX, int cameraOffsetY);
    /**
     * Copies this EditableEntity for the copy function.
     * @return a copied instance of this entity.
     */
    public EditableEntity copy();
}
