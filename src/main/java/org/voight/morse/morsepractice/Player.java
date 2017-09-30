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
public class Player {

    static AudioFormat af;
    static SourceDataLine sdl;

    public Player(int _frequency) throws LineUnavailableException {
        sdl = AudioSystem.getSourceDataLine(af);
        af = new AudioFormat(_frequency, 8, 1, true, false);
    }

    public void playTone(byte[] byteArray) throws LineUnavailableException {
        sdl.open(af);
        sdl.start();
        for (int i = 0; i < byteArray.length; i++) {
            sdl.write(byteArray, i, 1);
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }
}
