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
package bropals.lib.simplegame.leveldesigner;

import bropals.lib.simplegame.logger.ErrorLogger;

/**
 * Provides convenience methods for dealing with Class objects.
 * @author Jonathon
 */
public class TypeUtil {
    
    /**
     * Parses the input string into the expected type. If the type is not
     * one of the eight primitives, then this function returns <code>null</code>.
     * @param <T> the type that the string is expected to be parsed into
     * @param str the string to parse
     * @param type the type the string is expected to be parsed into
     * @return the parsed value, or <code>null</code> if an error occurs
     * or if the requested type is not a primitive
     */
    public static <T> T parsePrimitive(String str, Class<T> type) {
        if (!type.isPrimitive()) {
            return null;
        }
        Object val = null;
        if (type == Byte.TYPE) {
            try {
                val = Byte.parseByte(str);
            } catch(NumberFormatException nfe) {
                ErrorLogger.println("Could not parse byte from string \"" + str 
                        + "\"");
            }
        } else if (type == Boolean.TYPE) {
            if ( str.equals("true") ) {
                val = true;
            } else if ( str.equals("false") ) { 
                val = false;
            } else {
                ErrorLogger.println("Could not parse byte from string \"" + str 
                        + "\"");
            }
        } else if (type == Short.TYPE) {
            try {
                val = Short.parseShort(str);
            } catch(NumberFormatException nfe) {
                ErrorLogger.println("Could not parse short from string \"" + str 
                        + "\"");
            }
        } else if (type == Integer.TYPE) {
            try {
                val = Integer.parseInt(str);
            } catch(NumberFormatException nfe) {
                ErrorLogger.println("Could not parse int from string \"" + str 
                        + "\"");
            }
        } else if (type == Float.TYPE) {
            try {
                val = Float.parseFloat(str);
            } catch(NumberFormatException nfe) {
                ErrorLogger.println("Could not parse float from string \"" + str 
                        + "\"");
            }
        } else if (type == Long.TYPE) {
            try {
                val = Long.parseLong(str);
            } catch(NumberFormatException nfe) {
                ErrorLogger.println("Could not parse long from string \"" + str 
                        + "\"");
            }
        } else if (type == Character.TYPE) {
            try {
                val = str.charAt(0);
            } catch(ArrayIndexOutOfBoundsException nfe) {
                ErrorLogger.println("Could not parse char from string \"" + str 
                        + "\"");
            }
        } else if (type == Double.TYPE) {
            try {
                val = Double.parseDouble(str);
            } catch(NumberFormatException nfe) {
                ErrorLogger.println("Could not parse double from string \"" + str 
                        + "\"");
            }
        }
        return (T) val;
    }
}
