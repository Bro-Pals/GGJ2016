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
package bropals.lib.simplegame.io;

import java.io.DataInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import static bropals.lib.simplegame.logger.ErrorLogger.*;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Reads and writes to files via a binary stream.
 * <p>
 * This is for saving and loading game data to a file via binary storage, but
 * is different than a {@link java.io.DataInputStream DataInputStream} and
 * {@link java.io.DataOutputStream DataOutputStream} because one should extend
 * and override the <code>readFile()</code> and <code>writeFile()</code> methods 
 * to better encapsulate what is being read and written.
 * 
 * @author Jonathon
 * @param <T> the type of object that is being read and written to a binary file.
 */
public abstract class BinaryFileStorage<T> {
    
    private DataInputStream input = null;
    private DataOutputStream output = null;
    
    /**
     * Reads a binary file and interprets it as an object.
     * @param file the file that is being read.
     * @return the object created from the data stored in the file.
     */
    public abstract T readFile(File file);
    
    /**
     * Writes the object to the given binary file. Whether or not this function
     * overwrites the file is dependent on the implementation of it.
     * @param object the object to write.
     * @param file the binary file to write it to.
     */
    public abstract void writeFile(T object, File file);
    
    /**
     * Opens binary input with the specified file so that this object can
     * use its read functions.
     * @param file the file to open the input to.
     */
    protected void openInput(File file) {
        try {
            input = new DataInputStream(
                    Files.newInputStream(file.toPath(), 
                    StandardOpenOption.READ)
            );
        } catch(Exception e) {
            println("Unable to open binary input steam with " + file + ": " + e);
        }
    }
    
    /**
     * Opens binary output with the specified file so that this object can
     * use its write functions.
     * @param file the file to open the output to.
     * @param append whether or not the file should be appended to.
     */
    protected void openOutput(File file, boolean append) {
        try {
            output = new DataOutputStream(
                    Files.newOutputStream(file.toPath(),
                            StandardOpenOption.WRITE,
                            append ? StandardOpenOption.APPEND : StandardOpenOption.WRITE)
                    );
        } catch(Exception e) {
            println("Unable to open binary output steam with " + file + ": " + e);
        }
    }
    
    /**
     * Closes the binary input if it is open.
     */
    protected void closeInput() {
        if (input!=null) {
            try {
                input.close();
                input = null;
            } catch(IOException e) {
                println("Unable to close binary input stream: " + e);
            }
        }
    }
    
    /**
     * Flushes and closes the binary output if it is open.
     */
    protected void closeOutput() {
        if (output!=null) {
            try {
                output.flush();
                output.close();
                output = null;
            } catch(IOException e) {
                println("Unable to flush and close binary output stream: " + e);
            }
        }
    }
    
    /**
     * Reads the next byte from the binary stream.
     * @return the read byte.
     */
    public byte readByte() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return 0;
        }
        try {
            return input.readByte();
        } catch(IOException e) {
            println("Unable to read byte from current input stream: " + e);
            return 0;
        }
    }
    
    /**
     * Reads the next short from the binary stream.
     * @return the read short.
     */
    public short readShort() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return 0;
        }
        try {
            return input.readShort();
        } catch(IOException e) {
            println("Unable to read short from current input stream: " + e);
            return 0;
        }
    }
    
    /**
     * Reads the next integer from the binary stream.
     * @return the read integer.
     */
    public int readInt() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return 0;
        }
        try {
            return input.readInt();
        } catch(IOException e) {
            println("Unable to read integer from current input stream: " + e);
            return 0;
        }
    }
    
    /**
     * Reads the next float from the binary stream.
     * @return the read float.
     */
    public float readFloat() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return 0;
        }
        try {
            return input.readFloat();
        } catch(IOException e) {
            println("Unable to read float from current input stream: " + e);
            return 0;
        }
    }
    
    /**
     * Reads the next long from the binary stream.
     * @return the read long.
     */
    public long readLong() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return 0;
        }
        try {
            return input.readLong();
        } catch(IOException e) {
            println("Unable to read long from current input stream: " + e);
            return 0;
        }
    }
    
    /**
     * Reads the next character from the binary stream.
     * @return the read character.
     */
    public char readChar() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return 0;
        }
        try {
            return input.readChar();
        } catch(IOException e) {
            println("Unable to read char from current input stream: " + e);
            return 0;
        }
    }
    
    /**
     * Reads the next double from the binary stream.
     * @return the read double.
     */
    public double readDouble() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return 0;
        }
        try {
            return input.readDouble();
        } catch(IOException e) {
            println("Unable to read double from current input stream: " + e);
            return 0;
        }
    }
    
    /**
     * Reads the next boolean from the binary stream.
     * @return the read boolean.
     */
    public boolean readBoolean() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return false;
        }
        try {
            return input.readBoolean();
        } catch(IOException e) {
            println("Unable to read boolean from current input stream: " + e);
            return false;
        }
    }
    
    /**
     * Reads the next boolean from the binary stream.
     * The format for strings in a binary stream that this class uses is
     * to first read a short that is the number of bytes taken up by the string,
     * then read that many bytes, grouping them up to a string.
     * @return the read boolean.
     */
    public String readString() {
        if (input==null) {
            println("Open the binary input stream before using any read functions.");
            return null;
        }
        try {
            return input.readUTF();
        } catch(IOException e) {
            println("Unable to read string from current input stream: " + e);
            return null;
        }
    }
    
    /**
     * Writes the given byte to the end of the binary output.
     * @param b the byte to write.
     */
    public void writeByte(byte b) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeByte(b);
        } catch(IOException e) {
            println("Unable to write a byte to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given short to the end of the binary output.
     * @param s the short to write.
     */
    public void writeShort(short s) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeShort(s);
        } catch(IOException e) {
            println("Unable to write a short to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given boolean to the end of the binary output.
     * @param b the boolean to write.
     */
    public void writeBoolean(boolean b) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeBoolean(b);
        } catch(IOException e) {
            println("Unable to write a boolean to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given integer to the end of the binary output.
     * @param i the integer to write.
     */
    public void writeInt(int i) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeInt(i);
        } catch(IOException e) {
            println("Unable to write an integer to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given float to the end of the binary output.
     * @param f the float to write.
     */
    public void writeFloat(float f) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeFloat(f);
        } catch(IOException e) {
            println("Unable to write a float to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given double to the end of the binary output.
     * @param d the double to write.
     */
    public void writeDouble(double d) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeDouble(d);
        } catch(IOException e) {
            println("Unable to write a double to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given long to the end of the binary output.
     * @param l the long to write.
     */
    public void writeLong(long l) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeLong(l);
        } catch(IOException e) {
            println("Unable to write a long to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given character to the end of the binary output.
     * @param c the character to write.
     */
    public void writeChar(char c) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeChar(c);
        } catch(IOException e) {
            println("Unable to write a character to the current output stream: " + e);
        }
    }
    
    /**
     * Writes the given String to the end of the binary output.
     * This is done by first writing a short that is how many bytes the
     * String takes up, then by writing the byte data that is the String.
     * @param str the String to write.
     */
    public void writeString(String str) {
        if (output == null) {
            println("Open the binary output stream before using any write functions");
            return;
        }
        try {
            output.writeUTF(str);
        } catch(IOException e) {
            println("Unable to write a String to the current output stream: " + e);
        }
    }
}
