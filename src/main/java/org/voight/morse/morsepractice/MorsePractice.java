/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.SwingUtilities;
import org.voight.morse.morsepractice.ui.MainJFrame;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class MorsePractice {

    Logger log = Logger.getLogger(MorsePractice.class.getName());
    //HashMap<String,Symbol> symbols  = new HashMap<> ();
    int speed = 30; // 30 is the limit. Don't go over.
    String inputText = "abCDEfghI";//ijklmnopqrstuvwxyz1234567890";
    //MorsePlayer morsePlayer;

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public MorsePractice() throws FileNotFoundException, IOException, LineUnavailableException {
        log.log(Level.INFO, "MorsePractice by Jeffrey Voight");
        //morsePlayer=new MorsePlayer(44100, 700, 15);
        //morsePlayer.setHz(700);
    }

    /**
     *
     * @param argv
     */
    public static void main(String argv[]) {

        try {
            MorsePractice m = new MorsePractice();
            m.run();
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(MorsePractice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void run() throws LineUnavailableException, IOException {
        SwingUtilities.invokeLater(() -> {
            MainJFrame wnd = null;
            try {
                wnd = new MainJFrame();
            } catch (IOException | LineUnavailableException ex) {
                Logger.getLogger(MorsePractice.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (wnd != null) {
                    wnd.setVisible(true);
                }
            }
        });
    }
}
