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

    protected static int DIT = 0;
    protected static int DAH = 1;
    protected static Logger log = Logger.getLogger(Symbol.class.getName());

    protected int symbolDuration; // Duration in milliseconds
    protected int ditDuration; // 
    protected int dahDuration; // Three times as long
    protected int ditPause;
    protected int dahPause;
    protected int hz=700;
    protected Tone t;

    protected byte[] tone = new byte[0];


    public Symbol(String _code, int _hz, int _speed) throws LineUnavailableException {
        setDurations(_speed);
        setHz(_hz);
        t = new Tone();

        String display = "";
        //log.info("Code: " + _code);
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

        // log.info(display);
    }

    final public void setHz(int _hz){
        if(_hz>1000){
            log.severe("You've selected a tone Hz greater than 1000. RIP your hearing.");
        }
        hz=_hz;
    }
    
    private byte[] addTone(int _longOrShort) {
        int bytelen = tone.length;
        byte[] newbytes = t.getTone(hz, (_longOrShort == DIT ? ditDuration : dahDuration));
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
