package methodsLibrary;

import MSC_MATLAB.MSCMATLAB;
import SG_MATLAB.SGMATLAB;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

import javax.xml.crypto.Data;

public class SG_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray Wavelength;
    private double[][] spectrabySG;
    private int window_length;
    private int polynomial_order;
    private int derivative_order;

    public void setParameter(int window_length, int polynomial_order, int derivative_order){
        this.window_length=window_length;
        this.polynomial_order=polynomial_order;
        this.derivative_order=derivative_order;
    }

    public void  setWavelength(double[][] wavelength){
        Wavelength= DataProcessor.doubleArrayToMatrix(wavelength);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix= DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            SGMATLAB sgmatlab=new SGMATLAB();
            //the first integer parameter means how many objects will return in result
            result=sgmatlab.SG(1, matrix,Wavelength,derivative_order,polynomial_order,window_length);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        spectrabySG=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return spectrabySG;
    }
}
