/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame.leveldesigner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author Jonathon
 * @param <T> the type of entity that the level contains.
 */
public interface EditableLevel<T extends EditableEntity> {
    /**
     * Add an editable entity to this level.
     * @param editable the editable entity to add.
     */
    public void addEditable(T editable);
    /**
     * Removes an editable entity to this level.
     * @param editable the editable entity to remove.
     */
    public void removeEditable(T editable);
    /**
     * Gets a list of editable entities that reside in this world.
     * @return gets the list of the editable entities in the world.
     */
    public List<T> getEditables();
    /**
     * Saves this level at the given file location. One should not forget to
     * save the render layer, position, and size properties for each EditableEntity.
     * @param stream the output stream to the level file.
     * @throws java.io.IOException if there is a problem with saving the level.
     */
    public void saveLevel(OutputStream stream) throws IOException;
    /**
     * Loads a level data into this level by the given file location. One should
     * not forget to read the render layer, position, and size properties for each EditableEntity.
     * @param stream the input stream to the level file.
     * @throws java.io.IOException if there is a problem with loading the level.
     */
    public void loadLevel(InputStream stream) throws IOException;
    /**
     * Remove everything that resides in this level to bring it to 
     * its empty state.
     */
    public void clearLevel();
}
