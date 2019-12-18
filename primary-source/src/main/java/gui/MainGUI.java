/*
 * Created by JFormDesigner on Sun Dec 15 19:12:52 GMT 2019
 */

package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @author Shupeng Hu
 */
public class MainGUI extends JFrame {
    private static MainGUI mainGUI;
    private static OnlineModeGUI onlineModeGUI;
    private static OfflineModeGUI offlineModeGUI;

    public MainGUI() {
        initComponents();
    }

    private void onlineButtonMouseClicked(MouseEvent e) throws IOException {
        onlineModeGUI=new OnlineModeGUI();
        onlineModeGUI.setVisible(true);
        onlineModeGUI.setOnlineModeGUI(onlineModeGUI);
        mainGUI.setVisible(false);
    }

    private void offlineButtonMouseClicked(MouseEvent e) {
        offlineModeGUI=new OfflineModeGUI();
        offlineModeGUI.setVisible(true);
        mainGUI.setVisible(false);
    }

    private void initComponents() {
        setTitle("QNIRSA System");
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        onlineButton = new JButton();
        offlineButton = new JButton();
        buttonBar = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setPreferredSize(new Dimension(600, 200));
                contentPanel.setLayout(null);

                //---- onlineButton ----
                onlineButton.setText("Online Mode");
                onlineButton.setFont(onlineButton.getFont().deriveFont(onlineButton.getFont().getSize() + 5f));
                onlineButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            onlineButtonMouseClicked(e);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                contentPanel.add(onlineButton);
                onlineButton.setBounds(10, 65, 200, 100);

                //---- offlineButton ----
                offlineButton.setText("Offline Mode");
                offlineButton.setFont(offlineButton.getFont().deriveFont(offlineButton.getFont().getSize() + 5f));
                offlineButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        offlineButtonMouseClicked(e);
                    }
                });
                contentPanel.add(offlineButton);
                offlineButton.setBounds(235, 65, 200, 100);

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

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
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
    private JButton onlineButton;
    private JButton offlineButton;
    private JPanel buttonBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public static void main(String[] args) {
        mainGUI=new MainGUI();
        mainGUI.setVisible(true);
    }

}
