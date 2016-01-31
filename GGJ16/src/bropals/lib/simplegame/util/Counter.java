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

/**
 * Utility class for a simple counter. This Counter calls its CounterFunction
 * whenever it completes its count cycle.
 * @author Jonathon
 */
public class Counter {
    
    private int waitTime;
    private boolean looping;
    private CounterFunction function;
    private long startTime;
    
    /**
     * Creates a counter with the specified target number. Does not loop.
     * @param waitTime the number of milliseconds between
     * each function call.
     * @param function the function to call whenever the Counter is done
     * counting.
     */
    public Counter(int waitTime, CounterFunction function) {
        this.waitTime = waitTime;
        looping = false;
        this.function=function;
    }
    
    /**
     * Creates a counter with the specified target number and loop preference.
     * @param waitTime the number of milliseconds between
     * each function call.
     * @param looping sets the loop preference of this Counter.
     * @param function the function to call whenever the Counter is done
     * counting.
     */
    public Counter(int waitTime, boolean looping, CounterFunction function) {
        this.waitTime = waitTime;
        this.looping = looping;
        this.function = function;
        startTime = System.currentTimeMillis();
    }

    /**
     * Gets the number of milliseconds it takes to complete
     * a counter cycle.
     * @return the number of milliseconds it takes to complete
     * a counter cycle.
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     * Sets the number of milliseconds it takes to complete
     * a counter cycle.
     * @param waitTime the number of milliseconds between
     * each function call.
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * Sees if this Counter loops.
     * @return the looping state.
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * Sets the loop preference of this counter.
     * @param looping the loop preference.
     */
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    /**
     * Gets the function that is called when the counter completes its counting.
     * @return the function that is called when the counter completes its counting.
     */
    public CounterFunction getFunction() {
        return function;
    }

    /**
     * Sets the function that is called when the counter completes its counting.
     * @param function the new function that is called when the counter completes 
     * its counting.
     */
    public void setFunction(CounterFunction function) {
        this.function = function;
    }
    
    /**
     * Updates this counter, having it call its function if it completes 
     * its counting.
     */
    public void update() {
        if (System.currentTimeMillis() > startTime + waitTime) {
            if (looping) {
                startTime = System.currentTimeMillis();
            }
            function.countFinished();
        }
    }
    
    /**
     * Resets this Counter's counting.
     */
    public void reset() {
        startTime = System.currentTimeMillis();
    }
}
