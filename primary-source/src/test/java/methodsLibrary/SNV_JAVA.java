package methodsLibrary;


import SNV_MATLAB.SNVMATLAB;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class SNV_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private double[][] spectrabySNV;


    @Override
    public void invokeMethod(double[][] data) {
        matrix= DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            SNVMATLAB snvmatlab=new SNVMATLAB();
            //the first integer parameter means how many objects will return in result
            result=snvmatlab.SNV(1, matrix);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        spectrabySNV=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return spectrabySNV;
    }


}
