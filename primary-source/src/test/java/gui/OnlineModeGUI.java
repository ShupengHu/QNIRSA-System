package gui;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.steema.teechart.TChart;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.Series;
import dataManager.DataProcessor;
import dataManager.ExcelManager;
import excelTemplates.Rice;
import excelTemplates.Urea;
import methodsLibrary.MSC_JAVA;
import methodsLibrary.Predict_JAVA;
import methodsLibrary.SG_JAVA;
import methodsLibrary.SNV_JAVA;
import spectrometers.AOTF;
import spectrometers.MPA;
import spectrometers.NIRQuest;
import spectrometers.OmniDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class OnlineModeGUI extends JFrame {
    //class objects
    private OnlineModeGUI onlineModeGUI;
    private AOTF aotf;
    private MPA mpa;
    private NIRQuest nirQuest;
    private OmniDriver omniDriver;
    private SNV_JAVA snv_java;
    private MSC_JAVA msc_java;
    private SG_JAVA sg_java;
    private Predict_JAVA predict_java;

    //parameters
    private String selectedSpectrometer="";
    private double[] wavelengths;
    private double[] spectrum;
    private String selectedPreprocessMethod;
    private double[][] spectraAfterPreProcess;
    private double[][] model;
    private double[][] predictions;
    private int Noofproperty=1;
    private String sampleCategory;
    private ArrayList<Double> list1=new ArrayList<Double>();
    private ArrayList<Double> list2=new ArrayList<Double>();
    private ArrayList<Double> list3=new ArrayList<Double>();
    private ArrayList<Double> list4=new ArrayList<Double>();
    private ArrayList<Double> list5=new ArrayList<Double>();
    private ArrayList<Double> list6=new ArrayList<Double>();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String dateTime=df.format(new Date());
	private WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
    boolean isStopped=false;

    //GUI components
    private JPanel contentPane;
    private TChart spectrumChart1;
    private TChart spectrumChart2;
    private TChart propertyChart1;
    private TChart propertyChart2;
    private TChart propertyChart3;
    private TChart propertyChart4;
    private TChart propertyChart5;
    private TChart propertyChart6;

    private JButton SCMButton;
    private JButton spectrometerButton;
    private JButton preprocessButton;
    private JButton modelButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton autoRunButton;


    public OnlineModeGUI() throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
    }


    public void init(){
        //set size of GUI
        setTitle("Online Mode");
        setSize(1900,1000);
        setResizable(false);

        //---------set GUI elements-------//
        contentPane= (JPanel) getContentPane();
        contentPane.setLayout(null);
        contentPane.setSize(getMaximumSize());

        //--------set chart----------//
        //set original spectrum chart
        spectrumChart1=new TChart();
        spectrumChart1.setGraphics3D(null);
        spectrumChart1.getLegend().setVisible(false);
        spectrumChart1.setBounds(new Rectangle(0, 80, 900, 500));
        Series realS1=new Line();
        spectrumChart1.removeAllSeries();
        spectrumChart1.addSeries(realS1);
        spectrumChart1.getAspect().setView3D(false);
        spectrumChart1.getChart().getTitle().setText("Real-Time Original Spectrum");
        spectrumChart1.getChart().getTitle().getFont().setSize(20);
        spectrumChart1.getChart().getTitle().getFont().setColor(Color.blue);
        spectrumChart1.getAxes().getLeft().getTitle().setText("Absorbance");
        spectrumChart1.getAxes().getLeft().getTitle().getFont().setSize(20);
        spectrumChart1.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        spectrumChart1.getAxes().getBottom().getTitle().setText("Wavelength");
        spectrumChart1.getAxes().getBottom().getTitle().getFont().setSize(20);
        spectrumChart1.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);
        //set pre-processed spectrum
        spectrumChart2=new TChart();
        spectrumChart2.setGraphics3D(null);
        spectrumChart2.getLegend().setVisible(false);
        spectrumChart2.setBounds(new Rectangle(950, 80, 900, 500));
        Series realS2=new Line();
        spectrumChart2.removeAllSeries();
        spectrumChart2.addSeries(realS2);
        spectrumChart2.getAspect().setView3D(false);
        spectrumChart2.getChart().getTitle().setText("Real-Time Pre-processed Spectrum");
        spectrumChart2.getChart().getTitle().getFont().setSize(20);
        spectrumChart2.getChart().getTitle().getFont().setColor(Color.blue);
        spectrumChart2.getAxes().getLeft().getTitle().setText("Absorbance");
        spectrumChart2.getAxes().getLeft().getTitle().getFont().setSize(20);
        spectrumChart2.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        spectrumChart2.getAxes().getBottom().getTitle().setText("Wavelength");
        spectrumChart2.getAxes().getBottom().getTitle().getFont().setSize(20);
        spectrumChart2.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);
        //set predictions chart
        propertyChart1=new TChart();
        propertyChart1.setGraphics3D(null);
        propertyChart1.getLegend().setVisible(false);
        propertyChart1.setBounds(new Rectangle(0, 590, 600, 170));
        Series propertySerie1=new Line();
        propertyChart1.removeAllSeries();
        propertyChart1.addSeries(propertySerie1);
        propertyChart1.getAspect().setView3D(false);
        propertyChart1.getChart().getTitle().setText("Real-Time Sample Property");
        propertyChart1.getChart().getTitle().getFont().setSize(20);
        propertyChart1.getChart().getTitle().getFont().setColor(Color.blue);
        propertyChart1.getAxes().getLeft().getTitle().getFont().setSize(20);
        propertyChart1.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        propertyChart1.getAxes().getBottom().getTitle().setText("Number of Samples");
        propertyChart1.getAxes().getBottom().getTitle().getFont().setSize(20);
        propertyChart1.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        propertyChart2=new TChart();
        propertyChart2.setGraphics3D(null);
        propertyChart2.getLegend().setVisible(false);
        propertyChart2.setBounds(new Rectangle(0, 770, 600, 170));
        Series propertySerie2=new Line();
        propertyChart2.removeAllSeries();
        propertyChart2.addSeries(propertySerie2);
        propertyChart2.getAspect().setView3D(false);
        propertyChart2.getChart().getTitle().setText("Real-Time Sample Property");
        propertyChart2.getChart().getTitle().getFont().setSize(20);
        propertyChart2.getChart().getTitle().getFont().setColor(Color.blue);
        propertyChart2.getAxes().getLeft().getTitle().getFont().setSize(20);
        propertyChart2.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        propertyChart2.getAxes().getBottom().getTitle().setText("Number of Samples");
        propertyChart2.getAxes().getBottom().getTitle().getFont().setSize(20);
        propertyChart2.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        propertyChart3=new TChart();
        propertyChart3.setGraphics3D(null);
        propertyChart3.getLegend().setVisible(false);
        propertyChart3.setBounds(new Rectangle(620, 590, 600, 170));
        Series propertySerie3=new Line();
        propertyChart3.removeAllSeries();
        propertyChart3.addSeries(propertySerie3);
        propertyChart3.getAspect().setView3D(false);
        propertyChart3.getChart().getTitle().setText("Real-Time Sample Property");
        propertyChart3.getChart().getTitle().getFont().setSize(20);
        propertyChart3.getChart().getTitle().getFont().setColor(Color.blue);
        propertyChart3.getAxes().getLeft().getTitle().getFont().setSize(20);
        propertyChart3.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        propertyChart3.getAxes().getBottom().getTitle().setText("Number of Samples");
        propertyChart3.getAxes().getBottom().getTitle().getFont().setSize(20);
        propertyChart3.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        propertyChart4=new TChart();
        propertyChart4.setGraphics3D(null);
        propertyChart4.getLegend().setVisible(false);
        propertyChart4.setBounds(new Rectangle(620, 770, 600, 170));
        Series propertySerie4=new Line();
        propertyChart4.removeAllSeries();
        propertyChart4.addSeries(propertySerie4);
        propertyChart4.getAspect().setView3D(false);
        propertyChart4.getChart().getTitle().setText("Real-Time Sample Property");
        propertyChart4.getChart().getTitle().getFont().setSize(20);
        propertyChart4.getChart().getTitle().getFont().setColor(Color.blue);
        propertyChart4.getAxes().getLeft().getTitle().getFont().setSize(20);
        propertyChart4.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        propertyChart4.getAxes().getBottom().getTitle().setText("Number of Samples");
        propertyChart4.getAxes().getBottom().getTitle().getFont().setSize(20);
        propertyChart4.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        propertyChart5=new TChart();
        propertyChart5.setGraphics3D(null);
        propertyChart5.getLegend().setVisible(false);
        propertyChart5.setBounds(new Rectangle(1240, 590, 600, 170));
        Series propertySerie5=new Line();
        propertyChart5.removeAllSeries();
        propertyChart5.addSeries(propertySerie5);
        propertyChart5.getAspect().setView3D(false);
        propertyChart5.getChart().getTitle().setText("Real-Time Sample Property");
        propertyChart5.getChart().getTitle().getFont().setSize(20);
        propertyChart5.getChart().getTitle().getFont().setColor(Color.blue);
        propertyChart5.getAxes().getLeft().getTitle().getFont().setSize(20);
        propertyChart5.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        propertyChart5.getAxes().getBottom().getTitle().setText("Number of Samples");
        propertyChart5.getAxes().getBottom().getTitle().getFont().setSize(20);
        propertyChart5.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        propertyChart6=new TChart();
        propertyChart6.setGraphics3D(null);
        propertyChart6.getLegend().setVisible(false);
        propertyChart6.setBounds(new Rectangle(1240, 770, 600, 170));
        Series propertySerie6=new Line();
        propertyChart6.removeAllSeries();
        propertyChart6.addSeries(propertySerie6);
        propertyChart6.getAspect().setView3D(false);
        propertyChart6.getChart().getTitle().setText("Real-Time Sample Property");
        propertyChart6.getChart().getTitle().getFont().setSize(20);
        propertyChart6.getChart().getTitle().getFont().setColor(Color.blue);
        propertyChart6.getAxes().getLeft().getTitle().getFont().setSize(20);
        propertyChart6.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        propertyChart6.getAxes().getBottom().getTitle().setText("Number of Samples");
        propertyChart6.getAxes().getBottom().getTitle().getFont().setSize(20);
        propertyChart6.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        //-------set buttons------------//
        SCMButton=new JButton("SCM");
        SCMButton.setBounds(new Rectangle(0, 10, 70, 50));
        SCMButton.setFont(new Font("Arial", Font.PLAIN, 16));
        SCMButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SCMButtonMouseClicked(e);
            }
        });

        spectrometerButton=new JButton("Spectrometer");
        spectrometerButton.setBounds(new Rectangle(80, 10, 120, 50));
        spectrometerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        spectrometerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spectrometerButtonMouseClicked(e);
            }
        });

        preprocessButton=new JButton("Pre-Process");
        preprocessButton.setBounds(new Rectangle(210, 10, 130, 50));
        preprocessButton.setFont(new Font("Arial", Font.PLAIN, 14));
        preprocessButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                preprocessButtonMouseClicked(e);
            }
        });

        modelButton=new JButton("Model");
        modelButton.setBounds(new Rectangle(350, 10, 80, 50));
        modelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        modelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                modelButtonMouseClicked(e);
            }
        });

        runButton=new JButton("Run");
        runButton.setBounds(new Rectangle(440, 10, 70, 50));
        runButton.setFont(new Font("Arial", Font.PLAIN, 14));
        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                runButtonMouseClicked(e);
            }
        });

        autoRunButton=new JButton("Auto-Run");
        autoRunButton.setBounds(new Rectangle(520, 10, 100, 50));
        autoRunButton.setFont(new Font("Arial", Font.PLAIN, 14));
        autoRunButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                autoRunButtonMouseClicked(e);
            }
        });

        stopButton=new JButton("Stop");
        stopButton.setBounds(new Rectangle(630, 10, 70, 50));
        stopButton.setFont(new Font("Arial", Font.PLAIN, 14));
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stopButtonMouseClicked(e);
            }
        });

        //-------add components--------//
        contentPane.add(spectrumChart1);
        contentPane.add(spectrumChart2);
        contentPane.add(SCMButton);
        contentPane.add(spectrometerButton);
        contentPane.add(preprocessButton);
        contentPane.add(modelButton);
        contentPane.add(runButton);
        contentPane.add(autoRunButton);
        contentPane.add(stopButton);
    }

    /**
     * button for stop
     * @param e
     */
    private void stopButtonMouseClicked(MouseEvent e) {
        isStopped=true;
    }

    /**
     * button for auto run
     * @param e
     */
    private void autoRunButtonMouseClicked(final MouseEvent e) {
        final Timer timer=new Timer();
        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                runButtonMouseClicked(e);
                //
                if(isStopped==true){
                    timer.cancel();
                    isStopped=false;
                }
            }
        };
       timer.schedule(tt, 0, 30*1000);
    }

    /**
     * button for model
     * @param e
     */
    private void modelButtonMouseClicked(MouseEvent e) {
        ModelGUI modelGUI=new ModelGUI(onlineModeGUI);
        modelGUI.setVisible(true);
    }

    /**
     * button for pre-processing
     * @param e
     */
    private void preprocessButtonMouseClicked(MouseEvent e) {
        PreProcessGUI preProcessGUI=new PreProcessGUI(onlineModeGUI);
        preProcessGUI.setVisible(true);
    }

    /**
     * button for run
     * @param e
     */
    private void runButtonMouseClicked(MouseEvent e) {
        if(selectedSpectrometer.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Please Connect Spectrometer in Advance","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(selectedPreprocessMethod.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Please Select Pre-process Method in Advance","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(model==null){
            JOptionPane.showMessageDialog(this, "Please Import Model in Advance","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }

        //get spectral data from spectrometer
        getData();

        //draw real-time original spectrum
        spectrumChart1.removeAllSeries();
        spectrumChart1.getSeries(0).add(wavelengths,spectrum);
        spectrumChart1.repaint();

        //pre-processing spectral data
        preProcess(DataProcessor.oneDToTwoDArray(spectrum));

        //draw real-time pre-processed spectrum
        spectrumChart2.removeAllSeries();
        spectrumChart2.getSeries(0).add(wavelengths,spectraAfterPreProcess[0]);
        spectrumChart2.repaint();

        //Predict sample propertied based on model
        predict_java.invokeMethod(spectraAfterPreProcess);
        predict_java.parseResult();
        predictions=predict_java.getResult();

        //draw real-time predictions on chart
        drawPredictions();

        //store predictions into Excel document
        dateTime=dateTime.replaceAll(":", "-");
        switch (sampleCategory){
            case "Rice":
                ExcelManager.repeatWriteExcel("D:\\"+dateTime+" predictions.xlsx",DataProcessor.doubleArrayToList2(predictions,"Rice"), Rice.class,writeSheet);
                break;
            case "Urea":
                ExcelManager.repeatWriteExcel("D:\\"+dateTime+" predictions.xlsx",DataProcessor.doubleArrayToList2(predictions,"Urea"), Urea.class,writeSheet);
                break;
        }
    }

    /**
     * draw predictions on chart
     */
    public void drawPredictions(){
        switch (Noofproperty){
            case 1:
                list1.add(predictions[0][0]);
                propertyChart1.removeAllSeries();
                propertyChart1.getSeries(0).add(getX_axis(list1.size()),list1);
                propertyChart1.repaint();
                break;
            case 2:
                list1.add(predictions[0][0]);
                propertyChart1.removeAllSeries();
                propertyChart1.getSeries(0).add(getX_axis(list1.size()), list1);
                propertyChart1.repaint();
                list2.add(predictions[0][1]);
                propertyChart2.removeAllSeries();
                propertyChart2.getSeries(0).add(getX_axis(list2.size()), list2);
                propertyChart2.repaint();
                break;
            case 3:
                list1.add(predictions[0][0]);
                propertyChart1.removeAllSeries();
                propertyChart1.getSeries(0).add(getX_axis(list1.size()), list1);
                propertyChart1.repaint();
                list2.add(predictions[0][1]);
                propertyChart2.removeAllSeries();
                propertyChart2.getSeries(0).add(getX_axis(list2.size()), list2);
                propertyChart2.repaint();
                list3.add(predictions[0][2]);
                propertyChart3.removeAllSeries();
                propertyChart3.getSeries(0).add(getX_axis(list3.size()), list3);
                propertyChart3.repaint();
                break;
            case 4:
                list1.add(predictions[0][0]);
                propertyChart1.removeAllSeries();
                propertyChart1.getSeries(0).add(getX_axis(list1.size()),list1);
                propertyChart1.repaint();
                list2.add(predictions[0][1]);
                propertyChart2.removeAllSeries();
                propertyChart2.getSeries(0).add(getX_axis(list2.size()),  list2);
                propertyChart2.repaint();
                list3.add(predictions[0][2]);
                propertyChart3.removeAllSeries();
                propertyChart3.getSeries(0).add(getX_axis(list3.size()), list3);
                propertyChart3.repaint();
                list4.add(predictions[0][3]);
                propertyChart4.removeAllSeries();
                propertyChart4.getSeries(0).add(getX_axis(list4.size()), list4);
                propertyChart4.repaint();
                break;
            case 5:
                list1.add(predictions[0][0]);
                propertyChart1.removeAllSeries();
                propertyChart1.getSeries(0).add(getX_axis(list1.size()), list1);
                propertyChart1.repaint();
                list2.add(predictions[0][1]);
                propertyChart2.removeAllSeries();
                propertyChart2.getSeries(0).add(getX_axis(list2.size()), list2);
                propertyChart2.repaint();
                list3.add(predictions[0][2]);
                propertyChart3.removeAllSeries();
                propertyChart3.getSeries(0).add(getX_axis(list3.size()), list3);
                propertyChart3.repaint();
                list4.add(predictions[0][3]);
                propertyChart4.removeAllSeries();
                propertyChart4.getSeries(0).add(getX_axis(list4.size()), list4);
                propertyChart4.repaint();
                list5.add(predictions[0][4]);
                propertyChart5.removeAllSeries();
                propertyChart5.getSeries(0).add(getX_axis(list5.size()), list5);
                propertyChart5.repaint();
                break;
            case 6:
                list1.add(predictions[0][0]);
                propertyChart1.removeAllSeries();
                propertyChart1.getSeries(0).add(getX_axis(list1.size()), list1);
                propertyChart1.repaint();
                list2.add(predictions[0][1]);
                propertyChart2.removeAllSeries();
                propertyChart2.getSeries(0).add(getX_axis(list2.size()),  list2);
                propertyChart2.repaint();
                list3.add(predictions[0][2]);
                propertyChart3.removeAllSeries();
                propertyChart3.getSeries(0).add(getX_axis(list3.size()), list3);
                propertyChart3.repaint();
                list4.add(predictions[0][3]);
                propertyChart4.removeAllSeries();
                propertyChart4.getSeries(0).add(getX_axis(list4.size()),  list4);
                propertyChart4.repaint();
                list5.add(predictions[0][4]);
                propertyChart5.removeAllSeries();
                propertyChart5.getSeries(0).add(getX_axis(list5.size()),  list5);
                propertyChart5.repaint();
                list6.add(predictions[0][5]);
                propertyChart6.removeAllSeries();
                propertyChart6.getSeries(0).add(getX_axis(list6.size()),  list6);
                propertyChart6.repaint();
                break;
        }
    }

    /**
     * get the X axis for predictions chart
     * @param X_length length of X axis
     * @return
     */
    public ArrayList<Double> getX_axis(int X_length){
        ArrayList<Double> X_axis=new ArrayList<>();
        for(double i=0;i<(double)X_length;i++){
            X_axis.add(i+1);
        }
        return X_axis;
    }

    /**
     * get spectral data from spectrometer
     */
    public void getData(){
        switch (selectedSpectrometer){
            case "OmniDriver":
                omniDriver.runSpec();
                wavelengths=omniDriver.getWaveLengths();
                spectrum=omniDriver.getSpectrum();
                break;
            case "AOTF":
                aotf.runSpec();
                break;
            case "NIRQuest":
                nirQuest.runSpec();
                break;
            case "MPA":
                mpa.runSpec();
                break;
        }
    }

    /**
     * button for spectrometer
     * @param e
     */
    private void spectrometerButtonMouseClicked(MouseEvent e) {
        SpectrometerGUI spectrometerGUI=new SpectrometerGUI(onlineModeGUI);
        spectrometerGUI.setVisible(true);
    }

    /**
     * button for SCM
     * @param e
     */
    private void SCMButtonMouseClicked(MouseEvent e) {
        SCMGUI scmgui=new SCMGUI();
        scmgui.setVisible(true);
    }

    /**
     * set OnlineModeGUI class object
     * @param onlineModeGUI
     */
    public void setOnlineModeGUI(OnlineModeGUI onlineModeGUI) {
       this.onlineModeGUI= onlineModeGUI;
    }

    /**
     * set spectrometer class object
     * @param spectrometerName
     * @param o class object
     */
    public void setSpectrometer(String spectrometerName,Object o){
        switch (spectrometerName){
            case "AOTF":
                aotf= (AOTF) o;
                selectedSpectrometer="AOTF";
                break;
            case "MPA":
                mpa= (MPA) o;
                selectedSpectrometer="MPA";
                break;
            case "NIRQuest":
                nirQuest= (NIRQuest) o;
                selectedSpectrometer="NIRQuest";
                break;
            case "OmniDriver":
                omniDriver= (OmniDriver) o;
                selectedSpectrometer="OmniDriver";
                break;
        }
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
                sg_java.setWavelength(DataProcessor.oneDToTwoDArray(wavelengths));
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
     * set model
     * @param model
     */
    public void setModel(double[][] model){
        this.model=model;
        predict_java=new Predict_JAVA();
        predict_java.setB(this.model);
    }

    /**
     * set sample category
     */
    public void setSampleCategory(String sampleCategory){
        this.sampleCategory=sampleCategory;
    }
    /**
     * set chart for displaying predicitons of properties
     * @param propertyName
     */
    public void setProperty(String propertyName){
        switch (Noofproperty){
            case 1:
                propertyChart1.getAxes().getLeft().getTitle().setText(propertyName);
                contentPane.add(propertyChart1);
                repaint();
                break;
            case 2:
                propertyChart2.getAxes().getLeft().getTitle().setText(propertyName);
                contentPane.add(propertyChart2);
                repaint();
                break;
            case 3:
                propertyChart3.getAxes().getLeft().getTitle().setText(propertyName);
                contentPane.add(propertyChart3);
                repaint();
                break;
            case 4:
                propertyChart4.getAxes().getLeft().getTitle().setText(propertyName);
                contentPane.add(propertyChart4);
                repaint();
                break;
            case 5:
                propertyChart5.getAxes().getLeft().getTitle().setText(propertyName);
                contentPane.add(propertyChart5);
                repaint();
                break;
            case 6:
                propertyChart6.getAxes().getLeft().getTitle().setText(propertyName);
                contentPane.add(propertyChart6);
                repaint();
                break;
        }
        Noofproperty++;
    }

}
