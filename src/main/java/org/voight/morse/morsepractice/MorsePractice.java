/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class MorsePractice {

    Logger log = Logger.getLogger(MorsePractice.class.getName());
    HashMap<String,Symbol> symbols  = new HashMap<> ();
    int speed=40;
    String inputText="abcdefghijklmnopqrstuvwxyz1234567890";

    public MorsePractice() throws FileNotFoundException, IOException, LineUnavailableException {
        log.log(Level.INFO, "MorsePractice by Jeffrey Voight");
        Properties p = new Properties();
        File f = new File(".");
        System.out.println(f.getCanonicalPath());
        p.load(new FileReader("src/main/resources/ITUsymbols.properties"));
        Enumeration e = p.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = (String) p.getProperty(key);
            log.info("Key: '"+key+"' Value: '"+value+"'");
            symbols.put(key, new Symbol(value, speed));
        }
        symbols.put(" ", new Symbol(" ", speed));

    }

    public static void main(String argv[]) {

        try {
            MorsePractice m = new MorsePractice();
            m.run();
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(MorsePractice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void run() throws LineUnavailableException {
        Player p = new Player();
        String myText=inputText.toLowerCase();
        int inputLen=myText.length();
        ArrayList<Symbol> outputSymbols=new ArrayList<>();
        Symbol s;
        for(int i=0;i<inputLen;i++){
            char symbol=myText.charAt(i);            
            s=symbols.get(""+symbol);
            outputSymbols.add(s);
        }
        Iterator<Symbol> it=outputSymbols.iterator();
        while(it.hasNext()){
            Symbol t=it.next();
            p.playTone(t);
        }        

    }
}
