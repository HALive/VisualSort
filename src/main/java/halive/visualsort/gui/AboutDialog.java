package halive.visualsort.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutDialog extends JDialog {
    private JPanel contentPane;
    private JButton closeButton;
    private JTextArea aboutTextArea;

    public AboutDialog() {
        setContentPane(contentPane);
        setModal(true);
        this.setTitle("About VisualSort");
        this.setSize(450,300);
        this.setResizable(false);
        getRootPane().setDefaultButton(closeButton);

        closeButton.addActionListener(e -> onOK());
        aboutTextArea.setText("VisualSort - Version 1.0\n" +
                "By HALive, Copyright 2015\n" +
                "This Programm started out as a Small Visualisation for Sorting Algorithms\n" +
                "for a Presentation.\n" +
                "Since then i made many improvements and the Tolll is what it is now.");
    }

    private void onOK() {
// add your code here
        dispose();
    }
}
