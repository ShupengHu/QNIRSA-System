package gui;

import com.steema.teechart.TChart;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.Series;
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
import java.util.ArrayList;

public class OnlineModeGUI extends JFrame {
    //class objects
    private OnlineModeGUI onlineModeGUI;
    private AOTF aotf;
    private MPA mpa;
    private NIRQuest nirQuest;
    private OmniDriver omniDriver;

    //parameters
    private String selectedSpectrometer="";
    private double[] wavelengths;
    private double[] spectrum;

    //components
    private JPanel contentPane;
    private TChart spectrumChart;
    private TChart propertyChart1;
    private JButton SCMButton;
    private JButton spectrometerButton;
    private JButton preprocessButton;
    private JButton runButton;
    private JButton StopButton;
    private JButton autoRunButton;
    private JLabel propertyLable;
    private JTextField propertyText1;

    //曲线集
    private Series[] series=null;
    //用户输入参数
    //其他参数
    Connection con=null;
    String dateTime="";                                     //扫描实时时间
    int sampleNo=0;                                         //样品序号
    double[] B_LightIntensities=null;                       //背景光谱的光强
    double[] R_LightIntensities=null;                       //参考光谱的光强
    double[] S_LightIntensities=null;                       //样品光谱的光强
    double[] Wavelengths=null;                              //波长
    double[] reflectivity=null;                             //反射率
    double[] absorbance=null;                               //吸光度
    double[][] model_absorbance=null;                       //模型吸光度
    double[][] model_chemicalValue=null;                    //模型化学值
    double[] selectedAbsorbance=null;                       //被手动选择用于数据分析的光谱
    double[][] afterPreprocess=null;                        //经过预处理后的数据
    double[][] concentration=null;                          //经过多元校正处理后得到的浓度矩阵
    ArrayList<Double> biruetList=new ArrayList<>();         //经过多元校正处理后得到的缩二脲浓度
    ArrayList<Double> waterList=new ArrayList<>();          //经过多元校正处理后得到的水分浓度
    ArrayList<Double> carbamideList=new ArrayList<>();      //经过多元校正处理后得到的尿素浓度
    ArrayList<Double> X=new ArrayList<>();                  //浓度图通用横坐标
    boolean isRunned=false;                                 //判断光谱仪是否已经开始工作
    boolean isStoped=false;                                 //判断光谱仪是否已经停止工作
    boolean isAutoRun=false;                                //是否为自动运行状态
    File[] openFiles=null;                                  //选择打开的文件集


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
        //set spectrum chart
        spectrumChart=new TChart();
        spectrumChart.setGraphics3D(null);
        spectrumChart.getLegend().setVisible(false);
        spectrumChart.setBounds(new Rectangle(0, 80, 1000, 500));
        Series realS=new Line();
        spectrumChart.removeAllSeries();
        spectrumChart.addSeries(realS);
        spectrumChart.getAspect().setView3D(false);
        spectrumChart.getChart().getTitle().setText("Real-Time Spectrum");
        spectrumChart.getChart().getTitle().getFont().setSize(20);
        spectrumChart.getChart().getTitle().getFont().setColor(Color.blue);
        spectrumChart.getAxes().getLeft().getTitle().setText("Absorbance");
        spectrumChart.getAxes().getLeft().getTitle().getFont().setSize(20);
        spectrumChart.getAxes().getLeft().getTitle().getFont().setColor(Color.blue);
        spectrumChart.getAxes().getBottom().getTitle().setText("Wavelength");
        spectrumChart.getAxes().getBottom().getTitle().getFont().setSize(20);
        spectrumChart.getAxes().getBottom().getTitle().getFont().setColor(Color.blue);

        //set result chart

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

        runButton=new JButton("Run");
        runButton.setBounds(new Rectangle(340, 10, 70, 50));
        runButton.setFont(new Font("Arial", Font.PLAIN, 14));
        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                runButtonMouseClicked(e);
            }
        });

        //-------add components--------//
        contentPane.add(spectrumChart);
        contentPane.add(SCMButton);
        contentPane.add(spectrometerButton);
        contentPane.add(preprocessButton);


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

        //get spectral data from spectrometer
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

        //data pre-processing

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

    public static void main(String[] args) throws IOException {
        OnlineModeGUI o=new OnlineModeGUI();
        o.setVisible(true);
    }
}
