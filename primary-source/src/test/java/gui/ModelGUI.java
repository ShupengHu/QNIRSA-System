/*
 * Created by JFormDesigner on Thu Dec 26 10:46:13 GMT 2019
 */

package gui;

import dataManager.DataProcessor;
import dataManager.ExcelManager;
import excelTemplates.Model;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Shupeng Hu
 */
public class ModelGUI extends JFrame {
    private OnlineModeGUI onlineModeGUI;
    private JFileChooser fileChooser;
    private String filePath;
    private List<Object> model;

    public ModelGUI(OnlineModeGUI onlineModeGUI) {
        this.onlineModeGUI=onlineModeGUI;
        initComponents();
    }

    private void modelButtonMouseClicked(MouseEvent e) {
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
        //set model
        ExcelManager excelManager= new ExcelManager();
        excelManager.readExcel(filePath, Model.class);
        model=excelManager.getListO();
        onlineModeGUI.setModel(DataProcessor.listToDoubleArray2(model,"Model"));
        //set sample category
        onlineModeGUI.setSampleCategory(comboBox1.getSelectedItem().toString());
        //set sample properties
        if(proteincheckBox.isSelected()==true){
            onlineModeGUI.setProperty("Protein");
        }
        if(watercheckBox.isSelected()==true){
            onlineModeGUI.setProperty("Water");
        }
        if(biuretcheckBox.isSelected()==true){
            onlineModeGUI.setProperty("Biuret");
        }
        if(starchcheckBox.isSelected()==true){
            onlineModeGUI.setProperty("Starch");
        }
        if(aminoacidcheckBox.isSelected()==true){
            onlineModeGUI.setProperty("Amino Acid");
        }
        if(amylosecheckBox.isSelected()==true){
            onlineModeGUI.setProperty("Amylose");
        }
        this.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        modelButton = new JButton();
        confirmButton = new JButton();
        proteincheckBox = new JCheckBox();
        label1 = new JLabel();
        watercheckBox = new JCheckBox();
        biuretcheckBox = new JCheckBox();
        starchcheckBox = new JCheckBox();
        aminoacidcheckBox = new JCheckBox();
        amylosecheckBox = new JCheckBox();
        comboBox1 = new JComboBox<>();

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

                //---- modelButton ----
                modelButton.setText("Import Model");
                modelButton.setFont(modelButton.getFont().deriveFont(modelButton.getFont().getSize() + 5f));
                modelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        modelButtonMouseClicked(e);
                    }
                });
                contentPanel.add(modelButton);
                modelButton.setBounds(25, 35, 150, 55);

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
                confirmButton.setBounds(40, 230, 115, 40);

                //---- proteincheckBox ----
                proteincheckBox.setText("Protein");
                contentPanel.add(proteincheckBox);
                proteincheckBox.setBounds(295, 95, 85, 35);

                //---- label1 ----
                label1.setText("Select Sample and Properties");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 3f));
                contentPanel.add(label1);
                label1.setBounds(320, 0, 235, 50);

                //---- watercheckBox ----
                watercheckBox.setText("Water");
                contentPanel.add(watercheckBox);
                watercheckBox.setBounds(435, 95, 85, 35);

                //---- biuretcheckBox ----
                biuretcheckBox.setText("Biuret");
                contentPanel.add(biuretcheckBox);
                biuretcheckBox.setBounds(295, 140, 85, 35);

                //---- starchcheckBox ----
                starchcheckBox.setText("Starch");
                contentPanel.add(starchcheckBox);
                starchcheckBox.setBounds(435, 140, 85, 35);

                //---- aminoacidcheckBox ----
                aminoacidcheckBox.setText("Amino Acid");
                contentPanel.add(aminoacidcheckBox);
                aminoacidcheckBox.setBounds(295, 185, 100, 35);

                //---- amylosecheckBox ----
                amylosecheckBox.setText("Amylose");
                contentPanel.add(amylosecheckBox);
                amylosecheckBox.setBounds(435, 185, 85, 35);

                //---- comboBox1 ----
                comboBox1.setFont(comboBox1.getFont().deriveFont(comboBox1.getFont().getSize() + 3f));
                comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Rice ",
                    "Urea"
                }));
                contentPanel.add(comboBox1);
                comboBox1.setBounds(350, 50, 101, 29);

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
    private JButton modelButton;
    private JButton confirmButton;
    private JCheckBox proteincheckBox;
    private JLabel label1;
    private JCheckBox watercheckBox;
    private JCheckBox biuretcheckBox;
    private JCheckBox starchcheckBox;
    private JCheckBox aminoacidcheckBox;
    private JCheckBox amylosecheckBox;
    private JComboBox<String> comboBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
