/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.SwingUtilities;
import org.voight.morse.morsepractice.ui.MainJFrame;
import org.voight.morse.morsepractice.ui.MorsePracticePad;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class MorsePractice {

    

    /**
     *
     */
    public MorsePractice() {
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
            MorsePracticePad wnd = null;
            try {
                wnd = new MorsePracticePad();
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
