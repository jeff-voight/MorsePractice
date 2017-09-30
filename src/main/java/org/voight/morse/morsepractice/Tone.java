/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class Tone {

    static AudioFormat af;
    static SourceDataLine sdl;
    float frequency;
    int volume;

    public Tone() throws LineUnavailableException{
        this(44100, 50);
    }
    
    public Tone(float _frequency, int _volume) throws LineUnavailableException {
        frequency = _frequency;
        volume = _volume;
        af = new AudioFormat(frequency, 8, 1, true, false);
        sdl = AudioSystem.getSourceDataLine(af);
    }

    /**
     * Generates a tone.
     *
     * @param hz
     * @param msecs The number of milliseconds to play the tone.
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public void generateTone(int hz, int msecs) throws LineUnavailableException {

        byte[] buf = new byte[1];

        sdl.open(af);
        sdl.start();
        for (int i = 0; i < msecs * frequency / 1000; i++) {
            double angle = i / (frequency / hz) * 2.0 * Math.PI;
            buf[0] = (byte) (Math.sin(angle) * volume);

            sdl.write(buf, 0, 1);

        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }

    public byte[] getTone(int hz, int msecs) {
        byte[] returnArray = new byte[(int) (msecs * frequency / 1000)];
        for (int i = 0; i < returnArray.length; i++) {
            double angle = i / (frequency / hz) * 2.0 * Math.PI;
            returnArray[i] = (byte) (Math.sin(angle) * volume);
        }
        return returnArray;
    }

    /**
     * Plays a tone.
     *
     * @param _bytes
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public void playTone(byte[] _bytes)
            throws LineUnavailableException {

        sdl.open(af);
        sdl.start();
        for (int i = 0; i < _bytes.length; i++) {
            sdl.write(_bytes, i, 1);
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }

}
