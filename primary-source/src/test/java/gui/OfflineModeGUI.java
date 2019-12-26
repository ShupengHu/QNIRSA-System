package gui;

import com.steema.teechart.TChart;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.Series;
import org.apache.poi.ss.formula.functions.T;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class OfflineModeGUI extends JFrame {
    //class objects
    private OfflineModeGUI offlineModeGUI;

    //GUI components
    private JPanel contentPane;
    private JButton importButton;
    private JButton methodsButton;

    private TChart originalChart;
    private TChart preprocessChart;
    private TChart TsetChart;
    private TChart VsetChart;
    private TChart variableSelectChart;

    public OfflineModeGUI() throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        //set size of GUI
        setTitle("Offline Mode");
        setSize(1900,1000);
        setResizable(false);

        //---------set GUI elements-------//
        contentPane= (JPanel) getContentPane();
        contentPane.setLayout(null);
        contentPane.setSize(getMaximumSize());

        //--------set chart----------//
        //set original spectrum chart
        originalChart=new TChart();
        originalChart.setGraphics3D(null);
        originalChart.getLegend().setVisible(false);
        originalChart.setBounds(new Rectangle(0, 80, 900, 450));
        originalChart.removeAllSeries();
        originalChart.getAspect().setView3D(false);
        originalChart.getChart().getTitle().setText("Original Spectra");
        originalChart.getChart().getTitle().getFont().setSize(20);
        originalChart.getChart().getTitle().getFont().setColor(Color.blue);
        originalChart.getAxes().getLeft().getTitle().setText("Absorbance");
        originalChart.getAxes().getLeft().getTitle().getFont().setSize(20);
        originalChart.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        originalChart.getAxes().getBottom().getTitle().setText("Wavelength");
        originalChart.getAxes().getBottom().getTitle().getFont().setSize(20);
        originalChart.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        preprocessChart=new TChart();
        preprocessChart.setGraphics3D(null);
        preprocessChart.getLegend().setVisible(false);
        preprocessChart.setBounds(new Rectangle(950, 80, 900, 450));
        preprocessChart.removeAllSeries();
        preprocessChart.getAspect().setView3D(false);
        preprocessChart.getChart().getTitle().setText("Pre-Processed Spectra");
        preprocessChart.getChart().getTitle().getFont().setSize(20);
        preprocessChart.getChart().getTitle().getFont().setColor(Color.blue);
        preprocessChart.getAxes().getLeft().getTitle().setText("Absorbance");
        preprocessChart.getAxes().getLeft().getTitle().getFont().setSize(20);
        preprocessChart.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        preprocessChart.getAxes().getBottom().getTitle().setText("Wavelength");
        preprocessChart.getAxes().getBottom().getTitle().getFont().setSize(20);
        preprocessChart.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        //-------set buttons------------//
        importButton=new JButton("Import Spectral Data");
        importButton.setBounds(new Rectangle(0, 10, 180, 50));
        importButton.setFont(new Font("Arial", Font.PLAIN, 14));
        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                importButtonMouseClicked(e);
            }
        });

        methodsButton=new JButton("Choose Methods");
        methodsButton.setBounds(new Rectangle(200, 10, 150, 50));
        methodsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        methodsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                importButtonMouseClicked(e);
            }
        });

        //-------add components--------//
        contentPane.add(importButton);
        contentPane.add(methodsButton);
        contentPane.add(originalChart);
        contentPane.add(preprocessChart);
    }

    private void importButtonMouseClicked(MouseEvent e) {
    }

    /**
     * set OfflineModeGUI class object
     * @param offlineModeGUI
     */
    public void setOfflineModeGUI(OfflineModeGUI offlineModeGUI) {
        this.offlineModeGUI= offlineModeGUI;
    }

    public static void main(String[] args) throws IOException {
        OfflineModeGUI offlineModeGUI= new OfflineModeGUI();
        offlineModeGUI.setVisible(true);
    }
}
