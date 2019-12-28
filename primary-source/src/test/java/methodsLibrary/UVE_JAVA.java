package methodsLibrary;

import SA_MATLAB.SAMATLAB;
import UVE_MATLAB.UVEMATLAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class UVE_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray VsetX;
    private MWNumericArray TsetY;
    private int componentNo;
    private double[][] index;
    private double[][] selectedTsetSpectra;
    private double[][] selectedVsetSpectra;

    public void setParameters(int componentNo){
        this.componentNo=componentNo;
    }

    public void setVsetSpectra(double[][] VsetSpectra){
        VsetX= DataProcessor.doubleArrayToMatrix(VsetSpectra);
    }

    public void setTsetRefData(double[][] TsetRefData){
        TsetY= DataProcessor.doubleArrayToMatrix(TsetRefData);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix=DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            UVEMATLAB uvematlab=new UVEMATLAB();
            //the first integer parameter means how many objects will return in result
            result=uvematlab.UVE(4, matrix,TsetY,VsetX,componentNo,1);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        index=(double[][]) ((MWNumericArray)result[1]).toDoubleArray();
        selectedTsetSpectra=(double[][]) ((MWNumericArray)result[2]).toDoubleArray();
        selectedVsetSpectra=(double[][]) ((MWNumericArray)result[3]).toDoubleArray();
    }

    @Override
    public double[][] getResult() {
        return new double[0][];
    }

    public double[][] getSelectedTsetSpectra(){
        return  selectedTsetSpectra;
    }

    public double[][] getSelectedVsetSpectra(){
        return selectedVsetSpectra;
    }

    public double[][] getIndex(){
        return index;
    }

}
