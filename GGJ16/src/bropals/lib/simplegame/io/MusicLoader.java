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
package bropals.lib.simplegame.io;

import bropals.lib.simplegame.sound.Music;
import bropals.lib.simplegame.sound.SoundUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Loads Music objects for playing long audio files.
 * @author Jonathon
 */
public class MusicLoader extends AssetLoader {

    @Override
    public void loadAsset(String key, InputStream inputStream) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
            AudioFormat format = SoundUtil.convertToPCMSigned(audioStream.getFormat());
            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(format);
            Music music = new Music(sourceDataLine, audioStream);
            sourceDataLine.open(format);
            add(key, music);
        } catch(IOException e) {
            System.err.println("Could not load music with key: " + key);
            e.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("The audio format for this music key is not supported: " + key);
        } catch (LineUnavailableException ex) {
            System.err.println("There is no available Mixer for this music: " + key);
        }
    }
}
