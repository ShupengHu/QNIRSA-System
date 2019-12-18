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
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                //set table on GUI for user to type into input for spectrometer


                break;
            case "AOTF":
                aotf=new AOTF();
                b=aotf.connectSpec();
                if(b==true){
                    JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected","",JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                //set table on GUI for user to type into input for spectrometer

                break;
            case "MPA":
                mpa=new MPA();
                b=mpa.connectSpec();
                if(b==true){
                    JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected","",JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                //set table on GUI for user to type into input for spectrometer

                break;
            case "NIRQuest":
                nirQuest=new NIRQuest();
                b=nirQuest.connectSpec();
                if(b==true){
                    JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected","",JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(this,comboBox1.getSelectedItem().toString()+" connected unsuccessfully","",JOptionPane.INFORMATION_MESSAGE);

                //set table on GUI for user to type into input for spectrometer

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
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
