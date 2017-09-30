/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.util.logging.Level;
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
    private int ditDuration; // 
    private int dahDuration=3*ditDuration; // Three times as long
    private int ditPause=ditDuration;
    private int dahPause=dahDuration;
    
    
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
        setDurations(_speed);     
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
    
    private void setDurations(int _speed){
        ditDuration=getDit(_speed);
        dahDuration=ditDuration*3;
        ditPause=ditDuration;
        dahPause=dahDuration;
    }
    
    /**
     * Gets the duration of a DIT pulse based on the Groups Per Minute
     * Groups consist of 5 symbols each. The worst case scenario per symbol
     * is DAH DAH DAH DAH DAH plus a pause.
     * @param _gpm
     * @return 
     */
    private int getDit(int _gpm){
        double longest=60.0/(_gpm*5); // The longest duration a symbol can be at this rate in seconds
        // The longest symbol possible is 5 DAHs in a row plus a DAH pause at the end.
        // Including the DIT pauses between DITs and DAHs, that makes
        // 6*DAH + 4*DIT. Each DAH is 3*DIT. This means that the longest symbol is 
        // 3 * 6 * DAH + 4 * DIT = (18 + 4) * DIT or 22*DIT.
        // Therefore, a DIT is longest/22.
        int dit=(int)(longest*1000/22); // Convert to milliseconds while we're here
        log.info("At "+_gpm+"GPM, DIT is "+dit+" milliseconds.");
        return dit;        
    }
}
