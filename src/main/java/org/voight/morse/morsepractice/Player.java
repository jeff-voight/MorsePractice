/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.util.logging.Logger;
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
    public static Logger log = Logger.getLogger(Player.class.getName());
    /**
     * @throws javax.sound.sampled.LineUnavailableException
     * @deprecated
     */
    public Player() throws LineUnavailableException {
        this(44100);
//        log.info("Default constructor. Calling custom with 44100.");
    }

    public Player(int _frequency) throws LineUnavailableException {
        af = new AudioFormat(_frequency, 8, 1, true, false);
        sdl = AudioSystem.getSourceDataLine(af);
    }

    public void playTone(Symbol _s) throws LineUnavailableException {
        playTone(_s.getBytes());
    }

    public void playTone(byte[] byteArray) throws LineUnavailableException {
//        log.info("Playing tone. byteArray is " + byteArray.length + " bytes.");
        sdl = AudioSystem.getSourceDataLine(af);

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
