/*
 * Created by JFormDesigner on Sun Dec 15 00:39:38 GMT 2019
 */

package gui;

import hardwareAdapater.SCMAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author unknown
 */
public class SCMGUI extends JFrame {
    private SCMAdapter scmAdapter=new SCMAdapter();

    public SCMGUI() {
        initComponents();
    }

    private void forwardButtonMouseClicked(MouseEvent e) {
        scmAdapter.moveSCM(scmAdapter.forward);
    }

    private void backwardButtonMouseClicked(MouseEvent e) {
        scmAdapter.moveSCM(scmAdapter.backward);
    }

    private void resetButtonMouseClicked(MouseEvent e) {
        try {
            scmAdapter.resetSCM();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void connectButtonMouseClicked(MouseEvent e) {
        scmAdapter.setPortName(comboBox1.getSelectedItem().toString());
        scmAdapter.connectSCM();
        JOptionPane.showMessageDialog(this,"SCM connected","",JOptionPane.INFORMATION_MESSAGE);
    }

    private void stopButtonMouseClicked(MouseEvent e) {
        scmAdapter.moveSCM(scmAdapter.stop);
    }


    private void initComponents() {
        setTitle("SCM Controller");

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        forwardButton = new JButton();
        backwardButton = new JButton();
        resetButton = new JButton();
        label1 = new JLabel();
        comboBox1 = new JComboBox<>();
        connectButton = new JButton();
        stopButton = new JButton();

        //======== this ========
        setName("frame1");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- forwardButton ----
                forwardButton.setText("Forward");
                forwardButton.setFont(forwardButton.getFont().deriveFont(forwardButton.getFont().getSize() + 5f));
                forwardButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        forwardButtonMouseClicked(e);
                    }
                });
                contentPanel.add(forwardButton);
                forwardButton.setBounds(0, 110, 100, 50);

                //---- backwardButton ----
                backwardButton.setText("Backward");
                backwardButton.setFont(backwardButton.getFont().deriveFont(backwardButton.getFont().getSize() + 5f));
                backwardButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        backwardButtonMouseClicked(e);
                    }
                });
                contentPanel.add(backwardButton);
                backwardButton.setBounds(115, 110, 150, 50);

                //---- resetButton ----
                resetButton.setText("Reset");
                resetButton.setFont(resetButton.getFont().deriveFont(resetButton.getFont().getSize() + 5f));
                resetButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        resetButtonMouseClicked(e);
                    }
                });
                contentPanel.add(resetButton);
                resetButton.setBounds(410, 110, 100, 50);

                //---- label1 ----
                label1.setText("Select Port");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
                contentPanel.add(label1);
                label1.setBounds(5, 10, 125, 40);

                //---- comboBox1 ----
                comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                    "COM1",
                    "COM2",
                    "COM3",
                    "COM4"
                }));
                comboBox1.setFont(comboBox1.getFont().deriveFont(comboBox1.getFont().getSize() + 2f));
                contentPanel.add(comboBox1);
                comboBox1.setBounds(120, 20, 95, comboBox1.getPreferredSize().height);

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
                connectButton.setBounds(235, 10, 105, 45);

                //---- stopButton ----
                stopButton.setText("Stop");
                stopButton.setFont(stopButton.getFont().deriveFont(stopButton.getFont().getSize() + 5f));
                stopButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        stopButtonMouseClicked(e);
                    }
                });
                contentPanel.add(stopButton);
                stopButton.setBounds(290, 110, 100, 50);

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
    private JButton forwardButton;
    private JButton backwardButton;
    private JButton resetButton;
    private JLabel label1;
    private JComboBox<String> comboBox1;
    private JButton connectButton;
    private JButton stopButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


}
