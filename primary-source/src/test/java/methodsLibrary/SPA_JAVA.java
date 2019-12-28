package methodsLibrary;

import SPA_MATLAB.SPAMTALAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class SPA_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray VsetX;
    private MWNumericArray TsetY;
    private MWNumericArray VsetY;
    private double[][] index;
    private double[][] selectedTsetSpectra;
    private double[][] selectedVsetSpectra;

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
            SPAMTALAB spamatlab=new SPAMTALAB();
            //the first integer parameter means how many objects will return in result
            result=spamatlab.SPA(3, matrix,TsetY,VsetX,VsetY);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        index=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
        selectedTsetSpectra=(double[][]) ((MWNumericArray)result[1]).toDoubleArray();
        selectedVsetSpectra=(double[][]) ((MWNumericArray)result[2]).toDoubleArray();
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
