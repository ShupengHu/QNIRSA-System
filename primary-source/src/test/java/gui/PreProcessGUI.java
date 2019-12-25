/*
 * Created by JFormDesigner on Mon Dec 23 17:09:25 GMT 2019
 */

package gui;

import dataManager.ExcelManager;
import dataManager.DataProcessor;
import lombok.SneakyThrows;
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
public class PreProcessGUI extends JFrame {
    private OnlineModeGUI onlineModeGUI;
    private JFileChooser fileChooser;
    private String filePath;
    private List<double[]> refSpectrum;
    private List<Object> list;

    public PreProcessGUI(OnlineModeGUI onlineModeGUI) {
        this.onlineModeGUI=onlineModeGUI;
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

    private void confirmButtonMouseClicked(MouseEvent e) throws Exception {
        switch (comboBox.getSelectedItem().toString()){
            case "SNV":
                SNV_JAVA snv_java=new SNV_JAVA();
                onlineModeGUI.setPreProcessMethod("SNV",snv_java);
                break;
            case "MSC":
                MSC_JAVA msc_java=new MSC_JAVA();
                ExcelManager excelManager=new ExcelManager();
                excelManager.readExcel(filePath);
                refSpectrum=excelManager.getListD();
                msc_java.setParameters(DataProcessor.listToDoubleArray1(refSpectrum));
                onlineModeGUI.setPreProcessMethod("MSC",msc_java);
                break;
            case "SG":
                SG_JAVA sg_java=new SG_JAVA();
                sg_java.setParameter(Integer.parseInt(wlField.getText()),Integer.parseInt(pfoField.getText()), (Integer) docomboBox.getSelectedItem());
                onlineModeGUI.setPreProcessMethod("SG",sg_java);
                break;
        }
        this.setVisible(false);
    }

    private void comboBoxItemStateChanged(ItemEvent e) {
        switch (comboBox.getSelectedItem().toString()){
            case "SNV":
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        confirmButton = new JButton();
        label1 = new JLabel();
        comboBox = new JComboBox<>();
        refSpectrumButton = new JButton();
        wlLabel = new JLabel();
        pfoLabel = new JLabel();
        doLabel = new JLabel();
        wlField = new JTextField();
        pfoField = new JTextField();
        docomboBox = new JComboBox<>();

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

                //---- confirmButton ----
                confirmButton.setText("Confirm");
                confirmButton.setFont(confirmButton.getFont().deriveFont(confirmButton.getFont().getSize() + 5f));
                confirmButton.addMouseListener(new MouseAdapter() {
                    @SneakyThrows
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        confirmButtonMouseClicked(e);
                    }
                });
                contentPanel.add(confirmButton);
                confirmButton.setBounds(320, 230, 115, 50);

                //---- label1 ----
                label1.setText("Select Pre-processing Method and Type Relevant Parameters");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 4f));
                contentPanel.add(label1);
                label1.setBounds(15, 10, 475, 57);

                //---- comboBox ----
                comboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "SNV",
                    "MSC",
                    "SG"
                }));
                comboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        comboBoxItemStateChanged(e);
                    }
                });
                contentPanel.add(comboBox);
                comboBox.setBounds(25, 80, comboBox.getPreferredSize().width, 40);

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
                refSpectrumButton.setBounds(10, 230, 185, 50);

                //---- wlLabel ----
                wlLabel.setText("Window Length");
                wlLabel.setFont(wlLabel.getFont().deriveFont(wlLabel.getFont().getSize() + 3f));
                contentPanel.add(wlLabel);
                wlLabel.setBounds(230, 75, 110, 30);

                //---- pfoLabel ----
                pfoLabel.setText("Polynomial Fit Order");
                pfoLabel.setFont(pfoLabel.getFont().deriveFont(pfoLabel.getFont().getSize() + 3f));
                contentPanel.add(pfoLabel);
                pfoLabel.setBounds(200, 120, 170, 25);

                //---- doLabel ----
                doLabel.setText("Derivative Order");
                doLabel.setFont(doLabel.getFont().deriveFont(doLabel.getFont().getSize() + 3f));
                contentPanel.add(doLabel);
                doLabel.setBounds(230, 165, 130, 30);
                contentPanel.add(wlField);
                wlField.setBounds(360, 80, 69, 29);
                contentPanel.add(pfoField);
                pfoField.setBounds(360, 120, 69, 29);

                //---- docomboBox ----
                docomboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "0",
                    "1",
                    "2"
                }));
                contentPanel.add(docomboBox);
                docomboBox.setBounds(360, 170, 70, docomboBox.getPreferredSize().height);

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
    private JButton confirmButton;
    private JLabel label1;
    private JComboBox<String> comboBox;
    private JButton refSpectrumButton;
    private JLabel wlLabel;
    private JLabel pfoLabel;
    private JLabel doLabel;
    private JTextField wlField;
    private JTextField pfoField;
    private JComboBox<String> docomboBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
