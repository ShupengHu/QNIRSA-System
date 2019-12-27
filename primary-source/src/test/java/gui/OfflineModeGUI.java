package gui;

import com.steema.teechart.TChart;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.Series;
import dataManager.DataProcessor;
import methodsLibrary.MSC_JAVA;
import methodsLibrary.SG_JAVA;
import methodsLibrary.SNV_JAVA;
import org.apache.poi.ss.formula.functions.T;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class OfflineModeGUI extends JFrame {
    //class objects
    private OfflineModeGUI offlineModeGUI;
    private SNV_JAVA snv_java;
    private MSC_JAVA msc_java;
    private SG_JAVA sg_java;

    //parameters
    private double[][] spectra;
    private double[][] wavelength;
    private double[][] refData;
    private double[][] spectraAfterPreProcess;
    private double[][] spectralT;
    private double[][] spectralV;
    private double[][] spectraAfterVariableSelection;
    private String sampleCategory;
    private String selectedPreprocessMethod="";
    private String selectedDataPartitionMethod="";
    private String selectedVariableSelectionMethod="";

    //GUI components
    private JPanel contentPane;
    private JButton importButton;
    private JButton methodsButton;
    private JButton runButton;

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

        TsetChart=new TChart();
        TsetChart.setGraphics3D(null);
        TsetChart.getLegend().setVisible(false);
        TsetChart.setBounds(new Rectangle(0, 550, 600, 420));
        TsetChart.removeAllSeries();
        TsetChart.getAspect().setView3D(false);
        TsetChart.getChart().getTitle().setText("Spectra of Training Set");
        TsetChart.getChart().getTitle().getFont().setSize(20);
        TsetChart.getChart().getTitle().getFont().setColor(Color.blue);
        TsetChart.getAxes().getLeft().getTitle().setText("Absorbance");
        TsetChart.getAxes().getLeft().getTitle().getFont().setSize(20);
        TsetChart.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        TsetChart.getAxes().getBottom().getTitle().setText("Wavelength");
        TsetChart.getAxes().getBottom().getTitle().getFont().setSize(20);
        TsetChart.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        VsetChart=new TChart();
        VsetChart.setGraphics3D(null);
        VsetChart.getLegend().setVisible(false);
        VsetChart.setBounds(new Rectangle(620, 550, 600, 420));
        VsetChart.removeAllSeries();
        VsetChart.getAspect().setView3D(false);
        VsetChart.getChart().getTitle().setText("Spectra of Validation Set");
        VsetChart.getChart().getTitle().getFont().setSize(20);
        VsetChart.getChart().getTitle().getFont().setColor(Color.blue);
        VsetChart.getAxes().getLeft().getTitle().setText("Absorbance");
        VsetChart.getAxes().getLeft().getTitle().getFont().setSize(20);
        VsetChart.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        VsetChart.getAxes().getBottom().getTitle().setText("Wavelength");
        VsetChart.getAxes().getBottom().getTitle().getFont().setSize(20);
        VsetChart.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        //-------set buttons------------//
        importButton=new JButton("Import Data Set");
        importButton.setBounds(new Rectangle(0, 10, 180, 50));
        importButton.setFont(new Font("Arial", Font.PLAIN, 14));
        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                importButtonMouseClicked(e);
            }
        });

        methodsButton=new JButton("Choose Methods");
        methodsButton.setBounds(new Rectangle(190, 10, 150, 50));
        methodsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        methodsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                methodsButtonMouseClicked(e);
            }
        });

        runButton=new JButton("Run");
        runButton.setBounds(new Rectangle(350, 10, 70, 50));
        runButton.setFont(new Font("Arial", Font.PLAIN, 14));
        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                runButtonMouseClicked(e);
            }
        });

        //-------add components--------//
        contentPane.add(importButton);
        contentPane.add(methodsButton);
        contentPane.add(runButton);
        contentPane.add(originalChart);
        contentPane.add(preprocessChart);
        contentPane.add(TsetChart);
        contentPane.add(VsetChart);
    }

    private void runButtonMouseClicked(MouseEvent e) {
        if(spectra==null||wavelength==null||refData==null){
            JOptionPane.showMessageDialog(this, "Please Import Data in Advance","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(selectedPreprocessMethod==""){
        //if(selectedPreprocessMethod==""||selectedDataPartitionMethod==""||selectedVariableSelectionMethod==""){
            JOptionPane.showMessageDialog(this, "Please Select Methods (including none) in Advance","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }

        //pre-processing data
        preProcess(spectra);
        //draw pre-processed spectra
        drawSpectra(spectraAfterPreProcess,this.wavelength,preprocessChart);


    }

    private void methodsButtonMouseClicked(MouseEvent e) {
        MethodsGUI methodsGUI=new MethodsGUI(offlineModeGUI);
        methodsGUI.setVisible(true);
    }

    private void importButtonMouseClicked(MouseEvent e) {
        ImportDataGUI importDataGUI=new ImportDataGUI(offlineModeGUI);
        importDataGUI.setVisible(true);
    }

    /**
     *  set preprocess method
     * @param methodName
     * @param o class object
     */
    public void setPreProcessMethod(String methodName, Object o){
        selectedPreprocessMethod=methodName;
        switch (selectedPreprocessMethod){
            case "SNV":
                snv_java=(SNV_JAVA)o;
                break;
            case "MSC":
                msc_java=(MSC_JAVA)o;
                break;
            case "SG":
                sg_java=(SG_JAVA)o;
                sg_java.setWavelength(wavelength);
                break;
            case "None":
                break;
        }
    }

    /**
     * invoke preprocess methods
     * @param data
     */
    public void preProcess(double[][] data){
        switch (selectedPreprocessMethod){
            case "SNV":
                snv_java.invokeMethod(data);
                snv_java.parseResult();
                spectraAfterPreProcess=snv_java.getResult();
                break;
            case "MSC":
                msc_java.invokeMethod(data);
                msc_java.parseResult();
                spectraAfterPreProcess=msc_java.getResult();
                break;
            case "SG":
                sg_java.invokeMethod(data);
                sg_java.parseResult();
                spectraAfterPreProcess=sg_java.getResult();
                break;
            case "None":
                spectraAfterPreProcess=data;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedPreprocessMethod);
        }
    }

    public void setInput(double[][] spectra,double[][] wavelength,double[][] refData,String sampleCategory){
        this.spectra=spectra;
        this.wavelength=wavelength;
        this.refData=refData;
        this.sampleCategory=sampleCategory;

        //draw orignial spectra
        drawSpectra(this.spectra,this.wavelength,originalChart);
    }

    /**
     * draw spectra
     * @param spectra
     * @param wavelength
     * @param chart
     */
    public void drawSpectra(double[][] spectra, double[][] wavelength,TChart chart){
        Series[] series=new Series[spectra.length];
        chart.removeAllSeries();
        for(int s=0;s<series.length;s++){
            series[s]=new Line();
            chart.addSeries(series[s]);
            chart.getSeries(s).add(wavelength[0],spectra[s]);
        }
        chart.repaint();
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
