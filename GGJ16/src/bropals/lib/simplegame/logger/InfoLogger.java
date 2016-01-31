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
package bropals.lib.simplegame.logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Provides a level of indirection to message printing.
 * @author Jonathon
 */
public class InfoLogger {
    
    private static PrintStream info = System.out;
    private static boolean silent = false;
    
    /**
     * Set if you want the InfoLogger to stop printing messages or not.
     * @param silent whether or not to silence the InfoLogger.
     */
    public static void setSilent(boolean silent) {
        InfoLogger.println("InfoLogger is now silent = " + silent);
        InfoLogger.silent = silent;
    }
    
    /**
     * Sets where info messages are printed to.
     * @param err the new info reporting stream.
     */
    public static void setInfo(PrintStream err) {
        InfoLogger.info = err;
    }
    
    /**
     * Gets the print stream currently being used for info reporting.
     * @return the info message stream currently being used.
     */
    public static PrintStream getInfo() {
        return info;
    }
    
    /**
     * Sets the info output to go to a text file.
     * @param file the file location of the info log.
     */
    public static void openInfoLog(File file) {
        try {
            info = new PrintStream(file);
        } catch(IOException e) {
            println("Unable to open info log: " + e);
        }
    }
    
    /**
     * Identical to calling InfoLogger.getInfo().print(str).
     * @param str the string to print to the info stream.
     */
    public static void print(String str) {
        if (!silent) {
            getInfo().print(str);
            getInfo().flush();
        }
    }
    
    /**
     * Identical to calling InfoLogger.getInfo().println(str).
     * @param str the string to print to the info stream.
     */
    public static void println(String str) {
        if (!silent) {
            getInfo().println(str);
            getInfo().flush();
        }
    }
    
    /**
     * Identical to calling InfoLogger.getInfo().println(str).
     * @param obj the object to print to the info stream.
     */
    public static void println(Object obj) {
        if (!silent) {
            println(obj.toString());
        }
    }
}
