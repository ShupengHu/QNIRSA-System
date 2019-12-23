/*
 * Created by JFormDesigner on Mon Dec 16 22:08:29 GMT 2019
 */

package gui;

import spectrometers.AOTF;
import spectrometers.MPA;
import spectrometers.NIRQuest;
import spectrometers.OmniDriver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Shupeng Hu
 */
public class SpectrometerGUI extends JFrame {
    private OnlineModeGUI onlineModeGUI;
    private AOTF aotf;
    private MPA mpa;
    private NIRQuest nirQuest;
    private OmniDriver omniDriver;

    public SpectrometerGUI(OnlineModeGUI onlineModeGUI) {
        this.onlineModeGUI=onlineModeGUI;
        initComponents();
    }

    private void connectButtonMouseClicked(MouseEvent e) {
        boolean b;
        switch (comboBox1.getSelectedItem().toString()){
            case "OmniDriver":
                omniDriver=new OmniDriver();
                b=omniDriver.connectSpec();
                if(b==true){
                    JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected","",JOptionPane.INFORMATION_MESSAGE);
                    //set parameters as input for corresponding spectrometer
                    parameterlabel1.setText("Integral Time");
                    parameterlabel1.setBounds(20, 145, 100, 40);
                    parameterlabel2.setText("No. of Scans");
                    parameterlabel3.setText("Smoothness");
                    contentPanel.remove(parameterlabel4);
                    contentPanel.remove(parameterlabel5);
                    contentPanel.remove(parameterField4);
                    contentPanel.remove(parameterField5);
                    repaint();
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                break;
            case "AOTF":
                aotf=new AOTF();
                b=aotf.connectSpec();
                if(b==true){
                    JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected","",JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                break;
            case "MPA":
                mpa=new MPA();
                b=mpa.connectSpec();
                if(b==true){
                    JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected","",JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                break;
            case "NIRQuest":
                nirQuest=new NIRQuest();
                b=nirQuest.connectSpec();
                if(b==true){
                    JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected","",JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                break;
        }
    }

    private void confirmButtonMouseClicked(MouseEvent e) {
        switch (comboBox1.getSelectedItem().toString()){
            case "OmniDriver":
                omniDriver.setInput(Integer.parseInt(parameterField1.getText()),Integer.parseInt(parameterField2.getText()),Integer.parseInt(parameterField3.getText()),0);
                omniDriver.setSpec();
                onlineModeGUI.setSpectrometer("OmniDriver",omniDriver);
                break;
            case "AOTF":
                aotf.setInput();
                aotf.setSpec();
                onlineModeGUI.setSpectrometer("AOTF",aotf);
                break;
            case "MPA":
                mpa.setInput();
                mpa.setSpec();
                onlineModeGUI.setSpectrometer("MPA",mpa);
                break;
            case "NIRQuest":
                nirQuest.setInput();
                nirQuest.setSpec();
                onlineModeGUI.setSpectrometer("NIRQuest",nirQuest);
                break;
        }
    }

    private void initComponents() {
        setTitle("Spectrometr Configurator");

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        connectButton = new JButton();
        label1 = new JLabel();
        comboBox1 = new JComboBox<>();
        parameterlabel1 = new JLabel();
        parameterlabel2 = new JLabel();
        parameterlabel3 = new JLabel();
        parameterlabel4 = new JLabel();
        parameterlabel5 = new JLabel();
        parameterField1 = new JTextField();
        parameterField2 = new JTextField();
        parameterField3 = new JTextField();
        parameterField4 = new JTextField();
        parameterField5 = new JTextField();
        confirmButton = new JButton();

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

                //---- connectButton ----
                connectButton.setText("Connect");
                connectButton.setFont(connectButton.getFont().deriveFont(connectButton.getFont().getSize() + 5f));
                connectButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        connectButtonMouseClicked(e);
                    }
                });
                contentPanel.add(connectButton);
                connectButton.setBounds(405, 25, 125, 60);

                //---- label1 ----
                label1.setText("Select Spectrometer");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 5f));
                contentPanel.add(label1);
                label1.setBounds(15, 25, 170, 60);

                //---- comboBox1 ----
                comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                    "AOTF",
                    "MPA",
                    "NIRQuest",
                    "OmniDriver"
                }));
                comboBox1.setFont(comboBox1.getFont().deriveFont(comboBox1.getFont().getSize() + 3f));
                contentPanel.add(comboBox1);
                comboBox1.setBounds(220, 35, 111, 39);

                //---- parameterlabel1 ----
                parameterlabel1.setText("Parameter 1:");
                parameterlabel1.setFont(parameterlabel1.getFont().deriveFont(parameterlabel1.getFont().getSize() + 3f));
                contentPanel.add(parameterlabel1);
                parameterlabel1.setBounds(30, 145, 90, 40);

                //---- parameterlabel2 ----
                parameterlabel2.setText("Parameter 2:");
                parameterlabel2.setFont(parameterlabel2.getFont().deriveFont(parameterlabel2.getFont().getSize() + 3f));
                contentPanel.add(parameterlabel2);
                parameterlabel2.setBounds(30, 205, 90, 45);

                //---- parameterlabel3 ----
                parameterlabel3.setText("Parameter 3:");
                parameterlabel3.setFont(parameterlabel3.getFont().deriveFont(parameterlabel3.getFont().getSize() + 3f));
                contentPanel.add(parameterlabel3);
                parameterlabel3.setBounds(30, 265, 97, 40);

                //---- parameterlabel4 ----
                parameterlabel4.setText("Parameter 4:");
                parameterlabel4.setFont(parameterlabel4.getFont().deriveFont(parameterlabel4.getFont().getSize() + 3f));
                contentPanel.add(parameterlabel4);
                parameterlabel4.setBounds(30, 320, 94, 47);

                //---- parameterlabel5 ----
                parameterlabel5.setText("Parameter 5:");
                parameterlabel5.setFont(parameterlabel5.getFont().deriveFont(parameterlabel5.getFont().getSize() + 3f));
                contentPanel.add(parameterlabel5);
                parameterlabel5.setBounds(30, 385, 95, 45);
                contentPanel.add(parameterField1);
                parameterField1.setBounds(125, 150, 74, 34);
                contentPanel.add(parameterField2);
                parameterField2.setBounds(125, 210, 74, 34);
                contentPanel.add(parameterField3);
                parameterField3.setBounds(125, 270, 74, 34);
                contentPanel.add(parameterField4);
                parameterField4.setBounds(125, 325, 74, 34);
                contentPanel.add(parameterField5);
                parameterField5.setBounds(125, 390, 74, 34);

                //---- confirmButton ----
                confirmButton.setText("Confirm");
                confirmButton.setFont(confirmButton.getFont().deriveFont(confirmButton.getFont().getSize() + 5f));
                confirmButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        confirmButtonMouseClicked(e);
                    }
                });
                contentPanel.add(confirmButton);
                confirmButton.setBounds(250, 360, 145, 65);

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
            dialogPane.add(contentPanel, BorderLayout.CENTER);
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
    private JButton connectButton;
    private JLabel label1;
    private JComboBox<String> comboBox1;
    private JLabel parameterlabel1;
    private JLabel parameterlabel2;
    private JLabel parameterlabel3;
    private JLabel parameterlabel4;
    private JLabel parameterlabel5;
    private JTextField parameterField1;
    private JTextField parameterField2;
    private JTextField parameterField3;
    private JTextField parameterField4;
    private JTextField parameterField5;
    private JButton confirmButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
