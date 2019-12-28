package methodsLibrary;

import PLSR_MATLAB.PLSRMATLAB;
import SA_MATLAB.SAMATLAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class PLSR_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray TsetY;
    private MWNumericArray VsetX;
    private MWNumericArray VsetY;
    private double RMSEP;
    private double R2;

    private int PCNo;

    public void setParameters(int PCNo){
        this.PCNo=PCNo;
    }

    public void setTsetY(double[][] TsetRefData){
        TsetY= DataProcessor.doubleArrayToMatrix(TsetRefData);
    }

    public void setVsetX(double[][] VsetSpectra){
        VsetX= DataProcessor.doubleArrayToMatrix(VsetSpectra);
    }

    public void setVsetY(double[][] TsetRefData){
        VsetY=DataProcessor.doubleArrayToMatrix(TsetRefData);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix=DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            PLSRMATLAB plsrmatlab=new PLSRMATLAB();
            //the first integer parameter means how many objects will return in result
            result=plsrmatlab.PLSR(7, matrix,TsetY,VsetX,VsetY,PCNo);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        RMSEP=((MWNumericArray)result[4]).getDouble();
        R2=((MWNumericArray)result[5]).getDouble();
    }

    @Override
    public double[][] getResult() {
        return new double[0][];
    }

    public double getRMSEP(){
        return RMSEP;
    }

    public double getR2(){
        return R2;
    }

}
