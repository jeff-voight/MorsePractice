/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import static java.awt.SystemColor.text;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;
import org.voight.morse.morsepractice.Symbol;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class LetterPanel extends JPanel {

    Symbol symbol;
    boolean clear = false;
    int charWidth, charHeight, width, height, baseX,
            baseY, fHeight, fWidth;
    String symbolString;
    FontMetrics metrics;

    public LetterPanel() {
        super();
        this.setFont(this.getFont().deriveFont(90f));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Graphics g = e.getComponent().getGraphics();
                Font f = g.getFont();
                charWidth = getWidth();
                charHeight = getHeight();

                // get metrics from the graphics
                metrics = g.getFontMetrics(f);
                // get the height of a line of text in this
                // font and render context
                fHeight = metrics.getHeight();
                fWidth = metrics.stringWidth("W");
                baseY = fHeight; // height- (fHeight / 2);
                baseX = 30;

            }
        });
        try {
            setSymbol(new Symbol(' ', "", 700, 10)); // This is a dummy symbol. Probably never get seen
        } catch (LineUnavailableException ex) {
            Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, width, height);
        if (!clear && symbol != null) {
            g.drawString(symbolString, baseX, baseY);

        }
    }

    public void clearSymbol() {
        clear = true;
        this.paintImmediately(0, 0, width, height); // These two lines are buggy

    }

    public final void setSymbol(Symbol s) {
        clear = false;
        symbol = s;
        symbolString = symbol.getText();

        this.repaint(20);
    }

}
