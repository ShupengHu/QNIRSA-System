package methodsLibrary;

import GA_MATLAB.GAMATLAB;
import SA_MATLAB.SAMATLAB;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import dataManager.DataProcessor;

public class GA_JAVA implements Method{
    private Object[] result;
    private MWNumericArray matrix;
    private MWNumericArray VsetX;
    private MWNumericArray TsetY;
    private int componentNo;
    private double chromosomeNo;
    private double maxGenerations;
    private double p_crossover;
    private double p_mutation;
    private double[][] index;
    private double[][] selectedTsetSpectra;
    private double[][] selectedVsetSpectra;

    public void setParameters(int componentNo,double chromosomeNo,double maxGenerations,double p_crossover,double p_mutation){
        this.componentNo=componentNo;
        this.chromosomeNo=chromosomeNo;
        this.maxGenerations=maxGenerations;
        this.p_crossover=p_crossover;
        this.p_mutation=p_mutation;
    }

    public void setVsetSpectra(double[][] VsetSpectra){
        VsetX=DataProcessor.doubleArrayToMatrix(VsetSpectra);
    }

    public void setTsetRefData(double[][] TsetRefData){
        TsetY= DataProcessor.doubleArrayToMatrix(TsetRefData);
    }

    @Override
    public void invokeMethod(double[][] data) {
        matrix= DataProcessor.doubleArrayToMatrix(data);

        //invoke MATLAB method
        try {
            GAMATLAB gamatlab=new GAMATLAB();
            //the first integer parameter means how many objects will return in result
            result=gamatlab.GA(4, matrix,TsetY,VsetX,componentNo,chromosomeNo,maxGenerations,p_crossover,p_mutation);
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
