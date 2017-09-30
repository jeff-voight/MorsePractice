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

    //static AudioFormat af;
    //static SourceDataLine sdl;
    float frequency;
    int volume;

    public Tone() throws LineUnavailableException {
        this(44100, 20);
    }

    public Tone(float _frequency, int _volume) throws LineUnavailableException {
        frequency = _frequency;
        volume = _volume;
        //af = new AudioFormat(frequency, 8, 1, true, false);
        //sdl = AudioSystem.getSourceDataLine(af);
    }

    /**
     *
     * @param hz
     * @param msecs
     * @return
     */
    public byte[] getTone(int hz, int msecs) {
        byte[] returnArray = new byte[(int) (msecs * frequency / 1000)];
        if (hz > 0) {
            for (int i = 0; i < returnArray.length; i++) {
                double angle = i / (frequency / hz) * 2.0 * Math.PI;
                returnArray[i] = (byte) (Math.sin(angle) * volume);
            }
        }
        return returnArray;
    }
}
