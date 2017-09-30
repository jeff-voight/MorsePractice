/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class Tone {

    float frequency;
    int volume;

    public Tone() throws LineUnavailableException {
        this(44100, 40);
    }

    public Tone(float _frequency, int _volume) throws LineUnavailableException {
        frequency = _frequency;
        volume = _volume;
        
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
