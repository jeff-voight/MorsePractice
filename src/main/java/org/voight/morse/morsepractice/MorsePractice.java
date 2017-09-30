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
public class MorsePractice {
    Logger log=Logger.getLogger(MorsePractice.class.getName());

    public MorsePractice(){
        log.log(Level.INFO, "MorsePractice by Jeffrey Voight");
        
    }
    
    public static void main(String argv[]){
        MorsePractice m=new MorsePractice();
        m.run();
    }
    
    private void run(){
        Symbol s=new Symbol(5, 12);
    }
}
