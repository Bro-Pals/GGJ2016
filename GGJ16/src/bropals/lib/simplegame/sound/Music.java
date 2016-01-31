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
package bropals.lib.simplegame.sound;

import bropals.lib.simplegame.logger.InfoLogger;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

/**
 * Used for playing sounds that are too large to load entirely into memory.
 * @author Jonathon
 */
public class Music {
    /**
     * The Line that is having the audio data written to it.
     */
    private SourceDataLine line;
    /**
     * The stream that is used for getting the audio data.
     */
    private AudioInputStream stream;
    /**
     * Buffer for writing stuff
     */
    private byte[] buffer;
    /**
     * Whether or not the music is looping
     */
    private boolean looping;
    
    private Thread musicPlaybackThread;
        
    /**
     * Wraps an open source data line and an audio input stream so that it can
     * be played.
     * @param line the source data line that will have audio data written to
     * @param stream the stream that will have audio data read from
     */
    public Music(SourceDataLine line, AudioInputStream stream) {
        this.line=line;
        this.stream=stream;
        buffer = new byte[line.getBufferSize()/2];
    }
    
    /**
     * Causes this Music object to play its music until <code>stop</code> is called
     * on it, or until the music track runs out if the user set <code>loop</code>
     * to false.
     * 
     * If the music is already playing then this method does nothing.
     * 
     * @param loop whether or not the playback of this Music should be repeated
     */
    public void play(boolean loop) {
        if (!line.isRunning()) {
            looping = loop;
            if (musicPlaybackThread != null && musicPlaybackThread.isAlive()) {
                musicPlaybackThread.interrupt();
            }
            musicPlaybackThread = new Thread(new MusicPlayback());
            musicPlaybackThread.start();
        }
    }
    
    /**
     * Stops playing music.
     */
    public void stop() {
        if (musicPlaybackThread.isAlive()) {
            musicPlaybackThread.interrupt();
        }
    }
    
    class MusicPlayback implements Runnable {
        @Override
        public void run() {
            do {
                InfoLogger.println("RUNNING MUSIC");
                int status = 0;
                while (status != -1) {
                    try {
                        status = stream.read(buffer);
                    } catch(IOException e) {
                        System.err.println("Error reading sound file while the music was playing: " + e);
                    }
                    line.write(buffer, 0, buffer.length);
                }
                try {
                    stream.reset();
                } catch(IOException e) {
                        System.err.println("Error while resetting the audio data stream after the music track has been finished: " + e);
                }
            } while(looping);
        }
    }
 }
