/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.util.logging.Logger;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class Symbol {

    public static final int DIT = 0;
    public static final int DAH = 1;
    private static final Logger log = Logger.getLogger(Symbol.class.getName());

    private int symbolDuration; // Duration in milliseconds
    private int shortDuration=0; // 
    
    byte[] tone;

    /**
     * Instantiates a Symbol with the Morse Code symbol represented by the bits
     * in the parameter.
     *
     * @see https://en.wikipedia.org/wiki/Morse_code
     * @param _code A bitwise Morse Code where 0 represents dit and 1 represents
     * dah.
     * @param _speed Symbols per minute rate for this symbol
     */
    public Symbol(int _code, int _speed) {
        String display = "";
        for (int i = 0; i < 5; i++) {
            int bit = (int) Math.pow(2, i);
            if ((_code & bit) == 0) {
                display = display.concat("0");
            } else {
                display = display.concat("1");
            }
        }
        log.info(display);
    }

    public Symbol(String _code, int _speed) {
        String display = "";
        for (int i = 0; i < 5; i++) {
            int bit = Integer.parseInt("" + _code.charAt(i));
            if (bit == 0) {
                display = display.concat("0");
            } else {
                display = display.concat("1");
            }
        }
        log.info(display);
    }

    private byte[] addTone(int _longOrShort, int _speed) {
        int duration = 1 / _speed;
    }
}
