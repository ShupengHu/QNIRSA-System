/*
 * Created by JFormDesigner on Fri Dec 27 08:34:41 GMT 2019
 */

package gui;

import dataManager.DataProcessor;
import dataManager.ExcelManager;
import excelTemplates.Rice;
import excelTemplates.Urea;
import excelTemplates.Wavelength;

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
public class ImportDataGUI extends JFrame {
    private OfflineModeGUI offlineModeGUI;
    private JFileChooser spectraChooser;
    private JFileChooser wavelengthChooser;
    private JFileChooser referenceChooser;
    private String spectraPath;
    private String wavelengthPath;
    private String referencePath;


    public ImportDataGUI(OfflineModeGUI offlineModeGUI) {
        this.offlineModeGUI=offlineModeGUI;
        initComponents();
    }

    private void spectraButtonMouseClicked(MouseEvent e) {
        //select document
        spectraChooser=new JFileChooser();
        spectraChooser.setCurrentDirectory(new File("C:"));
        spectraChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("Excel Document(.xlsx)", "xlsx");
        spectraChooser.setFileFilter(fileFilter);
        spectraChooser.setDialogTitle("Please Select Document");
        int a =spectraChooser.showDialog(null, "Open");
        //get the path of the selected document
        if(a==JFileChooser.APPROVE_OPTION){
            spectraPath=spectraChooser.getSelectedFile().getPath();
        }
    }

    private void wavelengthButtonMouseClicked(MouseEvent e) {
        //select document
        wavelengthChooser=new JFileChooser();
        wavelengthChooser.setCurrentDirectory(new File("C:"));
        wavelengthChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("Excel Document(.xlsx)", "xlsx");
        wavelengthChooser.setFileFilter(fileFilter);
        wavelengthChooser.setDialogTitle("Please Select Document");
        int a =wavelengthChooser.showDialog(null, "Open");
        //get the path of the selected document
        if(a==JFileChooser.APPROVE_OPTION){
            wavelengthPath=wavelengthChooser.getSelectedFile().getPath();
        }
    }

    private void referenceButtonMouseClicked(MouseEvent e) {
        //select document
        referenceChooser=new JFileChooser();
        referenceChooser.setCurrentDirectory(new File("C:"));
        referenceChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("Excel Document(.xlsx)", "xlsx");
        referenceChooser.setFileFilter(fileFilter);
        referenceChooser.setDialogTitle("Please Select Document");
        int a =referenceChooser.showDialog(null, "Open");
        //get the path of the selected document
        if(a==JFileChooser.APPROVE_OPTION){
            referencePath=referenceChooser.getSelectedFile().getPath();
        }
    }

    private void confirmButtonMouseClicked(MouseEvent e) throws Exception {
        //get spectra
        ExcelManager.readExcel(spectraPath);
        double[][] spectra= DataProcessor.listToDoubleArray1(ExcelManager.readExcel(spectraPath));
        //get wavelength
        double[][] wavelength=DataProcessor.listToDoubleArray2(ExcelManager.readExcel(wavelengthPath,Wavelength.class),"Wavelength");
        //get reference data
        double[][] refData=null;
        String sampleCategory="";
        switch (comboBox1.getSelectedItem().toString()){
            case "Rice":
                refData=DataProcessor.listToDoubleArray2(ExcelManager.readExcel(referencePath, Rice.class),"Rice");
                sampleCategory="Rice";
                break;
            case "Urea":
                refData=DataProcessor.listToDoubleArray2(ExcelManager.readExcel(referencePath, Urea.class),"Urea");
                sampleCategory="Urea";
                break;
        }
        //set input in offlineMode GUI
        offlineModeGUI.setInput(spectra,wavelength,refData,sampleCategory);
        this.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        spectraButton = new JButton();
        wavelengthButton = new JButton();
        referenceButton = new JButton();
        confirmButton = new JButton();
        comboBox1 = new JComboBox<>();
        label1 = new JLabel();

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

                //---- spectraButton ----
                spectraButton.setText("Import Spectra");
                spectraButton.setFont(spectraButton.getFont().deriveFont(spectraButton.getFont().getSize() + 3f));
                spectraButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        spectraButtonMouseClicked(e);
                    }
                });
                contentPanel.add(spectraButton);
                spectraButton.setBounds(15, 15, 195, 60);

                //---- wavelengthButton ----
                wavelengthButton.setText("Import Wavelength");
                wavelengthButton.setFont(wavelengthButton.getFont().deriveFont(wavelengthButton.getFont().getSize() + 3f));
                wavelengthButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        wavelengthButtonMouseClicked(e);
                    }
                });
                contentPanel.add(wavelengthButton);
                wavelengthButton.setBounds(290, 15, 195, 60);

                //---- referenceButton ----
                referenceButton.setText("Import Reference Data");
                referenceButton.setFont(referenceButton.getFont().deriveFont(referenceButton.getFont().getSize() + 3f));
                referenceButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        referenceButtonMouseClicked(e);
                    }
                });
                contentPanel.add(referenceButton);
                referenceButton.setBounds(5, 165, 230, 60);

                //---- confirmButton ----
                confirmButton.setText("Confirm");
                confirmButton.setFont(confirmButton.getFont().deriveFont(confirmButton.getFont().getSize() + 3f));
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
                confirmButton.setBounds(190, 280, 105, 60);

                //---- comboBox1 ----
                comboBox1.setFont(comboBox1.getFont().deriveFont(comboBox1.getFont().getSize() + 3f));
                comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Rice",
                    "Urea"
                }));
                contentPanel.add(comboBox1);
                comboBox1.setBounds(335, 185, 90, 35);

                //---- label1 ----
                label1.setText("Choose Sample Category");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 3f));
                contentPanel.add(label1);
                label1.setBounds(295, 135, 195, 50);

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
    private JButton spectraButton;
    private JButton wavelengthButton;
    private JButton referenceButton;
    private JButton confirmButton;
    private JComboBox<String> comboBox1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
