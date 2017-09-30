/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class MorsePractice {
    Logger log=Logger.getLogger(MorsePractice.class.getName());

    public MorsePractice(){
        log.log(Level.INFO, "MorsePractice by Jeffrey Voight");
        
    }
    
    public static void main(String argv[]){
        MorsePractice m=new MorsePractice();
        try {
            m.run();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MorsePractice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void run() throws LineUnavailableException{
        Symbol s=new Symbol(1, 12);
        Symbol s1=new Symbol(127, 12);
    }
}
