package dataProcessor;

import methodsLibrary.SNV_JAVA;

import java.math.BigDecimal;

public class PreProcessor {

    /**
     * Convert Light Intensity (LI) into Absorbance (A)
     * Reflectance (R) = (SLI-BLI)/(RLI-BLI)*100%
     * A = -log10(R)
     * @param RLI Reference Light Intensity
     * @param BLI Background Light Intensity
     * @param SLI Sample Light Intensity
     */
    public double[] computeAbsorbance(double[] RLI,double[] BLI,double[] SLI){
        double[] a = new double[SLI.length];    //SLI-BLI
        double[] b = new double[SLI.length];    //RLI-BLI
        double[] R = new double[SLI.length];    //Reflectance
        double[] A = new double[SLI.length];    //Absorbance
        for(int i=0;i<SLI.length;i++){
            a[i]=SLI[i]-BLI[i];
            b[i]=RLI[i]-BLI[i];
            R[i]=a[i]/b[i];
            A[i]=-Math.log10(R[i]);
        }

        return A;
    }

    /**
     * Round half up data keeping n digits after the decimal point
     * @param data
     * @param n digits after the decimal point
     * @return
     */
    public double[] roundData(double[] data, int n){
        double[] d=new double[data.length];
        for(int i=0;i<data.length;i++){
            BigDecimal b = new BigDecimal(data[i]);
            d[i]=b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        return d;
    }

    /**
     * invoke preprocess methods
     * @param methodName
     * @param data
     */
    public double[][] preProcess(String methodName,double[][] data){
        double[][] spectraAfterPreProcess;
        switch (methodName){
            case "SNV":
                SNV_JAVA snv_java=new SNV_JAVA(data);
                snv_java.invokeMethod();
                snv_java.parseResult();
                spectraAfterPreProcess=snv_java.getResult();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + methodName);
        }
        return spectraAfterPreProcess;
    }


}
