package methodsLibrary;

import MSC_MATLAB.MSCMATLAB;
import SNV_MATLAB.SNVMATLAB;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class MSC_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray RefSpectrum;
    private double[][] spectrabyMSC;


    public void setRefSpectrum(double[][] refSpectrum) {
       RefSpectrum= DataProcessor.doubleArrayToMatrix(refSpectrum);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix=DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            MSCMATLAB mscmatlab=new MSCMATLAB();
            //the first integer parameter means how many objects will return in result
            result=mscmatlab.MSC(1, matrix,RefSpectrum);
        } catch (MWException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void parseResult() {
        spectrabyMSC=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return spectrabyMSC;
    }
}
