package methodsLibrary;

import IPLS_MATLAB.IPLSMATLAB;
import UVE_MATLAB.UVEMATLAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class IPLS_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray TsetY;
    private MWNumericArray VsetX;
    private int componentNo;
    private double[][] index;
    private double[][] selectedTsetSpectra;
    private double[][] selectedVsetSpectra;

    public void setParameters(int componentNo){
        this.componentNo=componentNo;
    }

    public void setTsetRefData(double[][] TsetRefData){
        TsetY= DataProcessor.doubleArrayToMatrix(TsetRefData);
    }

    public void setVsetSpectra(double[][] VsetSpectra){
        VsetX= DataProcessor.doubleArrayToMatrix(VsetSpectra);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix=DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            IPLSMATLAB iplsmatlab=new IPLSMATLAB();
            //the first integer parameter means how many objects will return in result
            result=iplsmatlab.iPLS(7, matrix,TsetY,VsetX,componentNo,-1);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        index=(double[][]) ((MWNumericArray)result[4]).toDoubleArray();
        selectedTsetSpectra=(double[][]) ((MWNumericArray)result[5]).toDoubleArray();
        selectedVsetSpectra=(double[][]) ((MWNumericArray)result[6]).toDoubleArray();
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
