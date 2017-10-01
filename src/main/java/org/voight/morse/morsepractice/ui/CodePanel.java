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

    public CodePanel() {
        super();
        try {
            symbol = new Symbol("10111", 700, 10);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
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

    public void setSymbol(Symbol s) {
        symbol = s;
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
