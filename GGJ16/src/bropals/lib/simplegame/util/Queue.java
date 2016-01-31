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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements a simple queue.
 * @author Jonathon
 * @param <T> the type that this Queue holds
 */
public class Queue<T> {
    
    private final List<T> queue = Collections.synchronizedList(new ArrayList<T>());
    
    /**
     * Adds an item to the back of the queue.
     * @param item the item to add.
     */
    public void addToQueue(T item) {
        queue.add(item);
    }
    
    /**
     * Checks to see if this Queue is empty.
     * @return <code>true</code> if there is nothing in the Queue, 
     * <code>false</code> if there is.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    /**
     * Removes everything from the Queue.
     */
    public void dumpQueue() {
        queue.clear();
    }
    
    /**
     * Gets the next item in the queue.
     * @return the next item in the queue, or <code>null</code> if there is
     * nothing else in the queue.
     */
    public T next() {
        if (!isEmpty()) {
            T next = queue.get(0);
            queue.remove(0);
            return next;
        } else {
            return null;
        }
    }
}
