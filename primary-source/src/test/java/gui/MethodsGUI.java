/*
 * Created by JFormDesigner on Fri Dec 27 11:04:27 GMT 2019
 */

package gui;

import dataManager.DataProcessor;
import dataManager.ExcelManager;
import methodsLibrary.MSC_JAVA;
import methodsLibrary.SG_JAVA;
import methodsLibrary.SNV_JAVA;

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
        repaint();
    }

    private void comboBoxItemStateChanged(ItemEvent e) {
        switch (preprocesscomboBox.getSelectedItem().toString()){
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
                repaint();
                break;
        }
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
        this.setVisible(false);
    }

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
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
