/*
 * Created by JFormDesigner on Fri Dec 27 11:04:27 GMT 2019
 */

package gui;

import dataManager.DataProcessor;
import dataManager.ExcelManager;
import methodsLibrary.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Shupeng Hu
 */
public class MethodsGUI extends JFrame {
    private OfflineModeGUI offlineModeGUI;
    private JFileChooser fileChooser;
    private String filePath;
    private List<double[]> refSpectrum;

    public MethodsGUI(OfflineModeGUI offlineModeGUI) {
        this.offlineModeGUI=offlineModeGUI;
        initComponents();
        contentPanel.remove(refSpectrumButton);
        contentPanel.remove(wlLabel);
        contentPanel.remove(pfoLabel);
        contentPanel.remove(doLabel);
        contentPanel.remove(wlField);
        contentPanel.remove(pfoField);
        contentPanel.remove(docomboBox);

        contentPanel.remove(compnenttextField);
        contentPanel.remove(componentlabel);
        contentPanel.remove(initialElabel);
        contentPanel.remove(initialEtextField);
        contentPanel.remove(initialTlabel);
        contentPanel.remove(initialTtextField);
        contentPanel.remove(coollabel);
        contentPanel.remove(cooltextField);
        contentPanel.remove(stopTlabel);
        contentPanel.remove(stopTtextField);

        repaint();
    }

    private void refSpectrumButtonMouseClicked(MouseEvent e) {
        //select document
        fileChooser=new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("Excel Document(.xlsx)", "xlsx");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle("Please Select Document");
        int a =fileChooser.showDialog(null, "Open");
        //get the path of the selected document
        if(a==JFileChooser.APPROVE_OPTION){
            filePath=fileChooser.getSelectedFile().getPath();
        }
    }

    private void confirmButtonMouseClicked(MouseEvent e) throws Exception {
        //pre-process
        switch (preprocesscomboBox.getSelectedItem().toString()){
            case "SNV":
                SNV_JAVA snv_java=new SNV_JAVA();
                offlineModeGUI.setPreProcessMethod("SNV",snv_java);
                break;
            case "MSC":
                MSC_JAVA msc_java=new MSC_JAVA();
                refSpectrum= ExcelManager.readExcel(filePath);
                msc_java.setRefSpectrum(DataProcessor.listToDoubleArray1(refSpectrum));
                offlineModeGUI.setPreProcessMethod("MSC",msc_java);
                break;
            case "SG":
                SG_JAVA sg_java=new SG_JAVA();
                sg_java.setParameter(Integer.parseInt(wlField.getText()),Integer.parseInt(pfoField.getText()), (Integer) docomboBox.getSelectedItem());
                offlineModeGUI.setPreProcessMethod("SG",sg_java);
                break;
            case "None":
                offlineModeGUI.setPreProcessMethod("None",null);
                break;
        }
        //data partition
        switch(dataPartitioncomboBox.getSelectedItem().toString()){
            case "KS":
                KS_JAVA ks_java=new KS_JAVA();
                ks_java.setParameters(Integer.parseInt(sampleForTsetField.getText()));
                offlineModeGUI.setDataPartitionMethod("KS",ks_java);
                break;
            case "SPXY":
                SPXY_JAVA spxy_java=new SPXY_JAVA();
                spxy_java.setParameters(Integer.parseInt(sampleForTsetField.getText()));
                offlineModeGUI.setDataPartitionMethod("SPXY",spxy_java);
                break;
        }
        //variable selection
        switch (variableSelectioncomboBox.getSelectedItem().toString()){
            case "None":
                offlineModeGUI.setVariableSelectionMethod("None",null);
                break;
            case "UVE":
                UVE_JAVA uve_java=new UVE_JAVA();
                uve_java.setParameters(Integer.parseInt(compnenttextField.getText()));
                offlineModeGUI.setVariableSelectionMethod("UVE",uve_java);
                break;
            case "SA":
                SA_JAVA sa_java=new SA_JAVA();
                sa_java.setParameters(Integer.parseInt(compnenttextField.getText()),Double.parseDouble(initialEtextField.getText()),Double.parseDouble(cooltextField.getText()),Double.parseDouble(initialTtextField.getText()),Double.parseDouble(stopTtextField.getText()));
                offlineModeGUI.setVariableSelectionMethod("SA",sa_java);
                break;
            case "SPA":
                SPA_JAVA spa_java=new SPA_JAVA();
                offlineModeGUI.setVariableSelectionMethod("SPA",spa_java);
                break;

        }
        //calibration
        PLSR_JAVA plsr_java=new PLSR_JAVA();
        plsr_java.setParameters(Integer.parseInt(pcField.getText()));
        offlineModeGUI.setCalibrationMethod("PLSR",plsr_java);

        this.setVisible(false);
    }

