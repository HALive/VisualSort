/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui;

import halive.visualsort.VisualSort;
import halive.visualsort.core.util.Configuration;
import halive.visualsort.core.interfaces.IVisualSortUI;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.util.VSLog;
import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.plugins.PluginHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;
import halive.visualsort.gui.rendering.IVisualSortRenderer;
import halive.visualsort.gui.rendering.j2d.SortingRenderCanvas;
import halive.visualsort.gui.rendering.slick2d.OpenGLRenderCanvas;
import halive.visualsort.gui.tree.NamableTreeModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
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
import java.util.logging.Level;

/**
 * This Class Describes the UI of VisualSort
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class VisualSortUI extends JFrame implements IVisualSortUI {

    /**
     * The RenderCanvas Component
     */
    private Canvas renderCanvas;
    /**
     * Stores the current Renderr used to render the Sorting Progress
     */
    private IVisualSortRenderer renderer;
    /**
     * The Sorting Handler used to Perform Sorting Operations
     */
    private SortingHandler sortingHandler;

    /**
     * Is true if Resizing is allowed, false otherwise
     */
    private boolean allowResize = true;
    //<editor-fold desc="GUI Component Attribute Definition">
    private JMenuBar uiMenuBar;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutButton;
    private JPanel optionPanel;
    private JTree algorithmSelector;
    private JTree dataGeneratorSelector;
    private JSpinner entrySpinner;
    private JSpinner barWidthSpinner;
    private JCheckBox useOpenGLCheckBox;
    private JCheckBox saveVisualisationCheckbox;
    private JButton startButton;
    private JSpinner delayMSSpinner;
    private JCheckBox applyDelayOnCompCheckBox;
    private JCheckBox applyDelayOnSwapCheckBox;
    private JLabel statusLabel;
    private JLabel compLabel;
    private JLabel swpLabel;
    private JLabel elTimeLabel;
    private JButton pauseOnNextSwapButton;
    private JButton pauseOnNextCompButton;
    private JButton continueButton;
    private JPanel renderPanel;
    private JScrollBar fovScrollBar;
    //</editor-fold>

    /**
     * Creates A New VisualSortUI
     *
     * @param handler the PluginHandler Storing All Plugins with their DataGenerators and SortingALgortihms
     */
    public VisualSortUI(PluginHandler handler, Configuration config) {
        sortingHandler = new SortingHandler(this);
        initComponents();
        algorithmSelector.setModel(new NamableTreeModel(handler, handler::getSortingAlgorithms));
        dataGeneratorSelector.setModel(new NamableTreeModel(handler, handler::getDataGenerators));
        JTree[] trees = {algorithmSelector, dataGeneratorSelector};
        for (JTree tree : trees) {
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        }
        Dimension defSize = new Dimension(700, 700);
        this.setSize(defSize);
        this.setMinimumSize(defSize);
        this.saveVisualisationCheckbox.setEnabled(config.isAllowVisualisationExport());
    }

    /**
     * Called when the Window is Closing.
     * This Stopps the Renderer
     *
     * @param e not used
     */
    private void onWindowsClosing(WindowEvent e) {
        exit();
    }

    /**
     * Called once the Exit (in the File Menu) Button is Clicked
     *
     * @param e not used
     */
    private void exitMenuItemActionPerformed(ActionEvent e) {
        exit();
    }

    /**
     * Updates The Width of the Value Bars
     *
     * @param e not used
     */
    private void barWidthSpinnerStateChanged(ChangeEvent e) {
        int newVal = (Integer) barWidthSpinner.getValue();
        int amtVal = (Integer) entrySpinner.getValue();
        if ((VisualSort.MAX_ENTRIES / newVal) < amtVal) {
            amtVal = VisualSort.MAX_ENTRIES / newVal;
        }
        entrySpinner.setModel(new SpinnerNumberModel(amtVal, 10, VisualSort.MAX_ENTRIES / newVal, 1));
        sortingHandler.setRenderWidth(newVal);
    }

    /**
     * Initiates the Sorting Process. It first Grabs the Selected SortingAlgorithm and DataGenerator
     * and all Other Properties, After That A Renderer (Depending on the Selection) gets Initialized
     * if that has not happened before. Afterwards the Current dataGenerator and SortingAlgorithms
     * are Set. At last The Sorting Thread gets Started and the Sorting will Begin.
     *
     * @param e not used
     */
    private void startButtonActionPerformed(ActionEvent e) {
        //<editor-fold desc="Get The Currently Selected Datagen and Algorithm">
        if (algorithmSelector.getSelectionPath() == null || dataGeneratorSelector.getSelectionPath() == null) {
            JOptionPane.showMessageDialog(this, "Invalid Algorithm/DataGenerator. Please Select a Proper one.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultMutableTreeNode algorithm =
                (DefaultMutableTreeNode) algorithmSelector.getSelectionPath().getLastPathComponent();
        DefaultMutableTreeNode dataGen =
                (DefaultMutableTreeNode) dataGeneratorSelector.getSelectionPath().getLastPathComponent();
        if (algorithm == null || dataGen == null ||
                !(algorithm.getUserObject() instanceof SortingAlgorithm) ||
                !(dataGen.getUserObject() instanceof DataGenerator)) {
            JOptionPane.showMessageDialog(this, "Invalid Algorithm/DataGenerator. Please Select a Proper one.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //</editor-fold>
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
        sortingHandler.setSortingAlgorithm((SortingAlgorithm) algorithm.getUserObject());
        sortingHandler.setDataGenerator((DataGenerator) dataGen.getUserObject());
        boolean delayOnSwap = applyDelayOnSwapCheckBox.isSelected();
        boolean delayOnComp = applyDelayOnCompCheckBox.isSelected();
        sortingHandler.setDelayOnComp(delayOnComp);
        sortingHandler.setDelayOnSwap(delayOnSwap);
        sortingHandler.init();
        //</editor-fold>
    }

    /**
     * Gets Called if the Window gets Resized.
     * It Updates the Length of the Scrollbar and Toggles a Redraw.
     *
     * @param e not used
     */
    private void onComponentResized(ComponentEvent e) {
        if (allowResize) {
            updateScrollbar();
            toggleRedraw();
        }
    }

    /**
     * Informs the SoringHandler about a Change of the amout of Values to sort
     *
     * @param e not Used
     */
    private void entrySpinnerStateChanged(ChangeEvent e) {
        int val = (Integer) entrySpinner.getValue();
        sortingHandler.setAmtEntries(val);
    }

    /**
     * Gets Called every time the DelaySpinners Value gets Changed
     * It Tells the SortingHandler the new Delay
     *
     * @param e not Used
     */
    private void delayMSSpinnerStateChanged(ChangeEvent e) {
        int val = (Integer) delayMSSpinner.getValue();
        sortingHandler.setDelay(val);
    }

    /**
     * This is Called if the Scrollbar ScrollItem Gets moved.
     * Informs the Renderer about the new Render position
     *
     * @param e the Event to get the Current ScrollValue
     */
    private void fovScrollBarAdjustmentValueChanged(AdjustmentEvent e) {
        if (renderer != null) {
            int value = e.getValue();
            renderer.setRenderPos(value);
        }
    }

    /**
     * Just like applyDelayOnNextSwap... for the Swap Checkbox
     *
     * @param e the ChangeEvent
     */
    private void applyDelayOnCompCheckBoxStateChanged(ChangeEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        sortingHandler.setDelayOnComp(checkBox.isSelected());
    }

    /**
     * Called if the Apply Delay on next Swap state gets changed.
     * it also informs the SoringHandler about the change
     *
     * @param e the Change Event used to Capture the source
     */
    private void applyDelayOnSwapCheckBoxStateChanged(ChangeEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        sortingHandler.setDelayOnSwap(checkBox.isSelected());
    }

    /**
     * Calls the repaint() method
     */
    private void toggleRedraw() {
        this.repaint();
    }

    /**
     * Just like pauseOnNextCompButtonActionPerformed except for Stopping at the next Swap
     *
     * @param e not used
     */
    private void pauseOnNextSwapButtonActionPerformed(ActionEvent e) {
        this.enableStopButtons(false);
        sortingHandler.setStopOnNextSwap(true);
        continueButton.setEnabled(true);
    }

    /**
     * Gets Called one The User Clicks on Pause on Next Comparison.
     * It Tells the Sorting Handler to Pause on the Next Comparison
     *
     * @param e not used
     */
    private void pauseOnNextCompButtonActionPerformed(ActionEvent e) {
        this.enableStopButtons(false);
        sortingHandler.setStopOnNextComp(true);
        continueButton.setEnabled(true);
    }

    /**
     * Gets Called when the User Clicks on the Continue Button.
     * It Tells the Current SortingHandler to Contiune Sorting
     *
     * @param e not used
     */
    private void continueButtonActionPerformed(ActionEvent e) {
        continueButton.setEnabled(false);
        sortingHandler.clearPause();
        enableStopButtons(true);
    }

    /**
     * Is Called if the Window Gets closed. It Closes the Application
     *
     * @param e Not Used
     */
    private void onWindowClosed(WindowEvent e) {
        System.out.println("exiting");
        System.exit(0);
    }

    /**
     * Initializes all UI Components
     */
    private void initComponents() {
        //<editor-fold desc="Component Instantiation">
        uiMenuBar = new JMenuBar();
        fileMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        aboutButton = new JMenuItem();
        optionPanel = new JPanel();
        JLabel algoLabel = new JLabel();
        algorithmSelector = new JTree();
        JLabel dataGenLabel = new JLabel();
        dataGeneratorSelector = new JTree();
        JLabel amtEntriesLabel = new JLabel();
        entrySpinner = new JSpinner();
        JLabel barWidthLabel = new JLabel();
        barWidthSpinner = new JSpinner();
        useOpenGLCheckBox = new JCheckBox();
        saveVisualisationCheckbox = new JCheckBox();
        startButton = new JButton();
        JPanel spacer2 = new JPanel(null);
        JLabel delayLabel = new JLabel();
        delayMSSpinner = new JSpinner();
        JLabel delayInfoLabel = new JLabel();
        applyDelayOnCompCheckBox = new JCheckBox();
        applyDelayOnSwapCheckBox = new JCheckBox();
        JPanel spacer1 = new JPanel(null);
        JLabel statusInfoLabel = new JLabel();
        statusLabel = new JLabel();
        JLabel compInfoLabel = new JLabel();
        compLabel = new JLabel();
        JLabel swaapinfoLabel = new JLabel();
        swpLabel = new JLabel();
        JLabel elTimeInfoLabel = new JLabel();
        elTimeLabel = new JLabel();
        JPanel vSpacer1 = new JPanel(null);
        JLabel pauseOnLabel = new JLabel();
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
        exitMenuItem.addActionListener(this::exitMenuItemActionPerformed);
        fileMenu.add(exitMenuItem);

        uiMenuBar.add(fileMenu);
        helpMenu.setText("?");

        aboutButton.setText("About");
        helpMenu.add(aboutButton);

        uiMenuBar.add(helpMenu);

        setJMenuBar(uiMenuBar);
        //</editor-fold>

        //<editor-fold desc="Initialize the OptionPanel">
        optionPanel.setPreferredSize(new Dimension(330, 600));
        optionPanel.setMinimumSize(new Dimension(330, 600));
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

        JScrollPane scrollPane = new JScrollPane(algorithmSelector);
        scrollPane.setPreferredSize(new Dimension(220, 100));
        scrollPane.setMinimumSize(new Dimension(220, 100));
        optionPanel.add(scrollPane, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        dataGenLabel.setText("Data Generator");
        optionPanel.add(dataGenLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        JScrollPane scrollPane2 = new JScrollPane(dataGeneratorSelector);
        scrollPane2.setPreferredSize(new Dimension(220, 100));
        scrollPane2.setMinimumSize(new Dimension(220, 100));
        optionPanel.add(scrollPane2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>

        //<editor-fold desc="Initialize Entry Selection Spinner and Label">
        amtEntriesLabel.setText("Amount of Entries");
        optionPanel.add(amtEntriesLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        entrySpinner.setModel(new SpinnerNumberModel(600, 10, 6000, 1));
        entrySpinner.addChangeListener(this::entrySpinnerStateChanged);
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
        barWidthSpinner.addChangeListener(this::barWidthSpinnerStateChanged);
        optionPanel.add(barWidthSpinner, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>

        //<editor-fold desc="Initialize USe OGL Checkbox">
        useOpenGLCheckBox.setText("Use OpenGL");
        optionPanel.add(useOpenGLCheckBox, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>

        //<editor-fold desc="Initialize the save Visualisation Checkbox">
        saveVisualisationCheckbox.setText("Save Visualisation");
        optionPanel.add(saveVisualisationCheckbox, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>

        //<editor-fold desc="Initialize StartButton">
        startButton.setText("Start Sorting");
        startButton.setPreferredSize(new Dimension(95, 56));
        startButton.setMaximumSize(new Dimension(95, 56));
        startButton.setMinimumSize(new Dimension(95, 56));
        startButton.addActionListener(this::startButtonActionPerformed);
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
        delayMSSpinner.addChangeListener(this::delayMSSpinnerStateChanged);
        optionPanel.add(delayMSSpinner, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        delayInfoLabel.setText("Apply delay on..");
        optionPanel.add(delayInfoLabel, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        applyDelayOnCompCheckBox.setText("Comparing");
        applyDelayOnCompCheckBox.addChangeListener(this::applyDelayOnCompCheckBoxStateChanged);
        optionPanel.add(applyDelayOnCompCheckBox, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        applyDelayOnSwapCheckBox.setText("Swaping");
        applyDelayOnSwapCheckBox.setSelected(true);
        applyDelayOnSwapCheckBox.addChangeListener(this::applyDelayOnSwapCheckBoxStateChanged);
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
        pauseOnNextSwapButton.addActionListener(this::pauseOnNextSwapButtonActionPerformed);
        optionPanel.add(pauseOnNextSwapButton, new GridBagConstraints(2, 18, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        pauseOnNextCompButton.setText("...next Compare");
        pauseOnNextCompButton.setEnabled(false);
        pauseOnNextCompButton.addActionListener(this::pauseOnNextCompButtonActionPerformed);
        optionPanel.add(pauseOnNextCompButton, new GridBagConstraints(2, 19, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        //</editor-fold>
        //<editor-fold desc="Initialize Continue Button">
        continueButton.setText("Continue");
        continueButton.setEnabled(false);
        continueButton.addActionListener(this::continueButtonActionPerformed);
        optionPanel.add(continueButton, new GridBagConstraints(2, 20, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        contentPane.add(optionPanel, BorderLayout.EAST);
        //</editor-fold>
        //<editor-fold desc="Initialize RenderPanel">
        renderPanel.setLayout(new BorderLayout());

        fovScrollBar.setOrientation(Adjustable.HORIZONTAL);
        fovScrollBar.setEnabled(false);
        fovScrollBar.addAdjustmentListener(this::fovScrollBarAdjustmentValueChanged);
        renderPanel.add(fovScrollBar, BorderLayout.SOUTH);

        contentPane.add(renderPanel, BorderLayout.CENTER);
        //</editor-fold>
        //<editor-fold desc="Finalize Initialisation">
        pack();
        setLocationRelativeTo(getOwner());
        exitMenuItem.addActionListener((e) -> System.exit(0));
        aboutButton.addActionListener((e) -> showAboutDialog());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //</editor-fold>
    }

    /**
     * This Method Opens the About Dialog
     */
    private void showAboutDialog() {
        AboutDialog dialog = new AboutDialog();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * This Method Updated the State of the Scrollbar
     */
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
                        VSLog.logger.info(maxRenderable + "");
                    }
                }
            } catch (Exception e) {
                VSLog.logger.log(Level.SEVERE, "Error Updating Scrollbar", e);
            }
        }
    }

    /**
     * Displays the given Message on the Status Label and Logs it to the Console Using the VSLog Class
     *
     * @param msg a TextString to Display as Status
     */
    public void displayStatus(String msg) {
        statusLabel.setText(msg);
        VSLog.logger.info(msg);
    }

    /**
     * Exits The Programm
     */
    private void exit() {
        if (renderer != null) {
            renderer.stop();
            //noinspection EmptyCatchBlock
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Calls the SetEnabled Method of All Components Relevant for Algorithm Selection
     *
     * @param b the State to Set to
     */
    public void enableAlgorithmSelection(boolean b) {
        JComponent[] c = new JComponent[]{algorithmSelector, dataGeneratorSelector, entrySpinner,
                barWidthSpinner};
        this.setStateOfJComponents(c, b);
    }

    /**
     * This Method Calls the SetEnabled Method of the Pause on Nex Comparions/next Swap Buttons
     *
     * @param b the State to set To
     */
    public void enableStopButtons(boolean b) {
        JComponent[] c = new JComponent[]{pauseOnNextCompButton, pauseOnNextSwapButton};
        setStateOfJComponents(c, b);
    }

    /**
     * Calls the SetEnabled Method of every Component in the Array
     *
     * @param comp the Component Array
     * @param b    the State
     */
    private void setStateOfJComponents(JComponent[] comp, boolean b) {
        for (JComponent aComp : comp) {
            aComp.setEnabled(b);
        }
    }

    /**
     * Updates the Comparison, Swap Count and Elapsed Time Labels
     *
     * @param comp the Current count of Comparisons
     * @param swap The current count of Swaps
     * @param time the current elapsed time
     */
    public void updateStatusLabels(long comp, long swap, String time) {
        compLabel.setText("" + comp);
        swpLabel.setText("" + swap);
        if (time != null) {
            elTimeLabel.setText(time);
        }
    }

    /**
     * If the OpenGL Ui could not get initilaized this Method will Force J2f Rendering
     *
     * @param e The Exception that Caused the Error
     */
    public void slickError(Exception e) {
        VSLog.logger.log(Level.SEVERE, "Error initializing Slick2D", e);
        allowResize = true;
        this.setResizable(true);
        this.setMinimumSize(new Dimension(600, 600));
        this.setMaximumSize(null);
        useOpenGLCheckBox.setSelected(false);
        JOptionPane.showMessageDialog(this, "Could not use OpenGL for Rendering, forcing J2D\nPlease retry.");
    }

    /**
     * This disables the selection of OpenGL Rendering
     */
    public void forceJavaDRendering() {
        useOpenGLCheckBox.setSelected(false);
        useOpenGLCheckBox.setEnabled(false);
    }

    /**
     * Returns the Start Button that initiates the Sorting when its clicked
     *
     * @return the StartButton
     */
    public JButton getStartButton() {
        return startButton;
    }
}
