package methodsLibrary;

import Predict_MATLAB.PredictMATLAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class Predict_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray B;
    private double[][] predictions;

    public void setB(double[][] model){
        B= DataProcessor.doubleArrayToMatrix(model);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix=DataProcessor.doubleArrayToMatrix(data);
        //invoke MATLAB method
        try {
            PredictMATLAB predictMATLAB=new PredictMATLAB();
            //the first integer parameter means how many objects will return in result
            result=predictMATLAB.Predict(1,B,matrix);
        } catch (MWException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void parseResult() {
        predictions=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return predictions;
    }
}
