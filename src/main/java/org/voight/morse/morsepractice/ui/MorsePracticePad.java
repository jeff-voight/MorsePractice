/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.voight.morse.morsepractice.ui;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.SwingWorker;
import org.voight.morse.morsepractice.MorsePlayer;
import org.voight.morse.morsepractice.Symbol;

/**
 *
 * @author Jeffrey Voight <jeff.voight@gmail.com>
 */
public class MorsePracticePad extends javax.swing.JFrame {

    private boolean immediatePlay = false;
    private MorsePlayer morsePlayer;
    private int frequency = 44100, hz = 700, gpm = 10, groups = 5;
    //practice listen testing
    private Mode mode = Mode.PRACTICE;
    private boolean currentlyOutputtingAudio = false, playing = false;
    private Random r = new Random();
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     *
     */
    public enum Mode {

        /**
         *
         */
        PRACTICE,
        /**
         *
         */
        LISTEN,
        /**
         *
         */
        TESTING;
    }

    /**
     * Creates new form MorsePracticePad
     *
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public MorsePracticePad() throws IOException, LineUnavailableException {
        initComponents();
        enablePracticeMode();
        morsePlayer = new MorsePlayer(frequency, hz, gpm);
    }

    private void drawLetter() {
        if (mode != Mode.TESTING) { // Don't display if testing mode

        }
    }

    private void enablePracticeMode() {
        mode = Mode.PRACTICE;
        this.groupCountLabel.setVisible(false);
        this.groupLabel.setVisible(false);
        this.groupSlider.setVisible(false);
        this.immediatePlayCheckBox.setEnabled(true);
        this.letterPanel.setVisible(true);

    }

    private void enableListenMode() {
        mode = Mode.LISTEN;
        this.groupCountLabel.setVisible(true);
        this.groupLabel.setVisible(true);
        this.groupSlider.setVisible(true);
        if (inputTextArea.getText().equals("")) {
            inputTextArea.setText(getRandomText(groupSlider.getValue()));
        }
        this.immediatePlayCheckBox.setSelected(false);
        this.immediatePlayCheckBox.setEnabled(false);
        this.letterPanel.setVisible(true);

    }

    private void enableTestingMode() {
        mode = Mode.LISTEN;
        this.groupCountLabel.setVisible(true);
        this.groupLabel.setVisible(true);
        this.groupSlider.setVisible(true);
        this.immediatePlayCheckBox.setSelected(false);
        this.immediatePlayCheckBox.setEnabled(false);
        this.letterPanel.setVisible(false);
    }

    private void changeMode() {
        if (this.practiceRadioButton.isSelected()) {
            enablePracticeMode();
        } else if (this.listenRadioButton.isSelected()) {
            enableListenMode();
        } else if (this.testingRadioButton.isSelected()) {
            enableTestingMode();
        }
    }

    private void changeGroupCount() {
        groups = this.groupSlider.getValue();
        this.groupCountLabel.setText(groups + " Groups");
        inputTextArea.setText(getRandomText(groups));
    }

    private String getRandomText(int numGroups) {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < numGroups; i++) {
            for (int j = 0; j < 5; j++) { // groups are 5 symbols
                char b = alphabet.charAt(r.nextInt(alphabet.length()));
                returnString.append(b);
            }
            returnString.append(' ');
        }
        return returnString.toString();
    }

    private void changeGPMCount() {
        gpm = this.gpmSlider.getValue();
        this.gpmCountLabel.setText(gpm + " GPM");
        restartMorsePlayer();
    }

    private void changeFrequency() {
        hz = this.frequencySlider.getValue();
        this.frequencyCountLabel.setText(hz + " Hz");
        restartMorsePlayer();
    }

    private void changeImmediatePlay() {
        immediatePlay = this.immediatePlayCheckBox.isSelected();
    }

    private void playInputText() {
        if (!playing) {
            playButton.setText("STOP");
            playButton.revalidate();
            playButton.paintImmediately(0, 0, playButton.getWidth(), playButton.getHeight());
            playing = true;

            String text = this.inputTextArea.getText();
            int strlen = text.length();
            for (int i = 0; i < strlen; i++) {
                char c = text.charAt(i);
                Symbol s=morsePlayer.getSymbol(c);
                ((LetterPanel)letterPanel).setSymbol(s);
                ((CodePanel) codePanel).setSymbol(s);
                codePanel.revalidate();
                codePanel.paintImmediately(0, 0, codePanel.getWidth(), codePanel.getHeight());
                letterPanel.revalidate();
                letterPanel.paintImmediately(0, 0, letterPanel.getWidth(), letterPanel.getHeight());
                play(c);
                try {
                    Thread.sleep(Symbol.getDit(gpm) * 3);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MorsePracticePad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            playing = false;
            playButton.setText("Play");
        }
        playing = false;
        playButton.setText("Play");
        playButton.paintImmediately(0, 0, playButton.getWidth(), playButton.getHeight());
        ((CodePanel) codePanel).clearSymbol();
        ((LetterPanel)letterPanel).clearSymbol();
    }

    private void play(char c) {
        if (playing) {
            currentlyOutputtingAudio = true;
            try {
                morsePlayer.play(c);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(MorsePracticePad.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentlyOutputtingAudio = false;
        }
    }

    private void restartMorsePlayer() {
        try {
            morsePlayer = new MorsePlayer(frequency, hz, gpm);

        } catch (IOException | LineUnavailableException ex) {
            Logger.getLogger(MorsePracticePad.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modeButtonGroup = new javax.swing.ButtonGroup();
        modePanel = new javax.swing.JPanel();
        practiceRadioButton = new javax.swing.JRadioButton();
        listenRadioButton = new javax.swing.JRadioButton();
        testingRadioButton = new javax.swing.JRadioButton();
        textInputPanel = new javax.swing.JPanel();
        textInputLabel = new javax.swing.JLabel();
        immediatePlayCheckBox = new javax.swing.JCheckBox();
        playButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputTextArea = new javax.swing.JTextArea();
        controlsPanel = new javax.swing.JPanel();
        groupsPanel = new javax.swing.JPanel();
        groupLabel = new javax.swing.JLabel();
        groupCountLabel = new javax.swing.JLabel();
        groupSlider = new javax.swing.JSlider();
        gpmPanel = new javax.swing.JPanel();
        gpmLabel = new javax.swing.JLabel();
        gpmCountLabel = new javax.swing.JLabel();
        gpmSlider = new javax.swing.JSlider();
        frequencyPanel = new javax.swing.JPanel();
        frequencyLabel = new javax.swing.JLabel();
        frequencyCountLabel = new javax.swing.JLabel();
        frequencySlider = new javax.swing.JSlider();
        displayPanel = new javax.swing.JPanel();
        letterPanel = new LetterPanel();
        codePanel = new CodePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        modePanel.setBackground(new java.awt.Color(204, 204, 204));
        modePanel.setName("modesPanel"); // NOI18N

        modeButtonGroup.add(practiceRadioButton);
        practiceRadioButton.setSelected(true);
        practiceRadioButton.setText("Practice Mode");
        practiceRadioButton.setToolTipText("Enter text and press play to hear.");
        practiceRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                practiceRadioButtonStateChanged(evt);
            }
        });

        modeButtonGroup.add(listenRadioButton);
        listenRadioButton.setText("Listen Mode");
        listenRadioButton.setToolTipText("Listen to random Morse Code.");
        listenRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                listenRadioButtonStateChanged(evt);
            }
        });

        modeButtonGroup.add(testingRadioButton);
        testingRadioButton.setText("Testing Mode");
        testingRadioButton.setToolTipText("Play code and see how well you copied.");
        testingRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                testingRadioButtonStateChanged(evt);
            }
        });

        javax.swing.GroupLayout modePanelLayout = new javax.swing.GroupLayout(modePanel);
        modePanel.setLayout(modePanelLayout);
        modePanelLayout.setHorizontalGroup(
            modePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(practiceRadioButton))
                    .addGroup(modePanelLayout.createSequentialGroup()
                        .addGroup(modePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listenRadioButton)
                            .addComponent(testingRadioButton))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        modePanelLayout.setVerticalGroup(
            modePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modePanelLayout.createSequentialGroup()
                .addComponent(practiceRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(listenRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testingRadioButton)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        textInputPanel.setBackground(new java.awt.Color(204, 204, 204));

        textInputLabel.setText("Text Input");

        immediatePlayCheckBox.setText("Immediate Play");
        immediatePlayCheckBox.setToolTipText("Play Morse Code as symbols are entered.");
        immediatePlayCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                immediatePlayCheckBoxStateChanged(evt);
            }
        });
        immediatePlayCheckBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                immediatePlayCheckBoxPropertyChange(evt);
            }
        });

        playButton.setText("Play");
        playButton.setToolTipText("Play the input text Morse Code");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        inputTextArea.setColumns(20);
        inputTextArea.setRows(5);
        inputTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inputTextAreaKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(inputTextArea);

        javax.swing.GroupLayout textInputPanelLayout = new javax.swing.GroupLayout(textInputPanel);
        textInputPanel.setLayout(textInputPanelLayout);
        textInputPanelLayout.setHorizontalGroup(
            textInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, textInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(textInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, textInputPanelLayout.createSequentialGroup()
                        .addComponent(textInputLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                        .addComponent(immediatePlayCheckBox))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, textInputPanelLayout.createSequentialGroup()
                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        textInputPanelLayout.setVerticalGroup(
            textInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(textInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textInputLabel)
                    .addComponent(immediatePlayCheckBox))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playButton)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        controlsPanel.setBackground(new java.awt.Color(153, 255, 255));

        groupsPanel.setPreferredSize(new java.awt.Dimension(500, 75));

        groupLabel.setText("Group Count");
        groupLabel.setToolTipText("");

        groupCountLabel.setText("5 Groups");
        groupCountLabel.setToolTipText("A Group is 5 symbols");

        groupSlider.setMajorTickSpacing(5);
        groupSlider.setMaximum(25);
        groupSlider.setMinimum(1);
        groupSlider.setMinorTickSpacing(1);
        groupSlider.setPaintLabels(true);
        groupSlider.setPaintTicks(true);
        groupSlider.setSnapToTicks(true);
        groupSlider.setToolTipText("Adjusts the number of groups to test");
        groupSlider.setValue(5);
        groupSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                groupSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout groupsPanelLayout = new javax.swing.GroupLayout(groupsPanel);
        groupsPanel.setLayout(groupsPanelLayout);
        groupsPanelLayout.setHorizontalGroup(
            groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, groupsPanelLayout.createSequentialGroup()
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(groupSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(groupsPanelLayout.createSequentialGroup()
                        .addComponent(groupLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(groupCountLabel)))
                .addContainerGap())
        );
        groupsPanelLayout.setVerticalGroup(
            groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupsPanelLayout.createSequentialGroup()
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupLabel)
                    .addComponent(groupCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        gpmPanel.setPreferredSize(new java.awt.Dimension(500, 75));

        gpmLabel.setText("Groups per Minute");
        gpmLabel.setToolTipText("");

        gpmCountLabel.setText("15 GPM");
        gpmCountLabel.setToolTipText("A Group is 5 symbols");

        gpmSlider.setMajorTickSpacing(5);
        gpmSlider.setMaximum(40);
        gpmSlider.setMinimum(5);
        gpmSlider.setMinorTickSpacing(1);
        gpmSlider.setPaintLabels(true);
        gpmSlider.setPaintTicks(true);
        gpmSlider.setSnapToTicks(true);
        gpmSlider.setToolTipText("Adjusts the speed of the Morse Code");
        gpmSlider.setValue(10);
        gpmSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                gpmSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout gpmPanelLayout = new javax.swing.GroupLayout(gpmPanel);
        gpmPanel.setLayout(gpmPanelLayout);
        gpmPanelLayout.setHorizontalGroup(
            gpmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gpmPanelLayout.createSequentialGroup()
                .addGroup(gpmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(gpmSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gpmPanelLayout.createSequentialGroup()
                        .addComponent(gpmLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gpmCountLabel)))
                .addContainerGap())
        );
        gpmPanelLayout.setVerticalGroup(
            gpmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gpmPanelLayout.createSequentialGroup()
                .addGroup(gpmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gpmLabel)
                    .addComponent(gpmCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gpmSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        frequencyPanel.setPreferredSize(new java.awt.Dimension(500, 75));

        frequencyLabel.setText("Tone Frequency");
        frequencyLabel.setToolTipText("");

        frequencyCountLabel.setText("700 Hz");
        frequencyCountLabel.setToolTipText("Standard is 700 Hz");

        frequencySlider.setMajorTickSpacing(100);
        frequencySlider.setMaximum(1000);
        frequencySlider.setMinimum(500);
        frequencySlider.setMinorTickSpacing(50);
        frequencySlider.setPaintLabels(true);
        frequencySlider.setPaintTicks(true);
        frequencySlider.setSnapToTicks(true);
        frequencySlider.setToolTipText("Adjusts the speed of the Morse Code");
        frequencySlider.setValue(700);
        frequencySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                frequencySliderStateChanged(evt);
            }
        });
        frequencySlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                frequencySliderPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout frequencyPanelLayout = new javax.swing.GroupLayout(frequencyPanel);
        frequencyPanel.setLayout(frequencyPanelLayout);
        frequencyPanelLayout.setHorizontalGroup(
            frequencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frequencyPanelLayout.createSequentialGroup()
                .addGroup(frequencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(frequencySlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(frequencyPanelLayout.createSequentialGroup()
                        .addComponent(frequencyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(frequencyCountLabel)))
                .addContainerGap())
        );
        frequencyPanelLayout.setVerticalGroup(
            frequencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frequencyPanelLayout.createSequentialGroup()
                .addGroup(frequencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frequencyLabel)
                    .addComponent(frequencyCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(frequencySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout controlsPanelLayout = new javax.swing.GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsPanelLayout);
        controlsPanelLayout.setHorizontalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
            .addComponent(gpmPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
            .addComponent(frequencyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
        );
        controlsPanelLayout.setVerticalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addComponent(groupsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gpmPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(frequencyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        displayPanel.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout letterPanelLayout = new javax.swing.GroupLayout(letterPanel);
        letterPanel.setLayout(letterPanelLayout);
        letterPanelLayout.setHorizontalGroup(
            letterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );
        letterPanelLayout.setVerticalGroup(
            letterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout codePanelLayout = new javax.swing.GroupLayout(codePanel);
        codePanel.setLayout(codePanelLayout);
        codePanelLayout.setHorizontalGroup(
            codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
        );
        codePanelLayout.setVerticalGroup(
            codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 157, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(letterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(codePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(letterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(controlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(modePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(controlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textInputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void practiceRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_practiceRadioButtonStateChanged
        changeMode();
    }//GEN-LAST:event_practiceRadioButtonStateChanged

    private void listenRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_listenRadioButtonStateChanged
        changeMode();
    }//GEN-LAST:event_listenRadioButtonStateChanged

    private void testingRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_testingRadioButtonStateChanged
        changeMode();
    }//GEN-LAST:event_testingRadioButtonStateChanged

    private void frequencySliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_frequencySliderPropertyChange
        changeFrequency();
    }//GEN-LAST:event_frequencySliderPropertyChange

    private void immediatePlayCheckBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_immediatePlayCheckBoxPropertyChange
        changeImmediatePlay();
    }//GEN-LAST:event_immediatePlayCheckBoxPropertyChange

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        playInputText();
    }//GEN-LAST:event_playButtonActionPerformed

    private void groupSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_groupSliderStateChanged
        changeGroupCount();
    }//GEN-LAST:event_groupSliderStateChanged

    private void gpmSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_gpmSliderStateChanged
        changeGPMCount();
    }//GEN-LAST:event_gpmSliderStateChanged

    private void frequencySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_frequencySliderStateChanged
        changeFrequency();
    }//GEN-LAST:event_frequencySliderStateChanged

    private void inputTextAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputTextAreaKeyTyped
        if (immediatePlay) {
            char c = evt.getKeyChar();
            playing = true;
            ((CodePanel) codePanel).setSymbol(morsePlayer.getSymbol(c));
            codePanel.paintImmediately(0, 0, codePanel.getWidth(), codePanel.getHeight());
            try {
                Thread.sleep(Symbol.getDit(gpm));
            } catch (InterruptedException ex) {
                Logger.getLogger(MorsePracticePad.class.getName()).log(Level.SEVERE, null, ex);
            }
            play(c);
            playing = false;
            ((CodePanel) codePanel).clearSymbol();
        }
    }//GEN-LAST:event_inputTextAreaKeyTyped

    private void immediatePlayCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_immediatePlayCheckBoxStateChanged
        changeImmediatePlay();
    }//GEN-LAST:event_immediatePlayCheckBoxStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MorsePracticePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MorsePracticePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MorsePracticePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MorsePracticePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MorsePracticePad().setVisible(true);

            } catch (IOException ex) {
                Logger.getLogger(MorsePracticePad.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (LineUnavailableException ex) {
                Logger.getLogger(MorsePracticePad.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel codePanel;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JLabel frequencyCountLabel;
    private javax.swing.JLabel frequencyLabel;
    private javax.swing.JPanel frequencyPanel;
    private javax.swing.JSlider frequencySlider;
    private javax.swing.JLabel gpmCountLabel;
    private javax.swing.JLabel gpmLabel;
    private javax.swing.JPanel gpmPanel;
    private javax.swing.JSlider gpmSlider;
    private javax.swing.JLabel groupCountLabel;
    private javax.swing.JLabel groupLabel;
    private javax.swing.JSlider groupSlider;
    private javax.swing.JPanel groupsPanel;
    private javax.swing.JCheckBox immediatePlayCheckBox;
    private javax.swing.JTextArea inputTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel letterPanel;
    private javax.swing.JRadioButton listenRadioButton;
    private javax.swing.ButtonGroup modeButtonGroup;
    private javax.swing.JPanel modePanel;
    private javax.swing.JButton playButton;
    private javax.swing.JRadioButton practiceRadioButton;
    private javax.swing.JRadioButton testingRadioButton;
    private javax.swing.JLabel textInputLabel;
    private javax.swing.JPanel textInputPanel;
    // End of variables declaration//GEN-END:variables
}