    private void preprocesscomboBoxItemStateChanged(ItemEvent e) {
        switch (preprocesscomboBox.getSelectedItem().toString()) {
            case "SNV":
            case "None":
                contentPanel.remove(refSpectrumButton);
                contentPanel.remove(wlLabel);
                contentPanel.remove(pfoLabel);
                contentPanel.remove(doLabel);
                contentPanel.remove(wlField);
                contentPanel.remove(pfoField);
                contentPanel.remove(docomboBox);
                repaint();
                break;
            case "MSC":
                contentPanel.add(refSpectrumButton);
                contentPanel.remove(wlLabel);
                contentPanel.remove(pfoLabel);
                contentPanel.remove(doLabel);
                contentPanel.remove(wlField);
                contentPanel.remove(pfoField);
                contentPanel.remove(docomboBox);
                repaint();
                break;
            case "SG":
                contentPanel.remove(refSpectrumButton);
                contentPanel.add(wlLabel);
                contentPanel.add(pfoLabel);
                contentPanel.add(doLabel);
                contentPanel.add(wlField);
                contentPanel.add(pfoField);
                contentPanel.add(docomboBox);
                repaint();
                break;
        }
    }

    private void variableSelectioncomboBoxItemStateChanged(ItemEvent e) {
        switch (variableSelectioncomboBox.getSelectedItem().toString()){
            case "None":
            case "SPA":
                contentPanel.remove(compnenttextField);
                contentPanel.remove(componentlabel);
                contentPanel.remove(initialElabel);
                contentPanel.remove(initialEtextField);
                contentPanel.remove(initialTlabel);
                contentPanel.remove(initialTtextField);
                contentPanel.remove(coollabel);
                contentPanel.remove(cooltextField);
                contentPanel.remove(stopTlabel);
                contentPanel.remove(stopTtextField);
                repaint();
                break;
            case "SA":
                contentPanel.add(compnenttextField);
                contentPanel.add(componentlabel);
                contentPanel.add(initialElabel);
                contentPanel.add(initialEtextField);
                contentPanel.add(initialTlabel);
                contentPanel.add(initialTtextField);
                contentPanel.add(coollabel);
                contentPanel.add(cooltextField);
                contentPanel.add(stopTlabel);
                contentPanel.add(stopTtextField);
                repaint();
                break;
            case "UVE":
                contentPanel.add(compnenttextField);
                contentPanel.add(componentlabel);
                contentPanel.remove(initialElabel);
                contentPanel.remove(initialEtextField);
                contentPanel.remove(initialTlabel);
                contentPanel.remove(initialTtextField);
                contentPanel.remove(coollabel);
                contentPanel.remove(cooltextField);
                contentPanel.remove(stopTlabel);
                contentPanel.remove(stopTtextField);
                repaint();
                break;
        }
    }

