package gui;

import com.steema.teechart.TChart;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.Series;
import dataManager.DataProcessor;
import methodsLibrary.*;
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
    private KS_JAVA ks_java;
    private SPXY_JAVA spxy_java;
    private UVE_JAVA uve_java;
    private SA_JAVA sa_java;
    private PLSR_JAVA plsr_java;
    private SPA_JAVA spa_java;

    //parameters
    private double[][] spectra;
    private double[][] wavelength;
    private double[][] refData;
    private double[][] spectraAfterPreProcess;
    private double[][] TsetSpectra;
    private double[][] VsetSpectra;
    private double[][] TsetRefData;
    private double[][] VsetRefData;
    private double[][] selectedTsetSpectra;
    private double[][] selectedVsetSpectra;
    private double[][] selectedWavelength;
    private String selectedPreprocessMethod="";
    private String selectedDataPartitionMethod="";
    private String selectedVariableSelectionMethod="";
    private String selectedCalibrationMethod="";
    private double RMSEP;
    private double R2;

    //GUI components
    private JPanel contentPane;
    private JButton importButton;
    private JButton methodsButton;
    private JButton runButton;
    private JLabel RMSEPLable;
    private JLabel R2Lable;
    private JTextField RMSEPField;
    private JTextField R2Field;

    private TChart originalChart;
    private TChart preprocessChart;
    private TChart TsetChart;
    private TChart VsetChart;
    private TChart variableSelectionChart;

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

        variableSelectionChart=new TChart();
        variableSelectionChart.setGraphics3D(null);
        variableSelectionChart.getLegend().setVisible(false);
        variableSelectionChart.setBounds(new Rectangle(1240, 550, 600, 420));
        variableSelectionChart.removeAllSeries();
        variableSelectionChart.getAspect().setView3D(false);
        variableSelectionChart.getChart().getTitle().setText("Selected Spectra of Training Set");
        variableSelectionChart.getChart().getTitle().getFont().setSize(20);
        variableSelectionChart.getChart().getTitle().getFont().setColor(Color.blue);
        variableSelectionChart.getAxes().getLeft().getTitle().setText("Absorbance");
        variableSelectionChart.getAxes().getLeft().getTitle().getFont().setSize(20);
        variableSelectionChart.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        variableSelectionChart.getAxes().getBottom().getTitle().setText("Wavelength");
        variableSelectionChart.getAxes().getBottom().getTitle().getFont().setSize(20);
        variableSelectionChart.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

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

        //set other GUI components
        RMSEPLable=new JLabel("RMSEP");
        RMSEPLable.setBounds(new Rectangle(500, 10, 50, 50));
        RMSEPField=new JTextField();
        RMSEPField.setBounds(new Rectangle(550, 10, 70, 50));
        R2Lable =new JLabel("R2");
        R2Lable.setBounds(new Rectangle(700, 10, 20, 50));
        R2Field=new JTextField();
        R2Field.setBounds(new Rectangle(720, 10, 70, 50));

        //-------add components--------//
        contentPane.add(importButton);
        contentPane.add(methodsButton);
        contentPane.add(runButton);
        contentPane.add(originalChart);
        contentPane.add(preprocessChart);
        contentPane.add(TsetChart);
        contentPane.add(VsetChart);
        contentPane.add(variableSelectionChart);
        contentPane.add(RMSEPLable);
        contentPane.add(RMSEPField);
        contentPane.add(R2Lable);
        contentPane.add(R2Field);
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
        System.out.println("Pre-processing finished");
        //draw pre-processed spectra
        drawSpectra(spectraAfterPreProcess,this.wavelength,preprocessChart);

        //data partition
        dataPartition(spectraAfterPreProcess);
        System.out.println("Data Partition finished");
        //draw spectra of training set and validation set respectively
        drawSpectra(TsetSpectra,this.wavelength,TsetChart);
        drawSpectra(VsetSpectra,this.wavelength,VsetChart);

        //variable selection
        variableSelection(TsetSpectra);
        System.out.println("Variable Selection finished");
        //draw selected spectra of training set
        drawSpectra(selectedTsetSpectra,selectedWavelength,variableSelectionChart);

        //calibration
        calibration(selectedTsetSpectra);
        System.out.println("Calibration finished");
        //display final results
        RMSEPField.setText(""+DataProcessor.roundData(RMSEP,4));
        R2Field.setText(""+DataProcessor.roundData(R2,4));

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

    /**
     *  set data partition method
     * @param methodName
     * @param o class object
     */
    public void setDataPartitionMethod(String methodName, Object o){
        selectedDataPartitionMethod=methodName;
        switch (selectedDataPartitionMethod){
            case "KS":
                ks_java=(KS_JAVA)o;
                break;
            case "SPXY":
                spxy_java=(SPXY_JAVA)o;
                break;
        }
    }

    /**
     * invoke data partition methods
     * @param data
     */
    public void dataPartition(double[][] data){
        switch (selectedDataPartitionMethod){
            case "KS":
                ks_java.setRefData(refData);
                ks_java.invokeMethod(data);
                ks_java.parseResult();
                TsetSpectra=ks_java.getTsetSpectra();
                TsetRefData=ks_java.getTsetRefData();
                VsetSpectra=ks_java.getVsetSpectra();
                VsetRefData=ks_java.getVsetRefData();
                break;
            case "SPXY":
                spxy_java.setRefData(refData);
                spxy_java.invokeMethod(data);
                spxy_java.parseResult();
                TsetSpectra=spxy_java.getTsetSpectra();
                TsetRefData=spxy_java.getTsetRefData();
                VsetSpectra=spxy_java.getVsetSpectra();
                VsetRefData=spxy_java.getVsetRefData();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedDataPartitionMethod);
        }
    }

    /**
     *  set variable selection method
     * @param methodName
     * @param o class object
     */
    public void setVariableSelectionMethod(String methodName, Object o){
        selectedVariableSelectionMethod=methodName;
        switch (selectedVariableSelectionMethod){
            case "None":
                break;
            case "UVE":
                uve_java=(UVE_JAVA)o;
                break;
            case "SA":
                sa_java=(SA_JAVA)o;
                break;
            case "SPA":
                spa_java=(SPA_JAVA) o;
                break;
        }
    }

    /**
     * invoke variable selection methods
     * @param data
     */
    public void variableSelection(double[][] data){
        switch (selectedVariableSelectionMethod){
            case "None":
                selectedTsetSpectra=TsetSpectra;
                selectedVsetSpectra=VsetSpectra;
                selectedWavelength=this.wavelength;
                break;
            case "UVE":
                uve_java.setTsetRefData(TsetRefData);
                uve_java.setVsetSpectra(VsetSpectra);
                uve_java.invokeMethod(data);
                uve_java.parseResult();
                selectedTsetSpectra=uve_java.getSelectedTsetSpectra();
                selectedVsetSpectra=uve_java.getSelectedVsetSpectra();
                selectedWavelength=DataProcessor.selectWavelength(uve_java.getIndex(),this.wavelength);
                break;
            case "SA":
                sa_java.setTsetRefData(TsetRefData);
                sa_java.setVsetSpectra(VsetSpectra);
                sa_java.invokeMethod(data);
                sa_java.parseResult();
                selectedTsetSpectra=sa_java.getSelectedTsetSpectra();
                selectedVsetSpectra=sa_java.getSelectedVsetSpectra();
                selectedWavelength=DataProcessor.selectWavelength(sa_java.getIndex(),this.wavelength);
                break;
            case "SPA":
                spa_java.setTsetY(TsetRefData);
                spa_java.setVsetX(VsetSpectra);
                spa_java.setVsetY(VsetRefData);
                spa_java.invokeMethod(data);
                spa_java.parseResult();
                selectedTsetSpectra=spa_java.getSelectedTsetSpectra();
                selectedVsetSpectra=spa_java.getSelectedVsetSpectra();
                selectedWavelength=DataProcessor.selectWavelength(spa_java.getIndex(),this.wavelength);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedVariableSelectionMethod);
        }
    }

    /**
     *  set calibration method
     * @param methodName
     * @param o class object
     */
    public void setCalibrationMethod(String methodName, Object o){
        selectedCalibrationMethod=methodName;
        switch (selectedCalibrationMethod){
            case "PLSR":
                plsr_java=(PLSR_JAVA) o;
                break;
            case "PCR":
                break;
            case "MLR":
                break;
        }
    }

    /**
     * invoke calibration methods
     * @param data
     */
    public void calibration(double[][] data){
        switch (selectedCalibrationMethod){
            case "PLSR":
                plsr_java.setTsetY(TsetRefData);
                plsr_java.setVsetX(selectedVsetSpectra);
                plsr_java.setVsetY(VsetRefData);
                plsr_java.invokeMethod(selectedTsetSpectra);
                plsr_java.parseResult();
                RMSEP=plsr_java.getRMSEP();
                R2=plsr_java.getR2();
                break;
            case "PCR":
                break;
            case "MLR":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedCalibrationMethod);
        }
    }

    public void setInput(double[][] spectra,double[][] wavelength,double[][] refData){
        this.spectra=spectra;
        this.wavelength=wavelength;
        this.refData=refData;

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

}
