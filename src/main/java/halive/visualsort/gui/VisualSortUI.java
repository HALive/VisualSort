/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui;

import halive.visualsort.VisualSort;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.sorting.SortingAlgorithm;
import halive.visualsort.gui.rendering.IVisualSortRenderer;
import halive.visualsort.gui.rendering.j2d.SortingRenderCanvas;
import halive.visualsort.gui.rendering.slick2d.OpenGLRenderCanvas;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class VisualSortUI extends JFrame {

    private Canvas renderCanvas;
    private IVisualSortRenderer renderer;
    private SortingHandler sortingHandler;

    private boolean allowResize = true;

    public VisualSortUI() {
        sortingHandler = new SortingHandler(this);
        initComponents();
        for (SortingAlgorithm a : SortingAlgorithm.ALGORTIHMS) {
            algorithmSelector.addItem(a);
        }
        for (DataGenerator g : DataGenerator.DATAGGENS) {
            dataGeneratorSelector.addItem(g);
        }
        Dimension defSize = new Dimension(600, 600);
        this.setSize(defSize);
        this.setMinimumSize(defSize);
    }

    private void onWindowsClosing(WindowEvent e) {
        exit();
    }

    private void exitMenuItemActionPerformed(ActionEvent e) {
        exit();
    }

    private void barWidthSpinnerStateChanged(ChangeEvent e) {
        int newVal = (Integer) barWidthSpinner.getValue();
        int amtVal = (Integer) entrySpinner.getValue();
        if ((VisualSort.MAX_ENTRIES / newVal) < amtVal) {
            amtVal = VisualSort.MAX_ENTRIES / newVal;
        }
        entrySpinner.setModel(new SpinnerNumberModel(amtVal, 10, VisualSort.MAX_ENTRIES / newVal, 1));
        sortingHandler.setRenderWidth(newVal);
    }

    private void startButtonActionPerformed(ActionEvent e) {
        //<editor-fold desc="Instantiate the Renderer">
        if (renderer == null) {
            if (!this.useOpenGLCheckBox.isSelected()) {
                renderCanvas = new SortingRenderCanvas(this, sortingHandler);
                renderer = (IVisualSortRenderer) renderCanvas;
            } else {
                allowResize = false;
                this.setResizable(false);
                this.setMinimumSize(getSize());
                this.setMaximumSize(getSize());
                renderer = new OpenGLRenderCanvas(sortingHandler, this);
                try {
                    renderCanvas = ((OpenGLRenderCanvas) renderer).createCanvas();
                } catch (Exception e1) {
                    slickError(e1);
                    return;
                }
            }
            renderPanel.add(renderCanvas, BorderLayout.CENTER);
        }
        //</editor-fold>

        this.useOpenGLCheckBox.setEnabled(false);
        startButton.setEnabled(false);
        //Trigger Events to Update their state
        barWidthSpinnerStateChanged(null);
        entrySpinnerStateChanged(null);
        delayMSSpinnerStateChanged(null);
        //Disable Algorthm Selction
        this.enableAlgorithmSelection(false);
        updateScrollbar();
        displayStatus("Initializing");
        //Launch the Renderer
        if (!renderer.isRendering()) {
            renderer.start();
        }
        //<editor-fold desc="Initialize the SortingHandler">
        sortingHandler.setSortingAlgorithm((SortingAlgorithm) algorithmSelector.getSelectedItem());
        sortingHandler.setDataGenerator((DataGenerator) dataGeneratorSelector.getSelectedItem());
        boolean delayOnSwap = applyDelayOnSwapCheckBox.isSelected();
        boolean delayOnComp = applyDelayOnCompCheckBox.isSelected();
        sortingHandler.setDelayOnComp(delayOnComp);
        sortingHandler.setDelayOnSwap(delayOnSwap);
        sortingHandler.init();
        //</editor-fold>
    }

    private void onComponentResized(ComponentEvent e) {
        if (allowResize) {
            updateScrollbar();
            toggleRedraw();
        }
    }

    private void entrySpinnerStateChanged(ChangeEvent e) {
        int val = (Integer) entrySpinner.getValue();
        sortingHandler.setAmtEntries(val);
    }

    private void delayMSSpinnerStateChanged(ChangeEvent e) {
        int val = (Integer) delayMSSpinner.getValue();
        sortingHandler.setDelay(val);
    }

    private void fovScrollBarAdjustmentValueChanged(AdjustmentEvent e) {
        if (renderer != null) {
            int value = e.getValue();
            renderer.setRenderPos(value);
        }
    }

    private void CheckBoxStateChanged(ChangeEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        sortingHandler.setDelayOnComp(checkBox.isSelected());
    }

    private void applyDelayOnSwapCheckBoxStateChanged(ChangeEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        sortingHandler.setDelayOnSwap(checkBox.isSelected());
    }

    private void toggleRedraw() {
        this.repaint();
    }

    private void pauseOnNextSwapButtonActionPerformed(ActionEvent e) {
        this.enableStopButtons(false);
        sortingHandler.setStopOnNextSwap(true);
        continueButton.setEnabled(true);
    }

    private void pauseOnNextCompButtonActionPerformed(ActionEvent e) {
        this.enableStopButtons(false);
        sortingHandler.setStopOnNextComp(true);
        continueButton.setEnabled(true);
    }

    private void continuebuttomnActionPerformed(ActionEvent e) {
        continueButton.setEnabled(false);
        sortingHandler.clearPause();
        enableStopButtons(true);
    }

    private void onWindowClosed(WindowEvent e) {
        System.out.println("exiting");
        System.exit(0);
    }

    private void initComponents() {
        //<editor-fold desc="Component Instantiation">
        uiMenuBar = new JMenuBar();
        fileMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        aboutButton = new JMenuItem();
        optionPanel = new JPanel();
        algoLabel = new JLabel();
        algorithmSelector = new JComboBox();
        dataGenLabel = new JLabel();
        dataGeneratorSelector = new JComboBox();
        amtEntriesLabel = new JLabel();
        entrySpinner = new JSpinner();
        barWidthLabel = new JLabel();
        barWidthSpinner = new JSpinner();
        useOpenGLCheckBox = new JCheckBox();
        startButton = new JButton();
        spacer2 = new JPanel(null);
        delayLabel = new JLabel();
        delayMSSpinner = new JSpinner();
        delayInfoLabel = new JLabel();
        applyDelayOnCompCheckBox = new JCheckBox();
        applyDelayOnSwapCheckBox = new JCheckBox();
        spacer1 = new JPanel(null);
        statusInfoLabel = new JLabel();
        statusLabel = new JLabel();
        compInfoLabel = new JLabel();
        compLabel = new JLabel();
        swaapinfoLabel = new JLabel();
        swpLabel = new JLabel();
        elTimeInfoLabel = new JLabel();
        elTimeLabel = new JLabel();
        vSpacer1 = new JPanel(null);
        pauseOnLabel = new JLabel();
        pauseOnNextSwapButton = new JButton();
        pauseOnNextCompButton = new JButton();
        continueButton = new JButton();
        renderPanel = new JPanel();
        fovScrollBar = new JScrollBar();
        //</editor-fold>
        //<editor-fold desc="Basic UI Initialisation">
        setTitle("VisualSort");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                toggleRedraw();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                onWindowClosed(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                onWindowsClosing(e);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                toggleRedraw();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                toggleRedraw();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                onComponentResized(e);
            }

            @Override
            public void componentShown(ComponentEvent e) {
                toggleRedraw();
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        //</editor-fold>
        //<editor-fold desc="MenuBar Initialisation">
        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(e -> exitMenuItemActionPerformed(e));
        fileMenu.add(exitMenuItem);

        uiMenuBar.add(fileMenu);
        helpMenu.setText("?");

        aboutButton.setText("About");
        helpMenu.add(aboutButton);

        uiMenuBar.add(helpMenu);

        setJMenuBar(uiMenuBar);
        //</editor-fold>
        //<editor-fold desc="Initialize the OptionPanel">
        optionPanel.setLayout(new GridBagLayout());
        ((GridBagLayout) optionPanel.getLayout()).columnWidths = new int[]{0, 0, 0, 0};
        ((GridBagLayout) optionPanel.getLayout()).rowHeights =
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout) optionPanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout) optionPanel.getLayout()).rowWeights =
                new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                        0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        //</editor-fold>
        //<editor-fold desc="Algorithm and DataGeneraor Selection Label Initialisation">
        algoLabel.setText("Select Algorithm");
        optionPanel.add(algoLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        optionPanel.add(algorithmSelector, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        dataGenLabel.setText("Data Generator");
        optionPanel.add(dataGenLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        optionPanel.add(dataGeneratorSelector, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize Entry Selection Spinner and Label">
        amtEntriesLabel.setText("Amount of Entries");
        optionPanel.add(amtEntriesLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        entrySpinner.setModel(new SpinnerNumberModel(600, 10, 6000, 1));
        entrySpinner.addChangeListener(e -> entrySpinnerStateChanged(e));
        optionPanel.add(entrySpinner, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize BarWidth Label and Spinner">
        barWidthLabel.setText("Bar width (px)");
        optionPanel.add(barWidthLabel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        barWidthSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
        barWidthSpinner.addChangeListener(e -> barWidthSpinnerStateChanged(e));
        optionPanel.add(barWidthSpinner, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize USe OGL Checkbox">
        useOpenGLCheckBox.setText("Use OpenGL");
        optionPanel.add(useOpenGLCheckBox, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize StartButton">
        startButton.setText("Start Sorting");
        startButton.setPreferredSize(new Dimension(95, 56));
        startButton.setMaximumSize(new Dimension(95, 56));
        startButton.setMinimumSize(new Dimension(95, 56));
        startButton.addActionListener(e -> startButtonActionPerformed(e));
        optionPanel.add(startButton, new GridBagConstraints(1, 5, 2, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        optionPanel.add(spacer2, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize Delay Settings Components">
        delayLabel.setText("Delay");
        optionPanel.add(delayLabel, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        delayMSSpinner.setModel(new SpinnerNumberModel(5, 0, 100, 1));
        delayMSSpinner.addChangeListener(e -> delayMSSpinnerStateChanged(e));
        optionPanel.add(delayMSSpinner, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        delayInfoLabel.setText("Apply delay on..");
        optionPanel.add(delayInfoLabel, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        applyDelayOnCompCheckBox.setText("Comparing");
        applyDelayOnCompCheckBox.addChangeListener(e -> CheckBoxStateChanged(e));
        optionPanel.add(applyDelayOnCompCheckBox, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        applyDelayOnSwapCheckBox.setText("Swaping");
        applyDelayOnSwapCheckBox.setSelected(true);
        applyDelayOnSwapCheckBox.addChangeListener(e -> applyDelayOnSwapCheckBoxStateChanged(e));
        optionPanel.add(applyDelayOnSwapCheckBox, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize Status Labels">
        spacer1.setPreferredSize(new Dimension(10, 40));
        spacer1.setOpaque(false);
        optionPanel.add(spacer1, new GridBagConstraints(1, 10, 1, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        statusInfoLabel.setText("Status");
        optionPanel.add(statusInfoLabel, new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        statusLabel.setText("text");
        statusLabel.setForeground(Color.black);
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        optionPanel.add(statusLabel, new GridBagConstraints(2, 13, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        compInfoLabel.setText("Comparisons");
        optionPanel.add(compInfoLabel, new GridBagConstraints(1, 14, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        compLabel.setText("text");
        compLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        optionPanel.add(compLabel, new GridBagConstraints(2, 14, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        swaapinfoLabel.setText("Swaps");
        optionPanel.add(swaapinfoLabel, new GridBagConstraints(1, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        swpLabel.setText("text");
        swpLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        optionPanel.add(swpLabel, new GridBagConstraints(2, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        elTimeInfoLabel.setText("Elapsed Time");
        optionPanel.add(elTimeInfoLabel, new GridBagConstraints(1, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        elTimeLabel.setText("text");
        elTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        optionPanel.add(elTimeLabel, new GridBagConstraints(2, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize Pause On Labels and Buttons">
        vSpacer1.setPreferredSize(new Dimension(10, 30));
        optionPanel.add(vSpacer1, new GridBagConstraints(1, 17, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        pauseOnLabel.setText("Pause on...");
        optionPanel.add(pauseOnLabel, new GridBagConstraints(1, 18, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        pauseOnNextSwapButton.setText("...next Swap");
        pauseOnNextSwapButton.setEnabled(false);
        pauseOnNextSwapButton.addActionListener(e -> pauseOnNextSwapButtonActionPerformed(e));
        optionPanel.add(pauseOnNextSwapButton, new GridBagConstraints(2, 18, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        pauseOnNextCompButton.setText("...next Compare");
        pauseOnNextCompButton.setEnabled(false);
        pauseOnNextCompButton.addActionListener(e -> pauseOnNextCompButtonActionPerformed(e));
        optionPanel.add(pauseOnNextCompButton, new GridBagConstraints(2, 19, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize Continue Button">
        continueButton.setText("Continue");
        continueButton.setEnabled(false);
        continueButton.addActionListener(e -> continuebuttomnActionPerformed(e));
        optionPanel.add(continueButton, new GridBagConstraints(2, 20, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        contentPane.add(optionPanel, BorderLayout.EAST);
        //</editor-fold>
        //<editor-fold desc="Initialize RenderPanel">
        renderPanel.setLayout(new BorderLayout());

        fovScrollBar.setOrientation(Adjustable.HORIZONTAL);
        fovScrollBar.setEnabled(false);
        fovScrollBar.addAdjustmentListener(e -> fovScrollBarAdjustmentValueChanged(e));
        renderPanel.add(fovScrollBar, BorderLayout.SOUTH);

        contentPane.add(renderPanel, BorderLayout.CENTER);
        //</editor-fold>
        //<editor-fold desc="Finalize Initialisation">
        pack();
        setLocationRelativeTo(getOwner());
        exitMenuItem.addActionListener((e) -> System.exit(0));
        aboutButton.addActionListener((e) -> showAboutDialog());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //</editor-fold>
    }

    private void showAboutDialog() {
        AboutDialog dialog = new AboutDialog();
        dialog.setVisible(true);
    }

    private void updateScrollbar() {
        if (renderCanvas != null && renderer != null) {
            try {
                int entries = sortingHandler.getAmtEntries();
                int maxRenderable = renderer.getMaxRenderable();
                if (maxRenderable >= entries) {
                    fovScrollBar.setEnabled(false);
                    renderer.setRenderPos(0);
                    fovScrollBar.setMaximum(1);
                } else {
                    if (!algorithmSelector.isEnabled()) {
                        fovScrollBar.setEnabled(true);
                        fovScrollBar.setMaximum(entries - maxRenderable + 11);
                        VisualSort.logger.info(maxRenderable);
                    }
                }
            } catch (Exception e) {
                VisualSort.logger.info("Error Updating Scrollbar", e);
                return;
            }
        }
    }

    public void displayStatus(String msg) {
        statusLabel.setText(msg);
        VisualSort.logger.info(msg);
    }

    private void exit() {
        if (renderer != null) {
            renderer.stop();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }

    public void enableAlgorithmSelection(boolean b) {
        JComponent[] c = new JComponent[]{algorithmSelector, dataGeneratorSelector, entrySpinner,
                barWidthSpinner};
        this.setStateOfJComponents(c, b);
    }

    public void enableStopButtons(boolean b) {
        JComponent[] c = new JComponent[]{pauseOnNextCompButton, pauseOnNextSwapButton};
        setStateOfJComponents(c, b);
    }

    private void setStateOfJComponents(JComponent[] comp, boolean b) {
        for (int i = 0; i < comp.length; i++) {
            comp[i].setEnabled(b);
        }
    }

    public void updateStatusLabels(long comp, long swap, String time) {
        compLabel.setText("" + comp);
        swpLabel.setText("" + swap);
        if (time != null) {
            elTimeLabel.setText(time);
        }
    }

    public void slickError(Exception e) {
        VisualSort.logger.error("Error initializing Slick2D", e);
        allowResize = true;
        this.setResizable(true);
        this.setMinimumSize(new Dimension(600, 600));
        this.setMaximumSize(null);
        useOpenGLCheckBox.setSelected(false);
        JOptionPane.showMessageDialog(this, "Could not use OpenGL for Rendering, forcing J2D\nPlease retry.");
    }

    public void forceJavaDRendering() {
        useOpenGLCheckBox.setSelected(false);
        useOpenGLCheckBox.setEnabled(false);
    }

    public JButton getStartButton() {
        return startButton;
    }

    //<editor-fold desc="GUI Component Attribute Definition">
    private JMenuBar uiMenuBar;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutButton;
    private JPanel optionPanel;
    private JLabel algoLabel;
    private JComboBox algorithmSelector;
    private JLabel dataGenLabel;
    private JComboBox dataGeneratorSelector;
    private JLabel amtEntriesLabel;
    private JSpinner entrySpinner;
    private JLabel barWidthLabel;
    private JSpinner barWidthSpinner;
    private JCheckBox useOpenGLCheckBox;
    private JButton startButton;
    private JPanel spacer2;
    private JLabel delayLabel;
    private JSpinner delayMSSpinner;
    private JLabel delayInfoLabel;
    private JCheckBox applyDelayOnCompCheckBox;
    private JCheckBox applyDelayOnSwapCheckBox;
    private JPanel spacer1;
    private JLabel statusInfoLabel;
    private JLabel statusLabel;
    private JLabel compInfoLabel;
    private JLabel compLabel;
    private JLabel swaapinfoLabel;
    private JLabel swpLabel;
    private JLabel elTimeInfoLabel;
    private JLabel elTimeLabel;
    private JPanel vSpacer1;
    private JLabel pauseOnLabel;
    private JButton pauseOnNextSwapButton;
    private JButton pauseOnNextCompButton;
    private JButton continueButton;
    private JPanel renderPanel;
    private JScrollBar fovScrollBar;
    //</editor-fold>
}
