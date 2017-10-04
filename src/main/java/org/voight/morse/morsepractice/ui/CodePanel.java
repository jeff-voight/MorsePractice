/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice.ui;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;
import org.voight.morse.morsepractice.Symbol;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class CodePanel extends JPanel {

    Symbol symbol;
    boolean clear = false;

    public CodePanel() {
        super();
        try {
            symbol = new Symbol("100", 700, 10); // This is a dummy symbol. Probably never get seen
        } catch (LineUnavailableException ex) {
            Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        if (!clear&&symbol!=null) {
            int charWidth = getWidth() / 5;
            int charHeight = getHeight();
            String symbolString = symbol.getCode();
            int symbolLength = symbolString.length();
            for (int i = 0; i < symbolLength; i++) {
                char theChar = symbolString.charAt(i);
                if ('1' == theChar) {
                    drawDash(g, i * charWidth, charWidth, charHeight);
                } else {
                    drawDot(g, i * charWidth, charWidth, charHeight);
                }
            }
        }
    }

    public void clearSymbol(){
        clear=true;
        this.repaint(10);
        this.paintImmediately(0, 0, getWidth(), getHeight()); // These two lines are buggy

    }
    
    public void setSymbol(Symbol s) {
        clear=false;
        symbol = s;
        this.repaint(10);
    }

    private void drawDash(Graphics g, int start, int charWidth, int charHeight) {
        int centerWidth = charWidth / 2;
        int centerHeight = charHeight / 2;
        int dashHeight = (int) (charHeight * .2); // 20%
        int dashWidth = (int) (charWidth * .8); // 80%
        int x = start + centerWidth - dashWidth / 2;
        int y = centerHeight - dashHeight / 2;
        g.fillRoundRect(x, y, dashWidth, dashHeight, 9, 9);
    }

    private void drawDot(Graphics g, int start, int charWidth, int charHeight) {
        int centerWidth = charWidth / 2;
        int centerHeight = charHeight / 2;
        int dashHeight = (int) (charHeight * .2); // 20%
        int dashWidth = (int) (charWidth * .2); // 80%
        int x = start + centerWidth - dashWidth / 2;
        int y = centerHeight - dashHeight / 2;
        g.fillOval(x, y, dashWidth, dashHeight);

    }
}
