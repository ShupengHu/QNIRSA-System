package dataManager;

import excelTemplates.Model;
import excelTemplates.Rice;
import excelTemplates.Urea;
import excelTemplates.Wavelength;
import methodsLibrary.SNV_JAVA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {

    /**
     * Convert Light Intensity (LI) into Absorbance (A)
     * Reflectance (R) = (SLI-BLI)/(RLI-BLI)*100%
     * A = -log10(R)
     * @param RLI Reference Light Intensity
     * @param BLI Background Light Intensity
     * @param SLI Sample Light Intensity
     */
    public static double[] computeAbsorbance(double[] RLI,double[] BLI,double[] SLI){
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
    public static double[][] roundData(double[][] data, int n){
        double[][] d=new double[data.length][data[0].length];
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data[0].length;j++) {
                BigDecimal b = new BigDecimal(data[i][j]);
                d[i][j] = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }

        return d;
    }

    /**
     * convert list<double[]> into double array
     * @return
     */
    public static double[][] listToDoubleArray1(List<double[]> data){
        double[][] d =new double[data.size()][data.get(0).length];
        for (int i=0; i< data.size();i++){
            d[i]=data.get(i);
        }
        return d;
    }

    /**
     * convert list<Object> into double array
     * @return
     */
    public static double[][] listToDoubleArray2(List<Object> data,String templateName){
        double[][] d = null;
        switch (templateName){
            case "Wavelength":
                d=new double[data.size()][1];
                for (int i=0; i< data.size();i++){
                    d[i][0]= ((Wavelength)data.get(i)).getWavelength();
        }
                break;
            case "Model":
                d=new double[data.size()][1];
                for (int i=0; i< data.size();i++){
                    d[i][0]= ((Model)data.get(i)).getCoefficients();
                }
                break;
            case "Rice":
                d=new double[data.size()][1];
                for (int i=0; i< data.size();i++){
                    d[i][0]= ((Rice)data.get(i)).getProtein();
                }
            case "Urea":
                d=new double[data.size()][2];
                for (int i=0; i< data.size();i++){
                    d[i][0]= ((Urea)data.get(i)).getBiuret();
                    d[i][1]= ((Urea)data.get(i)).getWater();
                }
            default:
                throw new IllegalStateException("Unexpected value: " + templateName);
        }
        return d;
    }

    /**
     * convert double array to list<double[]>
     * @param data
     * @return
     */
    public static List<double[]> doubleArrayToList1(double[][] data){
        List<double[]> list=new ArrayList<double[]>();
        for (int i=0; i< data.length;i++){
            list.add(data[i]);
        }
        return  list;
    }

    /**
     * convert double array to list<Object>
     * @param data
     * @return
     */
    public static List<Object> doubleArrayToList2(double[][] data,String templateName){
        List<Object> list=null;
        switch (templateName){
            case "Wavelength":
                list = new ArrayList<Object>();
                for(int i=0;i<data.length;i++){
                    Wavelength wavelength=new Wavelength();
                    wavelength.setWavelength(data[i][0]);
                    list.add(wavelength);
                }
                break;
            case "Model":
                list = new ArrayList<Object>();
                for(int i=0;i<data.length;i++){
                    Model model=new Model();
                    model.setCoefficients(data[i][0]);
                    list.add(model);
                }
                break;
            case "Rice":
                list = new ArrayList<Object>();
                for(int i=0;i<data.length;i++){
                    Rice rice=new Rice();
                    rice.setProtein(data[i][0]);
                    list.add(rice);
                }
                break;
            case "Urea":
                list = new ArrayList<Object>();
                for(int i=0;i<data.length;i++){
                    Urea urea=new Urea();
                    urea.setBiuret(data[i][0]);
                    urea.setWater(data[i][1]);
                    list.add(urea);
                }
            default:
                throw new IllegalStateException("Unexpected value: " + templateName);
        }
        return  list;
    }

    /**
     * convert one dimensional double array into two dimensional double array
     * @param data
     * @return
     */
    public static double[][] oneDToTwoDArray(double[] data){
        double[][] d=new double[1][data.length];
        d[0]=data;
        return d;
    }

}
