/*
 * Created by JFormDesigner on Mon Dec 23 17:09:25 GMT 2019
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Shupeng Hu
 */
public class PreProcessGUI extends JFrame {
    private OnlineModeGUI onlineModeGUI;
    private JFileChooser fileChooser;

    public PreProcessGUI(OnlineModeGUI onlineModeGUI) {
        this.onlineModeGUI=onlineModeGUI;
        initComponents();
    }

    private void confirmButtonMouseClicked(MouseEvent e) {
        this.setVisible(false);
    }

    private void comboBoxItemStateChanged(ItemEvent e) {
        switch (comboBox.getSelectedItem().toString()){
            case "SNV":
                System.out.println("1");
                break;
            case "MSC":
                System.out.println("2");
                break;
            case "SG":
                System.out.println("3");
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
        String filePath="";
        if(a==JFileChooser.APPROVE_OPTION){
            filePath=fileChooser.getSelectedFile().getPath();
        }
        //extract data from document

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
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();

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

                //---- label2 ----
                label2.setText("text");
                contentPanel.add(label2);
                label2.setBounds(235, 80, 60, 30);

                //---- label3 ----
                label3.setText("text");
                contentPanel.add(label3);
                label3.setBounds(230, 125, 55, 25);

                //---- label4 ----
                label4.setText("text");
                contentPanel.add(label4);
                label4.setBounds(230, 175, 45, 30);

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
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
