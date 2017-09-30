/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class Symbol {

    public static final int DIT = 0;
    public static final int DAH = 1;
    private static final Logger log = Logger.getLogger(Symbol.class.getName());

    private int symbolDuration; // Duration in milliseconds
    private int ditDuration; // 
    private int dahDuration; // Three times as long
    private int ditPause;
    private int dahPause;

    Tone t;

    byte[] tone = new byte[0];

//    /**
//     * Instantiates a Symbol with the Morse Code symbol represented by the bits
//     * in the parameter.
//     *
//     * @throws javax.sound.sampled.LineUnavailableException
//     * @see https://en.wikipedia.org/wiki/Morse_code
//     * @param _code A bitwise Morse Code where 0 represents dit and 1 represents
//     * dah.
//     * @param _speed Symbols per minute rate for this symbol
//     */
//    public Symbol(int _code, int _speed) throws LineUnavailableException {
//        setDurations(_speed);
//        t = new Tone();
//        String display = "";
//        for (int i = 0; i < 5; i++) {
//            int bit = (int) Math.pow(2, i);
//            if ((_code & bit) == 0) {
//                tone = addTone(DIT);
//                tone = addPause(DIT);
//                display = display.concat("0");
//            } else {
//                tone = addTone(DAH);
//                tone = addPause(DIT);
//                display = display.concat("1");
//            }
//        }
//        tone = addPause(DAH);
//        log.info(display);
//        log.info("The array is now " + tone.length + " bytes long.");
//    }
    public Symbol(String _code, int _speed) throws LineUnavailableException {
        setDurations(_speed);
        t = new Tone();

        String display = "";
        log.info("Code: " + _code);
        int strLen = _code.length();
        if (!" ".equals(_code)) {
            for (int i = 0; i < strLen; i++) {
                int bit = Integer.parseInt("" + _code.charAt(i));
                if (bit == 0) {
                    tone = addTone(DIT);
                    tone = addPause(DIT);
                    display = display.concat("0");
                } else {
                    tone = addTone(DAH);
                    tone = addPause(DIT);
                    display = display.concat("1");
                }
            }
        }
        tone = addPause(DAH);

        log.info(display);
    }

    private byte[] addTone(int _longOrShort) {
        int bytelen = tone.length;
        byte[] newbytes = t.getTone(700, (_longOrShort == DIT ? ditDuration : dahDuration));
        byte[] returnBytes = new byte[bytelen + newbytes.length];
        System.arraycopy(tone, 0, returnBytes, 0, bytelen);
        System.arraycopy(newbytes, 0, returnBytes, bytelen, newbytes.length);
        return returnBytes;
    }

    private byte[] addPause(int _longOrShort) {
        int bytelen = tone.length;
        byte[] newbytes = t.getTone(00, (_longOrShort == DIT ? ditDuration : dahDuration));
        byte[] returnBytes = new byte[bytelen + newbytes.length];
        System.arraycopy(tone, 0, returnBytes, 0, bytelen);
        System.arraycopy(newbytes, 0, returnBytes, bytelen, newbytes.length);
        return returnBytes;
    }

    /**
     * Gets the duration of a DIT pulse based on the Groups Per Minute Groups
     * consist of 5 symbols each. The worst case scenario per symbol is DAH DAH
     * DAH DAH DAH plus a pause.
     *
     * @param _gpm
     * @return
     */
    private int getDit(int _gpm) {
        double longest = 60.0 / (_gpm * 5); // The longest duration a symbol can be at this rate in seconds
        // The longest symbol possible is 5 DAHs in a row plus a DAH pause at the end.
        // Including the DIT pauses between DITs and DAHs, that makes
        // 6*DAH + 4*DIT. Each DAH is 3*DIT. This means that the longest symbol is 
        // 3 * 6 * DAH + 4 * DIT = (18 + 4) * DIT or 22*DIT.
        // Therefore, a DIT is longest/22.
        int dit = (int) (longest * 1000 / 22); // Convert to milliseconds while we're here
        log.info("At " + _gpm + "GPM, DIT is " + dit + " milliseconds.");
        return dit;
    }

    private void setDurations(int _speed) {
        ditDuration = getDit(_speed);
        dahDuration = ditDuration * 3;
        ditPause = ditDuration;
        dahPause = dahDuration;
    }

    public byte[] getBytes() {
        return tone;
    }

}
