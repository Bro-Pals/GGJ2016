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
package bropals.lib.simplegame.util;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * A structure for implementing open, save, and save as for editors.
 * @author Jonathon
 * @param <T> the object that is being saved, saved as, or opened.
 */
public abstract class AbstractFileManager<T> {
    
    private File current = null;
    private JFileChooser fc = new JFileChooser();
    
    /**
     * Saves the object to the current file. If there is no current file,
     * then this is equivalent to called <code>saveAs(saving)</code>
     * @param saving the object to save
     */
    public void save(T saving) {
        if (current == null) {
            saveAs(saving);
        } else {
            writeTo(current, saving);
        }
    }
    
    /**
     * Opens the open dialog to choose what file to open.
     * @return the object that was read from the selected file.
     */
    public T open() {
        int selected = fc.showOpenDialog(null);
        if (selected == JFileChooser.APPROVE_OPTION) {
            current = fc.getSelectedFile();
            T obj = readFrom(current);
            return obj;
        }
        return null;
    }
    
    /**
     * Opens the save as dialog to choose what to save the object as.
     * @param savingAs the object to save
     */
    public void saveAs(T savingAs) {
        int selected = fc.showSaveDialog(null);
        if (selected == JFileChooser.APPROVE_OPTION) {
            current = fc.getSelectedFile();
            fc.setCurrentDirectory(current.getParentFile());
            save(savingAs);
        }
    }
    
    /**
     * Implements the saving side of this AbstractFileManager implementation.
     * @param object the object to write.
     * @param file the file to write the object to.
     */
    protected abstract void writeTo(File file, T object);
    
    /**
     * Implements the opening side of this AbstractFileManager implementation.
     * @param file the file to read the object from.
     * @return the object that was read from the file.
     */
    protected abstract T readFrom(File file);
}
