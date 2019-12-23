package spectrometers;

import com.oceanoptics.omnidriver.api.wrapper.Wrapper;
import com.oceanoptics.omnidriver.features.thermoelectric.ThermoElectricWrapper;

import java.io.IOException;
import java.util.HashMap;

public class OmniDriver implements Spectrometer {
    private Wrapper wrapper=new Wrapper();
    private int spectrometer_index=0;
    private ThermoElectricWrapper tecController=wrapper.getFeatureControllerThermoElectric(spectrometer_index);
    private int spectrometer_quantity=1;
    private HashMap<String,String> specInfo=new HashMap<String, String>();

    private int time;
    private int smoothness;
    private int numberOfScansToAverage;
    private int mode;

    private double[] spectrum;
    private double[] waveLengths;


    /**
     * Test the connection of spectrometer
     */
    public boolean connectSpec() {
        boolean b;
        spectrometer_quantity=wrapper.openAllSpectrometers();
        if(spectrometer_quantity==-1||this.spectrometer_quantity==0) {
            SpecQuantityException();
            return false;
        }else return true;
    }

    /**
     * set input parameters for spectrometer
     * @param time
     * @param numberOfScansToAverage
     * @param smoothness
     * @param mode
     */
    public void setInput(int time,int numberOfScansToAverage,int smoothness, int mode) {
        this.time=time;
        this.smoothness=smoothness;
        this.numberOfScansToAverage=numberOfScansToAverage;
        this.mode=mode;
    }

    /**
     * Exception when no spectrometer connect
     */
    public void SpecQuantityException(){
        if (spectrometer_quantity == -1)
        {
            System.out.println("Exception message: " + this.wrapper.getLastException());
            System.out.println("Stack trace:\n" + this.wrapper.getLastExceptionStackTrace());
        }
        else if (spectrometer_quantity == 0)
        {
            System.out.println("No spectrometers were found. Exiting the application.");
        }
    }

    /**
     * get information of spectrometer
     */
    public HashMap<String,String> getSpecInfo() {
        specInfo.put("Name",wrapper.getName(spectrometer_index));
        specInfo.put("ID",wrapper.getSerialNumber(spectrometer_index));
        specInfo.put("Version",wrapper.getFirmwareVersion(spectrometer_index));

        return specInfo;
    }

    /**
     * set and configure spectrometer
     */
    public void setSpec(){
        /**
         * set parameters
         */
        //integral time:ms
        this.wrapper.setIntegrationTime(spectrometer_index, time);
        //smoothing degree
        this.wrapper.setBoxcarWidth(spectrometer_index,smoothness);
        //number of scans for a spectrum
        this.wrapper.setScansToAverage(spectrometer_index, numberOfScansToAverage);
        /**
         * other device setting
         */
        // disable electric dark correction
        this.wrapper.setCorrectForElectricalDark(spectrometer_index, 0);

        try {
            //open fan
            tecController.setFanEnable(true);
            // set operating temperature
            tecController.setDetectorSetPointCelsius(-25);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * set trigger modes
         * 0. Normal Mode
         * 1. External Software Trigger Mode
         * 2. External Synchronization Trigger Mode
         * 3. Hardware Trigger Mode
         * 4. Single-Shot Trigger Mode
         */
        wrapper.setExternalTriggerMode(spectrometer_index, mode);
    }

    /**
     * run spectrometer to get spectrum and wavelength
     */
    public void runSpec() {
        spectrum=wrapper.getSpectrum(spectrometer_index);
        waveLengths=wrapper.getWavelengths(spectrometer_index);
    }

    public void closeSpec() {
        wrapper.closeAllSpectrometers();
    }

    public double[] getSpectrum() {
        return spectrum;
    }

    public double[] getWaveLengths() {
        return waveLengths;
    }

}
