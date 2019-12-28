package methodsLibrary;

import SG_MATLAB.SGMATLAB;
import SPXY_MATLAB.SPXYMATLAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class SPXY_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray RefData;
    private int NoofsamplesForTset;
    private double[][] TsetSpectra;
    private double[][] VsetSpectra;
    private double[][] TsetRefData;
    private double[][] VsetRefData;

    public void setParameters(int NoofsamplesForTset){
        this.NoofsamplesForTset=NoofsamplesForTset;
    }

    public void setRefData(double[][] refData){
        RefData= DataProcessor.doubleArrayToMatrix(refData);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix=DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            SPXYMATLAB spxymatlab=new SPXYMATLAB();
            //the first integer parameter means how many objects will return in result
            result=spxymatlab.SPXY(6, matrix,RefData,NoofsamplesForTset);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        TsetSpectra=(double[][]) ((MWNumericArray)result[2]).toDoubleArray();
        TsetRefData=(double[][]) ((MWNumericArray)result[3]).toDoubleArray();
        VsetSpectra=(double[][]) ((MWNumericArray)result[4]).toDoubleArray();
        VsetRefData=(double[][]) ((MWNumericArray)result[5]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return new double[0][];
    }

    public double[][] getTsetSpectra(){
        return TsetSpectra;
    }

    public double[][] getVsetSpectra(){
        return VsetSpectra;
    }

    public double[][] getTsetRefData(){
        return TsetRefData;
    }

    public double[][] getVsetRefData(){
        return VsetRefData;
    }
}
