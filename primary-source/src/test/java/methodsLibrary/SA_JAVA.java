package methodsLibrary;

import SA_MATLAB.SAMATLAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;


public class SA_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray VsetX;
    private MWNumericArray TsetY;
    private int componentNo;
    private double initialE;
    private double coolingRatio;
    private double initialT;
    private double stopT;
    private double[][] index;
    private double[][] selectedTsetSpectra;
    private double[][] selectedVsetSpectra;


    public void setParameters(int componentNo,double initialE,double coolingRatio,double initialT,double stopT){
        this.componentNo=componentNo;
        this.initialE=initialE;
        this.coolingRatio=coolingRatio;
        this.initialT=initialT;
        this.stopT=stopT;
    }

    public void setVsetSpectra(double[][] VsetSpectra){
        VsetX=DataProcessor.doubleArrayToMatrix(VsetSpectra);
    }

    public void setTsetRefData(double[][] TsetRefData){
        TsetY= DataProcessor.doubleArrayToMatrix(TsetRefData);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix=DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            SAMATLAB samatlab=new SAMATLAB();
            //the first integer parameter means how many objects will return in result
            result=samatlab.SA(5, matrix,TsetY,VsetX,componentNo,initialE,coolingRatio,initialT,stopT);
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResult() {
        index=(double[][]) ((MWNumericArray)result[0]).toDoubleArray();
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