    private void comboBoxItemStateChanged(ItemEvent e) { }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        preprocesscomboBox = new JComboBox<>();
        wlLabel = new JLabel();
        pfoLabel = new JLabel();
        doLabel = new JLabel();
        refSpectrumButton = new JButton();
        wlField = new JTextField();
        pfoField = new JTextField();
        docomboBox = new JComboBox<>();
        confirmButton = new JButton();
        label2 = new JLabel();
        dataPartitioncomboBox = new JComboBox<>();
        label3 = new JLabel();
        sampleForTsetField = new JTextField();
        label4 = new JLabel();
        variableSelectioncomboBox = new JComboBox<>();
        componentlabel = new JLabel();
        compnenttextField = new JTextField();
        initialElabel = new JLabel();
        coollabel = new JLabel();
        initialTlabel = new JLabel();
        stopTlabel = new JLabel();
        initialTtextField = new JTextField();
        initialEtextField = new JTextField();
        cooltextField = new JTextField();
        stopTtextField = new JTextField();
        label5 = new JLabel();
        label6 = new JLabel();
        pcField = new JTextField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("Pre-process :");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 5f));
                contentPanel.add(label1);
                label1.setBounds(10, 10, 115, 65);

                //---- preprocesscomboBox ----
                preprocesscomboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "SNV",
                    "MSC",
                    "SG",
                    "None"
                }));
                preprocesscomboBox.setFont(preprocesscomboBox.getFont().deriveFont(preprocesscomboBox.getFont().getSize() + 2f));
                preprocesscomboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        comboBoxItemStateChanged(e);
                        preprocesscomboBoxItemStateChanged(e);
                    }
                });
                contentPanel.add(preprocesscomboBox);
                preprocesscomboBox.setBounds(125, 20, 65, 40);

                //---- wlLabel ----
                wlLabel.setText("Window Length");
                wlLabel.setFont(wlLabel.getFont().deriveFont(wlLabel.getFont().getSize() + 3f));
                contentPanel.add(wlLabel);
                wlLabel.setBounds(210, 10, 110, 30);

                //---- pfoLabel ----
                pfoLabel.setText("Polynomial Fit Order");
                pfoLabel.setFont(pfoLabel.getFont().deriveFont(pfoLabel.getFont().getSize() + 3f));
                contentPanel.add(pfoLabel);
                pfoLabel.setBounds(410, 15, 155, 25);

                //---- doLabel ----
                doLabel.setText("Derivative Order");
                doLabel.setFont(doLabel.getFont().deriveFont(doLabel.getFont().getSize() + 3f));
                contentPanel.add(doLabel);
                doLabel.setBounds(420, 60, 130, 30);

                //---- refSpectrumButton ----
                refSpectrumButton.setText("Import Ref. Spectrum");
                refSpectrumButton.setFont(refSpectrumButton.getFont().deriveFont(refSpectrumButton.getFont().getSize() + 3f));
                refSpectrumButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        refSpectrumButtonMouseClicked(e);
                    }
                });
                contentPanel.add(refSpectrumButton);
                refSpectrumButton.setBounds(200, 55, 185, 50);
                contentPanel.add(wlField);
                wlField.setBounds(330, 10, 69, 29);
                contentPanel.add(pfoField);
                pfoField.setBounds(565, 15, 69, 29);

                //---- docomboBox ----
                docomboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "0",
                    "1",
                    "2"
                }));
                contentPanel.add(docomboBox);
                docomboBox.setBounds(565, 65, 70, 24);

                //---- confirmButton ----
                confirmButton.setText("Confirm");
                confirmButton.setFont(confirmButton.getFont().deriveFont(confirmButton.getFont().getSize() + 5f));
                confirmButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            confirmButtonMouseClicked(e);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                contentPanel.add(confirmButton);
                confirmButton.setBounds(525, 500, 115, 50);

                //---- label2 ----
                label2.setText("Data Partition :");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 5f));
                contentPanel.add(label2);
                label2.setBounds(5, 140, 135, 65);

                //---- dataPartitioncomboBox ----
                dataPartitioncomboBox.setFont(dataPartitioncomboBox.getFont().deriveFont(dataPartitioncomboBox.getFont().getSize() + 2f));
                dataPartitioncomboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "KS",
                    "SPXY"
                }));
                contentPanel.add(dataPartitioncomboBox);
                dataPartitioncomboBox.setBounds(145, 150, 65, 40);

                //---- label3 ----
                label3.setText("No. of Samples for Trainning Set");
                label3.setFont(label3.getFont().deriveFont(label3.getFont().getSize() + 3f));
                contentPanel.add(label3);
                label3.setBounds(235, 130, 250, 70);
                contentPanel.add(sampleForTsetField);
                sampleForTsetField.setBounds(490, 150, 85, 35);

                //---- label4 ----
                label4.setText("Variable Selection :");
                label4.setFont(label4.getFont().deriveFont(label4.getFont().getSize() + 5f));
                contentPanel.add(label4);
                label4.setBounds(5, 225, 160, 65);

                //---- variableSelectioncomboBox ----
                variableSelectioncomboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "None",
                    "SA",
                    "GA",
                    "SPA",
                    "iPLS",
                    "FiPLS",
                    "BiPLS",
                    "UVE"
                }));
                variableSelectioncomboBox.setFont(variableSelectioncomboBox.getFont().deriveFont(variableSelectioncomboBox.getFont().getSize() + 2f));
                variableSelectioncomboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        comboBoxItemStateChanged(e);
                        variableSelectioncomboBoxItemStateChanged(e);
                    }
                });
                contentPanel.add(variableSelectioncomboBox);
                variableSelectioncomboBox.setBounds(165, 240, 65, 40);

                //---- componentlabel ----
                componentlabel.setText("ComponentNo");
                componentlabel.setFont(componentlabel.getFont().deriveFont(componentlabel.getFont().getSize() + 2f));
                contentPanel.add(componentlabel);
                componentlabel.setBounds(240, 230, 105, 55);
                contentPanel.add(compnenttextField);
                compnenttextField.setBounds(340, 240, 80, 30);

                //---- initialElabel ----
                initialElabel.setText("Initial Energy");
                initialElabel.setFont(initialElabel.getFont().deriveFont(initialElabel.getFont().getSize() + 2f));
                contentPanel.add(initialElabel);
                initialElabel.setBounds(10, 290, 100, 45);

                //---- coollabel ----
                coollabel.setText("Cooling Ratio");
                coollabel.setFont(coollabel.getFont().deriveFont(coollabel.getFont().getSize() + 2f));
                contentPanel.add(coollabel);
                coollabel.setBounds(225, 295, 105, 35);

                //---- initialTlabel ----
                initialTlabel.setText("Initial T");
                initialTlabel.setFont(initialTlabel.getFont().deriveFont(initialTlabel.getFont().getSize() + 2f));
                contentPanel.add(initialTlabel);
                initialTlabel.setBounds(470, 240, 65, 30);

                //---- stopTlabel ----
                stopTlabel.setText("Stop T");
                stopTlabel.setFont(stopTlabel.getFont().deriveFont(stopTlabel.getFont().getSize() + 2f));
                contentPanel.add(stopTlabel);
                stopTlabel.setBounds(460, 295, 55, 32);
                contentPanel.add(initialTtextField);
                initialTtextField.setBounds(535, 240, 80, 30);
                contentPanel.add(initialEtextField);
                initialEtextField.setBounds(105, 295, 80, 30);
                contentPanel.add(cooltextField);
                cooltextField.setBounds(320, 300, 80, 30);
                contentPanel.add(stopTtextField);
                stopTtextField.setBounds(515, 300, 80, 30);

                //---- label5 ----
                label5.setText("Calibration :");
                label5.setFont(label5.getFont().deriveFont(label5.getFont().getSize() + 5f));
                contentPanel.add(label5);
                label5.setBounds(5, 490, 110, 65);

                //---- label6 ----
                label6.setText("PCNo.");
                label6.setFont(label6.getFont().deriveFont(label6.getFont().getSize() + 5f));
                contentPanel.add(label6);
                label6.setBounds(115, 500, 70, 40);
                contentPanel.add(pcField);
                pcField.setBounds(180, 505, 79, 34);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.WEST);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JComboBox<String> preprocesscomboBox;
    private JLabel wlLabel;
    private JLabel pfoLabel;
    private JLabel doLabel;
    private JButton refSpectrumButton;
    private JTextField wlField;
    private JTextField pfoField;
    private JComboBox<String> docomboBox;
    private JButton confirmButton;
    private JLabel label2;
    private JComboBox<String> dataPartitioncomboBox;
    private JLabel label3;
    private JTextField sampleForTsetField;
    private JLabel label4;
    private JComboBox<String> variableSelectioncomboBox;
    private JLabel componentlabel;
    private JTextField compnenttextField;
    private JLabel initialElabel;
    private JLabel coollabel;
    private JLabel initialTlabel;
    private JLabel stopTlabel;
    private JTextField initialTtextField;
    private JTextField initialEtextField;
    private JTextField cooltextField;
    private JTextField stopTtextField;
    private JLabel label5;
    private JLabel label6;
    private JTextField pcField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) throws IOException {
        OfflineModeGUI offlineModeGUI= new OfflineModeGUI();
        MethodsGUI methodsGUI=new MethodsGUI(offlineModeGUI);
        methodsGUI.setVisible(true);
    }
}
