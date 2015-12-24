/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Describes the AboutDialog Class
 */
public class AboutDialog extends JDialog {

    /**
     * The ContentPane of the About Dialog
     */
    private JPanel contentPane;
    /**
     * The Close Button
     */
    private JButton closeButton;
    /**
     * The Textarea displaying the AboutInformation
     */
    private JTextArea aboutTextArea;

    /**
     * Creates A new About Dialog
     */
    public AboutDialog() {
        setContentPane(contentPane);
        setModal(true);
        this.setTitle("About VisualSort");
        this.setSize(450, 300);
        this.setResizable(false);
        getRootPane().setDefaultButton(closeButton);

        closeButton.addActionListener(e -> onOK());
        aboutTextArea.setText("VisualSort - Version 1.0\n" +
                "By HALive, Copyright 2015\n" +
                "This Programm started out as a Small Visualisation for Sorting Algorithms\n" +
                "for a Presentation.\n" +
                "Since then i made many improvements and the Tolll is what it is now.");
    }

    /**
     * Invoked when the ok Button gets clicked.
     * It Closes the Dialog
     */
    private void onOK() {
// add your code here
        dispose();
    }
}
